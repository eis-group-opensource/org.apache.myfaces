/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.*;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;
import javax.faces.model.*;
import javax.servlet.jsp.jstl.sql.Result;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFacet;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * Represents an abstraction of a component which has multiple "rows" of data.
 * <p>
 * The children of this component are expected to be UIColumn components.
 * <p>
 * Note that the same set of child components are reused to implement each row of the table in turn during
 * such phases as apply-request-values and render-response. Altering any of the members of these components
 * therefore affects the attribute for every row, except for the following members:
 * <ul>
 * <li>submittedValue
 * <li>value (where no EL binding is used)
 * <li>valid
 * </ul>
 * <p>
 * This reuse of the child components also means that it is not possible to save a reference to a component
 * during table processing, then access it later and expect it to still represent the same row of the table.
 * <h1>
 * Implementation Notes
 * </h1>
 * <p>
 * Each of the UIColumn children of this component has a few component children of its own to render the contents
 * of the table cell. However there can be a very large number of rows in a table, so it isn't efficient for the
 * UIColumn and all its child objects to be duplicated for each row in the table. Instead the "flyweight" pattern
 * is used where a serialized state is held for each row. When setRowIndex is invoked, the UIColumn objects and
 * their children serialize their current state then reinitialise themselves from the appropriate saved state.
 * This allows a single set of real objects to represent multiple objects which have the same types but potentially
 * different internal state. When a row is selected for the first time, its state is set to a clean "initial" state.
 * Transient components (including any read-only component) do not save their state; they are just reinitialised as
 * required. The state saved/restored when changing rows is not the complete component state, just the fields that
 * are expected to vary between rows: "submittedValue", "value", "isValid".
 * </p>
 * <p>
 * Note that a table is a "naming container", so that components within the table have their ids prefixed with the
 * id of the table. Actually, when setRowIndex has been called on a table with id of "zzz" the table pretends to
 * its children that its ID is "zzz_n" where n is the row index. This means that renderers for child components which
 * call component.getClientId automatically get ids of form "zzz_n:childId" thus ensuring that components in
 * different rows of the table get different ids.
 * </p>
 * <p>
 * When decoding a submitted page, this class iterates over all its possible rowIndex values, restoring the
 * appropriate serialized row state then calling processDecodes on the child components. Because the child
 * components (or their renderers) use getClientId to get the request key to look for parameter data, and because
 * this object pretends to have a different id per row ("zzz_n") a single child component can decode data from each
 * table row in turn without being aware that it is within a table. The table's data model is updated before each
 * call to child.processDecodes, so the child decode method can assume that the data model's rowData points to the
 * model object associated with the row currently being decoded. Exactly the same process applies for the later
 * validation and updateModel phases.
 * </p>
 * <p>
 * When the data model for the table is bound to a backing bean property, and no validation errors have occured
 * during processing of a postback, the data model is refetched at the start of the rendering phase (ie after the
 * update model phase) so that the contents of the data model can be changed as a result of the latest form
 * submission. Because the saved row state must correspond to the elements within the data model, the row state
 * must be discarded whenever a new data model is fetched; not doing this would cause all sorts of inconsistency
 * issues. This does imply that changing the state of any of the members "submittedValue", "value" or "valid" of
 * a component within the table during the invokeApplication phase has no effect on the rendering of the table.
 * When a validation error has occurred, a new DataModel is <i>not</i> fetched, and the saved state of the child
 * components is <i>not</i> discarded.
 * </p>
 * see Javadoc of the <a href="http://java.sun.com/j2ee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 * for more information.
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 1203044 $ $Date: 2011-11-17 06:22:07 +0200 (Thu, 17 Nov 2011) $
 */
