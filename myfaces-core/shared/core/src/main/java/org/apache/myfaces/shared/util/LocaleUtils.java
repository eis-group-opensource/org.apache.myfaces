/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Anton Koinov (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class LocaleUtils
{
    private static final Log log = LogFactory.getLog(LocaleUtils.class);

    /** Utility class, do not instatiate */
    private LocaleUtils()
    {
        // utility class, do not instantiate
    }

    /**
     * Converts a locale string to <code>Locale</code> class. Accepts both
     * '_' and '-' as separators for locale components.
     *
     * @param localeString string representation of a locale
     * @return Locale instance, compatible with the string representation
     */
    public static Locale toLocale(String localeString)
    {
        if ((localeString == null) || (localeString.length() == 0))
        {
            Locale locale = Locale.getDefault();
            if(log.isWarnEnabled())
                log.warn("Locale name in faces-config.xml null or empty, setting locale to default locale : "+locale.toString());
            return locale;
        }

        int separatorCountry = localeString.indexOf('_');
        char separator;
        if (separatorCountry >= 0) {
            separator = '_';
        }
        else
        {
            separatorCountry = localeString.indexOf('-');
            separator = '-';
        }

        String language, country, variant;
        if (separatorCountry < 0)
        {
            language = localeString;
            country = variant = "";
        }
        else
        {
            language = localeString.substring(0, separatorCountry);

            int separatorVariant = localeString.indexOf(separator, separatorCountry + 1);
            if (separatorVariant < 0)
            {
                country = localeString.substring(separatorCountry + 1);
                variant = "";
            }
            else
            {
                country = localeString.substring(separatorCountry + 1, separatorVariant);
                variant = localeString.substring(separatorVariant + 1);
            }
        }

        return new Locale(language, country, variant);
    }


    /**
     * Convert locale string used by converter tags to locale.
     *
     * @param name name of the locale
     * @return locale specified by the given String
     *
     * @see org.apache.myfaces.taglib.core.ConvertDateTimeTag#setConverterLocale
     * @see org.apache.myfaces.taglib.core.ConvertNumberTag#setConverterLocale
     */
    public static Locale converterTagLocaleFromString(String name)
    {
        try
        {
            Locale locale;
            StringTokenizer st = new StringTokenizer(name, "_");
            String language = st.nextToken();

            if(st.hasMoreTokens())
            {
                String country = st.nextToken();

                if(st.hasMoreTokens())
                {
                    String variant = st.nextToken();
                    locale = new Locale(language, country, variant);
                }
                else
                {
                    locale = new Locale(language, country);
                }
            }
            else
            {
                locale = new Locale(language);
            }


            return locale;
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Locale parsing exception - " +
                "invalid string representation '" + name + "'");
        }
    }
}
