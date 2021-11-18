/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.portlet;

import java.util.Enumeration;
import java.util.Map;
import javax.portlet.PortletRequest;

import org.apache.myfaces.util.AbstractAttributeMap;



/**
 * PortletRequest headers as Map.
 *
 * @author  Stan Silvert (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public class RequestHeaderMap extends AbstractAttributeMap<String>
{
    private final PortletRequest _portletRequest;

    RequestHeaderMap(PortletRequest portletRequest)
    {
        _portletRequest = portletRequest;
    }

    @Override
    protected String getAttribute(String key)
    {
        return _portletRequest.getProperty(key);
    }

    @Override
    protected void setAttribute(String key, String value)
    {
        throw new UnsupportedOperationException(
            "Cannot set PortletRequest property");
    }

    @Override
    protected void removeAttribute(String key)
    {
        throw new UnsupportedOperationException(
            "Cannot remove PortletRequest property");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _portletRequest.getPropertyNames();
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
