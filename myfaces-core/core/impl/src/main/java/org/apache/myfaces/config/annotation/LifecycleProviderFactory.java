/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.annotation;

import org.apache.commons.discovery.tools.DiscoverSingleton;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


public abstract class LifecycleProviderFactory {
    protected static final String FACTORY_DEFAULT = DefaultLifecycleProviderFactory.class.getName();

    private static final String FACTORY_KEY = LifecycleProviderFactory.class.getName();

    public static LifecycleProviderFactory getLifecycleProviderFactory()
    {
        // Since we always provide a StartupFacesContext on initialization time, this is safe:
        return getLifecycleProviderFactory(FacesContext.getCurrentInstance().getExternalContext());
    }
    
    public static LifecycleProviderFactory getLifecycleProviderFactory(ExternalContext ctx)
    {
        LifecycleProviderFactory instance = (LifecycleProviderFactory) ctx.getApplicationMap().get(FACTORY_KEY);
        if (instance != null)
        {
            return instance;
        }
        return (LifecycleProviderFactory) DiscoverSingleton.find(LifecycleProviderFactory.class, FACTORY_DEFAULT);
    }


    public static void setLifecycleProviderFactory(LifecycleProviderFactory instance) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(FACTORY_KEY, instance);
    }

    public abstract LifecycleProvider getLifecycleProvider(ExternalContext externalContext);

    public abstract void release();

}
