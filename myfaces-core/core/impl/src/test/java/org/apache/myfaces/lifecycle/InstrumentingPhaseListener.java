/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.lifecycle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class InstrumentingPhaseListener implements PhaseListener {
    private static final long serialVersionUID = -3222250142846233648L;
    private PhaseId listenPhaseId = null;
    private PhaseId eventPhaseId = null;
    private boolean before = true;
    private boolean after = true;
    private boolean render = false;
    private boolean complete = false;
    private List afterPhases = new ArrayList();
    private List beforePhases = new ArrayList();

    public InstrumentingPhaseListener() {
    }

    public InstrumentingPhaseListener(PhaseId interestingPhaseId) {
        this.listenPhaseId = interestingPhaseId;
    }
    
    public void afterPhase(PhaseEvent event) {
        afterPhases.add(event.getPhaseId());
        if(null != eventPhaseId && event.getPhaseId().equals(eventPhaseId)) {
            if(after && render) {
                event.getFacesContext().renderResponse();
            } else if(after && complete) {
                event.getFacesContext().responseComplete();
            }
        }
    }

    public void beforePhase(PhaseEvent event) {
        beforePhases.add(event.getPhaseId());
        if(null != eventPhaseId && event.getPhaseId().equals(eventPhaseId)) {
            if(before && render) {
                event.getFacesContext().renderResponse();
            } else if(before && complete) {
                event.getFacesContext().responseComplete();
            }
        }
    }

    public boolean isBefore() {
        return before;
    }

    public void setBefore(boolean before) {
        this.before = before;
    }

    public boolean isAfter() {
        return after;
    }

    public void setAfter(boolean after) {
        this.after = after;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public PhaseId getPhaseId() {
        if(null == listenPhaseId) {
            return PhaseId.ANY_PHASE;
        }
        return listenPhaseId;
    }

    public void setEventPhaseId(PhaseId phaseId) {
        this.eventPhaseId = phaseId;
    }

    public List getAfterPhases() {
        return afterPhases;
    }

    public List getBeforePhases() {
        return beforePhases;
    }

    
}
