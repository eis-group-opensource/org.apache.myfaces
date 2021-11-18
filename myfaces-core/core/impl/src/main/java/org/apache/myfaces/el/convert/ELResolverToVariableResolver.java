/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.convert;

import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

/**
 * Provides ELResolver wrapper so that legacy apps which rely on a VariableResolver can still work.
 * 
 * @author Stan Silvert
 */
public final class ELResolverToVariableResolver extends VariableResolver
{

    private final ELResolver elResolver;

    /**
     * Creates a new instance of ELResolverToVariableResolver
     */
    public ELResolverToVariableResolver(final ELResolver elResolver)
    {
        if (elResolver == null)
            throw new NullPointerException();
        this.elResolver = elResolver;
    }

    @Override
    public Object resolveVariable(final FacesContext facesContext, final String name) throws EvaluationException
    {

        try
        {
            return elResolver.getValue(facesContext.getELContext(), null, name);
        }
        catch (PropertyNotFoundException e)
        {
            throw new EvaluationException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }
    }

}
