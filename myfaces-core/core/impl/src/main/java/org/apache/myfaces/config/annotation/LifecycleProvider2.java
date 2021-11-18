/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.annotation;

import java.lang.reflect.InvocationTargetException;

import javax.naming.NamingException;

/**
 * Proposed interface to annotation service. An implementation of this class needs to know the appropriate classloader,
 * dependencies to be injected, and lifecycle methods to be called.
 *
 * @version $Rev: 775006 $ $Date: 2009-05-15 08:07:37 +0300 (Fri, 15 May 2009) $
 */
public interface LifecycleProvider2 extends LifecycleProvider
{
    /**
     * Create an object of the class with the supplied name, inject dependencies as appropriate.
     *
     * @param className name of the class of the desired object
     * @return a fully constructed, dependency-injected, and initialized object.
     */
    Object newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NamingException, InvocationTargetException;

    /**
     * Call a postConstruct method as appropriate.
     *
     * @param o object to initialize
     */
    void postConstruct(Object o) throws IllegalAccessException, InvocationTargetException;
}