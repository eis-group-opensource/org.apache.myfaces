/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter.servlet;

import org.apache.myfaces.shared_tomahawk.util.NullEnumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;


/**
 * HttpSession attibutes as Map.
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
public class SessionMap extends AbstractAttributeMap
{
    private final HttpServletRequest _httpRequest;

    SessionMap(HttpServletRequest httpRequest)
    {
        _httpRequest = httpRequest;
    }

    protected Object getAttribute(String key)
    {
        HttpSession httpSession = getSession();
        return (httpSession == null)
            ? null : httpSession.getAttribute(key.toString());
    }

    protected void setAttribute(String key, Object value)
    {
        _httpRequest.getSession(true).setAttribute(key, value);
    }

    protected void removeAttribute(String key)
    {
        HttpSession httpSession = getSession();
        if (httpSession != null)
        {
            httpSession.removeAttribute(key);
        }
    }

    protected Enumeration getAttributeNames()
    {
        HttpSession httpSession = getSession();
        return (httpSession == null)
            ? NullEnumeration.instance()
            : httpSession.getAttributeNames();
    }

    private HttpSession getSession()
    {
        return _httpRequest.getSession(false);
    }


    public void putAll(Map t)
    {
        throw new UnsupportedOperationException();
    }


    public void clear()
    {
        throw new UnsupportedOperationException();
    }
}