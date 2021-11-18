/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter.portlet;

import org.apache.myfaces.shared_tomahawk.util.NullEnumeration;

import java.util.Enumeration;
import java.util.Map;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import org.apache.myfaces.webapp.filter.servlet.AbstractAttributeMap;

/**
 * Portlet scope PortletSession attibutes as Map.
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
public class SessionMap extends AbstractAttributeMap
{
    private final PortletRequest _portletRequest;

    SessionMap(PortletRequest portletRequest)
    {
        _portletRequest = portletRequest;
    }

    protected Object getAttribute(String key)
    {
        PortletSession portletSession = getSession();
        return (portletSession == null)
               ? null : portletSession.getAttribute(key.toString(), PortletSession.PORTLET_SCOPE);
    }

    protected void setAttribute(String key, Object value)
    {
        _portletRequest.getPortletSession(true).setAttribute(key, value, PortletSession.PORTLET_SCOPE);
    }

    protected void removeAttribute(String key)
    {
        PortletSession portletSession = getSession();
        if (portletSession != null)
        {
            portletSession.removeAttribute(key, PortletSession.PORTLET_SCOPE);
        }
    }

    protected Enumeration getAttributeNames()
    {
        PortletSession portletSession = getSession();
        return (portletSession == null)
               ? NullEnumeration.instance()
               : portletSession.getAttributeNames(PortletSession.PORTLET_SCOPE);
    }

    private PortletSession getSession()
    {
        return _portletRequest.getPortletSession(false);
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