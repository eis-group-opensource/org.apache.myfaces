/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.tomahawk.application;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.autoscroll.AutoscrollBehaviorTagHandler;
import org.apache.myfaces.custom.autoscroll.AutoscrollBodyScript;
import org.apache.myfaces.custom.autoscroll.AutoscrollHiddenField;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.config.MyfacesConfig;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.tomahawk.util.TomahawkResourceUtils;

public class ResourceViewHandlerWrapper extends ViewHandlerWrapper
{
    private ViewHandler _delegate;

    public ResourceViewHandlerWrapper(ViewHandler delegate)
    {
        super();
        _delegate = delegate;
    }

    @Override
    public ViewHandler getWrapped()
    {
        return _delegate;
    }

    @Override
    public void renderView(FacesContext context, UIViewRoot viewToRender)
            throws IOException, FacesException
    {
        if (MyfacesConfig.getCurrentInstance(context.getExternalContext()).isAutoScroll() ||
            viewToRender.getAttributes().containsKey(AutoscrollBehaviorTagHandler.AUTOSCROLL_TAG_ON_PAGE))
        {
            AddResource addResource = AddResourceFactory.getInstance(context);

            if (!addResource.requiresBuffer())
            {
                //If the response is buffered, addResource instance takes
                //the responsability of render this script.
                AutoscrollBodyScript autoscrollBodyScript = (AutoscrollBodyScript) 
                    context.getApplication().createComponent(context, 
                        AutoscrollBodyScript.COMPONENT_TYPE,
                        AutoscrollBodyScript.DEFAULT_RENDERER_TYPE);
                autoscrollBodyScript.setTransient(true);
                autoscrollBodyScript.getAttributes().put(
                        JSFAttr.TARGET_ATTR, 
                        TomahawkResourceUtils.BODY_LOCATION);
                viewToRender.addComponentResource(context, autoscrollBodyScript);
            }
            
            AutoscrollHiddenField autoscrollHiddenField = (AutoscrollHiddenField) context.getApplication().
                createComponent(context, 
                        AutoscrollHiddenField.COMPONENT_TYPE,
                        AutoscrollHiddenField.DEFAULT_RENDERER_TYPE);
            autoscrollHiddenField.setTransient(true);
            autoscrollHiddenField.getAttributes().put(JSFAttr.TARGET_ATTR, TomahawkResourceUtils.FORM_LOCATION);
            viewToRender.addComponentResource(context, autoscrollHiddenField);
        }
        
        //Reset the added resource 
        TomahawkResourceUtils.resetAddedResources(context);
        
        _publishPreRenderViewAddResourceEvent(context, viewToRender);
        super.renderView(context, viewToRender);
    }
    
    private void _publishPreRenderViewAddResourceEvent(FacesContext context, UIComponent component)
    {
        context.getApplication().publishEvent(context,  PreRenderViewAddResourceEvent.class, UIComponent.class, component);
        
        //Scan children
        if (component.getChildCount() > 0)
        {
            for (UIComponent child : component.getChildren())
            {
                _publishPreRenderViewAddResourceEvent(context, child);
            }
        }

        //Scan facets
        if (component.getFacetCount() > 0)
        {
            for (Map.Entry<String, UIComponent> entry : component.getFacets().entrySet())
            {
                _publishPreRenderViewAddResourceEvent(context, entry.getValue());
            }
        }
    }
}
