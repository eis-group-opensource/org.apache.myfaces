/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFConverter;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFConverter
public class ShortConverter
        implements Converter
{
    // FIELDS
    public static final String CONVERTER_ID = "javax.faces.Short";
    public static final String STRING_ID = "javax.faces.converter.STRING";
    public static final String SHORT_ID = "javax.faces.converter.ShortConverter.SHORT";

    // CONSTRUCTORS
    public ShortConverter()
    {
    }

    // METHODS
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
    {
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

        if (value != null)
        {
            value = value.trim();
            if (value.length() > 0)
            {
                try
                {
                    return Short.valueOf(value);
                }
                catch (NumberFormatException e)
                {
                    throw new ConverterException(_MessageUtils.getErrorMessage(facesContext,
                                                                               SHORT_ID,
                                                                               new Object[]{value,"21",_MessageUtils.getLabel(facesContext, uiComponent)}), e);
                }
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
    {
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

        if (value == null)
        {
            return "";
        }
        if (value instanceof String)
        {
            return (String)value;
        }
        try
        {
            return Short.toString(((Number)value).shortValue());
        }
        catch (Exception e)
        {
            throw new ConverterException(_MessageUtils.getErrorMessage(facesContext, STRING_ID, new Object[]{value,_MessageUtils.getLabel(facesContext, uiComponent)}),e);
        }
    }
}
