/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule.util;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.myfaces.custom.schedule.model.ScheduleEntry;

/**
 * <p>
 * Comparator for ScheduleEntry objects. This is needed for correctly
 * rendering the schedule.
 * </p>
 *
 * @author Jurgen Lust (latest modification by $Author: schof $)
 * @author Bruno Aranda (adaptation of Jurgen's code to myfaces)
 * @version $Revision: 381473 $
 */
public class ScheduleEntryComparator implements Comparator, Serializable
{
    private static final long serialVersionUID = 6863061256811196989L;

    //~ Methods ----------------------------------------------------------------

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object o1, Object o2)
    {
        if (o1 instanceof ScheduleEntry && o2 instanceof ScheduleEntry)
        {
            ScheduleEntry entry1 = (ScheduleEntry) o1;
            ScheduleEntry entry2 = (ScheduleEntry) o2;

            int returnint = entry1.getStartTime().compareTo(
                    entry2.getStartTime());
            if (returnint == 0)
            {
                returnint = entry1.getEndTime().compareTo(entry2.getEndTime());
            }
            if (returnint == 0)
            {
                returnint = entry1.getId().compareTo(entry2.getId());
            }

            return returnint;
        }

        return 1;
    }
}
//The End
