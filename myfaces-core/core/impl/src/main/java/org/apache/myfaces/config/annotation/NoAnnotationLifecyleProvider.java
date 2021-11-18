/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.annotation;

import org.apache.myfaces.shared_impl.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;

public class NoAnnotationLifecyleProvider implements LifecycleProvider2
{


    public void destroyInstance(Object o) throws IllegalAccessException, InvocationTargetException
    {

    }

    public Object newInstance(String className) throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException
    {
        Class clazz = ClassUtils.classForName(className);
        return clazz.newInstance();
    }

    public void postConstruct(Object o) throws IllegalAccessException,
            InvocationTargetException
    {
        // No op
    }

}
