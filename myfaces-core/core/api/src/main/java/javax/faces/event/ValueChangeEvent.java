/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.event;

import javax.faces.component.UIComponent;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class ValueChangeEvent extends FacesEvent {
    private static final long serialVersionUID = -2490528664421353795L;
    // FIELDS
    private Object _oldValue;
    private Object _newValue;

    // CONSTRUCTORS
    public ValueChangeEvent(UIComponent uiComponent, Object oldValue, Object newValue)
    {
        super(uiComponent);
        if (uiComponent == null) throw new IllegalArgumentException("uiComponent");
        _oldValue = oldValue;
        _newValue = newValue;
    }

    // METHODS
    public Object getNewValue()
    {
        return _newValue;
    }

    public Object getOldValue()
    {
        return _oldValue;
    }

    public boolean isAppropriateListener(FacesListener facesListeners)
    {
        return facesListeners instanceof ValueChangeListener;
    }

    public void processListener(FacesListener facesListeners)
    {
        ((ValueChangeListener)facesListeners).processValueChange(this);
    }
}
