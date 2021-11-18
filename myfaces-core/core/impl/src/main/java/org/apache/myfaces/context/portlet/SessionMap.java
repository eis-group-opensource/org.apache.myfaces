/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.portlet;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.apache.myfaces.shared_impl.util.NullEnumeration;
import org.apache.myfaces.util.AbstractThreadSafeAttributeMap;

/**
 * Portlet scope PortletSession attibutes as Map.
 *
 * @author  Stan Silvert (latest modification by $Author: lu4242 $)
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public class SessionMap extends AbstractThreadSafeAttributeMap<Object>
{
    private final PortletRequest _portletRequest;

    SessionMap(PortletRequest portletRequest)
    {
        _portletRequest = portletRequest;
    }

    @Override
    protected Object getAttribute(String key)
    {
        PortletSession portletSession = getSession();
        return (portletSession == null)
               ? null : portletSession.getAttribute(key, PortletSession.PORTLET_SCOPE);
    }

    @Override
    protected void setAttribute(String key, Object value)
    {
        _portletRequest.getPortletSession(true).setAttribute(key, value, PortletSession.PORTLET_SCOPE);
    }

    @Override
    protected void removeAttribute(String key)
    {
        PortletSession portletSession = getSession();
        if (portletSession != null)
        {
            portletSession.removeAttribute(key, PortletSession.PORTLET_SCOPE);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
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

    @Override
    public void putAll(Map t)
    {
        throw new UnsupportedOperationException();
    }


    /**
     * This will clear the session without invalidation.  If no session has
     * been created, it will simply return.
     */
    @Override
    public void clear()
    {
        PortletSession session = getSession();
        if (session == null) return;
        for (Enumeration attributeNames = session.getAttributeNames(PortletSession.PORTLET_SCOPE); 
             attributeNames.hasMoreElements(); ) {
            String attributeName = (String)attributeNames.nextElement();
            session.removeAttribute(attributeName);
        }
    }
}