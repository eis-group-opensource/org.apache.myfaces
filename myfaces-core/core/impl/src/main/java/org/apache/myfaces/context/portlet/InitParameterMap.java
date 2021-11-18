/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.portlet;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletContext;

import org.apache.myfaces.util.AbstractAttributeMap;


/**
 * ServletContext init parameters as Map.
 *
 * @author  Stan Silvert (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public class InitParameterMap extends AbstractAttributeMap<String>
{
    final PortletContext _portletContext;

    InitParameterMap(PortletContext portletContext)
    {
        _portletContext = portletContext;
    }

    @Override
    protected String getAttribute(String key)
    {
        return _portletContext.getInitParameter(key);
    }

    @Override
    protected void setAttribute(String key, String value)
    {
        throw new UnsupportedOperationException(
            "Cannot set PortletContext InitParameter");
    }

    @Override
    protected void removeAttribute(String key)
    {
        throw new UnsupportedOperationException(
            "Cannot remove PortletContext InitParameter");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        return _portletContext.getInitParameterNames();
    }

    @Override
    public boolean equals(Object o) {
        boolean retValue;

        retValue = super.equals(o);
        return retValue;
    }

    @Override
    public int hashCode() {
        int retValue;

        retValue = super.hashCode();
        return retValue;
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
