/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html.util;

/**
 * Converts characters outside of latin-1 set in a string to numeric character references.
 * 
 */
public abstract class UnicodeEncoder
{
    /**
     * Encodes the given string, so that it can be used within a html page.
     * @param string the string to convert
     */
    public static String encode (String string)
    {
        if (string == null)
        {
            return "";
        }

        StringBuilder sb = null;
        char c;
        for (int i = 0; i < string.length (); ++i)
        {
            c = string.charAt(i);
            if (((int)c) >= 0x80)
            {
                if( sb == null ){
                    sb = new StringBuilder( string.length()+4 );
                    sb.append( string.substring(0,i) );
                }
                //encode all non basic latin characters
                sb.append("&#");
                sb.append((int)c);
                sb.append(";");
            }
            else if( sb != null )
            {
                sb.append(c);
            }
        }

        return sb != null ? sb.toString() : string;
    }


}
