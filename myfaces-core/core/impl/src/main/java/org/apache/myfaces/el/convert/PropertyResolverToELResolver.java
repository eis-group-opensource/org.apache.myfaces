/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.convert;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyResolver;
import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper that converts a VariableResolver into an ELResolver. See JSF 1.2 spec section 5.6.1.6
 *
 * @author Stan Silvert (latest modification by $Author$)
 * @author Mathias Broekelmann
 * @version $Revision$ $Date$
 */
@SuppressWarnings("deprecation")
public final class PropertyResolverToELResolver extends ELResolver
{
    private PropertyResolver propertyResolver;

    private ExpressionFactory expressionFactory;

    /**
     * Creates a new instance of PropertyResolverToELResolver
     */
    public PropertyResolverToELResolver(final PropertyResolver propertyResolver)
    {
        this.propertyResolver = propertyResolver;
    }

    @Override
    public void setValue(final ELContext context, final Object base, final Object property, final Object value)
        throws NullPointerException, PropertyNotFoundException, PropertyNotWritableException, ELException
    {
        if (base == null || property == null)
        {
            return;
        }

        try
        {
            context.setPropertyResolved(true);
            if (needsCoersion(base))
            {
                propertyResolver.setValue(base, coerceToInt(property), value);
            } else
            {
                propertyResolver.setValue(base, property, value);
            }
            // see: https://issues.apache.org/jira/browse/MYFACES-1670
            context.setPropertyResolved(
                FacesContext.getCurrentInstance().getELContext().isPropertyResolved());

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
    }

    @Override
    public boolean isReadOnly(final ELContext context, final Object base, final Object property) throws NullPointerException,
        PropertyNotFoundException, ELException
    {
        if (base == null || property == null)
        {
            return true;
        }

        try
        {
            boolean result;
            context.setPropertyResolved(true);
            if (needsCoersion(base))
            {
                result = propertyResolver.isReadOnly(base, coerceToInt(property));
            } else
            {
                result = propertyResolver.isReadOnly(base, property);
            }

            // see: https://issues.apache.org/jira/browse/MYFACES-1670
            context.setPropertyResolved(
                FacesContext.getCurrentInstance().getELContext().isPropertyResolved());
            return result;
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
    }

    @Override
    public Object getValue(final ELContext context, final Object base, final Object property) throws NullPointerException,
        PropertyNotFoundException, ELException
    {
        if (base == null || property == null)
        {
            return null;
        }

        try
        {
            context.setPropertyResolved(true);
            Object value;
            if (needsCoersion(base))
            {
                value = propertyResolver.getValue(base, coerceToInt(property));
            } else
            {
                value = propertyResolver.getValue(base, property);
            }

            // see: https://issues.apache.org/jira/browse/MYFACES-1670
            context.setPropertyResolved(
                FacesContext.getCurrentInstance().getELContext().isPropertyResolved());

            return value;
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
    }

    @Override
    public Class<?> getType(final ELContext context, final Object base, final Object property) throws NullPointerException,
        PropertyNotFoundException, ELException
    {
        if (base == null || property == null)
        {
            return null;
        }

        try
        {
            context.setPropertyResolved(true);
            Class<?> value;
            if (needsCoersion(base))
            {
                value = propertyResolver.getType(base, coerceToInt(property));
            } else
            {
                value = propertyResolver.getType(base, property);
            }

            // see: https://issues.apache.org/jira/browse/MYFACES-1670
            context.setPropertyResolved(
                FacesContext.getCurrentInstance().getELContext().isPropertyResolved());

            return value;
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
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base)
    {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base)
    {

        if (base == null)
        {
            return null;
        }

        return Object.class;
    }

    private static boolean needsCoersion(Object base)
    {
        return (base instanceof List) || base.getClass().isArray();
    }

    protected ExpressionFactory getExpressionFactory()
    {
        if (expressionFactory == null)
        {
            ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder
                .getFactory(FactoryFinder.APPLICATION_FACTORY);
            expressionFactory = appFactory.getApplication().getExpressionFactory();
        }
        return expressionFactory;
    }

    public void setExpressionFactory(ExpressionFactory expressionFactory)
    {
        this.expressionFactory = expressionFactory;
    }

    private int coerceToInt(Object property)
    {
        return (Integer) getExpressionFactory().coerceToType(property, Integer.class);
    }


    public PropertyResolver getPropertyResolver()
    {
        return propertyResolver;
    }
}