@JSFComponent(defaultRendererType = "javax.faces.Table")
public class UIData extends UIComponentBase
                    implements NamingContainer
{
    public static final String COMPONENT_FAMILY = "javax.faces.Data";
    public static final String COMPONENT_TYPE = "javax.faces.Data"; // for unit tests

    private static final String FOOTER_FACET_NAME = "footer";
    private static final String HEADER_FACET_NAME = "header";
    private static final Class OBJECT_ARRAY_CLASS = (new Object[0]).getClass();
    private static final int PROCESS_DECODES = 1;
    private static final int PROCESS_VALIDATORS = 2;
    private static final int PROCESS_UPDATES = 3;

    private static final Object[] LEAF_NO_STATE = new Object[]{null,null};

    private int _rowIndex = -1;
    private String _var;

    // Holds for each row the states of the child components of this UIData.
    // Note that only "partial" component state is saved: the component fields
    // that are expected to vary between rows.
    private Map _rowStates = new HashMap();

    /**
     * Handle case where this table is nested inside another table.
     * See method getDataModel for more details.
     * <p>
     * Key: parentClientId (aka rowId when nested within a parent table)
     * Value: DataModel
     */
    private Map _dataModelMap = new HashMap();

    // will be set to false if the data should not be refreshed at the beginning of the encode phase
    private boolean _isValidChilds = true;

    private Object _initialDescendantComponentState = null;

    private int _first;
    private boolean _firstSet;
    private int _rows;
    private boolean _rowsSet;
    private Object _value;

    private static class FacesEventWrapper extends FacesEvent
    {
        private static final long serialVersionUID = 6648047974065628773L;
        private FacesEvent _wrappedFacesEvent;
        private int _rowIndex;

        public FacesEventWrapper(FacesEvent facesEvent, int rowIndex,
                UIData redirectComponent)
        {
            super(redirectComponent);
            _wrappedFacesEvent = facesEvent;
            _rowIndex = rowIndex;
        }

        @Override
        public PhaseId getPhaseId()
        {
            return _wrappedFacesEvent.getPhaseId();
        }

        @Override
        public void setPhaseId(PhaseId phaseId)
        {
            _wrappedFacesEvent.setPhaseId(phaseId);
        }

        @Override
        public void queue()
        {
            _wrappedFacesEvent.queue();
        }

        @Override
        public String toString()
        {
            return _wrappedFacesEvent.toString();
        }

        @Override
        public boolean isAppropriateListener(FacesListener faceslistener)
        {
            return _wrappedFacesEvent.isAppropriateListener(faceslistener);
        }

        @Override
        public void processListener(FacesListener faceslistener)
        {
            _wrappedFacesEvent.processListener(faceslistener);
        }

        public FacesEvent getWrappedFacesEvent()
        {
            return _wrappedFacesEvent;
        }

        public int getRowIndex()
        {
            return _rowIndex;
        }
    }


    private static final DataModel EMPTY_DATA_MODEL = new DataModel()
    {
        @Override
        public boolean isRowAvailable()
        {
            return false;
        }

        @Override
        public int getRowCount()
        {
            return 0;
        }

        @Override
        public Object getRowData()
        {
            throw new IllegalArgumentException();
        }

        @Override
        public int getRowIndex()
        {
            return -1;
        }

        @Override
        public void setRowIndex(int i)
        {
            if (i < -1)
                throw new IllegalArgumentException();
        }

        @Override
        public Object getWrappedData()
        {
            return null;
        }

        @Override
        public void setWrappedData(Object obj)
        {
            if (obj == null)
            {
                return; //Clearing is allowed
            }
            throw new UnsupportedOperationException(this.getClass().getName()
                    + " UnsupportedOperationException");
        }
    };

    private class EditableValueHolderState
    {
        private final Object _value;
        private final boolean _localValueSet;
        private final boolean _valid;
        private final Object _submittedValue;

        public EditableValueHolderState(EditableValueHolder evh)
        {
            _value = evh.getLocalValue();
            _localValueSet = evh.isLocalValueSet();
            _valid = evh.isValid();
            _submittedValue = evh.getSubmittedValue();
        }

        public void restoreState(EditableValueHolder evh)
        {
            evh.setValue(_value);
            evh.setLocalValueSet(_localValueSet);
            evh.setValid(_valid);
            evh.setSubmittedValue(_submittedValue);
        }
    }

    /**
     * Construct an instance of the UIData.
     */
    public UIData()
    {
        setRendererType("javax.faces.Table");
    }

    @Override
    public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback) throws FacesException {
        if (context == null || clientId == null || callback == null)
        {
            throw new NullPointerException();
        }

        //searching for this component?
        boolean returnValue = this.getClientId(context).equals(clientId);

        boolean isCachedFacesContext = isCachedFacesContext();
        if (!isCachedFacesContext)
        {
            setCachedFacesContext(context);
        }
        
        try
        {
            if (returnValue)
            {
                try
                {
                    callback.invokeContextCallback(context, this);
                }
                catch (Exception e)
                {
                    throw new FacesException(e);
                }
                return returnValue;
            }
    
            //Now Look throught facets on this UIComponent
            for (Iterator<UIComponent> it = this.getFacets().values().iterator(); !returnValue && it.hasNext();)
            {
                returnValue = it.next().invokeOnComponent(context, clientId, callback);
            }
    
            if (returnValue == true)
            {
                return returnValue;
            }
            
            //Now we have to check if it is searching an inner component
            String baseClientId = super.getClientId(context);
            
            // is the component an inner component?
            if (clientId.startsWith(baseClientId))
            {
                // Check if the clientId for the component, which we 
                // are looking for, has a rowIndex attached
                String subId = clientId.substring(baseClientId.length() + 1);
                //If the char next to baseClientId is the separator one and
                //the subId matches the regular expression
                if (clientId.charAt(baseClientId.length()) == NamingContainer.SEPARATOR_CHAR && 
                        subId.matches("[0-9]+"+NamingContainer.SEPARATOR_CHAR+".*"))
                {
                    String clientRow = subId.substring(0, subId.indexOf(NamingContainer.SEPARATOR_CHAR));
        
                    //Now we save the current position
                    int oldRow = this.getRowIndex();
                    
                    // try-finally --> make sure, that the old row index is restored
                    try
                    {
                        //The conversion is safe, because its already checked on the
                        //regular expresion
                        this.setRowIndex(Integer.parseInt(clientRow));
                        
                        // check, if the row is available
                        if (!isRowAvailable())
                        {
                            return false;
                        }
            
                        for (Iterator<UIComponent> it1 = getChildren().iterator(); 
                                !returnValue && it1.hasNext();)
                        {
                            //recursive call to find the component
                            returnValue = it1.next().invokeOnComponent(context, clientId, callback);
                        }
                    }
                    finally
                    {
                        //Restore the old position. Doing this prevent
                        //side effects.
                        this.setRowIndex(oldRow);
                    }
                }
                else
                {
                    // MYFACES-2370: search the component in the childrens' facets too.
                    // We have to check the childrens' facets here, because in MyFaces
                    // the rowIndex is not attached to the clientId for the children of
                    // facets of the UIColumns. However, in RI the rowIndex is 
                    // attached to the clientId of UIColumns' Facets' children.
                    for (Iterator<UIComponent> itChildren = this.getChildren().iterator();
                            !returnValue && itChildren.hasNext();)
                    {
                        UIComponent child = itChildren.next();
                        if (child instanceof UIColumn && clientId.equals(child.getClientId(context)))
                        {
                            try {
                                callback.invokeContextCallback(context, child);
                            } catch (Exception e) {
                                throw new FacesException(e);
                            }
                            returnValue = true;
                        }
                        // process the child's facets
                        for (Iterator<UIComponent> itChildFacets = child.getFacets().values().iterator(); 
                                !returnValue && itChildFacets.hasNext();)
                        {
                            //recursive call to find the component
                            returnValue = itChildFacets.next().invokeOnComponent(context, clientId, callback);
                        }
                    }
                }
            }
        }
        finally
        {
            if (!isCachedFacesContext)
            {
                setCachedFacesContext(null);
            }
        }

        return returnValue;
    }

    public void setFooter(UIComponent footer)
    {
        getFacets().put(FOOTER_FACET_NAME, footer);
    }

    @JSFFacet
    public UIComponent getFooter()
    {
        return (UIComponent) getFacets().get(FOOTER_FACET_NAME);
    }

    public void setHeader(UIComponent header)
    {
        getFacets().put(HEADER_FACET_NAME, header);
    }

    @JSFFacet
    public UIComponent getHeader()
    {
        return (UIComponent) getFacets().get(HEADER_FACET_NAME);
    }

    public boolean isRowAvailable()
    {
        return getDataModel().isRowAvailable();
    }

    public int getRowCount()
    {
        return getDataModel().getRowCount();
    }

    public Object getRowData()
    {
        return getDataModel().getRowData();
    }

    public int getRowIndex()
    {
        return _rowIndex;
    }

    /**
     * Set the current row index that methods like getRowData use.
     * <p>
     * Param rowIndex can be -1, meaning "no row".
     * <p>
     * 
     * @param rowIndex
     */
    public void setRowIndex(int rowIndex)
    {
        if (rowIndex < -1)
        {
            throw new IllegalArgumentException("rowIndex is less than -1");
        }

        if (_rowIndex == rowIndex)
        {
            return;
        }

        FacesContext facesContext = getFacesContext();

        if (_rowIndex == -1)
        {
            if (_initialDescendantComponentState == null)
            {
                // Create a template that can be used to initialise any row
                // that we haven't visited before, ie a "saved state" that can
                // be pushed to the "restoreState" method of all the child
                // components to set them up to represent a clean row.
                _initialDescendantComponentState = saveDescendantComponentStates(this, false, false);
            }
        }
        else
        {
            // If no initial component state, there are no EditableValueHolder instances,
            // and that means there is no state to be saved for the current row, so we can
            // skip row state saving code safely.
            if (_initialDescendantComponentState != null)
            {
                // We are currently positioned on some row, and are about to
                // move off it, so save the (partial) state of the components
                // representing the current row. Later if this row is revisited
                // then we can restore this state.
                Collection<Object[]> savedRowState = saveDescendantComponentStates(this, false, false);
                if (savedRowState != null)
                {
                    _rowStates.put(getClientId(facesContext), savedRowState);
                }
            }
        }

        _rowIndex = rowIndex;

        DataModel dataModel = getDataModel();
        dataModel.setRowIndex(rowIndex);

        String var = _var;
        if (rowIndex == -1)
        {
            if (var != null)
            {
                facesContext.getExternalContext().getRequestMap().remove(var);
            }
        }
        else
        {
            if (var != null)
            {
                if (isRowAvailable())
                {
                    Object rowData = dataModel.getRowData();
                    facesContext.getExternalContext().getRequestMap().put(var,
                            rowData);
                }
                else
                {
                    facesContext.getExternalContext().getRequestMap().remove(
                            var);
                }
            }
        }

        if (_rowIndex == -1)
        {
            // reset components to initial state
            // If no initial state, skip row restore state code
            if (_initialDescendantComponentState != null)
            {
                restoreDescendantComponentStates(this, false, _initialDescendantComponentState, false);
            }
            else
            {
                restoreDescendantComponentWithoutRestoreState(this, false, false);
            }
        }
        else
        {
            Object rowState = _rowStates.get(getClientId(facesContext));
            if (rowState == null)
            {
                // We haven't been positioned on this row before, so just
                // configure the child components of this component with
                // the standard "initial" state
                // If no initial state, skip row restore state code
                if (_initialDescendantComponentState != null)
                {
                    restoreDescendantComponentStates(this, false, _initialDescendantComponentState, false);
                }
                else
                {
                    restoreDescendantComponentWithoutRestoreState(this, false, false);
                }
            }
            else
            {
                // We have been positioned on this row before, so configure
                // the child components of this component with the (partial)
                // state that was previously saved. Fields not in the
                // partial saved state are left with their original values.
                restoreDescendantComponentStates(this, false, rowState, false);
            }
        }
    }

    /**
     * Overwrite the state of the child components of this component with data previously saved by method
     * saveDescendantComponentStates.
     * <p>
     * The saved state info only covers those fields that are expected to vary between rows of a table. Other fields are
     * not modified.
     */
    @SuppressWarnings("unchecked")
    private void restoreDescendantComponentStates(UIComponent parent, boolean iterateFacets, Object state,
                                                  boolean restoreChildFacets)
    {
        int descendantStateIndex = -1;
        List<? extends Object[]> stateCollection = null;
        
        if (iterateFacets && parent.getFacetCount() > 0)
        {
            Iterator<UIComponent> childIterator = parent.getFacets().values().iterator();
            
            while (childIterator.hasNext())
            {
                UIComponent component = childIterator.next();

                // reset the client id (see spec 3.1.6)
                component.setId(component.getId());
                if (!component.isTransient())
                {
                    if (descendantStateIndex == -1)
                    {
                        stateCollection = ((List<? extends Object[]>) state);
                        descendantStateIndex = stateCollection.isEmpty() ? -1 : 0;
                    }
                    
                    if (descendantStateIndex != -1 && descendantStateIndex < stateCollection.size())
                    {
                        Object[] object = stateCollection.get(descendantStateIndex);
                        if (object[0] != null && component instanceof EditableValueHolder)
                        {
                            ((EditableValueHolderState) object[0]).restoreState((EditableValueHolder) component);
                        }
                        // If there is descendant state to restore, call it recursively, otherwise
                        // it is safe to skip iteration.
                        if (object[1] != null)
                        {
                            restoreDescendantComponentStates(component, restoreChildFacets, object[1], true);
                        }
                        else
                        {
                            restoreDescendantComponentWithoutRestoreState(component, restoreChildFacets, true);
                        }
                    }
                    else
                    {
                        restoreDescendantComponentWithoutRestoreState(component, restoreChildFacets, true);
                    }
                    descendantStateIndex++;
                }
            }
        }
        
        if (parent.getChildCount() > 0)
        {
            for (int i = 0; i < parent.getChildCount(); i++)
            {
                UIComponent component = parent.getChildren().get(i);

                // reset the client id (see spec 3.1.6)
                component.setId(component.getId());
                if (!component.isTransient())
                {
                    if (descendantStateIndex == -1)
                    {
                        stateCollection = ((List<? extends Object[]>) state);
                        descendantStateIndex = stateCollection.isEmpty() ? -1 : 0;
                    }
                    
                    if (descendantStateIndex != -1 && descendantStateIndex < stateCollection.size())
                    {
                        Object[] object = stateCollection.get(descendantStateIndex);
                        if (object[0] != null && component instanceof EditableValueHolder)
                        {
                            ((EditableValueHolderState) object[0]).restoreState((EditableValueHolder) component);
                        }
                        // If there is descendant state to restore, call it recursively, otherwise
                        // it is safe to skip iteration.
                        if (object[1] != null)
                        {
                            restoreDescendantComponentStates(component, restoreChildFacets, object[1], true);
                        }
                        else
                        {
                            restoreDescendantComponentWithoutRestoreState(component, restoreChildFacets, true);
                        }
                    }
                    else
                    {
                        restoreDescendantComponentWithoutRestoreState(component, restoreChildFacets, true);
                    }
                    descendantStateIndex++;
                }
            }
        }
    }

    /**
     * Just call component.setId(component.getId()) to reset all client ids and 
     * ensure they will be calculated for the current row, but do not waste time
     * dealing with row state code.
     * 
     * @param parent
     * @param iterateFacets
     * @param restoreChildFacets 
     */
    private void restoreDescendantComponentWithoutRestoreState(UIComponent parent, boolean iterateFacets, boolean restoreChildFacets)
    {
        if (iterateFacets && parent.getFacetCount() > 0)
        {
            Iterator<UIComponent> childIterator = parent.getFacets().values().iterator();
            
            while (childIterator.hasNext())
            {
                UIComponent component = childIterator.next();

                // reset the client id (see spec 3.1.6)
                component.setId(component.getId());
                if (!component.isTransient())
                {
                    restoreDescendantComponentWithoutRestoreState(component, restoreChildFacets, true);
                }
            }
        }
        
        if (parent.getChildCount() > 0)
        {
            for (int i = 0; i < parent.getChildCount(); i++)
            {
                UIComponent component = parent.getChildren().get(i);

                // reset the client id (see spec 3.1.6)
                component.setId(component.getId());
                if (!component.isTransient())
                {
                    restoreDescendantComponentWithoutRestoreState(component, restoreChildFacets, true);
                }
            }
        }
    }

    /**
     * Walk the tree of child components of this UIData, saving the parts of their state that can vary between rows.
     * <p>
     * This is very similar to the process that occurs for normal components when the view is serialized. Transient
     * components are skipped (no state is saved for them).
     * <p>
     * If there are no children then null is returned. If there are one or more children, and all children are transient
     * then an empty collection is returned; this will happen whenever a table contains only read-only components.
     * <p>
     * Otherwise a collection is returned which contains an object for every non-transient child component; that object
     * may itself contain a collection of the state of that child's child components.
     */
    private Collection<Object[]> saveDescendantComponentStates(UIComponent parent, boolean iterateFacets,
                                                               boolean saveChildFacets)
    {
        Collection<Object[]> childStates = null;
        // Index to indicate how many components has been passed without state to save.
        int childEmptyIndex = 0;
        int totalChildCount = 0;
                
        if (iterateFacets && parent.getFacetCount() > 0)
        {
            Iterator<UIComponent> childIterator = parent.getFacets().values().iterator();

            while (childIterator.hasNext())
            {
                UIComponent child = childIterator.next();
                if (!child.isTransient())
                {
                    // Add an entry to the collection, being an array of two
                    // elements. The first element is the state of the children
                    // of this component; the second is the state of the current
                    // child itself.

                    if (child instanceof EditableValueHolder)
                    {
                        if (childStates == null)
                        {
                            childStates = new ArrayList<Object[]>(
                                    parent.getFacetCount()
                                    + parent.getChildCount()
                                    - totalChildCount
                                    + childEmptyIndex);
                            for (int ci = 0; ci < childEmptyIndex; ci++)
                            {
                                childStates.add(LEAF_NO_STATE);
                            }
                        }
                    
                        childStates.add(child.getChildCount() > 0 ? 
                                new Object[]{new EditableValueHolderState((EditableValueHolder) child),
                                    saveDescendantComponentStates(child, saveChildFacets, true)} :
                                new Object[]{new EditableValueHolderState((EditableValueHolder) child),
                                    null});
                    }
                    else if (child.getChildCount() > 0 || (saveChildFacets && child.getFacetCount() > 0))
                    {
                        Object descendantSavedState = saveDescendantComponentStates(child, saveChildFacets, true);
                        
                        if (descendantSavedState == null)
                        {
                            if (childStates == null)
                            {
                                childEmptyIndex++;
                            }
                            else
                            {
                                childStates.add(LEAF_NO_STATE);
                            }
                        }
                        else
                        {
                            if (childStates == null)
                            {
                                childStates = new ArrayList<Object[]>(
                                        parent.getFacetCount()
                                        + parent.getChildCount()
                                        - totalChildCount
                                        + childEmptyIndex);
                                for (int ci = 0; ci < childEmptyIndex; ci++)
                                {
                                    childStates.add(LEAF_NO_STATE);
                                }
                            }
                            childStates.add(new Object[]{null, descendantSavedState});
                        }
                    }
                    else
                    {
                        if (childStates == null)
                        {
                            childEmptyIndex++;
                        }
                        else
                        {
                            childStates.add(LEAF_NO_STATE);
                        }
                    }
                }
                totalChildCount++;
            }
        }
        
        if (parent.getChildCount() > 0)
        {
            for (int i = 0; i < parent.getChildCount(); i++)
            {
                UIComponent child = parent.getChildren().get(i);
                if (!child.isTransient())
                {
                    // Add an entry to the collection, being an array of two
                    // elements. The first element is the state of the children
                    // of this component; the second is the state of the current
                    // child itself.

                    if (child instanceof EditableValueHolder)
                    {
                        if (childStates == null)
                        {
                            childStates = new ArrayList<Object[]>(
                                    parent.getFacetCount()
                                    + parent.getChildCount()
                                    - totalChildCount
                                    + childEmptyIndex);
                            for (int ci = 0; ci < childEmptyIndex; ci++)
                            {
                                childStates.add(LEAF_NO_STATE);
                            }
                        }
                    
                        childStates.add(child.getChildCount() > 0 ? 
                                new Object[]{new EditableValueHolderState((EditableValueHolder) child),
                                    saveDescendantComponentStates(child, saveChildFacets, true)} :
                                new Object[]{new EditableValueHolderState((EditableValueHolder) child),
                                    null});
                    }
                    else if (child.getChildCount() > 0 || (saveChildFacets && child.getFacetCount() > 0))
                    {
                        Object descendantSavedState = saveDescendantComponentStates(child, saveChildFacets, true);
                        
                        if (descendantSavedState == null)
                        {
                            if (childStates == null)
                            {
                                childEmptyIndex++;
                            }
                            else
                            {
                                childStates.add(LEAF_NO_STATE);
                            }
                        }
                        else
                        {
                            if (childStates == null)
                            {
                                childStates = new ArrayList<Object[]>(
                                        parent.getFacetCount()
                                        + parent.getChildCount()
                                        - totalChildCount
                                        + childEmptyIndex);
                                for (int ci = 0; ci < childEmptyIndex; ci++)
                                {
                                    childStates.add(LEAF_NO_STATE);
                                }
                            }
                            childStates.add(new Object[]{null, descendantSavedState});
                        }
                    }
                    else
                    {
                        if (childStates == null)
                        {
                            childEmptyIndex++;
                        }
                        else
                        {
                            childStates.add(LEAF_NO_STATE);
                        }
                    }
                }
                totalChildCount++;
            }
        }
        
        return childStates;
    }
    
    @Override
    public void setValueExpression(String name, ValueExpression binding) {
        if (name == null)
        {
            throw new NullPointerException("name");
        }
        else if (name.equals("value"))
        {
            _dataModelMap.clear();
        }
        else if (name.equals("rowIndex"))
        {
            throw new IllegalArgumentException("name " + name);
        }
        super.setValueExpression(name, binding);
    }

    @Override
    public String getClientId(FacesContext context)
    {
        String clientId = super.getClientId(context);
        int rowIndex = getRowIndex();
        if (rowIndex == -1)
        {
            return clientId;
        }

        StringBuilder bld = __getSharedStringBuilder();
        return bld.append(clientId).append(NamingContainer.SEPARATOR_CHAR).append(rowIndex).toString();
    }

    /**
     * Modify events queued for any child components so that the
     * UIData state will be correctly configured before the event's
     * listeners are executed.
     * <p>
     * Child components or their renderers may register events against
     * those child components. When the listener for that event is
     * eventually invoked, it may expect the uidata's rowData and
     * rowIndex to be referring to the same object that caused the
     * event to fire.
     * <p>
     * The original queueEvent call against the child component has been
     * forwarded up the chain of ancestors in the standard way, making
     * it possible here to wrap the event in a new event whose source
     * is <i>this</i> component, not the original one. When the event
     * finally is executed, this component's broadcast method is invoked,
     * which ensures that the UIData is set to be at the correct row
     * before executing the original event.
     */
    @Override
    public void queueEvent(FacesEvent event)
    {
        super.queueEvent(new FacesEventWrapper(event, getRowIndex(), this));
    }

    /**
     * Ensure that before the event's listeners are invoked this UIData
     * component's "current row" is set to the row associated with the event.
     * <p>
     * See queueEvent for more details.
     */
    @Override
    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        if (event instanceof FacesEventWrapper)
        {
            FacesEvent originalEvent = ((FacesEventWrapper) event)
                    .getWrappedFacesEvent();
            int eventRowIndex = ((FacesEventWrapper) event).getRowIndex();
            int currentRowIndex = getRowIndex();
            setRowIndex(eventRowIndex);
            try
            {
              originalEvent.getComponent().broadcast(originalEvent);
            }
            finally
            {
              setRowIndex(currentRowIndex);
            }
        }
        else
        {
            super.broadcast(event);
        }
    }

    /**
     * Perform necessary actions when rendering of this component starts,
     * before delegating to the inherited implementation which calls the
     * associated renderer's encodeBegin method.
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException
    {
        _initialDescendantComponentState = null;
        if (_isValidChilds && !hasErrorMessages(context))
        {
            // Clear the data model so that when rendering code calls
            // getDataModel a fresh model is fetched from the backing
            // bean via the value-binding.
            _dataModelMap.clear();

            // When the data model is cleared it is also necessary to
            // clear the saved row state, as there is an implicit 1:1
            // relation between objects in the _rowStates and the
            // corresponding DataModel element.
            _rowStates.clear();
        }
        super.encodeBegin(context);
    }

    private boolean hasErrorMessages(FacesContext context)
    {
        for(Iterator iter = context.getMessages(); iter.hasNext();)
        {
            FacesMessage message = (FacesMessage) iter.next();
            if(FacesMessage.SEVERITY_ERROR.compareTo(message.getSeverity()) <= 0)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @see javax.faces.component.UIComponentBase#encodeEnd(javax.faces.context.FacesContext)
     */
    @Override
    public void encodeEnd(FacesContext context) throws IOException
    {
        try
        {
            setCachedFacesContext(context);
            setRowIndex(-1);
        }
        finally
        {
            setCachedFacesContext(null);
        }
        super.encodeEnd(context);
    }

    @Override
    public void processDecodes(FacesContext context)
    {
        if (context == null)
        {
            throw new NullPointerException("context");
        }
        try
        {
            setCachedFacesContext(context);
            if (!isRendered())
            {
                return;
            }
            setRowIndex(-1);
            processFacets(context, PROCESS_DECODES);
            processColumnFacets(context, PROCESS_DECODES);
            processColumnChildren(context, PROCESS_DECODES);
            setRowIndex(-1);
            try
            {
                decode(context);
            }
            catch (RuntimeException e)
            {
                context.renderResponse();
                throw e;
            }
        }
        finally
        {
            setCachedFacesContext(null);
        }
    }

    @Override
    public void processValidators(FacesContext context)
    {
        if (context == null)
        {
            throw new NullPointerException("context");
        }

        try
        {
            setCachedFacesContext(context);
            if (!isRendered())
            {
                return;
            }
    
            setRowIndex(-1);
            processFacets(context, PROCESS_VALIDATORS);
            processColumnFacets(context, PROCESS_VALIDATORS);
            processColumnChildren(context, PROCESS_VALIDATORS);
            setRowIndex(-1);
    
            // check if an validation error forces the render response for our data
            if (context.getRenderResponse())
            {
                _isValidChilds = false;
            }
        }
        finally
        {
            setCachedFacesContext(null);
        }
    }

    @Override
    public void processUpdates(FacesContext context)
    {
        if (context == null)
        {
            throw new NullPointerException("context");
        }
        try
        {
            setCachedFacesContext(context);
            if (!isRendered())
            {
                return;
            }
            setRowIndex(-1);
            processFacets(context, PROCESS_UPDATES);
            processColumnFacets(context, PROCESS_UPDATES);
            processColumnChildren(context, PROCESS_UPDATES);
            setRowIndex(-1);
    
            if (context.getRenderResponse())
            {
                _isValidChilds = false;
            }
        }
        finally
        {
            setCachedFacesContext(null);
        }
    }

    private void processFacets(FacesContext context, int processAction)
    {
        for (Iterator it = getFacets().values().iterator(); it.hasNext();)
        {
            UIComponent facet = (UIComponent) it.next();
            process(context, facet, processAction);
        }
    }

    /**
     * Invoke the specified phase on all facets of all UIColumn children
     * of this component. Note that no methods are called on the UIColumn
     * child objects themselves.
     *
     * @param context is the current faces context.
     * @param processAction specifies a JSF phase: decode, validate or update.
     */
    private void processColumnFacets(FacesContext context, int processAction)
    {
        for (Iterator childIter = getChildren().iterator(); childIter.hasNext();)
        {
            UIComponent child = (UIComponent) childIter.next();
            if (child instanceof UIColumn)
            {
                if (!child.isRendered())
                {
                    //Column is not visible
                    continue;
                }
                for (Iterator facetsIter = child.getFacets().values()
                        .iterator(); facetsIter.hasNext();)
                {
                    UIComponent facet = (UIComponent) facetsIter.next();
                    process(context, facet, processAction);
                }
            }
        }
    }

    /**
     * Invoke the specified phase on all non-facet children of all UIColumn
     * children of this component. Note that no methods are called on the
     * UIColumn child objects themselves.
     *
     * @param context is the current faces context.
     * @param processAction specifies a JSF phase: decode, validate or update.
     */
    private void processColumnChildren(FacesContext context, int processAction)
    {
        int first = getFirst();
        int rows = getRows();
        int last;
        if (rows == 0)
        {
            last = getRowCount();
        }
        else
        {
            last = first + rows;
        }
        for (int rowIndex = first; last==-1 || rowIndex < last; rowIndex++)
        {
            setRowIndex(rowIndex);

            //scrolled past the last row
            if (!isRowAvailable())
            {
                break;
            }

            for (Iterator it = getChildren().iterator(); it.hasNext();)
            {
                UIComponent child = (UIComponent) it.next();
                if (child instanceof UIColumn)
                {
                    if (!child.isRendered())
                    {
                        //Column is not visible
                        continue;
                    }
                    for (Iterator columnChildIter = child.getChildren()
                            .iterator(); columnChildIter.hasNext();)
                    {
                        UIComponent columnChild = (UIComponent) columnChildIter
                                .next();
                        process(context, columnChild, processAction);
                    }
                }
            }
        }
    }

    private void process(FacesContext context, UIComponent component,
            int processAction)
    {
        switch (processAction)
        {
        case PROCESS_DECODES:
            component.processDecodes(context);
            break;
        case PROCESS_VALIDATORS:
            component.processValidators(context);
            break;
        case PROCESS_UPDATES:
            component.processUpdates(context);
            break;
        }
    }

    /**
     * Return the datamodel for this table, potentially fetching the data from
     * a backing bean via a value-binding if this is the first time this method
     * has been called.
     * <p>
     * This is complicated by the fact that this table may be nested within
     * another table. In this case a different datamodel should be fetched
     * for each row. When nested within a parent table, the parent reference
     * won't change but parent.getClientId() will, as the suffix changes
     * depending upon the current row index. A map object on this component
     * is therefore used to cache the datamodel for each row of the table.
     * In the normal case where this table is not nested inside a component
     * that changes its id (like a table does) then this map only ever has
     * one entry.
     */
    protected DataModel getDataModel()
    {
        DataModel dataModel;
        String clientID = "";

        UIComponent parent = getParent();
        if (parent != null)
        {
            clientID = parent.getClientId(getFacesContext());
        }
        dataModel = (DataModel) _dataModelMap.get(clientID);
        if (dataModel == null)
        {
            dataModel = createDataModel();
            _dataModelMap.put(clientID, dataModel);
        }
        return dataModel;
    }

    protected void setDataModel(DataModel dataModel)
    {
        throw new UnsupportedOperationException(
                "this method is here only to maintain binary compatibility w/ the RI");
    }

    /**
     * Evaluate this object's value property and convert the result into a
     * DataModel. Normally this object's value property will be a value-binding
     * which will cause the value to be fetched from some backing bean.
     * <p>
     * The result of fetching the value may be a DataModel object, in which
     * case that object is returned directly. If the value is of type
     * List, Array, ResultSet, Result, other object or null then an appropriate
     * wrapper is created and returned.
     * <p>
     * Null is never returned by this method.
     */
    private DataModel createDataModel()
    {
        Object value = getValue();

        if (value == null)
        {
            return EMPTY_DATA_MODEL;
        }
        else if (value instanceof DataModel)
        {
            return (DataModel) value;
        }
        else if (value instanceof List)
        {
            return new ListDataModel((List) value);
        }
        else if (OBJECT_ARRAY_CLASS.isAssignableFrom(value.getClass()))
        {
            return new ArrayDataModel((Object[]) value);
        }
        else if (value instanceof ResultSet)
        {
            return new ResultSetDataModel((ResultSet) value);
        }
        else if (value instanceof Result)
        {
            return new ResultDataModel((Result) value);
        }
        else
        {
            return new ScalarDataModel(value);
        }
    }

    /**
     * An EL expression that specifies the data model that backs this table.
     * <p>
     * The value referenced by the EL expression can be of any type.
     * </p>
     * <ul>
     * <li>A value of type DataModel is used directly.</li>
     * <li>Array-like parameters of type array-of-Object, java.util.List, java.sql.ResultSet
     *  or javax.servlet.jsp.jstl.sql.Result are wrapped in a corresponding DataModel that
     *  knows how to iterate over the elements.</li>
     * <li>Other values are wrapped in a DataModel as a single row.</li>
     * </ul>
     * <p>
     * Note in particular that unordered collections, eg Set are not supported. Therefore if the
     * value expression references such an object then the table will be considered to contain just
     * one element - the collection itself.
     * </p>
     */
    @JSFProperty
    public Object getValue()
    {
      if (_value != null)
      {
        return _value;
      }
      ValueExpression expression = getValueExpression("value");
      if (expression != null)
      {
        return expression.getValue(getFacesContext().getELContext());
      }
      return null;
    }

    public void setValue(Object value)
    {
        _value = value;
        _dataModelMap.clear();
        _rowStates.clear();
        _isValidChilds = true;
    }

    /**
     * Defines the index of the first row to be displayed, starting from 0.
     */
    @JSFProperty
    public int getFirst()
    {
      if (_firstSet)
      {
        return _first;
      }
      ValueExpression expression = getValueExpression("first");
      if (expression != null)
      {
        return (Integer)expression.getValue(getFacesContext().getELContext());
      }
      return 0;
    }

    public void setFirst(int first)
    {
        if (first < 0)
        {
            throw new IllegalArgumentException("Illegal value for first row: " + first);
        }
        _first = first;
        _firstSet=true;
    }

    /**
     * Defines the maximum number of rows of data to be displayed.
     * <p>
     * Specify zero to display all rows from the "first" row to the end
     * of available data.
     * </p>
     */
    @JSFProperty
    public int getRows()
    {
      if (_rowsSet)
      {
        return _rows;
      }
      ValueExpression expression = getValueExpression("rows");
      if (expression != null)
      {
        return (Integer)expression.getValue(getFacesContext().getELContext());
      }
      return 0;
    }

    /**
     * Set the maximum number of rows displayed in the table.
     */
    public void setRows(int rows)
    {
        if (rows < 0)
        {
            throw new IllegalArgumentException("rows: " + rows);
        }
        _rows = rows;
        _rowsSet = true;
    }

  /**
   * Defines the name of the request-scope variable that will hold the current row during iteration.
   * <p>
   * During rendering of child components of this UIData, the variable with this name can be read to
   * learn what the "rowData" object for the row currently being rendered is.
   * </p>
   * <p>
   * This value must be a static value, ie an EL expression is not permitted.
   * </p>
   */
  @JSFProperty(literalOnly = true)
  public String getVar()
  {
    return _var;
  }

  public void setVar(String var)
  {
    this._var = var;
  }


  @Override
  public Object saveState(FacesContext facesContext)
  {
    Object[] values = new Object[7];
    values[0] = super.saveState(facesContext);
    values[1] = _value;
    values[2] = _var;
    values[3] = _rows;
    values[4] = _rowsSet;
    values[5] = _first;
    values[6] = _firstSet;

    return values;
  }

  @Override
  public void restoreState(FacesContext facesContext, Object state)
  {
    Object[] values = (Object[])state;
    super.restoreState(facesContext,values[0]);
    _value = values[1];
    _var = (String)values[2];
    _rows = (Integer)values[3];
    _rowsSet = (Boolean)values[4];
    _first = (Integer)values[5];
    _firstSet = (Boolean)values[6];
  }

  @Override
  public String getFamily()
  {
    return COMPONENT_FAMILY;
  }
}
