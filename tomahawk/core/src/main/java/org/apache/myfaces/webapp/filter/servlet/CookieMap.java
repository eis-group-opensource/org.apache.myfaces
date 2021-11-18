/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter.servlet;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest Cookies as Map.
 * <p>
 * NOTE: This class was copied from myfaces impl 
 * org.apache.myfaces.context.servlet and it is
 * used by TomahawkFacesContextWrapper. By that reason, it could change
 * in the future.
 * </p>
 * 
 * @since 1.1.7
 * @author Dimitry D'hondt
 * @author Anton Koinov
 * @version $Revision: 691871 $ $Date: 2008-09-04 07:32:08 +0300 (Thu, 04 Sep 2008) $
 */
public class CookieMap extends AbstractAttributeMap
{
    private static final Cookie[] EMPTY_ARRAY = new Cookie[0];

    final HttpServletRequest _httpServletRequest;

    CookieMap(HttpServletRequest httpServletRequest)
    {
        _httpServletRequest = httpServletRequest;
    }

    public void clear()
    {
        throw new UnsupportedOperationException(
            "Cannot clear HttpRequest Cookies");
    }

    public boolean containsKey(Object key)
    {
        Cookie[] cookies = _httpServletRequest.getCookies();
        if (cookies == null) return false;
        for (int i = 0, len = cookies.length; i < len; i++)
        {
            if (cookies[i].getName().equals(key))
            {
                return true;
            }
        }

        return false;
    }

    public boolean containsValue(Object findValue)
    {
        if (findValue == null)
        {
            return false;
        }

        Cookie[] cookies = _httpServletRequest.getCookies();
        if (cookies == null) return false;
        for (int i = 0, len = cookies.length; i < len; i++)
        {
            if (findValue.equals(cookies[i]))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isEmpty()
    {
        Cookie[] cookies = _httpServletRequest.getCookies();
        return cookies == null || cookies.length == 0;
    }

    public int size()
    {
        Cookie[] cookies = _httpServletRequest.getCookies();
        return cookies == null ? 0 : cookies.length;
    }

    public void putAll(Map t)
    {
        throw new UnsupportedOperationException();
    }


    protected Object getAttribute(String key)
    {
        Cookie[] cookies = _httpServletRequest.getCookies();
        if (cookies == null) return null;
        for (int i = 0, len = cookies.length; i < len; i++)
        {
            if (cookies[i].getName().equals(key))
            {
                return cookies[i];
            }
        }

        return null;
    }

    protected void setAttribute(String key, Object value)
    {
        throw new UnsupportedOperationException(
            "Cannot set HttpRequest Cookies");
    }

    protected void removeAttribute(String key)
    {
        throw new UnsupportedOperationException(
            "Cannot remove HttpRequest Cookies");
    }

    protected Enumeration getAttributeNames()
    {
        Cookie[] cookies = _httpServletRequest.getCookies();
        if (cookies == null)
        {
            return new CookieNameEnumeration(EMPTY_ARRAY);
        }
        else
        {
            return new CookieNameEnumeration(cookies);
        }
    }

    private static class CookieNameEnumeration implements Enumeration
    {
        private final Cookie[] _cookies;
        private final int _length;
        private int _index;

        public CookieNameEnumeration(Cookie[] cookies)
        {
            _cookies = cookies;
            _length = cookies.length;
        }

        public boolean hasMoreElements()
        {
            return _index < _length;
        }

        public Object nextElement()
        {
            return _cookies[_index++].getName();
        }
    }
}
