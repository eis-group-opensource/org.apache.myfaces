/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.annotation;

import org.apache.myfaces.shared_impl.util.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.NamingException;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import java.lang.reflect.InvocationTargetException;

public class TomcatAnnotationLifecycleProvider implements 
    DiscoverableLifecycleProvider, LifecycleProvider2
{
    private static Log log = LogFactory.getLog(TomcatAnnotationLifecycleProvider.class);

    private ExternalContext externalContext;
    private org.apache.AnnotationProcessor annotationProcessor;

    public TomcatAnnotationLifecycleProvider(ExternalContext externalContext)
    {
        this.externalContext = externalContext;
    }


    public Object newInstance(String className)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NamingException, ClassNotFoundException {
        Class clazz = ClassUtils.classForName(className);
        log.info("Creating instance of " + className);
        Object object = clazz.newInstance();
        annotationProcessor.processAnnotations(object);
        //annotationProcessor.postConstruct(object);
        return object;
    }


    public void destroyInstance(Object o) throws IllegalAccessException, InvocationTargetException
    {
        log.info("Destroy instance of " + o.getClass().getName());
        annotationProcessor.preDestroy(o);
    }

    public boolean isAvailable()
    {
        try
        {
            annotationProcessor =  (org.apache.AnnotationProcessor) ((ServletContext)
                     externalContext.getContext()).getAttribute(org.apache.AnnotationProcessor.class.getName());
            return annotationProcessor != null;
        } catch (Exception e) {
            // ignore
        }
        return false;
    }


    public void postConstruct(Object o) throws IllegalAccessException,
            InvocationTargetException
    {
        annotationProcessor.postConstruct(o);
    }

}
