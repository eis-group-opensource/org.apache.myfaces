/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.calendar;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public class DefaultDateBusinessConverter implements DateBusinessConverter
{

    public Object getBusinessValue(FacesContext context, UIComponent component,
            Date value)
    {
        ValueBinding vb = component.getValueBinding("value");
        Class type = vb.getType(context); 
        if (type != null)
        {
            if (java.sql.Date.class.isAssignableFrom(type))
            {
                return new java.sql.Date(value.getTime());
            }
        }
        return value;
    }

    public Date getDateValue(FacesContext context, UIComponent component,
            Object value)
    {
        if (value instanceof java.sql.Date)
        {
            //Convert to strict java.util.Date
            return new Date(((java.sql.Date)value).getTime());
        }
        return (Date) value;
    }
}
