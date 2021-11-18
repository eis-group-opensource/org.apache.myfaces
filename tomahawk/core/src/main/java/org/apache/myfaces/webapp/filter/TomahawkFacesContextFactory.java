/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.tomahawk.util.ExternalContextUtils;

/**
 * The objective of this factory is this:
 * <ol>
 * <li> Wrap a multipart request (used for t:inputFileUpload), 
 *    so the request could be correctly decoded.</li>
 * <li>If a buffered instance of AddResource is configured, 
 *    ExtensionsFilter must buffer and add the resource reference 
 *    to the head of jsf pages (for example when it is used 
 *    DefaultAddResource)</li>
 * </ol>
 * 
 * @since 1.1.7
 * @author Martin Marinschek (latest modification by $Author: lu4242 $)
 * @version $Revision: 697311 $ $Date: 2008-09-20 04:14:01 +0300 (Sat, 20 Sep 2008) $
 */
public class TomahawkFacesContextFactory extends FacesContextFactory {

    /**
     * Disable or enable this factory to wrap the request using 
     * TomahawkFacesContextWrapper as an alternative to ExtensionsFilter.
     * If ExtensionsFilter is configured, use of TomahawkFacesContextWrapper
     * is skipped
     */
    public static final String DISABLE_TOMAHAWK_FACES_CONTEXT_WRAPPER = 
        "org.apache.myfaces.DISABLE_TOMAHAWK_FACES_CONTEXT_WRAPPER";
    
    public static final boolean DISABLE_TOMAHAWK_FACES_CONTEXT_WRAPPER_DEFAULT = true;
    
    private FacesContextFactory delegate;

    public TomahawkFacesContextFactory(FacesContextFactory delegate) {
        this.delegate = delegate;
    }

    public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle) throws FacesException {
        
        if(!ExternalContextUtils.getRequestType(context, request).isPortlet())
        {
            ServletRequest servletRequest = (ServletRequest) request;
            ServletContext servletContext = (ServletContext) context;
            // If no ExtensionsFilter wraps before the call, and 
            //there is not a value indicating disabling the wrapping,
            //use TomahawkFacesContextWrapper 
            if (servletRequest.getAttribute(ExtensionsFilter.DOFILTER_CALLED) == null &&
                    !getBooleanValue(servletContext.getInitParameter(
                            DISABLE_TOMAHAWK_FACES_CONTEXT_WRAPPER),
                            DISABLE_TOMAHAWK_FACES_CONTEXT_WRAPPER_DEFAULT))
            {
                //This is servlet world
                //For handle buffered response we need to wrap response object here,
                //so all response will be written and then on facesContext
                //release() method write to the original response.
                //This could not be done on TomahawkFacesContextWrapper
                //constructor, because the delegate ExternalContext do
                //calls like dispatch, forward and redirect, that requires
                //the wrapped response instance to work properly.            
                AddResource addResource = AddResourceFactory.getInstance((HttpServletRequest)request,(ServletContext)context);
                
                if (addResource.requiresBuffer())
                {
                    ExtensionsResponseWrapper extensionsResponseWrapper = new ExtensionsResponseWrapper((HttpServletResponse)response);
                    return new TomahawkFacesContextWrapper(delegate.getFacesContext(context, request, extensionsResponseWrapper, lifecycle),
                            extensionsResponseWrapper);
                }
                else
                {
                    return new TomahawkFacesContextWrapper(delegate.getFacesContext(context, request, response, lifecycle));
                }
            }
            else
            {
                //The wrapping was done by extensions filter, so 
                //TomahawkFacesContextWrapper is not needed
                return delegate.getFacesContext(context, request, response, lifecycle);
            }
        }
        else
        {
            if (!PortletUtils.isDisabledTomahawkFacesContextWrapper(context))
            {            
                return new TomahawkFacesContextWrapper(delegate.getFacesContext(context, request, response, lifecycle));
            }
        }    
        return delegate.getFacesContext(context, request, response, lifecycle);
    }
    
    private static boolean getBooleanValue(String initParameter, boolean defaultVal)
    {
        if(initParameter == null || initParameter.trim().length()==0)
            return defaultVal;

        return (initParameter.equalsIgnoreCase("on") || initParameter.equals("1") || initParameter.equalsIgnoreCase("true"));
    }    
}
