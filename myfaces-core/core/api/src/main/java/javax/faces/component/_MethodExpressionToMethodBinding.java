/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.component;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

/**
 * Converts a MethodExpression to a MethodBinding.  
 * See JSF 1.2 spec section 5.8.4
 *
 * ATTENTION: If you make changes to this class, treat 
 * org.apache.myfaces.el.convert.MethodExpressionToMethodBinding
 * accordingly.
 *
 * @author Stan Silvert
 * @see org.apache.myfaces.el.convert.MethodExpressionToMethodBinding
 */
class _MethodExpressionToMethodBinding extends MethodBinding implements StateHolder {
    
    private MethodExpression methodExpression;
    
    private boolean isTransient = false;
    
    public _MethodExpressionToMethodBinding() {
        methodExpression = null;
    }

    /** Creates a new instance of MethodExpressionToMethodBinding */
    public _MethodExpressionToMethodBinding(MethodExpression methodExpression) {
        this.methodExpression = methodExpression;
    }
    
    @Override
    public String getExpressionString()
    {
        return methodExpression.getExpressionString();
    }

    public Class getType(FacesContext facesContext) 
        throws MethodNotFoundException {
        
        try {
            return methodExpression.getMethodInfo(facesContext.getELContext()).getReturnType();
        } catch (javax.el.MethodNotFoundException e) {
            throw new javax.faces.el.MethodNotFoundException(e);
        } catch (ELException e) {
            throw new EvaluationException(e);
        }
    }

    public Object invoke(FacesContext facesContext, Object[] params) 
        throws EvaluationException, MethodNotFoundException {
        
        try {
            return methodExpression.invoke(facesContext.getELContext(), params);
        } catch (javax.el.MethodNotFoundException e) {
            throw new javax.faces.el.MethodNotFoundException(e);
        } catch (ELException e) {
            throw new EvaluationException(e);
        }
    }

// -------- StateHolder methods -------------------------------------------    
    
    public void restoreState(FacesContext context, Object state) {
        if(state != null)
            methodExpression = (MethodExpression)state;
    }

    public Object saveState(FacesContext context) {
        if(!isTransient)
            return methodExpression;
        return null;
    }

    public void setTransient(boolean newTransientValue) {
        isTransient = newTransientValue;
    }

    public boolean isTransient() {
        return isTransient;
    }
    
}
