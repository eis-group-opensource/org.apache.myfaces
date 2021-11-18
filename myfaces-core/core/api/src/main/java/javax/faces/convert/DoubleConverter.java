/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFConverter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @version $Revision: 1073877 $ $Date: 2011-02-23 20:28:56 +0200 (Wed, 23 Feb 2011) $
 */
@JSFConverter
public class DoubleConverter
        implements Converter
{
    // API FIELDS
    public static final String CONVERTER_ID = "javax.faces.Double";
    public static final String STRING_ID = "javax.faces.converter.STRING";
    public static final String DOUBLE_ID = "javax.faces.converter.DoubleConverter.DOUBLE";

    // CONSTRUCTORS
    public DoubleConverter()
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
                    value = fixLocale(facesContext, value);
                    return this.stringToDouble(value);
                }
                catch (NumberFormatException e)
                {
                    throw new ConverterException(_MessageUtils.getErrorMessage(facesContext,
                                                                               DOUBLE_ID,
                                                                               new Object[]{value,"4214",_MessageUtils.getLabel(facesContext, uiComponent)}), e);
                }

            }
        }
        return null;
    }

    /**
     * Since Double.valueOf is not Locale aware, and NumberFormatter
     * cannot parse E values correctly, we need to make a US Locale
     * string from our input value.
     * E.g. '34,383e3' will be translated to '34.383e3' if Locale.DE
     * is set in the {@link javax.faces.component.UIViewRoot#getLocale()}
     *
     * @param facesContext
     * @param value
     * @return the 'fixed' value String
     */
    private String fixLocale(FacesContext facesContext, String value)
    {
        Locale loc = facesContext.getViewRoot().getLocale();
        if (loc == null || loc == Locale.US)
        {
            // nothing to fix if we are already using the US Locale
            return value;
        }

        // TODO: DecimalFormatSymbols.getInstance exists only on JDK 1.6
        // change it on JSF 2.1
        //DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(loc);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(loc);

        char decSep   = dfs.getDecimalSeparator();


        // replace decimal separators which are different to '.'
        if (decSep != '.' && value.lastIndexOf(decSep) >= 0)
        {
            StringBuffer sbVal = new StringBuffer();

            // remove all groupSeperators and change the decimalSeperator
            for (int i = 0; i < value.length(); i++)
            {
                if (value.charAt(i) == decSep)
                {
                    sbVal.append('.'); // we append the Locale.US decimal separator
                    continue;
                }

                // just append all other characters as usual
                sbVal.append(value.charAt(i));
            }

            value = sbVal.toString();
        }

        // we need the formatter with the correct Locale of the user
        return value;
    }

    private Double stringToDouble(String value)
    {
        // this is a special hack for a jvm vulnerability with
        // converting some special double values.
        // e.g. "2.225073858507201200000e-308"
        // see MYFACES-3024 for further information
        // TODO we can remove this hack, once this got fixed in the jvm!
        if (value.length() >= 23)
        {
            StringBuffer normalized = new StringBuffer();
            for (int i=0; i< value.length(); i++)
            {
                char c = value.charAt(i);
                if ( c != '.')
                {
                    normalized.append(c);
                }
            }

            String normalizedString = normalized.toString();
            if (normalizedString.contains("22250738585072012") && normalizedString.contains("e-"))
            {
                // oops, baaad value!
               throw new NumberFormatException("Not Allowed! This value could possibly kill the VM!");
            }
        }


        return Double.valueOf(value);
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
            return Double.toString(((Number)value).doubleValue());
        }
        catch (Exception e)
        {
            throw new ConverterException(_MessageUtils.getErrorMessage(facesContext, STRING_ID, new Object[]{value,_MessageUtils.getLabel(facesContext, uiComponent)}),e);
        }
    }
}
