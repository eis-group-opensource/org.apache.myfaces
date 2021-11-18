/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.annotation;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;

// TODO @Resources
public class ResourceAnnotationLifecycleProvider extends NoInjectionAnnotationLifecycleProvider
{

    protected Context context;
    private static final String JAVA_COMP_ENV = "java:comp/env/";

    public ResourceAnnotationLifecycleProvider(Context context)
    {
        this.context = context;
    }


    /**
     * Inject resources in specified instance.
     */
    protected void processAnnotations(Object instance)
            throws IllegalAccessException, InvocationTargetException, NamingException
    {

        if (context == null)
        {
            // No resource injection
            return;
        }

        checkAnnotation(instance.getClass(), instance);

        /* TODO the servlet spec is not clear about searching in superclass??
         * May be only check non private fields and methods
         * for @Resource (JSR 250), if used all superclasses MUST be examined
         * to discover all uses of this annotation.

        Class superclass = instance.getClass().getSuperclass();
        while (superclass != null && (!superclass.equals(Object.class)))
        {
            checkAnnotation(superclass, instance);
            superclass = superclass.getSuperclass();
        } */
    }

    private void checkAnnotation(Class clazz, Object instance)
            throws NamingException, IllegalAccessException, InvocationTargetException
    {
        // Initialize fields annotations
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            checkFieldAnnotation(field, instance);
        }

        // Initialize methods annotations
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods)
        {
            checkMethodAnnotation(method, instance);
        }
    }

    protected void checkMethodAnnotation(Method method, Object instance)
            throws NamingException, IllegalAccessException, InvocationTargetException
    {
        if (method.isAnnotationPresent(Resource.class))
        {
            Resource annotation = method.getAnnotation(Resource.class);
            lookupMethodResource(context, instance, method, annotation.name());
        }
    }

    protected void checkFieldAnnotation(Field field, Object instance)
            throws NamingException, IllegalAccessException
    {
        if (field.isAnnotationPresent(Resource.class))
        {
            Resource annotation = field.getAnnotation(Resource.class);
            lookupFieldResource(context, instance, field, annotation.name());
        }
    }

    /**
     * Inject resources in specified field.
     */
    protected static void lookupFieldResource(javax.naming.Context context,
            Object instance, Field field, String name)
            throws NamingException, IllegalAccessException
    {

        Object lookedupResource;

        if ((name != null) && (name.length() > 0))
        {
            // TODO local or global JNDI
            lookedupResource = context.lookup(JAVA_COMP_ENV + name);
        }
        else
        {
            // TODO local or global JNDI 
            lookedupResource = context.lookup(JAVA_COMP_ENV + instance.getClass().getName() + "/" + field.getName());
        }

        boolean accessibility = field.isAccessible();
        field.setAccessible(true);
        field.set(instance, lookedupResource);
        field.setAccessible(accessibility);
    }


    /**
     * Inject resources in specified method.
     */
    protected static void lookupMethodResource(javax.naming.Context context,
            Object instance, Method method, String name)
            throws NamingException, IllegalAccessException, InvocationTargetException
    {

        if (!method.getName().startsWith("set")
                || method.getParameterTypes().length != 1
                || !method.getReturnType().getName().equals("void"))
        {
            throw new IllegalArgumentException("Invalid method resource injection annotation");
        }

        Object lookedupResource;

        if ((name != null) && (name.length() > 0))
        {
            // TODO local or global JNDI
            lookedupResource = context.lookup(JAVA_COMP_ENV + name);
        }
        else
        {
            // TODO local or global JNDI
            lookedupResource =
                    context.lookup(JAVA_COMP_ENV + instance.getClass().getName() + "/" + getFieldName(method));
        }

        boolean accessibility = method.isAccessible();
        method.setAccessible(true);
        method.invoke(instance, lookedupResource);
        method.setAccessible(accessibility);
    }

    /**
     * Returns the field name for the given Method.
     * E.g. setName() will be "name". 
     *
     * @param setter the setter method
     * @return the field name of the given setter method
     */
    protected static String getFieldName(Method setter)
    {
        StringBuilder name = new StringBuilder(setter.getName());

        // remove 'set'
        name.delete(0, 3);

        // lowercase first char
        name.setCharAt(0, Character.toLowerCase(name.charAt(0)));

        return name.toString();
    }

}
