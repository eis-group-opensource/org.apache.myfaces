/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.myfaces.util.AbstractAttributeMap;


/**
 * ServletRequest attributes Map.
 * 
 * @author Anton Koinov (latest modification by $Author: lu4242 $)
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public final class RequestMap extends AbstractAttributeMap<Object>
{
    final ServletRequest _servletRequest;

    RequestMap(final ServletRequest servletRequest)
    {
        _servletRequest = servletRequest;
    }

    @Override
    protected Object getAttribute(final String key)
    {
        return _servletRequest.getAttribute(key);
    }

    @Override
    protected void setAttribute(final String key, final Object value)
    {
        _servletRequest.setAttribute(key, value);
    }

    @Override
    protected void removeAttribute(final String key)
    {
        _servletRequest.removeAttribute(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _servletRequest.getAttributeNames();
    }

    @Override
    public void putAll(final Map<? extends String, ? extends Object> t)
    {
        throw new UnsupportedOperationException();
    }


    @Override
    public void clear()
    {
        throw new UnsupportedOperationException();
    }    
}
