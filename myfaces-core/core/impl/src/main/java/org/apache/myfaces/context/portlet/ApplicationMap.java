/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.portlet;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletContext;

import org.apache.myfaces.util.AbstractThreadSafeAttributeMap;


/**
 * PortletContext attributes as a Map.
 *
 * @author  Stan Silvert (latest modification by $Author: lu4242 $)
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public class ApplicationMap extends AbstractThreadSafeAttributeMap<Object>
{
    final PortletContext _portletContext;

    ApplicationMap(PortletContext portletContext)
    {
        _portletContext = portletContext;
    }

    @Override
    protected Object getAttribute(String key)
    {
        return _portletContext.getAttribute(key);
    }

    @Override
    protected void setAttribute(String key, Object value)
    {
        _portletContext.setAttribute(key, value);
    }

    @Override
    protected void removeAttribute(String key)
    {
        _portletContext.removeAttribute(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _portletContext.getAttributeNames();
    }

    @Override
    public void putAll(Map t)
    {
        throw new UnsupportedOperationException();
    }


    @Override
    public void clear()
    {
        throw new UnsupportedOperationException();
    }    
}
