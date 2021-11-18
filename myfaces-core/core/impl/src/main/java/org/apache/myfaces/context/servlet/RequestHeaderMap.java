/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.util.AbstractAttributeMap;


/**
 * HttpServletRequest headers as Map.
 * 
 * @author Anton Koinov (latest modification by $Author: lu4242 $)
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public final class RequestHeaderMap extends AbstractAttributeMap<String>
{
    private final HttpServletRequest _httpServletRequest;

    RequestHeaderMap(final HttpServletRequest httpServletRequest)
    {
        _httpServletRequest = httpServletRequest;
    }

    @Override
    protected String getAttribute(final String key)
    {
        return _httpServletRequest.getHeader(key);
    }

    @Override
    protected void setAttribute(final String key, final String value)
    {
        throw new UnsupportedOperationException(
            "Cannot set HttpServletRequest Header");
    }

    @Override
    protected void removeAttribute(final String key)
    {
        throw new UnsupportedOperationException(
            "Cannot remove HttpServletRequest Header");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _httpServletRequest.getHeaderNames();
    }

    @Override
    public void putAll(final Map<? extends String, ? extends String> t)
    {
        throw new UnsupportedOperationException();
    }


    @Override
    public void clear()
    {
        throw new UnsupportedOperationException();
    }    
}
