/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.event;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

/**
 * See Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
public class MethodExpressionActionListener implements ActionListener, StateHolder {
    
    private MethodExpression methodExpression;
    
    private boolean isTransient = false;
    
    /** Creates a new instance of MethodExpressionActionListener */
    public MethodExpressionActionListener() {
    }
    
    public MethodExpressionActionListener(MethodExpression methodExpression) {
        this.methodExpression = methodExpression;
    }

    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
        try
        {
            Object[] params = new Object[]{actionEvent};
            methodExpression.invoke(elContext(), params);
        }
        catch (ELException e)
        {
            Throwable cause = e.getCause();
            if (cause != null && cause instanceof AbortProcessingException)
            {
                throw (AbortProcessingException)cause;
            }
            else
            {
                throw e;
            }
        }
    }
    
    private ELContext elContext() {
        return FacesContext.getCurrentInstance().getELContext();
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
