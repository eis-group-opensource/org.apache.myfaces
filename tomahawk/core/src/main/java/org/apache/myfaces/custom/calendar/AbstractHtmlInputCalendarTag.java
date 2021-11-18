/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

/**
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public abstract class AbstractHtmlInputCalendarTag extends 
    org.apache.myfaces.generated.taglib.html.ext.HtmlInputTextTag
{
    
    private String _dateBusinessConverter;
    
    public void setDateBusinessConverter(String dateBusinessConverter)
    {
        _dateBusinessConverter = dateBusinessConverter;
    }

    public void release() {
        super.release();
        _dateBusinessConverter = null;
    }

    /**
     * Applies attributes to the tree component
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        org.apache.myfaces.custom.calendar.AbstractHtmlInputCalendar comp =
            (org.apache.myfaces.custom.calendar.AbstractHtmlInputCalendar) component;
        
        if (_dateBusinessConverter != null)
        {
            if (isValueReference(_dateBusinessConverter))
            {
                ValueBinding vb = context.getApplication().createValueBinding(_dateBusinessConverter);
                comp.setValueBinding("dateBusinessConverter", vb);
            }
            else
            {
                try
                {
                    Class clazz = ClassUtils.classForName(_dateBusinessConverter);
                    comp.setDateBusinessConverter( (DateBusinessConverter) ClassUtils.newInstance(clazz));
                }
                catch(ClassNotFoundException e)
                {
                    throw new IllegalArgumentException("Class referenced in calendarConverter not found: "+_dateBusinessConverter);
                }
                catch(ClassCastException e)
                {
                    throw new IllegalArgumentException("Class referenced in calendarConverter is not instance of org.apache.myfaces.custom.calendar.CalendarConverter: "+_dateBusinessConverter);
                }
            }
        }
    }
}
