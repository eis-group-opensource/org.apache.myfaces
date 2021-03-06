/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.schedule.model.ScheduleDay;
import org.apache.myfaces.custom.schedule.model.ScheduleEntry;

/**
 * <p>
 * The ScheduleEntryRenderer is responsible for rendering the content and the
 * tooltip of a ScheduleEntry.
 * </p>
 * <p>
 * Note that the box around the entry is rendered by the ScheduleRenderer,
 * because determining the position and size of the box cannot be done
 * independent of the other entries.
 * </p>
 * <p>
 * The color of the box can be set using the getEntryColor method. This allows a
 * developer to use different colors for the entries of different users for
 * example.
 * </p>
 * 
 * @since 1.1.7
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$
 */
public interface ScheduleEntryRenderer extends Serializable
{
    /**
     * Render the content of an entry.
     * 
     * @param context
     *            the FacesContext
     * @param writer
     *            the ResponseWriter
     * @param schedule
     *            the Schedule component
     * @param day the current day           
     * @param entry
     *            the entry that should be rendered
     * @param compact
     *            is the schedule rendered in a compact mode?
     * @param selected
     *            whether or not the entry is currently selected
     * @throws IOException
     *             when the output cannot be written
     */
    public void renderContent(FacesContext context,
            ResponseWriter writer, HtmlSchedule schedule, ScheduleDay day,
            ScheduleEntry entry, boolean compact, boolean selected) throws IOException;

    /**
     * Get the color of an entry. The border around the entry will be rendered
     * in this color. The return value of this method should be a CSS2 color
     * specification, such as #000000 or rgb(0,0,0). If the return value is
     * null, then the current theme's default color will be used.
     * 
     * @param context
     *            the FacesContext
     * @param schedule
     *            the Schedule component
     * @param entry
     *            the entry
     * @param selected
     *            whether or not the entry is currently selected
     * @return the color
     */
    public String getColor(FacesContext context, HtmlSchedule schedule,
            ScheduleEntry entry, boolean selected);

    /**
     * Render the tooltip of a ScheduleEntry. This method will only be called if
     * the schedule's tooltip property is set to 'true'.
     * 
     * @param context
     *            the FacesContext
     * @param writer
     *            the ResponseWriter
     * @param schedule
     *            the Schedule component
     * @param entry
     *            the entry
     * @param selected
     *            whether or not the entry is currently selected
     * @throws IOException
     *             when the output cannot be written
     */
    public void renderToolTip(FacesContext context, ResponseWriter writer,
            HtmlSchedule schedule, ScheduleEntry entry, boolean selected)
            throws IOException;

    /**
     * Get the class for the entry container. Overriding this allows the 
     * class to vary based on the entry being displayed. 
     * 
     * @param schedule
     *            the Schedule component
     * @param entry
     *            the entry
     */
    public String getEntryClass(HtmlSchedule schedule, ScheduleEntry entry);
}
