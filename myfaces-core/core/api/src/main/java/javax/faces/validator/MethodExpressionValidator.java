/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.validator;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
public class MethodExpressionValidator implements Validator, StateHolder {
    
    private MethodExpression methodExpression;
    
    private boolean isTransient = false;
    
    /** Creates a new instance of MethodExpressionValidator */
    public MethodExpressionValidator() {
    }
    
    public MethodExpressionValidator(MethodExpression methodExpression) {
        if (methodExpression == null) throw new NullPointerException("methodExpression can not be null.");
        
        this.methodExpression = methodExpression;
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object[] params = new Object[3];
        params[0] = context;
        params[1] = component;
        params[2] = value;
        
        try {
            methodExpression.invoke(context.getELContext(), params);
        } 
        catch (ELException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ValidatorException) {
                throw (ValidatorException) cause;
            }
            else {
                throw e;
            }
        }
    }

    public void restoreState(FacesContext context, Object state) {
        methodExpression = (MethodExpression)state;
    }

    public Object saveState(FacesContext context) {
        return methodExpression;
    }

    public void setTransient(boolean newTransientValue) {
        isTransient = newTransientValue;
    }

    public boolean isTransient() {
        return isTransient;
    }
    
}
