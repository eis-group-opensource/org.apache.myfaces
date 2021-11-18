/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.aliasbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspProperties;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspProperty;
import org.apache.myfaces.shared_tomahawk.component.BindingAware;
import org.apache.myfaces.shared_tomahawk.util.RestoreStateUtils;

/**
 * Holds several aliases that are configured by aliasBean tags.
 * <p>
 * The aliasBean tag must enclose all the components that are within the scope
 * of the alias. When multiple aliasas are defined, this makes the page structure
 * very clumsy; for example defining 5 aliases means the content must be nested
 * 5 indentation levels deep. This tag instead allows the content block to be
 * wrapped in just one AliasBeansScope tag, and then have AliasBean tags with
 * empty bodies added as direct children of this component. The scope of the AliasBean
 * tag still starts when the tag begins, but instead of ending when the tag ends
 * the scope of the nested AliasBean tags extends to the end of this component.
 * </p>
 * 
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 1082663 $ $Date: 2011-03-17 21:47:10 +0200 (Thu, 17 Mar 2011) $
 */
@JSFComponent(
        name = "t:aliasBeansScope",
        tagClass = "org.apache.myfaces.custom.aliasbean.AliasBeansScopeTag",
        tagHandler = "org.apache.myfaces.custom.aliasbean.AliasBeansScopeTagHandler")
@JSFJspProperties(properties={
        @JSFJspProperty(
                name = "rendered",
                returnType = "boolean", 
                tagExcluded = true),
        @JSFJspProperty(
                name = "binding",
                returnType = "java.lang.String",
                tagExcluded = true)
                })
public class AliasBeansScope extends UIComponentBase implements BindingAware
{
    static final Log log = LogFactory.getLog(AliasBeansScope.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.AliasBeansScope";
    public static final String COMPONENT_FAMILY = "javax.faces.Data";

    private ArrayList _aliases = new ArrayList();
    transient FacesContext _context = null;

    void addAlias(Alias alias)
    {
        _aliases.add(alias);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public String getRendererType() {
      return null;
    }

  public Object saveState(FacesContext context)
    {
        log.debug("saveState");
        _context = context;

        return super.saveState(context);
    }

    public void restoreState(FacesContext context, Object state)
    {
        log.debug("restoreState");
        _context = context;

        super.restoreState(context, state);
    }

    public Object processSaveState(FacesContext context)
    {
        if (context == null)
            throw new NullPointerException("context");
        if (isTransient())
            return null;

        makeAliases(context);

        Map facetMap = null;
        for (Iterator it = getFacets().entrySet().iterator(); it.hasNext();)
        {
            Map.Entry entry = (Map.Entry) it.next();
            if (facetMap == null)
                facetMap = new HashMap();
            UIComponent component = (UIComponent) entry.getValue();
            if (!component.isTransient())
            {
                facetMap.put(entry.getKey(), component.processSaveState(context));
            }
        }

        List childrenList = null;
        if (getChildCount() > 0)
        {
            for (Iterator it = getChildren().iterator(); it.hasNext();)
            {
                UIComponent child = (UIComponent) it.next();
                if (!child.isTransient())
                {
                    if (childrenList == null)
                        childrenList = new ArrayList(getChildCount());
                    childrenList.add(child.processSaveState(context));
                }
            }
        }

        removeAliases(context);

        return new Object[]{saveState(context), facetMap, childrenList};
    }

    public void processRestoreState(FacesContext context, Object state)
    {
        if (context == null)
            throw new NullPointerException("context");
        Object myState = ((Object[]) state)[0];

        restoreState(context, myState);

        makeAliases(context);

        Map facetMap = (Map) ((Object[]) state)[1];

        for (Iterator it = getFacets().entrySet().iterator(); it.hasNext();)
        {
            Map.Entry entry = (Map.Entry) it.next();
            Object facetState = facetMap.get(entry.getKey());
            if (facetState != null)
            {
                ((UIComponent) entry.getValue()).processRestoreState(context, facetState);
            }
            else
            {
                context.getExternalContext().log("No state found to restore facet " + entry.getKey());
            }
        }

        List childrenList = (List) ((Object[]) state)[2];
        if (getChildCount() > 0)
        {
            int idx = 0;
            for (Iterator it = getChildren().iterator(); it.hasNext();)
            {
                UIComponent child = (UIComponent) it.next();
                Object childState = childrenList.get(idx++);
                if (childState != null)
                {
                    child.processRestoreState(context, childState);
                }
                else
                {
                    context.getExternalContext().log("No state found to restore child of component " + getId());
                }
            }
        }

        removeAliases(context);
    }

    public void processValidators(FacesContext context)
    {
        log.debug("processValidators");
        makeAliases(context);
        super.processValidators(context);
        removeAliases(context);
    }

    public void processDecodes(FacesContext context)
    {
        log.debug("processDecodes");
        makeAliases(context);
        super.processDecodes(context);
        removeAliases(context);
    }

    public void processUpdates(FacesContext context)
    {
        log.debug("processUpdates");
        makeAliases(context);
        super.processUpdates(context);
        removeAliases(context);
    }

    public void encodeBegin(FacesContext context) throws IOException
    {
        log.debug("encodeBegin");
        makeAliases(context);
    }

    public void encodeEnd(FacesContext context)
    {
        log.debug("encodeEnd");
        removeAliases(context);
    }

    public void queueEvent(FacesEvent event)
    {
        super.queueEvent(new FacesEventWrapper(event, this));
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        makeAliases();

        if (event instanceof FacesEventWrapper)
        {
            FacesEvent originalEvent = ((FacesEventWrapper) event).getWrappedFacesEvent();
            originalEvent.getComponent().broadcast(originalEvent);
        }
        else
        {
            super.broadcast(event);
        }

        removeAliases();
    }

    void makeAliases(FacesContext context)
    {
        _context = context;
        makeAliases();
    }

    private void makeAliases()
    {
        for (Iterator i = _aliases.iterator(); i.hasNext();)
            ((Alias) i.next()).make(_context);
    }

    void removeAliases(FacesContext context)
    {
        _context = context;
        removeAliases();
    }

    private void removeAliases()
    {
        for (Iterator i = _aliases.iterator(); i.hasNext();)
            ((Alias) i.next()).remove(_context);
    }

    public void handleBindings()
    {
        makeAliases(getFacesContext());

        RestoreStateUtils.recursivelyHandleComponentReferencesAndSetValid(getFacesContext(), this, true);

        removeAliases(getFacesContext());
    }

    @Override
    public boolean invokeOnComponent(FacesContext context, String clientId,
            ContextCallback callback) throws FacesException
    {
        makeAliases(getFacesContext());
        try
        {
            return super.invokeOnComponent(context, clientId, callback);
        }
        finally
        {
            removeAliases(getFacesContext());
        }
    }

}
