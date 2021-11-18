/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule.model;

import java.util.Date;


/**
 * <p>
 * A schedule entry is an appointment or event.
 * </p>
 *
 * @author Jurgen Lust (latest modification by $Author: skitching $)
 * @version $Revision: 349804 $
 */
public interface ScheduleEntry
{
    //~ Methods ----------------------------------------------------------------

    /**
     * @return Returns the description.
     */
    public abstract String getDescription();

    /**
     * @return Returns the endTime.
     */
    public abstract Date getEndTime();

    /**
     * @return Returns the id.
     */
    public abstract String getId();

    /**
     * @return Returns the startTime.
     */
    public abstract Date getStartTime();

    /**
     * @return Returns the subtitle.
     */
    public abstract String getSubtitle();

    /**
     * @return Returns the title.
     */
    public abstract String getTitle();

    /**
     * @return Returns true if the entry last all day.
     */
    public abstract boolean isAllDay();
}
//The End
