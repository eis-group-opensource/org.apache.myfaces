/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.convert;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

/**
 * Converts a MethodExpression to a MethodBinding. See JSF 1.2 spec section 5.8.4
 * 
 * ATTENTION: If you make changes to this class, treat javax.faces.component._MethodExpressionToMethodBinding
 * accordingly.
 * 
 * @author Stan Silvert
 * @see javax.faces.component._MethodExpressionToMethodBinding
 */
public final class MethodExpressionToMethodBinding extends MethodBinding implements StateHolder
{

    private MethodExpression methodExpression;

    private boolean isTransient = false;

    public MethodExpressionToMethodBinding()
    {
        methodExpression = null;
    }

    /** Creates a new instance of MethodExpressionToMethodBinding */
    public MethodExpressionToMethodBinding(final MethodExpression methodExpression)
    {
        this.methodExpression = methodExpression;
    }

    @Override
    public String getExpressionString()
    {
        return methodExpression.getExpressionString();
    }

    @Override
    public Class getType(FacesContext facesContext) throws MethodNotFoundException
    {

        try
        {
            return methodExpression.getMethodInfo(facesContext.getELContext()).getReturnType();
        }
        catch (javax.el.MethodNotFoundException e)
        {
            throw new javax.faces.el.MethodNotFoundException(e);
        }
    }

    @Override
    public Object invoke(final FacesContext facesContext, final Object[] params) throws EvaluationException,
                                                                                MethodNotFoundException
    {

        try
        {
            return methodExpression.invoke(facesContext.getELContext(), params);
        }
        catch (javax.el.MethodNotFoundException e)
        {
            throw new javax.faces.el.MethodNotFoundException(e);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e.getCause());
        }
    }

    // -------- StateHolder methods -------------------------------------------

    public void restoreState(final FacesContext context, final Object state)
    {
        methodExpression = (MethodExpression) state;
    }

    public Object saveState(final FacesContext context)
    {
        return methodExpression;
    }

    public void setTransient(final boolean newTransientValue)
    {
        isTransient = newTransientValue;
    }

    public boolean isTransient()
    {
        return isTransient;
    }

}
