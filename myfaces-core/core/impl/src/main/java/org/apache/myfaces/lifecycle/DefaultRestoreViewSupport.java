/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.lifecycle;

import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_impl.renderkit.RendererUtils;
import org.apache.myfaces.shared_impl.util.Assert;
import org.apache.myfaces.shared_impl.util.RestoreStateUtils;

/**
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 825575 $ $Date: 2009-10-15 20:18:20 +0300 (Thu, 15 Oct 2009) $
 */
public class DefaultRestoreViewSupport implements RestoreViewSupport
{
    private static final String JAVAX_SERVLET_INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";

    private static final String JAVAX_SERVLET_INCLUDE_PATH_INFO = "javax.servlet.include.path_info";
    
    /**
     * Constant defined on javax.portlet.faces.Bridge class that helps to 
     * define if the current request is a portlet request or not.
     */
    private static final String PORTLET_LIFECYCLE_PHASE = "javax.portlet.faces.phase";

    private final Log log = LogFactory.getLog(DefaultRestoreViewSupport.class);

    public void processComponentBinding(FacesContext facesContext, UIComponent component)
    {
        ValueExpression binding = component.getValueExpression("binding");
        if (binding != null)
        {
            binding.setValue(facesContext.getELContext(), component);
        }
        
        //This part is for make compatibility with t:aliasBean, because
        //this components has its own code before and after binding is 
        //set for child components.
        RestoreStateUtils.recursivelyHandleComponentReferencesAndSetValid(facesContext,component);

        //The required behavior for the spec is call recursively this method
        //for walk the component tree. 
        //for (Iterator<UIComponent> iter = component.getFacetsAndChildren(); iter.hasNext();)
        //{
        //    processComponentBinding(facesContext, iter.next());
        //}
    }

    public String calculateViewId(FacesContext facesContext)
    {
        Assert.notNull(facesContext);
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, Object> requestMap = externalContext.getRequestMap();

        String viewId = null;
        boolean traceEnabled = log.isTraceEnabled();
        
        if (requestMap.containsKey(PORTLET_LIFECYCLE_PHASE))
        {
            viewId = (String) externalContext.getRequestPathInfo();
        }
        else
        {
            viewId = (String) requestMap.get(JAVAX_SERVLET_INCLUDE_PATH_INFO);
            if (viewId != null)
            {
                if (traceEnabled)
                {
                    log.trace("Calculated viewId '" + viewId + "' from request param '" + JAVAX_SERVLET_INCLUDE_PATH_INFO
                            + "'");
                }
            }
            else
            {
                viewId = externalContext.getRequestPathInfo();
                if (viewId != null && traceEnabled)
                {
                    log.trace("Calculated viewId '" + viewId + "' from request path info");
                }
            }
    
            if (viewId == null)
            {
                viewId = (String) requestMap.get(JAVAX_SERVLET_INCLUDE_SERVLET_PATH);
                if (viewId != null && traceEnabled)
                {
                    log.trace("Calculated viewId '" + viewId + "' from request param '"
                            + JAVAX_SERVLET_INCLUDE_SERVLET_PATH + "'");
                }
            }
        }
        
        if (viewId == null)
        {
            viewId = externalContext.getRequestServletPath();
            if (viewId != null && traceEnabled)
            {
                log.trace("Calculated viewId '" + viewId + "' from request servlet path");
            }
        }

        if (viewId == null)
        {
            throw new FacesException("Could not determine view id.");
        }

        return viewId;
    }

    public boolean isPostback(FacesContext facesContext)
    {
        ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
        String renderkitId = viewHandler.calculateRenderKitId(facesContext);
        ResponseStateManager rsm = RendererUtils.getResponseStateManager(facesContext, renderkitId);
        return rsm.isPostback(facesContext);
    }
}
