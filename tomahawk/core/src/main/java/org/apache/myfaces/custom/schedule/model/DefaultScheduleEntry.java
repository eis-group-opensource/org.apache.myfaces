/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule.model;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.myfaces.custom.schedule.util.ScheduleUtil;


/**
 * <p>
 * A default implementation of a Schedule entry
 * </p>
 *
 * @author Jurgen Lust (latest modification by $Author: werpu $)
 * @version $Revision: 371736 $
 */
public class DefaultScheduleEntry
    implements Serializable, ScheduleEntry
{
    //~ Instance fields --------------------------------------------------------

    /**
     * serial id for serialisation versioning
     */
    private static final long serialVersionUID = 1L;
    private Date endTime;
    private Date startTime;
    private String description;
    private String id;
    private String subtitle;
    private String title;
    private boolean allDay;
    private TimeZone timeZone;

    //~ Methods ----------------------------------------------------------------

    /**
     * @return the current timezone
     */
    public TimeZone getTimeZone ()
    {
      return this.timeZone;
    }

    /**
     * Set current timezone
     * @param timeZone the timezone
     */
    public void setTimeZone (TimeZone timeZone)
    {
      this.timeZone = timeZone;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param endTime The endTime to set.
     */
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    /**
     * @return Returns the endTime.
     */
    public Date getEndTime()
    {
      if (endTime == null) endTime = new Date();
      if (isAllDay()) {
        Date truncated = ScheduleUtil.truncate(endTime, getTimeZone());
        Calendar cal = ScheduleUtil.getCalendarInstance(truncated, getTimeZone());
        cal.add(Calendar.MILLISECOND, -1);
        truncated = cal.getTime();
        if (!truncated.equals(endTime)) {
          cal.add(Calendar.DATE, 1);
        }
        return cal.getTime();
      }
        return endTime;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return Returns the id.
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param startTime The startTime to set.
     */
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    /**
     * @return Returns the startTime. If the allDay property is true, the startTime is truncated to 00:00.
     */
    public Date getStartTime()
    {
      if (startTime == null) startTime = new Date();
      if (isAllDay()) {
        return ScheduleUtil.truncate(startTime, getTimeZone());
      } else {
            return startTime;
      }
    }

    /**
     * @param subtitle The subtitle to set.
     */
    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    /**
     * @return Returns the subtitle.
     */
    public String getSubtitle()
    {
        return subtitle;
    }

    /**
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }
    
    /**
     * @return Returns true if the entry last all day.
     */
    public boolean isAllDay()
    {
        return allDay;
    }

    /**
     * @param allDay does the entry last all day?
     */
    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }
    
}
//The End
