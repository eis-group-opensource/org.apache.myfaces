/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.el.unified.resolver;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotFoundException;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.myfaces.config.element.ManagedBean;

import com.google.inject.Injector;

/**
 * <p>Register this ELResolver in faces-config.xml.</p>
 * 
 *     &ltapplication>
 *        &ltel-resolver>org.apache.myfaces.el.unified.resolver.GuiceResolver&lt/el-resolver>
 *    &lt/application>
 *
 * <p>Implement and configure a ServletContextListener in web.xml .</p>
 * 
 *  &ltlistener>
 *      <listener-class>com.your_company.GuiceServletContextListener&lt/listener-class>
 *  &lt/listener>
 * 
 * <p>Configure Guice in your ServletContextListener implementation, and place the 
 * Injector in application scope.</p>
 * 
 * public class GuiceServletContextListener implements ServletContextListener {
 *
 *    public void contextInitialized(ServletContextEvent event) {
 *        ServletContext ctx = event.getServletContext();
 *              //when on Java6, use ServiceLoader.load(com.google.inject.Module.class);
 *        Injector injector = Guice.createInjector(new YourModule());
 *        ctx.setAttribute(GuiceResolver.KEY, injector);
 *    }
 *
 *    public void contextDestroyed(ServletContextEvent event) {
 *        ServletContext ctx = event.getServletContext();
 *        ctx.removeAttribute(GuiceResolver.KEY);
 *    }
 *
 *}
 * 
 * @author Dennis Byrne
 */

public class GuiceResolver extends ManagedBeanResolver
{

    public static final String KEY = "oam." + Injector.class.getName();

    @Override
    public Object getValue(ELContext ctx, Object base, Object property) throws NullPointerException,
        PropertyNotFoundException, ELException
    {

        if (base != null || !(property instanceof String))
            return null;
        
        if (property == null)
            throw new PropertyNotFoundException();
        
        FacesContext fctx = (FacesContext)ctx.getContext(FacesContext.class);

        if (fctx == null)
            return null;

        ExternalContext ectx = fctx.getExternalContext();

        if (ectx == null || ectx.getRequestMap().containsKey(property) || ectx.getSessionMap().containsKey(property)
                || ectx.getApplicationMap().containsKey(property))
            return null;

        ManagedBean managedBean = runtimeConfig(ctx).getManagedBean((String)property);

        return managedBean == null ? null : getValue(ctx, ectx, managedBean.getManagedBeanClass());
    }

    private Object getValue(ELContext ctx, ExternalContext ectx, Class<?> managedBeanClass)
    {

        Injector injector = (Injector)ectx.getApplicationMap().get(KEY);

        if (injector == null)
            throw new FacesException("Could not find an instance of " + Injector.class.getName()
                    + " in application scope using key '" + KEY + "'");

        Object value = injector.getInstance(managedBeanClass);
        ctx.setPropertyResolved(true);
        return value;
    }

}
