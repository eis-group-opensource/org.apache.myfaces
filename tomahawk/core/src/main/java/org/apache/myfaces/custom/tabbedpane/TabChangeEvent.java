/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tabbedpane;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */
public class TabChangeEvent
        extends FacesEvent
{
    private static final long serialVersionUID = -7249763750612129099L;
    //private static final Log log = LogFactory.getLog(TabChangeEvent.class);
    private int _oldTabIndex;
    private int _newTabIndex;

    public TabChangeEvent(UIComponent component, int oldTabIndex, int newTabIndex)
    {
        super(component);
        _oldTabIndex = oldTabIndex;
        _newTabIndex = newTabIndex;
        setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
    }

    public int getOldTabIndex()
    {
        return _oldTabIndex;
    }

    public int getNewTabIndex()
    {
        return _newTabIndex;
    }

    public boolean isAppropriateListener(FacesListener listener)
    {
        return listener instanceof TabChangeListener;
    }

    public void processListener(FacesListener listener)
    {
        ((TabChangeListener)listener).processTabChange(this);
    }

}
