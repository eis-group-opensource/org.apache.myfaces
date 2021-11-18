/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule.model;

import java.util.Date;

/**
 * <p>
 * This class represents an interval of up to half an hour.
 * The interval will always round up to the next half hour
 * e.g. a start time of 14:15 will generate an interval with
 * end time of 14:30.
 * </p>
 * @since 1.1.7
 * @author Peter Mahoney
 * @version $Revision: 371736 $
 */
public class HalfHourInterval extends Interval {

    public static final long HALF_HOUR = 1000 * 60 * 30;

    public HalfHourInterval(Date startTime, Date maxEnd)
    {
        super(null, startTime, new Date(Math.min(startTime.getTime() + HALF_HOUR, maxEnd.getTime())));
    }

    private HalfHourInterval(String label, Date startTime, Date endTime)
    {
        super(label, startTime, endTime); 
    }
    
    /**
     * Create a new half hour interval following on from the supplied interval.
     * The interval will be anything up to half an hour, depending on when the
     * end of the previous interval was. If an interval cannot be fitted between
     * the end of the last interval and the maximum end time, null will be returned.
     * 
     * @param interval The previous interval
     * @param maxEnd The maximum end time of the new interval
     * @return The next half hour interval
     */
    public static Interval next(Interval interval, Date maxEnd) {
        Date startTime = interval.getEndTime();
        
        if (startTime.before(maxEnd))
        {
            Date endTime = new Date(Math.min(startTime.getTime() - (startTime.getTime() % HALF_HOUR) + HALF_HOUR, maxEnd.getTime()));
            
            return new HalfHourInterval(null, startTime, endTime);
        }
        else
        {
            
            return null;
        }
    }
}
