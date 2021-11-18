/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.util.AbstractAttributeMap;

/**
 * HttpServletRequest header values (multi-value headers) as Map of String[].
 * 
 * @author Anton Koinov (latest modification by $Author: lu4242 $)
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public final class RequestHeaderValuesMap extends AbstractAttributeMap<String[]>
{
    private final HttpServletRequest _httpServletRequest;
    private final Map<String, String[]> _valueCache = new HashMap<String, String[]>();

    RequestHeaderValuesMap(final HttpServletRequest httpServletRequest)
    {
        _httpServletRequest = httpServletRequest;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected String[] getAttribute(final String key)
    {
        String[] ret = _valueCache.get(key);
        if (ret == null)
        {
            _valueCache.put(key, ret = toArray(_httpServletRequest.getHeaders(key)));
        }

        return ret;
    }

    @Override
    protected void setAttribute(final String key, final String[] value)
    {
        throw new UnsupportedOperationException("Cannot set HttpServletRequest HeaderValues");
    }

    @Override
    protected void removeAttribute(final String key)
    {
        throw new UnsupportedOperationException("Cannot remove HttpServletRequest HeaderValues");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _httpServletRequest.getHeaderNames();
    }

    private String[] toArray(Enumeration<String> e)
    {
        List<String> ret = new ArrayList<String>();

        while (e.hasMoreElements())
        {
            ret.add(e.nextElement());
        }

        return ret.toArray(new String[ret.size()]);
    }
}
