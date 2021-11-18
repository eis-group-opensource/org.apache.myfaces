/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.schedule;

/**
 * A listener that listens for ScheduleMouseEvents
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$
 */
public interface ScheduleMouseListener
{
    /**
     * process the mouse event from the schedule
     * 
     * @param event the mouse event
     */
    void processMouseEvent(ScheduleMouseEvent event);
}
