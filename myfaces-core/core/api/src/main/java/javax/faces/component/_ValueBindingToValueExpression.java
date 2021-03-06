/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.component;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Wraps a ValueBinding inside a ValueExpression. Also allows access to the original ValueBinding object.
 * 
 * Although ValueExpression implements Serializable, this class implements StateHolder instead.
 * 
 * ATTENTION: If you make changes to this class, treat {@link _ValueBindingToValueExpression} accordingly.
 * 
 * @author Stan Silvert
 * @see javax.faces.component._ValueBindingToValueExpression
 */
@SuppressWarnings("deprecation")
class _ValueBindingToValueExpression extends ValueExpression implements StateHolder
{
    private static final long serialVersionUID = 8071429285360496554L;

    private static final Log logger = LogFactory.getLog(_ValueBindingToValueExpression.class);

    private ValueBinding _valueBinding;

    private boolean _transient;

    /**
     * No-arg constructor used during restoreState
     */
    protected _ValueBindingToValueExpression()
    {
    }

    private ValueBinding getNotNullValueBinding()
    {
        if (_valueBinding == null)
        {
            throw new IllegalStateException("value binding is null");
        }
        return _valueBinding;
    }

    /** Creates a new instance of ValueBindingToValueExpression */
    public _ValueBindingToValueExpression(ValueBinding valueBinding)
    {
        if (valueBinding == null)
        {
            throw new IllegalArgumentException("value binding must not be null");
        }
        this._valueBinding = valueBinding;
    }

    public ValueBinding getValueBinding()
    {
        return _valueBinding;
    }

    @Override
    public boolean isReadOnly(final ELContext context) throws NullPointerException, PropertyNotFoundException,
            ELException
    {
        return invoke(new Invoker<Boolean>()
        {
            public Boolean invoke()
            {
                return getNotNullValueBinding().isReadOnly(getFacesContext(context));
            }
        });
    }

    @Override
    public Object getValue(final ELContext context) throws NullPointerException, PropertyNotFoundException, ELException
    {
        return invoke(new Invoker<Object>()
        {
            public Object invoke()
            {
                return getNotNullValueBinding().getValue(getFacesContext(context));
            }
        });
    }

    @Override
    public Class<?> getType(final ELContext context) throws NullPointerException, PropertyNotFoundException,
            ELException
    {
        return invoke(new Invoker<Class<?>>()
        {
            public Class<?> invoke()
            {
                return getNotNullValueBinding().getType(getFacesContext(context));
            }
        });
    }

    @Override
    public void setValue(final ELContext context, final Object value) throws NullPointerException,
            PropertyNotFoundException, PropertyNotWritableException, ELException
    {
        invoke(new Invoker<Object>()
        {
            public Object invoke()
            {
                getNotNullValueBinding().setValue(getFacesContext(context), value);
                return null;
            }
        });
    }

    @Override
    public int hashCode()
    {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + (_transient ? 1231 : 1237);
        result = PRIME * result + ((_valueBinding == null) ? 0 : _valueBinding.hashCode());
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
        final _ValueBindingToValueExpression other = (_ValueBindingToValueExpression) obj;
        if (_transient != other._transient)
            return false;
        if (_valueBinding == null)
        {
            if (other._valueBinding != null)
                return false;
        }
        else if (!_valueBinding.equals(other._valueBinding))
            return false;
        return true;
    }

    @Override
    public boolean isLiteralText()
    {
        return false;
    }

    @Override
    public String getExpressionString()
    {
        return getNotNullValueBinding().getExpressionString();
    }

    @Override
    public Class<?> getExpectedType()
    {
        if (_valueBinding != null)
        {
            try
            {
                Object value = getNotNullValueBinding().getValue(FacesContext.getCurrentInstance());
                if (value != null)
                {
                    return value.getClass();
                }
            }
            catch (Throwable e)
            {
                logger.warn("Could not determine expected type for '" + _valueBinding.getExpressionString() + "': "
                        + e.getMessage(), e);
            }
        }
        return null;
    }

    public void restoreState(FacesContext context, Object state)
    {
        if (state instanceof ValueBinding)
        {
            _valueBinding = (ValueBinding) state;
        }
        else if (state != null)
        {
            Object[] stateArray = (Object[]) state;
            _valueBinding = (ValueBinding) _ClassUtils.newInstance((String) stateArray[0], ValueBinding.class);
            ((StateHolder) _valueBinding).restoreState(context, stateArray[1]);
        }
    }

    public Object saveState(FacesContext context)
    {
        if (!_transient)
        {
            if (_valueBinding instanceof StateHolder)
            {
                Object[] state = new Object[2];
                state[0] = _valueBinding.getClass().getName();
                state[1] = ((StateHolder) _valueBinding).saveState(context);
                return state;
            }
            return _valueBinding;
        }
        return null;
    }

    public void setTransient(boolean newTransientValue)
    {
        _transient = newTransientValue;
    }

    public boolean isTransient()
    {
        return _transient;
    }

    private FacesContext getFacesContext(ELContext context)
    {
        if (context == null)
        {
            throw new IllegalArgumentException("el context must not be null.");
        }
        FacesContext facesContext = (FacesContext) context.getContext(FacesContext.class);
        if (context == null)
        {
            throw new IllegalStateException("faces context not available in el context.");
        }
        return facesContext;
    }

    private <T> T invoke(Invoker<T> invoker)
    {
        try
        {
            return invoker.invoke();
        }
        catch (javax.faces.el.PropertyNotFoundException e)
        {
            throw new PropertyNotFoundException(e.getMessage(), e);
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