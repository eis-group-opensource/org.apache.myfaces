/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.StringTokenizer;

/**
 * @author Thomas Spiegl (latest modification by $Author: grantsmith $)
 * @version $Revision: 472630 $ $Date: 2006-11-08 22:40:03 +0200 (Wed, 08 Nov 2006) $
 */
public class StringArrayConverter
    implements Converter
{
    public Object getAsObject(FacesContext context, UIComponent component, String value)
        throws ConverterException
    {
        try
        {
            StringTokenizer tokenizer = new StringTokenizer(value, ",");
            String[] newValue = new String[tokenizer.countTokens()];
            for (int i = 0; tokenizer.hasMoreTokens(); i++)
            {
                newValue[i] = URLDecoder.decode(tokenizer.nextToken(), "UTF-8");
            }
            return newValue;
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String getAsString(FacesContext context, UIComponent component, Object value)
            throws ConverterException
    {
        return getAsString((String[])value,
                           true);   //escapeCommas
    }


    public static String getAsString(String[] strings,
                                     boolean escapeCommas)
    {
        try
        {
            if (strings == null || strings.length == 0)
            {
                return null;
            }
            else if (strings.length == 1)
            {

                return escapeCommas
                        ? URLEncoder.encode(strings[0], "UTF-8") //Encode, so that commas within Strings are escaped
                        : strings[0];
            }
            else
            {
                StringBuffer buf = new StringBuffer();
                for (int i = 0; i < strings.length; i++)
                {
                    if (i > 0)
                    {
                        buf.append(',');
                    }

                    String s = strings[i];

                    if(s!=null)
                    {
                        if (escapeCommas)
                        {
                            //Encode, so that commas within Strings are escaped
                            s = URLEncoder.encode(s, "UTF-8");
                        }
                        buf.append(s);
                    }
                }
                return buf.toString();
            }
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

}
