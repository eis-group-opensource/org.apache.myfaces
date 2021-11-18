/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter.portlet;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.apache.myfaces.webapp.filter.servlet.AbstractAttributeMap;

/**
 * PortletRequest headers as Map.
 * <p>
 * NOTE: This class was copied from myfaces impl 
 * org.apache.myfaces.context.portlet and it is
 * used by TomahawkFacesContextWrapper. By that reason, it could change
 * in the future.
 * </p>
 * 
 * @since 1.1.7
 * @author  Stan Silvert (latest modification by $Author: lu4242 $)
 * @version $Revision: 691871 $ $Date: 2008-09-04 07:32:08 +0300 (Thu, 04 Sep 2008) $
 */
public class RequestHeaderMap extends AbstractAttributeMap
{
    private final PortletRequest _portletRequest;

    RequestHeaderMap(PortletRequest portletRequest)
    {
        _portletRequest = portletRequest;
    }

    protected Object getAttribute(String key)
    {
        return _portletRequest.getProperty(key);
    }

    protected void setAttribute(String key, Object value)
    {
        throw new UnsupportedOperationException(
            "Cannot set PortletRequest property");
    }

    protected void removeAttribute(String key)
    {
        throw new UnsupportedOperationException(
            "Cannot remove PortletRequest property");
    }

    protected Enumeration getAttributeNames()
    {
        return _portletRequest.getPropertyNames();
    }

    public void putAll(Map t)
    {
        throw new UnsupportedOperationException();
    }


    public void clear()
    {
        throw new UnsupportedOperationException();
    }    
}
