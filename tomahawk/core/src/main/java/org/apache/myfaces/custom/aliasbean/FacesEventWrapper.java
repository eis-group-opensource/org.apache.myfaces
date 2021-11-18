/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.aliasbean;

import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;
import javax.faces.component.UIComponent;

/**
 * @author Sylvain Vieujot (latest modification by $Author: mmarinschek $)
 * @version $Revision: 478533 $ $Date: 2006-11-23 13:10:01 +0200 (Thu, 23 Nov 2006) $
 */

class FacesEventWrapper extends FacesEvent {
    private static final long serialVersionUID = -6878195444276533114L;
    private FacesEvent _wrappedFacesEvent;

    public FacesEventWrapper(FacesEvent facesEvent, UIComponent redirectComponent) {
        super(redirectComponent);
        _wrappedFacesEvent = facesEvent;
    }

    public PhaseId getPhaseId() {
        return _wrappedFacesEvent.getPhaseId();
    }

    public void setPhaseId(PhaseId phaseId) {
        _wrappedFacesEvent.setPhaseId(phaseId);
    }

    public void queue() {
        _wrappedFacesEvent.queue();
    }

    public String toString() {
        return _wrappedFacesEvent.toString();
    }

    public boolean isAppropriateListener(FacesListener faceslistener) {
        return _wrappedFacesEvent.isAppropriateListener(faceslistener);
    }

    public void processListener(FacesListener faceslistener) {
        _wrappedFacesEvent.processListener(faceslistener);
    }

    public FacesEvent getWrappedFacesEvent() {
        return _wrappedFacesEvent;
    }
}