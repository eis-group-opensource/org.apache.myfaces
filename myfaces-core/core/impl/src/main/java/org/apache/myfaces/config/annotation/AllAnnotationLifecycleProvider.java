/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.annotation;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;


import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;

// TODO @EJBs
public class AllAnnotationLifecycleProvider extends ResourceAnnotationLifecycleProvider
{

    public AllAnnotationLifecycleProvider(Context context)
    {
        super(context);
    }

    protected void checkMethodAnnotation(Method method, Object instance)
            throws NamingException, IllegalAccessException, InvocationTargetException
    {
        super.checkMethodAnnotation(method, instance);
        if (method.isAnnotationPresent(Resource.class))
        {
            Resource annotation =  method.getAnnotation(Resource.class);
            lookupMethodResource(context, instance, method, annotation.name());
        }
        if (method.isAnnotationPresent(EJB.class))
        {
            EJB annotation =  method.getAnnotation(EJB.class);
            lookupMethodResource(context, instance, method, annotation.name());
        }
        // TODO where i find WebServiceRef?
        /*if (method.isAnnotationPresent(WebServiceRef.class)) {
            WebServiceRef annotation =
                (WebServiceRef) method.getAnnotation(WebServiceRef.class);
            lookupMethodResource(context, instance, methods, annotation.name());
        }*/
        if (method.isAnnotationPresent(PersistenceContext.class))
        {
            PersistenceContext annotation = method.getAnnotation(PersistenceContext.class);
            lookupMethodResource(context, instance, method, annotation.name());
        }
        if (method.isAnnotationPresent(PersistenceUnit.class))
        {
            PersistenceUnit annotation = method.getAnnotation(PersistenceUnit.class);
            lookupMethodResource(context, instance, method, annotation.name());
        }
    }

    protected void checkFieldAnnotation(Field field, Object instance)
            throws NamingException, IllegalAccessException
    {
        super.checkFieldAnnotation(field, instance);
        if (field.isAnnotationPresent(EJB.class))
        {
            EJB annotation = field.getAnnotation(EJB.class);
            lookupFieldResource(context, instance, field, annotation.name());
        }
        /*if (field.isAnnotationPresent(WebServiceRef.class)) {
            WebServiceRef annotation =
                (WebServiceRef) field.getAnnotation(WebServiceRef.class);
            lookupFieldResource(context, instance, field, annotation.name());
        }*/
        if (field.isAnnotationPresent(PersistenceContext.class))
        {
            PersistenceContext annotation = field.getAnnotation(PersistenceContext.class);
            lookupFieldResource(context, instance, field, annotation.name());
        }
        if (field.isAnnotationPresent(PersistenceUnit.class))
        {
            PersistenceUnit annotation = field.getAnnotation(PersistenceUnit.class);
            lookupFieldResource(context, instance, field, annotation.name());
        }
    }
}
