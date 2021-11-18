/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


/**
 * A converter for Integer that changes the integer a bit.
 * 
 * @author Jakob Korherr (latest modification by $Author: lu4242 $)
 * @version $Revision: 963899 $ $Date: 2010-07-14 01:57:38 +0300 (Wed, 14 Jul 2010) $
 */
public class MockIntegerConverter implements Converter
{
    
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException
    {
        return (new Integer(value) - 10);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException
    {
        if (value instanceof String)
        {
            value = new Integer((String) value);
        }
        return (((Integer) value) + 10) + "";
    }

}
