/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule;


import org.apache.myfaces.custom.schedule.model.ScheduleDay;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;


/**
 * <p>
 * Renderer for the week view of the UISchedule component
 * </p>
 * @since 1.1.7
 * @author Jurgen Lust (latest modification by $Author: schof $)
 * @author Bruno Aranda (adaptation of Jurgen's code to myfaces)
 * @version $Revision: 382051 $
 */
public class ScheduleCompactWeekRenderer
    extends AbstractCompactScheduleRenderer
    implements Serializable
{
    private static final long serialVersionUID = 5504081783797695487L;

    //~ Methods ----------------------------------------------------------------

    /**
     * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeBegin(
        FacesContext context,
        UIComponent component
    )
        throws IOException
    {
        if (!component.isRendered()) {
            return;
        }

        super.encodeBegin(context, component);

        HtmlSchedule schedule = (HtmlSchedule) component;
        ResponseWriter writer = context.getResponseWriter();

        //container div for the schedule grid
        writer.startElement(HTML.DIV_ELEM, schedule);
        writer.writeAttribute(HTML.CLASS_ATTR, "schedule-compact-" + schedule.getTheme(), null);
        writer.writeAttribute(
            HTML.STYLE_ATTR, "border-style: none; overflow: hidden;", null
        );

        writer.startElement(HTML.TABLE_ELEM, schedule);
        writer.writeAttribute(HTML.CLASS_ATTR, getStyleClass(schedule, "week"), null);
        writer.writeAttribute(
            HTML.STYLE_ATTR, "position: relative; left: 0px; top: 0px; width: 100%;",
            null
        );
        writer.writeAttribute(HTML.CELLPADDING_ATTR, "0", null);
        writer.writeAttribute(HTML.CELLSPACING_ATTR, "1", null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute(HTML.WIDTH_ATTR, "100%", null);
        writer.startElement(HTML.TBODY_ELEM, schedule);

        for (
            Iterator dayIterator = schedule.getModel().iterator();
                dayIterator.hasNext();
        ) {
            ScheduleDay day = (ScheduleDay) dayIterator.next();
            Calendar cal = getCalendarInstance(schedule, day.getDate());

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            boolean isWeekend =
                (dayOfWeek == Calendar.SATURDAY) ||
                (dayOfWeek == Calendar.SUNDAY);

            if (
                (dayOfWeek == Calendar.MONDAY) ||
                    (dayOfWeek == Calendar.WEDNESDAY) ||
                    (dayOfWeek == Calendar.FRIDAY) ||
                    (dayOfWeek == Calendar.SUNDAY)
            ) {
                writer.startElement(HTML.TR_ELEM, schedule);
            }

            writeDayCell(
                context, writer, schedule, day, 50f, dayOfWeek, dayOfMonth,
                isWeekend, true, (dayOfWeek == Calendar.FRIDAY) ? (schedule.isSplitWeekend() ? 2 : 1) : 1
            );

            if (
                (dayOfWeek == Calendar.TUESDAY) ||
                    (dayOfWeek == Calendar.THURSDAY) ||
                    (dayOfWeek == Calendar.SATURDAY) ||
                    (dayOfWeek == Calendar.SUNDAY)
            ) {
                writer.endElement(HTML.TR_ELEM);
            }
        }

        writer.endElement(HTML.TBODY_ELEM);
        writer.endElement(HTML.TABLE_ELEM);

        writer.endElement(HTML.DIV_ELEM);
    }

    /**
     * @see org.apache.myfaces.custom.schedule.AbstractCompactScheduleRenderer#getDefaultRowHeight()
     */
    protected int getDefaultRowHeight()
    {
        return 200;
    }

    protected int getRowHeight(UIScheduleBase schedule)
    {
        if (schedule != null) {
            int height = schedule.getCompactWeekRowHeight();
            return height == 0 ? getDefaultRowHeight() : height;
        }
        return getDefaultRowHeight();
    }

}
//The End
