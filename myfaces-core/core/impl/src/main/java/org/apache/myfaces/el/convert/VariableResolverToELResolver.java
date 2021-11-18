/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.convert;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;
import java.beans.FeatureDescriptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Wrapper that converts a VariableResolver into an ELResolver. See JSF 1.2 spec section 5.6.1.5
 * 
 * @author Stan Silvert (latest modification by $Author$)
 * @author Mathias Broekelmann
 * @version $Revision$ $Date$
 */
@SuppressWarnings("deprecation")
public final class VariableResolverToELResolver extends ELResolver
{

    // holds a flag to check if this instance is already called in current thread 
    private static final ThreadLocal<Collection<String>> propertyGuardThreadLocal
            = new ThreadLocal<Collection<String>>();

    /**
     * Gets the Collection<String> value of the propertyGuardThreadLocal.
     * If the value from the ThreadLocal ist null, a new Collection<String>
     * will be created.
     *
     * NOTE that we should not accomplish this by setting an initialValue on the
     * ThreadLocal, because this will automatically be set on the ThreadLocalMap
     * and thus can propably cause a memory leak.
     *
     * @return
     */
    private static Collection<String> getPropertyGuard()
    {
        Collection<String> propertyGuard = propertyGuardThreadLocal.get();

        if (propertyGuard == null)
        {
            propertyGuard = new HashSet<String>();
            propertyGuardThreadLocal.set(propertyGuard);
        }

        return propertyGuard;
    }
    
    private VariableResolver variableResolver;

    /**
     * Creates a new instance of VariableResolverToELResolver
     */
    public VariableResolverToELResolver(final VariableResolver variableResolver)
    {
        this.variableResolver = variableResolver;
    }
    
    /**
     * @return the variableResolver
     */
    public VariableResolver getVariableResolver()
    {
        return variableResolver;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) throws NullPointerException,
            PropertyNotFoundException, ELException
    {

        if (base != null)
            return null;
        if (property == null)
            throw new PropertyNotFoundException();

        context.setPropertyResolved(true);

        if (!(property instanceof String))
            return null;

        final String strProperty = (String) property;

        Collection<String> propertyGuard = getPropertyGuard();

        Object result = null;
        try
        {
            // only call the resolver if we haven't done it in current stack
            if(!propertyGuard.contains(strProperty)) {
                propertyGuard.add(strProperty);
                result = variableResolver.resolveVariable(facesContext(context), strProperty);
            }
        }
        catch (javax.faces.el.PropertyNotFoundException e)
        {
            context.setPropertyResolved(false);
            throw new PropertyNotFoundException(e.getMessage(), e);
        }
        catch (EvaluationException e)
        {
            context.setPropertyResolved(false);
            throw new ELException(e.getMessage(), e);
        }
        catch (RuntimeException e)
        {
            context.setPropertyResolved(false);
            throw e;
        }
        finally
        {
            propertyGuard.remove(strProperty);

            // if the propertyGuard is empty, remove the ThreadLocal
            // in order to prevent a memory leak
            if (propertyGuard.isEmpty())
            {
                propertyGuardThreadLocal.remove();
            }

            // set property resolved to false in any case if result is null
            context.setPropertyResolved(result != null);
        }

        return result;
    }

    // get the FacesContext from the ELContext
    private static FacesContext facesContext(ELContext context)
    {
        return (FacesContext) context.getContext(FacesContext.class);
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base)
    {
        if (base != null)
            return null;

        return String.class;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) throws NullPointerException,
            PropertyNotFoundException, PropertyNotWritableException, ELException
    {

        if ((base == null) && (property == null))
            throw new PropertyNotFoundException();
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) throws NullPointerException,
            PropertyNotFoundException, ELException
    {

        if ((base == null) && (property == null))
            throw new PropertyNotFoundException();

        return false;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) throws NullPointerException,
            PropertyNotFoundException, ELException
    {

        if ((base == null) && (property == null))
            throw new PropertyNotFoundException();

        return null;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base)
    {
        return null;
    }

}
