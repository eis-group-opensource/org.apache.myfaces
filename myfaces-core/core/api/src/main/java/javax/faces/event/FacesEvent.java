/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.event;

import java.util.EventObject;

import javax.faces.component.UIComponent;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public abstract class FacesEvent extends EventObject
{
    // FIELDS
    private PhaseId _phaseId;

    // CONSTRUCTORS
    public FacesEvent(UIComponent uiComponent)
    {
        super(uiComponent);
        if (uiComponent == null) throw new IllegalArgumentException("uiComponent");
        _phaseId = PhaseId.ANY_PHASE;
    }

    // METHODS
    public abstract boolean isAppropriateListener(FacesListener faceslistener);

    public abstract void processListener(FacesListener faceslistener);

    public UIComponent getComponent()
    {
        return (UIComponent)getSource();
    }

    public void queue()
    {
        ((UIComponent)getSource()).queueEvent(this);
    }

    public PhaseId getPhaseId()
    {
        return _phaseId;
    }

    public void setPhaseId(PhaseId phaseId)
    {
        _phaseId = phaseId;
    }
}
