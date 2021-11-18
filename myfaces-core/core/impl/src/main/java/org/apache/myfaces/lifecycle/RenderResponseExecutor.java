/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.lifecycle;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

/**
 * Implements the lifecycle as described in Spec. 1.0 PFD Chapter 2
 * @author Nikolay Petrov
 *
 * render response phase (JSF Spec 2.2.6)
 */
class RenderResponseExecutor implements PhaseExecutor {
    public boolean execute(FacesContext facesContext) {
        Application application = facesContext.getApplication();
        ViewHandler viewHandler = application.getViewHandler();

        try {
            viewHandler.renderView(facesContext, facesContext.getViewRoot());
        } catch (IOException e) {
            throw new FacesException(e.getMessage(), e);
        }
        return false;
    }

    public PhaseId getPhase() {
        return PhaseId.RENDER_RESPONSE;
    }
}
