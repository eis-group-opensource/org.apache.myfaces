/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified.resolver;

import org.apache.myfaces.context.servlet.StartupServletExternalContextImpl;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * See JSF 1.2 spec section 5.6.2.7
 * 
 * @author Stan Silvert
 */
public final class ScopedAttributeResolver extends ELResolver
{

    /**
     * Creates a new instance of ScopedAttributeResolver
     */
    public ScopedAttributeResolver()
    {
    }

    @Override
    public void setValue(final ELContext context, final Object base, final Object property, final Object value)
        throws NullPointerException, PropertyNotFoundException, PropertyNotWritableException, ELException
    {
        if (base != null)
        {
            return;
        }

        if (property == null)
        {
            throw new PropertyNotFoundException();
        }

        final Map<String, Object> scopedMap = findScopedMap(facesContext(context), property);
        if (scopedMap != null)
        {
            scopedMap.put((String)property, value);
        }
        else
        {
            externalContext(context).getRequestMap().put((String)property, value);
        }

        context.setPropertyResolved(true);
    }

    @Override
    public boolean isReadOnly(final ELContext context, final Object base, final Object property)
        throws NullPointerException, PropertyNotFoundException, ELException
    {
        if (base == null)
        {
            context.setPropertyResolved(true);
        }

        return false;
    }

    @Override
    public Object getValue(final ELContext context, final Object base, final Object property)
        throws NullPointerException, PropertyNotFoundException, ELException
    {
        if (base != null)
        {
            return null;
        }

        if (property == null)
        {
            throw new PropertyNotFoundException();
        }

        context.setPropertyResolved(true);

        final Map<String, Object> scopedMap = findScopedMap(facesContext(context), property);
        if (scopedMap != null)
        {
            return scopedMap.get(property);
        }

        return null;
    }

    @Override
    public Class<?> getType(final ELContext context, final Object base, final Object property)
        throws NullPointerException, PropertyNotFoundException, ELException
    {

        if (base != null)
            return null;
        if (property == null)
            throw new PropertyNotFoundException();

        context.setPropertyResolved(true);
        return Object.class;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(final ELContext context, final Object base)
    {

        if (base != null)
            return null;

        final List<FeatureDescriptor> descriptorList = new ArrayList<FeatureDescriptor>();
        final ExternalContext extContext = externalContext(context);
        addDescriptorsToList(descriptorList, extContext.getRequestMap());
        addDescriptorsToList(descriptorList, extContext.getSessionMap());
        addDescriptorsToList(descriptorList, extContext.getApplicationMap());

        return descriptorList.iterator();
    }

    @Override
    public Class<?> getCommonPropertyType(final ELContext context, final Object base)
    {

        if (base != null)
            return null;

        return String.class;
    }

    // side effect: modifies the list
    private static void addDescriptorsToList(final List<FeatureDescriptor> descriptorList,
                                             final Map<String, Object> scopeMap)
    {
        for (Object name : scopeMap.keySet())
        {
            String strName = (String)name;
            Class<?> runtimeType = scopeMap.get(strName).getClass();
            descriptorList.add(makeDescriptor(strName, runtimeType));
        }
    }

    private static FeatureDescriptor makeDescriptor(final String name, final Class<?> runtimeType)
    {
        FeatureDescriptor fd = new FeatureDescriptor();
        fd.setValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME, Boolean.TRUE);
        fd.setValue(ELResolver.TYPE, runtimeType);
        fd.setName(name);
        fd.setDisplayName(name);
        fd.setShortDescription(name);
        fd.setExpert(false);
        fd.setHidden(false);
        fd.setPreferred(true);
        return fd;
    }

    // returns null if not found
    private static Map<String, Object> findScopedMap(final FacesContext facesContext, final Object property)
    {
        if (facesContext == null)
        {
            return null;
        }
        
        final ExternalContext extContext = facesContext.getExternalContext();
        if (extContext == null)
        {
            return null;
        }

        final boolean startup = (extContext instanceof StartupServletExternalContextImpl);
        Map<String, Object> scopedMap;

        // request scope (not available at startup)
        if (!startup)
        {
            scopedMap = extContext.getRequestMap();
            if (scopedMap.containsKey(property))
            {
                return scopedMap;
            }
        }


        // session scope (not available at startup)
        if (!startup)
        {
            scopedMap = extContext.getSessionMap();
            if (scopedMap.containsKey(property))
            {
                return scopedMap;
            }
        }

        // application scope
        scopedMap = extContext.getApplicationMap();
        if (scopedMap.containsKey(property))
        {
            return scopedMap;
        }

        // not found
        return null;
    }

    // get the FacesContext from the ELContext
    private static FacesContext facesContext(final ELContext context)
    {
        return (FacesContext)context.getContext(FacesContext.class);
    }

    private static ExternalContext externalContext(final ELContext context)
    {
        FacesContext facesContext = facesContext(context);
        if (facesContext != null)
        {
            return facesContext(context).getExternalContext();
        }

        return null;
    }

}
