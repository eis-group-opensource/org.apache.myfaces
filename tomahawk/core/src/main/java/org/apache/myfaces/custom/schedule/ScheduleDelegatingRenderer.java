/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.schedule;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.myfaces.custom.schedule.model.ScheduleModel;

/**
 * <p>
 * Renderer for the Schedule component that delegates the actual rendering
 * to a compact or detailed renderer, depending on the mode of the ScheduleModel
 * </p>
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.Schedule"
 * @since 1.1.7
 * @author Jurgen Lust (latest modification by $Author: skitching $)
 * @author Bruno Aranda (adaptation of Jurgen's code to myfaces)
 * @version $Revision: 367444 $
 */
public class ScheduleDelegatingRenderer extends Renderer implements Serializable
{
    private static final long serialVersionUID = -837566590780480244L;
    
    //~ Instance fields --------------------------------------------------------

    private final ScheduleCompactMonthRenderer monthDelegate = new ScheduleCompactMonthRenderer();
    private final ScheduleCompactWeekRenderer weekDelegate = new ScheduleCompactWeekRenderer();
    private final ScheduleDetailedDayRenderer dayDelegate = new ScheduleDetailedDayRenderer();

    //~ Methods ----------------------------------------------------------------

    /**
     * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void decode(FacesContext context, UIComponent component)
    {
        getDelegateRenderer(component).decode(context, component);
    }

    /**
     * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {
        getDelegateRenderer(component).encodeBegin(context, component);
    }

    /**
     * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException
    {
        getDelegateRenderer(component).encodeChildren(context, component);
    }

    /**
     * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        getDelegateRenderer(component).encodeEnd(context, component);
    }

    protected Renderer getDelegateRenderer(UIComponent component)
    {
        HtmlSchedule schedule = (HtmlSchedule) component;

        if ((schedule == null) || (schedule.getModel() == null))
        {
            return dayDelegate;
        }

        switch (schedule.getModel().getMode())
        {
        case ScheduleModel.WEEK:
            return weekDelegate;

        case ScheduleModel.MONTH:
            return monthDelegate;

        default:
            return dayDelegate;
        }
    }

    /**
     * @see javax.faces.render.Renderer#getRendersChildren()
     */
    public boolean getRendersChildren()
    {
        return true;
    }
}
//The End
