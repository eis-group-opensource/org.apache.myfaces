/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Provide a bridge between the java.util.Date instance used by a component 
 * that receive date/time values and the "business" value used to represent
 * the value.
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public interface DateBusinessConverter
{
    /**
     * Convert the java.util.Date instance calculated from submittedValue, 
     * so the resulting object will be used later as the converted value 
     * and validation. 
     * 
     * @param context
     * @param component
     * @param value
     * @return
     */
    public Object getBusinessValue(FacesContext context,
                       UIComponent component,
                       java.util.Date value);

    /**
     * Used to retrieve the value stored in the business bean and convert 
     * it in a representation that the component (t:inputCalendar and 
     * t:inputDate for example)using this class can manipulate. 
     *  
     * @param context
     * @param component
     * @param value
     * @return
     */
    public java.util.Date getDateValue(FacesContext context,
                       UIComponent component,
                       Object value);
}
