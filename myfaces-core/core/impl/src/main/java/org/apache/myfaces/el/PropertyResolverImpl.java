/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import java.util.List;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Anton Koinov
 * @version $Revision: 1136229 $ $Date: 2011-06-16 01:38:34 +0300 (Thu, 16 Jun 2011) $
 */
@SuppressWarnings("deprecation")
public final class PropertyResolverImpl extends PropertyResolver
{
    // ~ Public PropertyResolver Methods
    // ----------------------------------------

    @Override
    public Object getValue(final Object base, final Object property) throws EvaluationException,
        PropertyNotFoundException
    {
        if (base == null)
        {
            return null;
        }
        if (property == null)
        {
            return null;
        }
        return invokeResolver(new ResolverInvoker<Object>(base, property)
        {
            @Override
            public Object invoke(ELResolver resolver, ELContext context)
            {
                return getELResolver().getValue(getELContext(), base, property);
            }
        });
    }

    @Override
    public Object getValue(final Object base, final int index) throws EvaluationException, PropertyNotFoundException
    {
        return getValue(base, Integer.valueOf(index));
    }

    @Override
    public void setValue(final Object base, final Object property, final Object newValue) throws EvaluationException,
        PropertyNotFoundException
    {
        if (base == null || property == null || isReadOnly (base, property))
            throw new PropertyNotFoundException();

        invokeResolver(new ResolverInvoker<Object>(base, property)
        {
            @Override
            public Object invoke(ELResolver resolver, ELContext context)
            {
                resolver.setValue(context, base, property, newValue);
                return null;
            }

            @Override
            String getMessage()
            {
                return super.getMessage() + " newValue: '" + newValue + "'";
            }
        });
    }

    @Override
    public void setValue(Object base, int index, Object newValue) throws EvaluationException, PropertyNotFoundException
    {
        if (base == null)
            throw new PropertyNotFoundException();

        if (base instanceof Object[])
        {
            if (index < 0 || index >= ((Object[])base).length)
            {
                throw new PropertyNotFoundException();
            }
        }
        else if (base instanceof List)
        {
            if (index < 0 || index >= ((List<?>)base).size())
            {
                throw new PropertyNotFoundException();
            }
        }

        setValue(base, Integer.valueOf(index), newValue);
    }

    @Override
    public boolean isReadOnly(final Object base, final Object property)
    {
        return invokeResolver(new ResolverInvoker<Boolean>(base, property)
        {
            @Override
            public Boolean invoke(ELResolver resolver, ELContext context)
            {
                return Boolean.valueOf(getELResolver().isReadOnly(getELContext(), base, property));
            }
        });
    }

    @Override
    public boolean isReadOnly(final Object base, final int index)
    {
        return isReadOnly(base, Integer.valueOf(index));
    }

    @Override
    public Class getType(final Object base, final Object property)
    {
        if (base == null || property == null)
            throw new PropertyNotFoundException();

        return invokeResolver(new ResolverInvoker<Class<?>>(base, property)
        {
            @Override
            public Class<?> invoke(final ELResolver resolver, final ELContext context)
            {
                return resolver.getType(context, base, property);
            }
        });
    }

    @Override
    public Class getType(Object base, int index)
    {
        if (base == null)
            throw new PropertyNotFoundException();

        if (base instanceof Object[])
        {
            if (index < 0 || index >= ((Object[])base).length)
            {
                throw new PropertyNotFoundException();
            }
        }
        else if (base instanceof List)
        {
            if (index < 0 || index >= ((List<?>)base).size())
            {
                throw new PropertyNotFoundException();
            }
        }

        return getType(base, Integer.valueOf(index));
    }

    // ~ Internal Helper Methods
    // ------------------------------------------------

    ELContext getELContext()
    {
        return getFacesContext().getELContext();
    }

    ELResolver getELResolver()
    {
        return getFacesContext().getApplication().getELResolver();
    }

    FacesContext getFacesContext()
    {
        return FacesContext.getCurrentInstance();
    }

    <T> T invokeResolver(ResolverInvoker<T> invoker) throws EvaluationException, PropertyNotFoundException
    {
        try
        {
            return invoker.invoke(getELResolver(), getELContext());
        }
        catch (javax.el.PropertyNotFoundException e)
        {
            throw new PropertyNotFoundException("property not found: " + invoker.getMessage() + ": " + e.getMessage(),
                e);
        }
        catch (ELException e)
        {
            throw new EvaluationException("exception: " + invoker.getMessage() + ": " + e.getMessage(), e);
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException("runtime exception: " + invoker.getMessage() + ": " + e.getMessage(), e);
        }
    }

    abstract static class ResolverInvoker<T>
    {
        private final Object _base;
        private final Object _property;

        ResolverInvoker(final Object base, final Object property)
        {
            _base = base;
            _property = property;
        }

        abstract T invoke(ELResolver resolver, ELContext context);

        String getMessage()
        {
            return "base: '" + _base + "' property/index: '" + _property + "'";
        }
    }
}
