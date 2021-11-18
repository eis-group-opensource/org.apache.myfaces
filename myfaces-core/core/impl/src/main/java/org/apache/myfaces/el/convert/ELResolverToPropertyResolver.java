/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.convert;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

/**
 * 
 * @author Stan Silvert
 */
public final class ELResolverToPropertyResolver extends PropertyResolver
{

    private final ELResolver elResolver;

    /**
     * Creates a new instance of ELResolverToPropertyResolver
     */
    public ELResolverToPropertyResolver(final ELResolver elResolver)
    {
        this.elResolver = elResolver;
    }

    @Override
    public boolean isReadOnly(final Object base, final int index) throws EvaluationException, PropertyNotFoundException
    {

        try
        {
            return elResolver.isReadOnly(elContext(), base, Integer.valueOf(index));
        }
        catch (javax.el.PropertyNotFoundException e)
        {
            throw new javax.faces.el.PropertyNotFoundException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }

    }

    @Override
    public boolean isReadOnly(final Object base, final Object property) throws EvaluationException, PropertyNotFoundException
    {

        try
        {
            return elResolver.isReadOnly(elContext(), base, property);
        }
        catch (javax.el.PropertyNotFoundException e)
        {
            throw new javax.faces.el.PropertyNotFoundException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }

    }

    @Override
    public Object getValue(final Object base, final int index) throws EvaluationException, PropertyNotFoundException
    {

        try
        {
            return elResolver.getValue(elContext(), base, Integer.valueOf(index));
        }
        catch (javax.el.PropertyNotFoundException e)
        {
            throw new javax.faces.el.PropertyNotFoundException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }

    }

    @Override
    public Object getValue(final Object base, final Object property) throws EvaluationException, PropertyNotFoundException
    {

        try
        {
            return elResolver.getValue(elContext(), base, property);
        }
        catch (javax.el.PropertyNotFoundException e)
        {
            throw new javax.faces.el.PropertyNotFoundException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }
    }

    @Override
    public Class getType(final Object base, int index) throws EvaluationException, PropertyNotFoundException
    {

        try
        {
            return elResolver.getType(elContext(), base, Integer.valueOf(index));
        }
        catch (javax.el.PropertyNotFoundException e)
        {
            throw new javax.faces.el.PropertyNotFoundException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }
    }

    @Override
    public Class getType(final Object base, final Object property) throws EvaluationException, PropertyNotFoundException
    {

        try
        {
            return elResolver.getType(elContext(), base, property);
        }
        catch (javax.el.PropertyNotFoundException e)
        {
            throw new javax.faces.el.PropertyNotFoundException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }
    }

    @Override
    public void setValue(final Object base, final Object property, final Object value) throws EvaluationException,
                                                                                      PropertyNotFoundException
    {

        try
        {
            elResolver.setValue(elContext(), base, property, value);
        }
        catch (javax.el.PropertyNotFoundException e)
        {
            throw new javax.faces.el.PropertyNotFoundException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }
    }

    @Override
    public void setValue(final Object base, int index, final Object value) throws EvaluationException, PropertyNotFoundException
    {

        try
        {
            elResolver.setValue(elContext(), base, Integer.valueOf(index), value);
        }
        catch (javax.el.PropertyNotFoundException e)
        {
            throw new javax.faces.el.PropertyNotFoundException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }

    }

    private ELContext elContext()
    {
        return FacesContext.getCurrentInstance().getELContext();
    }

}
