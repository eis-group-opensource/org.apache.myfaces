/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.calendar;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

/**
 * <p>
 * HtmlTree tag.
 * </p>
 * @since 1.1.7
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller </a>
 * @version $Revision: 888604 $ $Date: 2004/10/13 11:50:58
 */
public abstract class AbstractHtmlInputCalendarTag extends 
    org.apache.myfaces.generated.taglib.html.ext.HtmlInputTextTag
{
    
    private ValueExpression _dateBusinessConverter;
    
    public void setDateBusinessConverter( ValueExpression dateBusinessConverter)
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
            if (!_dateBusinessConverter.isLiteralText())
            {
                comp.setValueExpression("dateBusinessConverter", _dateBusinessConverter);
            }
            else
            {
                String s = _dateBusinessConverter.getExpressionString();
                if (s != null)
                {            
                    try
                    {
                        Class clazz = ClassUtils.classForName(s);
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
}
