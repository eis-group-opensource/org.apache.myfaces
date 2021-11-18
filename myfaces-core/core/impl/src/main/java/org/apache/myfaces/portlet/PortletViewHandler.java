/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.portlet;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO: test this portlet view handler in portlet environment
 * 
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 517404 $ $Date: 2007-03-12 23:17:24 +0200 (Mon, 12 Mar 2007) $
 */
public class PortletViewHandler extends ViewHandlerWrapper
{
    private final ViewHandler _viewHandler;

    private static final Log log = LogFactory.getLog(PortletViewHandler.class);

    public PortletViewHandler(ViewHandler viewHandler)
    {
        _viewHandler = viewHandler;
    }

    @Override
    protected ViewHandler getWrapped()
    {
        return _viewHandler;
    }

    @Override
    public UIViewRoot restoreView(FacesContext context, String viewId)
    {
        if (PortletUtil.isPortletRequest(context))
        {
            PortletRequest request = (PortletRequest) context.getExternalContext().getRequest();
            String portletViewId = request.getParameter(MyFacesGenericPortlet.VIEW_ID);
            Application application = context.getApplication();
            ViewHandler applicationViewHandler = application.getViewHandler();
            String renderKitId = applicationViewHandler.calculateRenderKitId(context);
            UIViewRoot viewRoot = application.getStateManager().restoreView(context, portletViewId, renderKitId);
            return viewRoot;
        }
        return super.restoreView(context, viewId);
    }

    @Override
    public UIViewRoot createView(FacesContext context, String viewId)
    {
        UIViewRoot viewRoot = super.createView(context, viewId);
        if (PortletUtil.isPortletRequest(context))
        {
            PortletRequest request = (PortletRequest) context.getExternalContext().getRequest();
            viewRoot.setViewId(request.getParameter(MyFacesGenericPortlet.VIEW_ID));
        }
        return viewRoot;
    }

    @Override
    public String getActionURL(FacesContext context, String viewId)
    {
        if (PortletUtil.isRenderResponse(context))
        {
            RenderResponse response = (RenderResponse) context.getExternalContext().getResponse();
            PortletURL url = response.createActionURL();
            url.setParameter(MyFacesGenericPortlet.VIEW_ID, viewId);
            return url.toString();
        }
        return super.getActionURL(context, viewId);
    }

    @Override
    public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException
    {
        if (PortletUtil.isPortletRequest(context))
        {
            if (viewToRender.isRendered())
            {
                if (log.isTraceEnabled())
                    log.trace("It is a portlet request. Dispatching to view");
                context.getExternalContext().dispatch(viewToRender.getViewId());
            }
        }
        else
        {
            super.renderView(context, viewToRender);
        }
    }
}
