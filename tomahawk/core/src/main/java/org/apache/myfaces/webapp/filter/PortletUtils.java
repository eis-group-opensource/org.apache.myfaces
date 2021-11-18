/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import org.apache.commons.fileupload.portlet.PortletFileUpload;

/**
 * This class is used to hold some utilities used by tomahawk in portlet
 * environments. The idea is that all calls to portlet api methods
 * should be done here, to avoid ClassNotFoundException error in
 * servlet environments.
 * 
 * The public methods should not use classes on portlet api, so the
 * other classes calling this class does not have dependencies to this api.
 * 
 * @since 1.1.8
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 954965 $ $Date: 2010-06-15 19:58:31 +0300 (Tue, 15 Jun 2010) $
 */
public class PortletUtils
{

    public static boolean isDisabledTomahawkFacesContextWrapper(Object contextOrConfig)
    {
        PortletContext portletContext = null;
        if (contextOrConfig instanceof PortletConfig)
        {
            portletContext = ((PortletConfig)contextOrConfig).getPortletContext();
        }
        else
        {
            portletContext = (PortletContext)contextOrConfig;
        }
        
        return getBooleanValue(portletContext.getInitParameter(
                TomahawkFacesContextFactory.DISABLE_TOMAHAWK_FACES_CONTEXT_WRAPPER),
                TomahawkFacesContextFactory.DISABLE_TOMAHAWK_FACES_CONTEXT_WRAPPER_DEFAULT);
    }
    
    private static boolean getBooleanValue(String initParameter, boolean defaultVal)
    {
        if(initParameter == null || initParameter.trim().length()==0)
            return defaultVal;

        return (initParameter.equalsIgnoreCase("on") || initParameter.equals("1") || initParameter.equalsIgnoreCase("true"));
    }
    
    public static boolean isMultipartContent(Object request)
    {
        if (request instanceof ActionRequest)
        {
            return PortletFileUpload.isMultipartContent((ActionRequest)request);
        }
        else
        {
            return false;
        }
    }
    
    public static String getContextInitParameter(Object context, String paramName)
    {
        PortletContext portletContext = (PortletContext) context;
        return portletContext.getInitParameter(paramName);
    }
    
    public static Object getAttribute(Object context, String key)
    {
        PortletContext portletContext = (PortletContext) context;
        return portletContext.getAttribute(key);
    }
    
    public static void setAttribute(Object context, String key, Object value)
    {
        PortletContext portletContext = (PortletContext) context;
        portletContext.setAttribute(key, value);
    }
}
