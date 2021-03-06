/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified.resolver.implicitobject;

import javax.el.*;
import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * See JSF 1.2 spec sections 5.6.1.1 and 5.6.2.1
 * 
 * @author Stan Silvert
 */
public class ImplicitObjectResolver extends ELResolver
{

    private Map<String, ImplicitObject> implicitObjects;

    /**
     * Static factory for an ELResolver for resolving implicit objects in JSPs. See JSF 1.2 spec section 5.6.1.1
     */
    public static ELResolver makeResolverForJSP()
    {
        Map<String, ImplicitObject> forJSPList = new HashMap<String, ImplicitObject>(2);
        ImplicitObject io1 = new FacesContextImplicitObject();
        forJSPList.put(io1.getName(), io1);
        ImplicitObject io2 = new ViewImplicitObject();
        forJSPList.put(io2.getName(), io2);
        return new ImplicitObjectResolver(forJSPList);
    }

    /**
     * Static factory for an ELResolver for resolving implicit objects in all of Faces. See JSF 1.2 spec section 5.6.1.2
     */
    public static ELResolver makeResolverForFaces()
    {
        Map<String, ImplicitObject> forFacesList = new HashMap<String, ImplicitObject>(14);
        ImplicitObject io1 = new ApplicationImplicitObject();
        forFacesList.put(io1.getName(), io1);
        ImplicitObject io2 = new ApplicationScopeImplicitObject();
        forFacesList.put(io2.getName(), io2);
        ImplicitObject io3 = new CookieImplicitObject();
        forFacesList.put(io3.getName(), io3);
        ImplicitObject io4 = new FacesContextImplicitObject();
        forFacesList.put(io4.getName(), io4);
        ImplicitObject io5 = new HeaderImplicitObject();
        forFacesList.put(io5.getName(), io5);
        ImplicitObject io6 = new HeaderValuesImplicitObject();
        forFacesList.put(io6.getName(), io6);
        ImplicitObject io7 = new InitParamImplicitObject();
        forFacesList.put(io7.getName(), io7);
        ImplicitObject io8 = new ParamImplicitObject();
        forFacesList.put(io8.getName(), io8);
        ImplicitObject io9 = new ParamValuesImplicitObject();
        forFacesList.put(io9.getName(), io9);
        ImplicitObject io10 = new RequestImplicitObject();
        forFacesList.put(io10.getName(), io10);
        ImplicitObject io11 = new RequestScopeImplicitObject();
        forFacesList.put(io11.getName(), io11);
        ImplicitObject io12 = new SessionImplicitObject();
        forFacesList.put(io12.getName(), io12);
        ImplicitObject io13 = new SessionScopeImplicitObject();
        forFacesList.put(io13.getName(), io13);
        ImplicitObject io14 = new ViewImplicitObject();
        forFacesList.put(io14.getName(), io14);
        return new ImplicitObjectResolver(forFacesList);
    }

    private ImplicitObjectResolver()
    {
        super();
        this.implicitObjects = new HashMap<String, ImplicitObject>();
    }

    /** Creates a new instance of ImplicitObjectResolverForJSP */
    private ImplicitObjectResolver(Map<String, ImplicitObject> implicitObjects)
    {
        this();
        this.implicitObjects = implicitObjects;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) throws NullPointerException,
        PropertyNotFoundException, PropertyNotWritableException, ELException
    {

        if (base != null)
            return;
        if (property == null)
            throw new PropertyNotFoundException();
        if (!(property instanceof String))
            return;

        String strProperty = property.toString();

        if (implicitObjects.containsKey(strProperty))
        {
            throw new PropertyNotWritableException();
        }
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) throws NullPointerException,
        PropertyNotFoundException, ELException
    {

        if (base != null)
            return false;
        if (property == null)
            throw new PropertyNotFoundException();
        if (!(property instanceof String))
            return false;

        String strProperty = property.toString();

        if (implicitObjects.containsKey(strProperty))
        {
            context.setPropertyResolved(true);
            return true;
        }

        return false;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) throws NullPointerException,
        PropertyNotFoundException, ELException
    {

        if (base != null)
            return null;
        if (property == null)
            throw new PropertyNotFoundException();
        if (!(property instanceof String))
            return null;

        String strProperty = property.toString();

        ImplicitObject obj = implicitObjects.get(strProperty);
        if (obj != null)
        {
            context.setPropertyResolved(true);
            return obj.getValue(context);
        }

        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) throws NullPointerException,
        PropertyNotFoundException, ELException
    {

        if (base != null)
            return null;
        if (property == null)
            throw new PropertyNotFoundException();
        if (!(property instanceof String))
            return null;

        String strProperty = property.toString();

        if (implicitObjects.containsKey(strProperty))
        {
            context.setPropertyResolved(true);
        }

        return null;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base)
    {
        if (base != null)
            return null;

        ArrayList<FeatureDescriptor> descriptors = new ArrayList<FeatureDescriptor>(implicitObjects.size());

        for (ImplicitObject obj : implicitObjects.values())
        {
            descriptors.add(obj.getDescriptor());
        }

        return descriptors.iterator();
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base)
    {
        if (base != null)
            return null;

        return String.class;
    }

}
