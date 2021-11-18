/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified.resolver;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import org.apache.myfaces.config.RuntimeConfig;

/**
 * See JSF 1.2 spec section 5.6.1.4
 * 
 * @author Stan Silvert
 */
public final class ResourceBundleResolver extends ELResolver
{

    /**
     * RuntimeConfig is instantiated once per servlet and never changes--we can safely cache it
     */
    private RuntimeConfig runtimeConfig;

    /** Creates a new instance of ResourceBundleResolver */
    public ResourceBundleResolver()
    {
    }

    @Override
    public void setValue(final ELContext context, final Object base, final Object property, final Object value)
        throws NullPointerException, PropertyNotFoundException, PropertyNotWritableException, ELException
    {

        if ((base == null) && (property == null))
            throw new PropertyNotFoundException();

        if (!(property instanceof String))
            return;

        final ResourceBundle bundle = getResourceBundle(context, (String)property);

        if (bundle != null)
        {
            throw new PropertyNotWritableException("ResourceBundles are read-only");
        }
    }

    @Override
    public boolean isReadOnly(final ELContext context, final Object base, final Object property)
        throws NullPointerException, PropertyNotFoundException, ELException
    {

        if (base != null)
            return false;
        if (property == null)
            throw new PropertyNotFoundException();
        if (!(property instanceof String))
            return false;

        final ResourceBundle bundle = getResourceBundle(context, (String)property);

        if (bundle != null)
        {
            context.setPropertyResolved(true);
            return true;
        }

        return false;
    }

    @Override
    public Object getValue(final ELContext context, final Object base, final Object property)
        throws NullPointerException, PropertyNotFoundException, ELException
    {

        if (base != null)
            return null;
        if (property == null)
            throw new PropertyNotFoundException();
        if (!(property instanceof String))
            return null;

        final ResourceBundle bundle = getResourceBundle(context, (String)property);

        if (bundle != null)
        {
            context.setPropertyResolved(true);
            return bundle;
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
        if (!(property instanceof String))
            return null;

        final ResourceBundle bundle = getResourceBundle(context, (String)property);

        if (bundle != null)
        {
            context.setPropertyResolved(true);
            return ResourceBundle.class;
        }

        return null;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(final ELContext context, final Object base)
    {

        if (base != null)
            return null;

        final ArrayList<FeatureDescriptor> descriptors = new ArrayList<FeatureDescriptor>();

        final Map<String, org.apache.myfaces.config.impl.digester.elements.ResourceBundle> resourceBundles =
                runtimeConfig(context).getResourceBundles();

        for (org.apache.myfaces.config.impl.digester.elements.ResourceBundle resourceBundle : resourceBundles.values())
        {
            descriptors.add(makeDescriptor(resourceBundle));
        }

        return descriptors.iterator();
    }

    @Override
    public Class<?> getCommonPropertyType(final ELContext context, final Object base)
    {

        if (base != null)
            return null;

        return String.class;
    }

    // get the FacesContext from the ELContext
    private static FacesContext facesContext(final ELContext context)
    {
        return (FacesContext)context.getContext(FacesContext.class);
    }

    private static ResourceBundle getResourceBundle(final ELContext context, final String property)
    {
        final FacesContext facesContext = facesContext(context);
        if (facesContext != null)
        {
            final Application application = facesContext.getApplication();
            return application.getResourceBundle(facesContext, property);
        }

        return null;
    }

    protected RuntimeConfig runtimeConfig(ELContext context)
    {
        final FacesContext facesContext = facesContext(context);

        // application-level singleton - we can safely cache this
        if (this.runtimeConfig == null)
        {
            this.runtimeConfig = RuntimeConfig.getCurrentInstance(facesContext.getExternalContext());
        }

        return runtimeConfig;
    }

    private static FeatureDescriptor makeDescriptor(
                                                    org.apache.myfaces.config.impl.digester.elements.ResourceBundle bundle)
    {
        final FeatureDescriptor fd = new FeatureDescriptor();
        fd.setValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME, Boolean.TRUE);
        fd.setName(bundle.getVar());
        fd.setDisplayName(bundle.getDisplayName());
        fd.setValue(ELResolver.TYPE, ResourceBundle.class);
        fd.setShortDescription("");
        fd.setExpert(false);
        fd.setHidden(false);
        fd.setPreferred(true);
        return fd;
    }
}
