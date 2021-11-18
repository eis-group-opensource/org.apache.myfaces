/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.component;

import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 * Converts a MethodBinding to a ValueChangeListener
 *
 * @author Stan Silvert
 */
class _MethodBindingToValueChangeListener extends _MethodBindingToListener implements ValueChangeListener {
    
    public _MethodBindingToValueChangeListener() {
        super();
    }
    
    public _MethodBindingToValueChangeListener(MethodBinding methodBinding) {
        super(methodBinding);
    }
    
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        invokeMethodBinding(event);
    }

}
