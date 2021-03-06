/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.schedule;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import org.apache.myfaces.custom.schedule.model.Interval;
import org.apache.myfaces.custom.schedule.model.ScheduleDay;

/**
 * An event that is fired when the schedule is clicked, and the submitOnClick
 * property on the schedule is set to true.
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$
 */
public class ScheduleMouseEvent extends FacesEvent implements Serializable
{
    public static final int SCHEDULE_NOTHING_CLICKED = 0;
    public static final int SCHEDULE_BODY_CLICKED = 1;
    public static final int SCHEDULE_HEADER_CLICKED = 2;
    public static final int SCHEDULE_ENTRY_CLICKED = 3;

    private static final long serialVersionUID = -2810582008938303475L;

    private final int eventType;

    public ScheduleMouseEvent(final HtmlSchedule source, final int eventType)
    {
        super(source);
        //the FacesEvent throws an IllegalArgumentException when source == null
        //so don't bother here
        this.eventType = eventType;
    }

    public Date getClickedDate()
    {
        return getSchedule().getLastClickedDateAndTime();
    }

    public Date getClickedTime()
    {
        return getSchedule().getLastClickedDateAndTime();
    }
   
    public Interval getClickedInterval()
    {
        Date clickedDate = getClickedDate();
        
        for (Iterator intervalIt = getSchedule().getModel().iterator(); intervalIt.hasNext(); ) {
            ScheduleDay day = (ScheduleDay) intervalIt.next();

            if (day.equalsDate(clickedDate))
            {
                return day.getInterval(clickedDate);
            }
        }
        
        return null;
    }

    public int getEventType()
    {
        return eventType;
    }

    /**
     * @return The schedule component where the mouse event originated
     */
    public HtmlSchedule getSchedule()
    {
        return (HtmlSchedule) getSource();
    }

    /**
     * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
     */
    public boolean isAppropriateListener(FacesListener listener)
    {
        return (listener instanceof ScheduleMouseListener);
    }

    /**
     * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
     */
    public void processListener(FacesListener listener)
    {
        if (listener instanceof ScheduleMouseListener)
        {
            ScheduleMouseListener mouseListener = (ScheduleMouseListener) listener;
            mouseListener.processMouseEvent(this);
        }
    }

}
