/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.convert;


/**
 * TODO: Move to util package and rename to better name
 *
 * @author Manfred Geiler (latest modification by $Author: bommel $)
 * @version $Revision: 693059 $ $Date: 2008-09-08 14:42:28 +0300 (Mon, 08 Sep 2008) $
 */
public final class ConverterUtils
{
    //private static final Log log = LogFactory.getLog(ConverterUtils.class);

    private ConverterUtils() {}


    public static int convertToInt(Object value)
    {
        if (value instanceof Number)
        {
            return ((Number) value).intValue();
        }
        else if (value instanceof String)
        {
            try
            {
                return Integer.parseInt((String) value);
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Cannot convert " + value.toString() + " to int");
            }
        }
        else
        {
            throw new IllegalArgumentException("Cannot convert " + value.toString() + " to int");
        }
    }

    public static boolean convertToBoolean(Object value)
    {
        if (value instanceof Boolean)
        {
            return ((Boolean) value);
        }
        else if (value instanceof String)
        {
            try
            {
                return Boolean.parseBoolean((String) value);
            }
            catch (Exception e)
            {
                throw new IllegalArgumentException("Cannot convert " + value.toString() + " to boolean");
            }
        }
        else
        {
            throw new IllegalArgumentException("Cannot convert " + value.toString() + " to boolean");
        }
    }    

    public static long convertToLong(Object value)
    {
        if (value instanceof Number)
        {
            return ((Number) value).longValue();
        }
        else if (value instanceof String)
        {
            try
            {
                return Long.parseLong((String) value);
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Cannot convert " + value.toString() + " to long");
            }
        }
        else
        {
            throw new IllegalArgumentException("Cannot convert " + value.toString() + " to long");
        }
    }

    public static double convertToDouble(Object value)
    {
        if (value instanceof Number)
        {
            return ((Number) value).doubleValue();
        }
        else if (value instanceof String)
        {
            try
            {
                return Double.parseDouble((String) value);
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Cannot convert " + value.toString() + " to double");
            }
        }
        else
        {
            throw new IllegalArgumentException("Cannot convert " + value.toString() + " to double");
        }
    }


}
