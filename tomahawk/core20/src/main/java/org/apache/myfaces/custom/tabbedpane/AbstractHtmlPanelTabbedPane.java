/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tabbedpane;

import java.util.Iterator;
import java.util.List;

import javax.faces.component.PartialStateHolder;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.AttachedDeltaWrapper;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.PanelProperties;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.UserRoleAware;

/**
 * TODO: Document this component.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:panelTabbedPane"
 *   class = "org.apache.myfaces.custom.tabbedpane.HtmlPanelTabbedPane"
 *   tagClass = "org.apache.myfaces.custom.tabbedpane.HtmlPanelTabbedPaneTag"
 *   tagHandler = "org.apache.myfaces.custom.tabbedpane.HtmlPanelTabbedPaneTagHandler"
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 745263 $ $Date: 2009-02-17 16:51:58 -0500 (mar, 17 feb 2009) $
 */
public abstract class AbstractHtmlPanelTabbedPane
        extends HtmlPanelGroup
        implements UniversalProperties, EventAware, PanelProperties,
        AlignProperty, DataProperties, UserRoleAware, ClientBehaviorHolder
        
{
    //private static final Log log = LogFactory.getLog(HtmlPanelTabbedPane.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelTabbedPane";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TabbedPane";
    private static final int DEFAULT_SELECTEDINDEX = 0;
    private static final boolean DEFAULT_SERVER_SIDE_TAB_SWITCH = false;

    private MethodBinding _tabChangeListener = null;
    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

    public void decode(FacesContext context)
    {
        super.decode(context);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void processDecodes(javax.faces.context.FacesContext context)
   {
       if (context == null) throw new NullPointerException("context");
       decode(context);

       int tabIdx = 0;
       int selectedIndex = getSelectedIndex();

       Iterator it = getFacetsAndChildren();

       while (it.hasNext())
       {
           UIComponent childOrFacet = getUIComponent((UIComponent) it.next());
           if (childOrFacet instanceof HtmlPanelTab) {
               if (isClientSide() || selectedIndex == tabIdx) {
                   childOrFacet.processDecodes(context);
               }
               tabIdx++;
           } else {
               childOrFacet.processDecodes(context);
           }
       }
   }

    public void processValidators(FacesContext context)
    {
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;

        int tabIdx = 0;
        int selectedIndex = getSelectedIndex();

        Iterator it = getFacetsAndChildren();

        while (it.hasNext())
        {
            UIComponent childOrFacet = getUIComponent((UIComponent) it.next());
            if (childOrFacet instanceof HtmlPanelTab) {
                if (isClientSide() || selectedIndex == tabIdx) {
                    childOrFacet.processValidators(context);
                }
                tabIdx++;
            } else {
                childOrFacet.processValidators(context);
            }
        }
    }

    public void processUpdates(FacesContext context)
    {
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;

        int tabIdx = 0;
        int selectedIndex = getSelectedIndex();

        Iterator it = getFacetsAndChildren();

        while (it.hasNext())
        {
            UIComponent childOrFacet = getUIComponent((UIComponent) it.next());
            if (childOrFacet instanceof HtmlPanelTab) {
                if (isClientSide() || selectedIndex == tabIdx) {
                    childOrFacet.processUpdates(context);
                }
                tabIdx++;
            } else {
                childOrFacet.processUpdates(context);
            }
        }
    }

    private UIComponent getUIComponent(UIComponent uiComponent)
    {
        /*todo: checking for UIForm is not enough - Trinidad form, etc.*/
        if (uiComponent instanceof UINamingContainer || uiComponent instanceof UIForm)
        {
            List children = uiComponent.getChildren();
            for (int i = 0, len = children.size(); i < len; i++)
            {
                uiComponent = getUIComponent((UIComponent)children.get(i));
            }
        }
        return uiComponent;
    }

    public void addTabChangeListener(TabChangeListener listener)
    {
        addFacesListener(listener);
    }

    public void removeTabChangeListener(TabChangeListener listener)
    {
        removeFacesListener(listener);
    }

    /**
     * TODO: This should be something like this:
     * 
     * JSFProperty
     *   returnSignature = "void"
     *   methodSignature = "org.apache.myfaces.custom.tabbedpane.TabChangeEvent"
     * 
     * And be added on tld. But you can do the same with TabChangeListenerTag. 
     * 
     * @return
     */
    public MethodBinding getTabChangeListener()
    {
        return _tabChangeListener;
    }
    
    private boolean _isSetTabChangeListener()
    {
        Boolean value = (Boolean) getStateHelper().get(PropertyKeys.tabChangeListenerSet);
        return value == null ? false : value;
    }
    
    public void setTabChangeListener(MethodBinding tabChangeListener)
    {
        _tabChangeListener = tabChangeListener;
    }
    
    public Object saveState(FacesContext facesContext)
    {
        if (initialStateMarked())
        {
            boolean nullDelta = true;
            Object parentSaved = super.saveState(facesContext);
            Object tabChangeListenerSaved = null;
            if (!_isSetTabChangeListener() &&
                _tabChangeListener != null && _tabChangeListener instanceof PartialStateHolder)
            {
                //Delta
                StateHolder holder = (StateHolder) _tabChangeListener;
                if (!holder.isTransient())
                {
                    Object attachedState = holder.saveState(facesContext);
                    if (attachedState != null)
                    {
                        nullDelta = false;
                    }
                    tabChangeListenerSaved = new AttachedDeltaWrapper(_tabChangeListener.getClass(),
                        attachedState);
                }
            }
            else  if (_isSetTabChangeListener() || _tabChangeListener != null)
            {
                //Full
                tabChangeListenerSaved = saveAttachedState(facesContext,_tabChangeListener);
                nullDelta = false;
            }        
            if (parentSaved == null && nullDelta)
            {
                //No values
                return null;
            }
            
            Object[] values = new Object[2];
            values[0] = parentSaved;
            values[1] = tabChangeListenerSaved;
            return values;
        }
        else
        {
            Object[] values = new Object[2];
            values[0] = super.saveState(facesContext);
            values[1] = saveAttachedState(facesContext,_tabChangeListener);
            return values;
        }
    }

    public void restoreState(FacesContext facesContext, Object state)
    {
        if (state == null)
        {
            return;
        }
        
        Object[] values = (Object[])state;
        super.restoreState(facesContext,values[0]);
        if (values[1] instanceof AttachedDeltaWrapper)
        {
            //Delta
            ((StateHolder)_tabChangeListener).restoreState(facesContext, ((AttachedDeltaWrapper) values[1]).getWrappedStateObject());
        }
        else
        {
            //Full
            _tabChangeListener = (javax.faces.el.MethodBinding) restoreAttachedState(facesContext,values[1]);
        }
    }    

    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        if (event instanceof TabChangeEvent)
        {
            TabChangeEvent tabChangeEvent = (TabChangeEvent)event;
            if (tabChangeEvent.getComponent() == this)
            {
                setSelectedIndex(tabChangeEvent.getNewTabIndex());
                getFacesContext().renderResponse();
            }
        }
        super.broadcast(event);

        MethodBinding tabChangeListenerBinding = getTabChangeListener();
        if (tabChangeListenerBinding != null)
        {
            try
            {
                tabChangeListenerBinding.invoke(getFacesContext(), new Object[]{event});
            }
            catch (EvaluationException e)
            {
                Throwable cause = e.getCause();
                if (cause != null && cause instanceof AbortProcessingException)
                {
                    throw (AbortProcessingException)cause;
                }
                else
                {
                    throw e;
                }
            }
        }
    }
    
    /**
     * Write out information about the toggling mode - the component might
     * be toggled server side or client side.
     */
    public boolean isClientSide()
    {
        return !isServerSideTabSwitch(); 
    }

    /**
     * 
     */
    @JSFProperty(
       tagExcluded = true)
    public abstract String getActiveTabVar();
    
    /**
     * Boolean Variable that is set in request scope when 
     * rendering a panelTab. True means that the currently 
     * rendered panelTab is active.
     * 
     */
    @JSFProperty
    public abstract Boolean getActivePanelTabVar();

    /**
     * Index of tab that is selected by default.
     * 
     */
    @JSFProperty(
       defaultValue = "0")
    public abstract int getSelectedIndex();
    
    public abstract void setSelectedIndex(int selectedIndex);

    /**
     * Style class of the active tab cell.
     * 
     */
    @JSFProperty
    public abstract String getActiveTabStyleClass();

    /**
     * Style class of the inactive tab cells.
     * 
     */
    @JSFProperty
    public abstract String getInactiveTabStyleClass();

    /**
     * Style class of the active tab sub cell.
     * 
     */
    @JSFProperty
    public abstract String getActiveSubStyleClass();

    /**
     * Style class of the inactive tab sub cells.
     * 
     */
    @JSFProperty
    public abstract String getInactiveSubStyleClass();

    /**
     * Style class of the active tab content cell.
     * 
     */
    @JSFProperty
    public abstract String getTabContentStyleClass();

    /**
     * Style class of the disabled tab cells.
     * 
     */
    @JSFProperty
    public abstract String getDisabledTabStyleClass();

    /**
     * Toggle client-side/server-side tab switches.
     * 
     */
    @JSFProperty(
       defaultValue = "false")
    public abstract boolean isServerSideTabSwitch();
    
    public boolean getServerSideTabSwitch()
    {
        return isServerSideTabSwitch();
    }

    /**
     * Define if the process validation and update model phases
     * should be executed before change between tabs, when
     * serverSideTabSwitch = true (if is false, the switch
     * is done by other way so this property does not have any
     * effect).
     * 
     * Note that if this property is set as false, only a tab 
     * change is done if all input fields inside the form are valid 
     * (including input components outside this panel). 
     * 
     * By default is true, so both phases are not executed.
     * 
     */
    @JSFProperty(
       defaultValue = "true")
    public abstract boolean isImmediateTabChange();
    
    public void queueEvent(FacesEvent event)
    {
        if (event != null && event instanceof TabChangeEvent)
        {
            if (isImmediateTabChange())
            {
                event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            }
            else
            {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            }
        }
        super.queueEvent(event);
    }
    
    enum PropertyKeys
    {
        tabChangeListenerSet
    }
}
