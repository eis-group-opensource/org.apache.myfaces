/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.Enumeration;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.util.AbstractAttributeMap;

/**
 * HttpServletRequest Cookies as Map.
 *
 * @author Dimitry D'hondt
 * @author Anton Koinov
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public final class CookieMap extends AbstractAttributeMap<Cookie>
{
    private static final Cookie[] EMPTY_ARRAY = new Cookie[0];

    private final HttpServletRequest _httpServletRequest;

    CookieMap(final HttpServletRequest httpServletRequest)
    {
        _httpServletRequest = httpServletRequest;
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException(
            "Cannot clear HttpRequest Cookies");
    }

    @Override
    public boolean containsValue(final Object findValue)
    {
        if (findValue == null)
        {
            return false;
        }

        final Cookie[] cookies = _httpServletRequest.getCookies();
        if (cookies == null)
            return false;
        for (int i = 0, len = cookies.length; i < len; i++)
        {
            if (findValue.equals(cookies[i]))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty()
    {
        final Cookie[] cookies = _httpServletRequest.getCookies();
        return cookies == null || cookies.length == 0;
    }

    @Override
    public int size()
    {
        final Cookie[] cookies = _httpServletRequest.getCookies();
        return cookies == null ? 0 : cookies.length;
    }

    @Override
    public void putAll(final Map t)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Cookie getAttribute(final String key)
    {
        final Cookie[] cookies = _httpServletRequest.getCookies();
        if (cookies == null)
            return null;
        for (int i = 0, len = cookies.length; i < len; i++)
        {
            if (cookies[i].getName().equals(key))
            {
                return cookies[i];
            }
        }

        return null;
    }

    @Override
    protected void setAttribute(final String key, final Cookie value)
    {
        throw new UnsupportedOperationException(
            "Cannot set HttpRequest Cookies");
    }

    @Override
    protected void removeAttribute(final String key)
    {
        throw new UnsupportedOperationException(
            "Cannot remove HttpRequest Cookies");
    }

    @Override
    protected Enumeration<String> getAttributeNames()
    {
        final Cookie[] cookies = _httpServletRequest.getCookies();

        return cookies == null ? new CookieNameEnumeration(EMPTY_ARRAY) : new CookieNameEnumeration(cookies);
  
    }

    private static class CookieNameEnumeration implements Enumeration<String>
    {
        private final Cookie[] _cookies;
        private final int _length;
        private int _index;

        public CookieNameEnumeration(final Cookie[] cookies)
        {
            _cookies = cookies;
            _length = cookies.length;
        }

        public boolean hasMoreElements()
        {
            return _index < _length;
        }

        public String nextElement()
        {
            if (!hasMoreElements())
            {
                throw new NoSuchElementException();
            }
            return _cookies[_index++].getName();
        }
    }
}
