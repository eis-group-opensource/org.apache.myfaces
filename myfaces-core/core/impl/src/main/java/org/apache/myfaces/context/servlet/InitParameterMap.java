/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.myfaces.util.AbstractAttributeMap;


/**
 * ServletContext init parameters as Map.
 * 
 * @author Anton Koinov (latest modification by $Author: lu4242 $)
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public final class InitParameterMap extends AbstractAttributeMap<String>
{
    private final ServletContext _servletContext;

    InitParameterMap(final ServletContext servletContext)
    {
        _servletContext = servletContext;
    }

    @Override
    protected String getAttribute(final String key)
    {
        return _servletContext.getInitParameter(key);
    }

    @Override
    protected void setAttribute(final String key, final String value)
    {
        throw new UnsupportedOperationException(
            "Cannot set ServletContext InitParameter");
    }

    @Override
    protected void removeAttribute(final String key)
    {
        throw new UnsupportedOperationException(
            "Cannot remove ServletContext InitParameter");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _servletContext.getInitParameterNames();
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
