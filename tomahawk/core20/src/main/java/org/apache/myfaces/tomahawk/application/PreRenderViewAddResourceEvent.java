/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.tomahawk.application;

import javax.faces.component.UIComponent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.SystemEventListener;

/**
 * This event is triggered after PreRenderViewEvent but before 
 * ViewHandler.render, to give the chance to component to add 
 * resources to the current view before render occur.
 * 
 * <p>The component resources to be added are supposed to be transient, so
 * each time before the view will be rendered, this event will be published
 * and the resources will be on the page.</p>
 * 
 * <p>This event should be propagated only for the "real" component tree, that
 * means for a datatable component it should to traverse all rows.</p>
 * 
 * @since 1.1.10
 */
public class PreRenderViewAddResourceEvent extends ComponentSystemEvent
{

    /**
     * 
     */
    private static final long serialVersionUID = 3534476922995054263L;

    public PreRenderViewAddResourceEvent(UIComponent component)
    {
        super(component);
    }

    @Override
    public boolean isAppropriateListener(FacesListener listener)
    {
        return listener instanceof SystemEventListener;
    }
}
