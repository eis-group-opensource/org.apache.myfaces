/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.component;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.MethodNotFoundException;
import javax.el.PropertyNotFoundException;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;

/**
 * Converts a MethodBinding to a MethodExpression
 * 
 * TODO: find a way to share the implementation of class with impl.
 * 
 * @author Stan Silvert
 */
@SuppressWarnings("deprecation")
class _MethodBindingToMethodExpression extends MethodExpression implements StateHolder
{
    private static final Class[] EXPECTED_TYPES = new Class[] { MethodBinding.class, StateHolder.class };

    private MethodBinding methodBinding;

    private boolean _transientFlag;

    private transient MethodInfo methodInfo;

    /**
     * No-arg constructor used during restoreState
     */
    protected _MethodBindingToMethodExpression()
    {
    }

    /** Creates a new instance of MethodBindingToMethodExpression */
    public _MethodBindingToMethodExpression(MethodBinding methodBinding)
    {
        checkNullArgument(methodBinding, "methodBinding");
        this.methodBinding = methodBinding;
    }

    /**
     * Return the wrapped MethodBinding.
     */
    public MethodBinding getMethodBinding()
    {
        return methodBinding;
    }
    
    void setMethodBinding(MethodBinding methodBinding)
    {
        this.methodBinding = methodBinding;
    }

    /**
     * Note: MethodInfo.getParamTypes() may incorrectly return an empty class array if invoke() has not been called.
     * 
     * @throws IllegalStateException
     *             if expected params types have not been determined.
     */
    public MethodInfo getMethodInfo(ELContext context) throws PropertyNotFoundException, MethodNotFoundException,
            ELException
    {
        checkNullArgument(context, "elcontext");
        checkNullState(methodBinding, "methodBinding");

        if (methodInfo == null)
        {
            final FacesContext facesContext = (FacesContext) context.getContext(FacesContext.class);
            if (facesContext != null)
            {
                methodInfo = invoke(new Invoker<MethodInfo>()
                {
                    public MethodInfo invoke()
                    {
                        return new MethodInfo(null, methodBinding.getType(facesContext), null);
                    }
                });
            }
        }
        return methodInfo;
    }

    public Object invoke(ELContext context, final Object[] params) throws PropertyNotFoundException,
            MethodNotFoundException, ELException
    {
        checkNullArgument(context, "elcontext");
        checkNullState(methodBinding, "methodBinding");
        final FacesContext facesContext = (FacesContext) context.getContext(FacesContext.class);
        if (facesContext != null)
        {
            return invoke(new Invoker<Object>()
            {
                public Object invoke()
                {
                    return methodBinding.invoke(facesContext, params);
                }
            });
        }
        return null;
    }

    public boolean isLiteralText()
    {
        if (methodBinding == null)
            throw new IllegalStateException("methodBinding is null");
        String expr = methodBinding.getExpressionString();
        return !(expr.startsWith("#{") && expr.endsWith("}"));
    }

    public String getExpressionString()
    {
        return methodBinding.getExpressionString();
    }

    public Object saveState(FacesContext context)
    {
        if (!isTransient())
        {
            if (methodBinding instanceof StateHolder)
            {
                Object[] state = new Object[2];
                state[0] = methodBinding.getClass().getName();
                state[1] = ((StateHolder) methodBinding).saveState(context);
                return state;
            }
            else
            {
                return methodBinding;
            }
        }
        return null;
    }

    public void restoreState(FacesContext context, Object state)
    {
        if (state instanceof MethodBinding)
        {
            methodBinding = (MethodBinding) state;
            methodInfo = null;
        }
        else if (state != null)
        {
            Object[] values = (Object[]) state;
            methodBinding = (MethodBinding) _ClassUtils.newInstance(values[0].toString(), EXPECTED_TYPES);
            ((StateHolder) methodBinding).restoreState(context, values[1]);
            methodInfo = null;
        }
    }

    public void setTransient(boolean transientFlag)
    {
        _transientFlag = transientFlag;
    }

    public boolean isTransient()
    {
        return _transientFlag;
    }

    @Override
    public int hashCode()
    {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((methodBinding == null) ? 0 : methodBinding.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final _MethodBindingToMethodExpression other = (_MethodBindingToMethodExpression) obj;
        if (methodBinding == null)
        {
            if (other.methodBinding != null)
                return false;
        }
        else if (!methodBinding.equals(other.methodBinding))
            return false;
        return true;
    }

    private void checkNullState(Object notNullInstance, String instanceName)
    {
        if (notNullInstance == null)
            throw new IllegalStateException(instanceName + " is null");
    }

    private void checkNullArgument(Object notNullInstance, String instanceName)
    {
        if (notNullInstance == null)
            throw new IllegalArgumentException(instanceName + " is null");
    }

    private <T> T invoke(Invoker<T> invoker)
    {
        try
        {
            return invoker.invoke();
        }
        catch (javax.faces.el.MethodNotFoundException e)
        {
            throw new MethodNotFoundException(e.getMessage(), e);
        }
        catch (EvaluationException e)
        {
            throw new ELException(e.getMessage(), e);
        }
    }

    private interface Invoker<T>
    {
        T invoke();
    }
}
