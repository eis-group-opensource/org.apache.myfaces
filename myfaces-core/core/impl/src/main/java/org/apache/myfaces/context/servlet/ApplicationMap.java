/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.myfaces.util.AbstractThreadSafeAttributeMap;


/**
 * ServletContext attributes as a Map.
 *
 * @author Anton Koinov (latest modification by $Author: lu4242 $)
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public final class ApplicationMap extends AbstractThreadSafeAttributeMap<Object>
{
    final ServletContext _servletContext;

    ApplicationMap(final ServletContext servletContext)
    {
        _servletContext = servletContext;
    }

    @Override
    protected Object getAttribute(final String key)
    {
        return _servletContext.getAttribute(key);
    }

    @Override
    protected void setAttribute(final String key, final Object value)
    {
        _servletContext.setAttribute(key, value);
    }

    @Override
    protected void removeAttribute(final String key)
    {
        _servletContext.removeAttribute(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _servletContext.getAttributeNames();
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
