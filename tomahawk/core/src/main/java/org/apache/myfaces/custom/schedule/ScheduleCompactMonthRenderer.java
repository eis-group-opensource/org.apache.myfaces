/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule;


import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.schedule.model.ScheduleDay;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;


/**
 * <p>
 * Renderer for the month view of the Schedule component
 * </p>
 * @since 1.1.7
 * @author Jurgen Lust (latest modification by $Author: schof $)
 * @author Bruno Aranda (adaptation of Jurgen's code to myfaces)
 * @version $Revision: 382051 $
 */
public class ScheduleCompactMonthRenderer
    extends AbstractCompactScheduleRenderer
    implements Serializable
{

    private static final long serialVersionUID = 2926607881214603314L;
    
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
        writer.writeAttribute(HTML.CLASS_ATTR, getStyleClass(schedule, "month"), null);
        writer.writeAttribute(
            HTML.STYLE_ATTR, "width: 100%;",
            null
        );
        writer.writeAttribute(HTML.CELLPADDING_ATTR, "0", null);
        writer.writeAttribute(HTML.CELLSPACING_ATTR, "1", null);

        Calendar cal = getCalendarInstance(schedule, schedule.getModel().getSelectedDate());

        String dayOfWeekDateFormat = schedule.getCompactMonthDayOfWeekDateFormat();

        if (dayOfWeekDateFormat != null && dayOfWeekDateFormat.length() > 0) {
            DateFormat dayOfWeekFormater = getDateFormat(context, schedule, dayOfWeekDateFormat);
            writer.startElement(HTML.THEAD_ELEM, schedule);
            writer.startElement(HTML.TR_ELEM, schedule);

            for (Iterator dayIterator = schedule.getModel().iterator(); dayIterator.hasNext();) {
                ScheduleDay day = (ScheduleDay) dayIterator.next();
                cal.setTime(day.getDate());

                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

                writer.startElement(HTML.TH_ELEM, schedule);
                writer.writeAttribute(HTML.CLASS_ATTR, getStyleClass(schedule, "header"), null);

                if (schedule.isSplitWeekend() && dayOfWeek == Calendar.SATURDAY) {
                    // Don't label the weekend
                    writer.endElement(HTML.TH_ELEM);
                    break;
                } else {
                    writer.writeText(dayOfWeekFormater.format(day.getDate()), null);
                    writer.endElement(HTML.TH_ELEM);                    
                }
                if (dayOfWeek == Calendar.SUNDAY) {
                    break;
                }
            }
            writer.endElement(HTML.TR_ELEM);
            writer.endElement(HTML.THEAD_ELEM);
        }

        writer.startElement(HTML.TBODY_ELEM, schedule);

        int selectedMonth = cal.get(Calendar.MONTH);

        for (
            Iterator dayIterator = schedule.getModel().iterator();
            dayIterator.hasNext();
        ) {
            ScheduleDay day = (ScheduleDay) dayIterator.next();
            cal.setTime(day.getDate());

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            int currentMonth = cal.get(Calendar.MONTH);
            boolean isWeekend =
                (dayOfWeek == Calendar.SATURDAY) ||
                (dayOfWeek == Calendar.SUNDAY);

            cal.setTime(day.getDate());

            writeDayCell(
                context, writer, schedule, day, dayOfWeek, dayOfMonth, isWeekend,
                currentMonth == selectedMonth, (!isWeekend && schedule.isSplitWeekend() ? 2 : 1)
            );

        }

        writer.endElement(HTML.TBODY_ELEM);
        writer.endElement(HTML.TABLE_ELEM);

        writer.endElement(HTML.DIV_ELEM);
    }

    /**
     * @see AbstractCompactScheduleRenderer#getDefaultRowHeight()
     */
    protected int getDefaultRowHeight()
    {
        return 120;
    }


    /**
     */
    protected void writeDayCell(
        FacesContext context,
        ResponseWriter writer,
        HtmlSchedule schedule,
        ScheduleDay day,
        int dayOfWeek,
        int dayOfMonth,
        boolean isWeekend,
        boolean isCurrentMonth,
        int rowspan
    )
        throws IOException
    {
        if ((dayOfWeek == Calendar.MONDAY) || (schedule.isSplitWeekend() && dayOfWeek == Calendar.SUNDAY)) {
            writer.startElement(HTML.TR_ELEM, schedule);
        }

        super.writeDayCell(
            context, writer, schedule, day, 100f / (schedule.isSplitWeekend() ? 6 : 7), dayOfWeek, dayOfMonth,
            isWeekend, isCurrentMonth, rowspan
        );

        if ((schedule.isSplitWeekend() && dayOfWeek == Calendar.SATURDAY) || (dayOfWeek == Calendar.SUNDAY)) {
            writer.endElement(HTML.TR_ELEM);
        }
    }

    protected int getRowHeight(UIScheduleBase schedule)
    {
        if (schedule != null) {
            int height = schedule.getCompactMonthRowHeight();
            return height == 0 ? getDefaultRowHeight() : height;
        }
        return getDefaultRowHeight();
    }
}
//The End
