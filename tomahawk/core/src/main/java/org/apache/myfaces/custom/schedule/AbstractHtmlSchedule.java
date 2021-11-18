/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule;

import java.io.Serializable;

import org.apache.myfaces.component.UserRoleAware;

/**
 * A schedule component similar to the ones found in Outlook or Evolution
 * <p> 
 * Renders a schedule component, showing appointments and events
 * in a day, workweek, week or month view, similar to the schedule
 * part of MS Outlook or Evolution.
 * </p><p>
 * The component is backed by an implementation of the
 * ScheduleModel interface. Creating a custom model can be easily
 * achieved by implementing this interface, or by overriding
 * the AbstractScheduleModel class.
 * </p><p>
 * AbstractHtmlSchedule class holds all properties specific to the HTML version of the Schedule component.
 * </p>
 * 
 * @JSFComponent
 *   name = "t:schedule"
 *   class = "org.apache.myfaces.custom.schedule.HtmlSchedule"
 *   tagClass = "org.apache.myfaces.custom.schedule.ScheduleTag"
 *   tagHandler = "org.apache.myfaces.custom.schedule.ScheduleTagHandler"
 *   implements = "java.io.Serializable"
 *   serialuid = "5859593107442371656L"
 *
 * @author Bruno Aranda (latest modification by $Author: lu4242 $)
 * @author Jurgen Lust
 * @since 1.1.7
 * @version $Revision: 691856 $
 */
public abstract class AbstractHtmlSchedule extends UISchedule implements UserRoleAware,
        Serializable
{

    //private static final long serialVersionUID = 5859593107442371656L;
    
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.Schedule";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Schedule";

    /**
     * @JSFProperty
     * @return the backgroundClass
     */
    public abstract String getBackgroundClass();

    /**
     * @JSFProperty
     * @return the columnClass
     */
    public abstract String getColumnClass();

    /**
     * @JSFProperty
     * @return the contentClass
     */
    public abstract String getContentClass();

    /**
     * @JSFProperty
     * @return the dateClass
     */
    public abstract String getDateClass();

    /**
     * @JSFProperty
     * @return the dayClass
     */
    public abstract String getDayClass();

    /**
     * @JSFProperty
     * @return the entryClass
     */
    public abstract String getEntryClass();

    /**
     * @JSFProperty
     * @return the entryRenderer
     */
    public abstract Object getEntryRenderer();

    /**
     * @JSFProperty
     * @return the evenClass
     */
    public abstract String getEvenClass();

    /**
     * @JSFProperty
     * @return the foregroundClass
     */
    public abstract String getForegroundClass();

    /**
     * @JSFProperty
     * @return the freeClass
     */
    public abstract String getFreeClass();

    /**
     * @JSFProperty
     * @return the gutterClass
     */
    public abstract String getGutterClass();

    /**
     * @JSFProperty
     * @return the headerClass
     */
    public abstract String getHeaderClass();

    /**
     * @JSFProperty
     * @return the holidayClass
     */
    public abstract String getHolidayClass();

    /**
     * @JSFProperty
     * @return the hoursClass
     */
    public abstract String getHoursClass();

    /**
     * @JSFProperty
     * @return the inactiveDayClass
     */
    public abstract String getInactiveDayClass();

    /**
     * @JSFProperty
     * @return the minutesClass
     */
    public abstract String getMinutesClass();

    /**
     * @JSFProperty
     * @return the monthClass
     */
    public abstract String getMonthClass();

    /**
     * @JSFProperty
     * @return the selectedClass
     */
    public abstract String getSelectedClass();

    /**
     * @JSFProperty
     * @return the selectedEntryClass
     */
    public abstract String getSelectedEntryClass();

    /**
     * @JSFProperty
     * @return the subtitleClass
     */
    public abstract String getSubtitleClass();

    /**
     * @JSFProperty
     * @return the textClass
     */
    public abstract String getTextClass();

    /**
     * @JSFProperty
     * @return the titleClass
     */
    public abstract String getTitleClass();

    /**
     * @JSFProperty
     * @return the unevenClass
     */
    public abstract String getUnevenClass();

    /**
     * the css class of the table representing a week in the compact mode (default: week)
     * 
     * @JSFProperty
     * @return the weekClass
     */
    public abstract String getWeekClass();

}
//The End
