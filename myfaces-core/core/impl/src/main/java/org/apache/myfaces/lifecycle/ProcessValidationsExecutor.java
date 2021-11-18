/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.lifecycle;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

/**
 * Implements the lifecycle as described in Spec. 1.0 PFD Chapter 2
 * @author Nikolay Petrov
 *
 * Process validations phase (JSF Spec 2.2.3)
 */
class ProcessValidationsExecutor implements PhaseExecutor {
    public boolean execute(FacesContext facesContext) {
        facesContext.getViewRoot().processValidators(facesContext);
        return false;
    }

    public PhaseId getPhase() {
        return PhaseId.PROCESS_VALIDATIONS;
    }
}
