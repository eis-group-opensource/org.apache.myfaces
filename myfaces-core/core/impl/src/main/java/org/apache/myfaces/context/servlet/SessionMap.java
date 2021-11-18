/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.myfaces.shared_impl.util.NullEnumeration;
import org.apache.myfaces.util.AbstractThreadSafeAttributeMap;


/**
 * HttpSession attibutes as Map.
 *
 * @author Anton Koinov (latest modification by $Author: lu4242 $)
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public final class SessionMap extends AbstractThreadSafeAttributeMap<Object>
{
    private final HttpServletRequest _httpRequest;

    SessionMap(final HttpServletRequest httpRequest)
    {
        _httpRequest = httpRequest;
    }

    @Override
    protected Object getAttribute(final String key)
    {
        final HttpSession httpSession = _getSession();
        return (httpSession == null) ? null : httpSession.getAttribute(key);
    }

    @Override
    protected void setAttribute(final String key, final Object value)
    {
        _httpRequest.getSession(true).setAttribute(key, value);
    }

    @Override
    protected void removeAttribute(final String key)
    {
        final HttpSession httpSession = _getSession();
        if (httpSession != null)
        {
            httpSession.removeAttribute(key);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        final HttpSession httpSession = _getSession();
        return (httpSession == null) ? NullEnumeration.instance() : httpSession.getAttributeNames();
    }

    @Override
    public void putAll(final Map<? extends String, ? extends Object> t)
    {
        throw new UnsupportedOperationException();
    }
    
    // we can use public void clear() from super-class
    
    private HttpSession _getSession()
    {
        return _httpRequest.getSession(false);
    }

}
