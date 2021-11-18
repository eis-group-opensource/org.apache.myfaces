/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.component;

import javax.faces.FacesException;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

/**
 * Common base class for converting a MethodBinding to a FacesListener
 *
 * @author Stan Silvert
 */
abstract class _MethodBindingToListener implements StateHolder {
    
    protected MethodBinding methodBinding;
    
    public _MethodBindingToListener() {
    }
    
    /**
     * Creates a new instance of MethodBindingToListener
     */
    public _MethodBindingToListener(MethodBinding methodBinding) {
        if (methodBinding == null) throw new NullPointerException("methodBinding can not be null");
        if (!(methodBinding instanceof StateHolder)) throw new IllegalArgumentException("methodBinding must implement the StateHolder interface");
        
        this.methodBinding = methodBinding;
    }

    private FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected void invokeMethodBinding(FacesEvent event) throws AbortProcessingException {
        try {
            methodBinding.invoke(getFacesContext(), new Object[] {event});
        }
        catch (EvaluationException e) {
            Throwable cause = e.getCause();
            if (cause != null && cause instanceof AbortProcessingException) {
                throw (AbortProcessingException)cause;
            }
            
            throw e;
        }
    }
    
    public MethodBinding getMethodBinding() {
        return methodBinding;
    }
    
    public void restoreState(FacesContext context, Object state) {
        Object[] stateArray = (Object[])state;
        try {
            methodBinding = (MethodBinding)Thread.currentThread()
                                                 .getContextClassLoader()
                                                 .loadClass((String)stateArray[0])
                                                 .newInstance();
        } catch (Exception e) {
            throw new FacesException(e);
        }
       
        ((StateHolder)methodBinding).restoreState(context, stateArray[1]);
    }

    public Object saveState(FacesContext context) {
        Object[] stateArray = new Object[2];
        stateArray[0] = methodBinding.getClass().getName();
        stateArray[1] = ((StateHolder)methodBinding).saveState(context);
        return stateArray;
    }

    public void setTransient(boolean newTransientValue) {
        ((StateHolder)methodBinding).setTransient(newTransientValue);
    }

    public boolean isTransient() {
        return ((StateHolder)methodBinding).isTransient();
    }
    
}
