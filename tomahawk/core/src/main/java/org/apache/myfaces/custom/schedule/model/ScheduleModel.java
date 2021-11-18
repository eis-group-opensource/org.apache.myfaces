/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule.model;

import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;


/**
 * <p>
 * The underlying model of the UISchedule component. You should implement this
 * interface when creating real implementations, which would typically be backed
 * by a database.
 * </p>
 *
 * @author Jurgen Lust (latest modification by $Author: schof $)
 * @version $Revision: 368941 $
 */
public interface ScheduleModel
{
    //~ Static fields/initializers ---------------------------------------------

    public static final int DAY = 0;
    public static final int WORKWEEK = 1;
    public static final int WEEK = 2;
    public static final int MONTH = 3;

    //~ Methods ----------------------------------------------------------------

    /**
     * @return true if there are no entries
     */
    public abstract boolean isEmpty();

    /**
     * @param mode the mode: DAY, WORKWEEK, WEEK or MONTH
     */
    public abstract void setMode(int mode);

    /**
     * @return the mode: DAY, WORKWEEK, WEEK or MONTH
     */
    public abstract int getMode();

    /**
     * @param date the date to select
     */
    public abstract void setSelectedDate(Date date);

    /**
     * @return the selected date
     */
    public abstract Date getSelectedDate();

    /**
     * @param selectedEntry the entry to select
     */
    public abstract void setSelectedEntry(ScheduleEntry selectedEntry);

    /**
     * @return the selected entry
     */
    public abstract ScheduleEntry getSelectedEntry();
    
    /**
     * @return whether an entry is currently selected
     */
    public abstract boolean isEntrySelected();

    /**
     * <p>
     * Check if the schedule contains the specified date
     * </p>
     *
     * @param date the date to check
     *
     * @return whether the schedule containts this date
     */
    public abstract boolean containsDate(Date date);

    /**
     * <p>
     * Get the day at position <i>index</i>.
     * </p>
     *
     * @param index the index
     *
     * @return the day
     */
    public abstract Object get(int index);

    /**
     * @return an iterator for the days
     */
    public abstract Iterator iterator();

    /**
     * @return the number of days in this model
     */
    public abstract int size();
    
    /**
     * Add an entry to the this model. 
     *  
     * @param entry the entry to be added
     */
    public abstract void addEntry(ScheduleEntry entry);
    
    /**
     * Remove an entry from this model
     * 
     * @param entry the entry to be removed
     */
    public abstract void removeEntry(ScheduleEntry entry);
    
    /**
     * Remove the currently selected entry from this model. If no entry
     * is currently selected, nothing should happen.
     */
    public abstract void removeSelectedEntry();
    
    /**
     * Reload the entries for the currently selected period
     */
    public abstract void refresh();
    
    /**
     * @return The timezone for which the model should be built
     */
    public abstract TimeZone getTimeZone();
    
    /**
     * @return true, if each day contains the same set of intervals
     */
    public abstract boolean containsRepeatedIntervals();
}
//The End
