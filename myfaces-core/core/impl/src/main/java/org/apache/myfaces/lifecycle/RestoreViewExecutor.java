/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.lifecycle;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ViewExpiredException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.portlet.PortletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.portlet.MyFacesGenericPortlet;
import org.apache.myfaces.portlet.PortletUtil;
import org.apache.myfaces.util.DebugUtils;

/**
 * Implements the Restore View Phase (JSF Spec 2.2.1)
 * 
 * @author Nikolay Petrov
 * @author Bruno Aranda (JSF 1.2)
 * @version $Revision: 673926 $ $Date: 2008-07-04 10:03:53 +0300 (Fri, 04 Jul 2008) $
 * 
 */
class RestoreViewExecutor implements PhaseExecutor
{

    private static final Log log = LogFactory.getLog(RestoreViewExecutor.class);
    private RestoreViewSupport _restoreViewSupport;

    public boolean execute(FacesContext facesContext)
    {
        if (facesContext == null)
        {
            throw new FacesException("FacesContext is null");
        }

        // init the View
        Application application = facesContext.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        viewHandler.initView(facesContext);

        UIViewRoot viewRoot = facesContext.getViewRoot();

        RestoreViewSupport restoreViewSupport = getRestoreViewSupport();
        
        if (viewRoot != null)
        {
            if (log.isTraceEnabled())
                log.trace("View already exists in the FacesContext");

            viewRoot.setLocale(facesContext.getExternalContext().getRequestLocale());
            restoreViewSupport.processComponentBinding(facesContext, viewRoot);
            return false;
        }

        String viewId = restoreViewSupport.calculateViewId(facesContext);

        // Determine if this request is a postback or initial request
        if (restoreViewSupport.isPostback(facesContext))
        {
            if (log.isTraceEnabled())
                log.trace("Request is a postback");

            viewRoot = viewHandler.restoreView(facesContext, viewId);
            if (viewRoot == null)
            {
                throw new ViewExpiredException(
                    "No saved view state could be found for the view identifier: "
                        + viewId, viewId);
            }
            restoreViewSupport.processComponentBinding(facesContext, viewRoot);
        }
        else
        {
            if (log.isTraceEnabled())
                log.trace("Request is not a postback. New UIViewRoot will be created");

            viewRoot = viewHandler.createView(facesContext, viewId);
            facesContext.renderResponse();
        }

        facesContext.setViewRoot(viewRoot);

        return false;
    }

    protected RestoreViewSupport getRestoreViewSupport()
    {
        if (_restoreViewSupport == null)
        {
            _restoreViewSupport = new DefaultRestoreViewSupport();
        }
        return _restoreViewSupport;
    }

    /**
     * @param restoreViewSupport
     *            the restoreViewSupport to set
     */
    public void setRestoreViewSupport(RestoreViewSupport restoreViewSupport)
    {
        _restoreViewSupport = restoreViewSupport;
    }

    public PhaseId getPhase()
    {
        return PhaseId.RESTORE_VIEW;
    }

    /**
     * TODO place that stuff into the default view handler implementation.
     */
    private static String deriveViewId(FacesContext facesContext)
    {
        ExternalContext externalContext = facesContext.getExternalContext();

        if (PortletUtil.isPortletRequest(facesContext))
        {
            PortletRequest request = (PortletRequest) externalContext.getRequest();
            return request.getParameter(MyFacesGenericPortlet.VIEW_ID);
        }

        String viewId = externalContext.getRequestPathInfo(); // getPathInfo
        if (viewId == null)
        {
            // No extra path info found, so it is probably extension mapping
            viewId = externalContext.getRequestServletPath(); // getServletPath
            DebugUtils.assertError(viewId != null, log,
                    "RequestServletPath is null, cannot determine viewId of current page.");
            if (viewId == null)
                return null;

            // TODO: JSF Spec 2.2.1 - what do they mean by "if the default
            // ViewHandler implementation is used..." ?
            // - probably that this should use DefaultViewHandlerSupport.calculateServletFacesMapping
            // rather than duplicating the logic here.
            String defaultSuffix = externalContext.getInitParameter(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);
            String suffix = defaultSuffix != null ? defaultSuffix : ViewHandler.DEFAULT_SUFFIX;
            DebugUtils.assertError(suffix.charAt(0) == '.', log, "Default suffix must start with a dot!");

            int slashPos = viewId.lastIndexOf('/');
            int extensionPos = viewId.lastIndexOf('.');
            if (extensionPos == -1 || extensionPos <= slashPos)
            {
                log.error("Assumed extension mapping, but there is no extension in " + viewId);
                viewId = null;
            }
            else
            {
                viewId = viewId.substring(0, extensionPos) + suffix;
            }
        }

        return viewId;
    }
}
