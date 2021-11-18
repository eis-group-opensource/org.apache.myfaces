/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.tomahawk.util.ExternalContextUtils;

/**
 * This listener is used for serve resources, as a replacement of 
 * ExtensionsFilter serve resources feature.
 * <p>
 * The idea is map FacesServlet to org.apache.myfaces.RESOURCE_VIRTUAL_PATH
 * (Default is "/faces/myFacesExtensionResource), so this
 * listener can receive the request.
 * </p>
 * 
 * @author Martin Marinschek (latest modification by $Author: lu4242 $)
 * @version $Revision: 685725 $ $Date: 2008-08-14 02:14:31 +0300 (Thu, 14 Aug 2008) $
 */
public class ServeResourcePhaseListener implements PhaseListener {

    private Log log = LogFactory.getLog(ServeResourcePhaseListener.class);

    public static final String DOFILTER_CALLED = "org.apache.myfaces.component.html.util.ExtensionFilter.doFilterCalled";

    public void afterPhase(PhaseEvent event) {
    }

    public void beforePhase(PhaseEvent event) {
        if(event.getPhaseId()==PhaseId.RESTORE_VIEW || event.getPhaseId()==PhaseId.RENDER_RESPONSE) {

            FacesContext fc = event.getFacesContext();
            ExternalContext externalContext = event.getFacesContext().getExternalContext();

            if(externalContext.getRequestMap().get(DOFILTER_CALLED)!=null)
            {
                //we have already been called (before-restore-view, and we are now in render-response),
                // no need to do everything again...
                return;
            }

            externalContext.getRequestMap().put(DOFILTER_CALLED,"true");


            //Use ExternalContextUtils to find if this is a portled request
            //if(externalContext.getRequest() instanceof PortletRequest) {            
            if(ExternalContextUtils.getRequestType(externalContext).isPortlet()) {
                //we are in portlet-world! in portlet 1.0 (JSR-168), we cannot do anything here, but
                //TODO in portlet 2.0 (JSR-286), we will write the resource to the stream here if we
                //get a resource-request (resource-requests are only available in 286)
                if(log.isDebugEnabled()) {
                    log.debug("We are in portlet-space, but we cannot do anything here in JSR-168 - " +
                            "for resource-serving, our resource-servlet has to be registered.");
                }
            }
            else if(externalContext.getResponse() instanceof HttpServletResponse) {

                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
                ServletContext context = (ServletContext) fc.getExternalContext().getContext();

                // Serve resources
                AddResource addResource;

                try
                {
                    addResource= AddResourceFactory.getInstance(request, context);
                    if( addResource.isResourceUri(context, request ) ){
                        addResource.serveResource(context, request, response);
                        event.getFacesContext().responseComplete();
                        return;
                    }
                }
                catch(Throwable th)
                {
                    log.error("Exception wile retrieving addResource",th);
                    throw new FacesException(th);
                }
            }
            else {
                if(log.isDebugEnabled()) {
                    log.debug("Response of type : "+(
                            externalContext.getResponse()==null?"null":externalContext.getResponse().getClass().getName())+" not handled so far.");
                }
            }
        }
    }

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
