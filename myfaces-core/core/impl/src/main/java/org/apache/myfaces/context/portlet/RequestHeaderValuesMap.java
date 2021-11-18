/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.portlet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.apache.myfaces.util.AbstractAttributeMap;

/**
 * PortletRequest header values (multi-value headers) as Map of String[].
 * 
 * @author Stan Silvert (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public class RequestHeaderValuesMap extends AbstractAttributeMap<String[]>
{
    private final PortletRequest _portletRequest;
    private final Map<String, String[]> _valueCache = new HashMap<String, String[]>();

    RequestHeaderValuesMap(PortletRequest portletRequest)
    {
        _portletRequest = portletRequest;
    }

    @SuppressWarnings("unchecked")
    protected String[] getAttribute(String key)
    {
        String[] ret = _valueCache.get(key);
        if (ret == null)
        {
            _valueCache.put(key, ret = toArray(_portletRequest.getProperties(key)));
        }

        return ret;
    }

    protected void setAttribute(String key, String[] value)
    {
        throw new UnsupportedOperationException("Cannot set PortletRequest Properties");
    }

    protected void removeAttribute(String key)
    {
        throw new UnsupportedOperationException("Cannot remove PortletRequest Properties");
    }

    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _portletRequest.getPropertyNames();
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