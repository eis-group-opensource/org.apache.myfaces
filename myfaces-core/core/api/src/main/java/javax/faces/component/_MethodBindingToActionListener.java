/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * Converts a MethodBinding to an ActionListener
 *
 * @author Stan Silvert
 */
class _MethodBindingToActionListener extends _MethodBindingToListener implements ActionListener {
    
    public _MethodBindingToActionListener() {
        super();
    }
    
    public _MethodBindingToActionListener(MethodBinding methodBinding) {
        super(methodBinding);
    }
    
    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
        invokeMethodBinding(actionEvent);
    }
    
}
