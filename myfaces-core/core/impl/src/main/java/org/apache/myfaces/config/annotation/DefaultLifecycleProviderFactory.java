/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.discovery.resource.ClassLoaders;
import org.apache.commons.discovery.resource.names.DiscoverServiceNames;
import org.apache.commons.discovery.ResourceNameIterator;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFWebConfigParam;
import org.apache.myfaces.shared_impl.util.ClassUtils;

import javax.faces.context.ExternalContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;

/*
 * Date: Mar 12, 2007
 * Time: 9:53:40 PM
 */
public class DefaultLifecycleProviderFactory extends LifecycleProviderFactory {
    private static Log log = LogFactory.getLog(DefaultLifecycleProviderFactory.class);
    public static final String LIFECYCLE_PROVIDER_INSTANCE_KEY = LifecycleProvider.class.getName() + ".LIFECYCLE_PROVIDER_INSTANCE";

    @JSFWebConfigParam(name="org.apache.myfaces.config.annotation.LifecycleProvider", since="1.1")
    public static final String LIFECYCLE_PROVIDER = LifecycleProvider.class.getName();


    public DefaultLifecycleProviderFactory()
    {
    }

    @Override
    public LifecycleProvider getLifecycleProvider(ExternalContext externalContext)
    {
        LifecycleProvider lifecycleProvider = null;
        if (externalContext == null)
        {
            log.info("No ExternalContext using fallback LifecycleProvider.");
            lifecycleProvider = resolveFallbackLifecycleProvider();
        }
        else
        {
            lifecycleProvider = (LifecycleProvider) externalContext.getApplicationMap().get(LIFECYCLE_PROVIDER_INSTANCE_KEY);
        }
        if (lifecycleProvider == null)
        {
            if (!resolveLifecycleProviderFromExternalContext(externalContext))
            {
                if (!resolveLifecycleProviderFromService(externalContext))
                {
                    lifecycleProvider = resolveFallbackLifecycleProvider();
                    externalContext.getApplicationMap().put(LIFECYCLE_PROVIDER_INSTANCE_KEY, lifecycleProvider);
                }
                else
                {
                    //Retrieve it because it was resolved
                    lifecycleProvider = (LifecycleProvider) externalContext.getApplicationMap().get(LIFECYCLE_PROVIDER_INSTANCE_KEY);
                }
            }
            else
            {
                //Retrieve it because it was resolved
                lifecycleProvider = (LifecycleProvider) externalContext.getApplicationMap().get(LIFECYCLE_PROVIDER_INSTANCE_KEY);
            }
            log.info("Using LifecycleProvider "+ lifecycleProvider.getClass().getName());
        }
        return lifecycleProvider;
    }

    @Override
    public void release() {

    }



    private boolean resolveLifecycleProviderFromExternalContext(ExternalContext externalContext)
    {
        try
        {
            String lifecycleProvider = externalContext.getInitParameter(LIFECYCLE_PROVIDER);
            if (lifecycleProvider != null)
            {

                Object obj = createClass(lifecycleProvider, externalContext);

                if (obj instanceof LifecycleProvider) {
                    externalContext.getApplicationMap().put(LIFECYCLE_PROVIDER_INSTANCE_KEY, (LifecycleProvider) obj);
                    return true;
                }
            }
        }
        catch (ClassNotFoundException e)
        {
            log.error("", e);
        }
        catch (InstantiationException e)
        {
            log.error("", e);
        }
        catch (IllegalAccessException e)
        {
            log.error("", e);
        }
        catch (InvocationTargetException e)
        {
            log.error("", e);
        }
        return false;
    }


    private boolean resolveLifecycleProviderFromService(ExternalContext externalContext) {
        ClassLoader classLoader = ClassUtils.getContextClassLoader();
        ClassLoaders loaders = new ClassLoaders();
        loaders.put(classLoader);
        loaders.put(this.getClass().getClassLoader());
        DiscoverServiceNames dsn = new DiscoverServiceNames(loaders);
        ResourceNameIterator iter = dsn.findResourceNames(LIFECYCLE_PROVIDER);
        while (iter.hasNext()) {
            String className = iter.nextResourceName();
            try
            {
                Object obj = createClass(className, externalContext);
                if (DiscoverableLifecycleProvider.class.isAssignableFrom(obj.getClass())) {
                    DiscoverableLifecycleProvider discoverableLifecycleProvider =
                            (DiscoverableLifecycleProvider) obj;
                    if (discoverableLifecycleProvider.isAvailable()) {
                        externalContext.getApplicationMap().put(LIFECYCLE_PROVIDER_INSTANCE_KEY, discoverableLifecycleProvider);
                        return true;
                    }
                }
            }
            catch (ClassNotFoundException e)
            {
                // ignore
            }
            catch (NoClassDefFoundError e)
            {
                // ignore
            }
            catch (InstantiationException e)
            {
                log.error("", e);
            }
            catch (IllegalAccessException e)
            {
                log.error("", e);
            }
            catch (InvocationTargetException e)
            {
                log.error("", e);
            }
        }
        return false;
    }

    private Object createClass(String className, ExternalContext externalContext)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException
    {
        Class clazz = ClassUtils.classForName(className);

        Object obj;
        try
        {
            Constructor constructor = clazz.getConstructor(ExternalContext.class);
            obj = constructor.newInstance(externalContext);
        }
        catch (NoSuchMethodException e)
        {
            obj = clazz.newInstance();
        }
        return obj;
    }


    private LifecycleProvider resolveFallbackLifecycleProvider()
    {
        try
        {
                ClassUtils.classForName("javax.annotation.PreDestroy");
        }
        catch (ClassNotFoundException e)
        {
            // no annotation available don't process annotations
            return new NoAnnotationLifecyleProvider(); 
        }
        Context context;
        try
        {
            context = new InitialContext();
            try
            {
                ClassUtils.classForName("javax.ejb.EJB");
                // Asume full JEE 5 container
                return new AllAnnotationLifecycleProvider(context);
            }
            catch (ClassNotFoundException e)
            {
                // something else
                return new ResourceAnnotationLifecycleProvider(context);
            }
        }
        catch (NamingException e)
        {
            // no initial context available no injection
            log.error("No InitialContext found. Using NoInjectionAnnotationProcessor.", e);
            return new NoInjectionAnnotationLifecycleProvider();
        }
    }
}
