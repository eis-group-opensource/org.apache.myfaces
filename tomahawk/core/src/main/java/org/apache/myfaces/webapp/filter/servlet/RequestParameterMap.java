/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter.servlet;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

/**
 * ServletRequest parameters as Map.
 * 
 * <p>
 * NOTE: This class was copied from myfaces impl 
 * org.apache.myfaces.context.servlet and it is
 * used by TomahawkFacesContextWrapper. By that reason, it could change
 * in the future.
 * </p>
 * 
 * @since 1.1.7
 * @author Anton Koinov (latest modification by $Author: lu4242 $)
 * @version $Revision: 691871 $ $Date: 2008-09-04 07:32:08 +0300 (Thu, 04 Sep 2008) $
 */
public class RequestParameterMap extends AbstractAttributeMap
{
    private final ServletRequest _servletRequest;

    RequestParameterMap(ServletRequest servletRequest)
    {
        _servletRequest = servletRequest;
    }

    protected Object getAttribute(String key)
    {
        return _servletRequest.getParameter(key);
    }

    protected void setAttribute(String key, Object value)
    {
        throw new UnsupportedOperationException(
            "Cannot set ServletRequest Parameter");
    }

    protected void removeAttribute(String key)
    {
        throw new UnsupportedOperationException(
            "Cannot remove ServletRequest Parameter");
    }

    protected Enumeration getAttributeNames()
    {
        return _servletRequest.getParameterNames();
    }
}