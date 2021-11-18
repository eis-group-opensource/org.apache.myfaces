/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

import org.apache.myfaces.util.AbstractAttributeMap;

/**
 * ServletRequest multi-value parameters as Map.
 * 
 * @author Anton Koinov (latest modification by $Author: bommel $)
 * @version $Revision: 693059 $ $Date: 2008-09-08 14:42:28 +0300 (Mon, 08 Sep 2008) $
 */
public final class RequestParameterValuesMap extends AbstractAttributeMap<String[]>
{
    private final ServletRequest _servletRequest;

    RequestParameterValuesMap(final ServletRequest servletRequest)
    {
        _servletRequest = servletRequest;
    }

    @Override
    protected String[] getAttribute(final String key)
    {
        return _servletRequest.getParameterValues(key);
    }

    @Override
    protected void setAttribute(final String key, final String[] value)
    {
        throw new UnsupportedOperationException(
            "Cannot set ServletRequest ParameterValues");
    }

    @Override
    protected void removeAttribute(final String key)
    {
        throw new UnsupportedOperationException(
            "Cannot remove ServletRequest ParameterValues");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _servletRequest.getParameterNames();
    }
}