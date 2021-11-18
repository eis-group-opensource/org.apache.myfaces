/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.FacesException;

class _SubIdConverter
{
    private static final String HEX_CHARSET = "0123456789ABCDEF";
    
    /**
     * Encode the string into an html sub id valid value.  
     * 
     * An html id must comply with the following rules
     * 
     * 1. Must begin with a letter A-Z or a-z
     * 2. Can be followed by: letters (A-Za-z), digits (0-9), hyphens ("-"), underscores ("_"), colons (":"), and periods (".")
     * 3. Values are case-sensitive
     * 
     * The first rule is warranted because this convert an sub id, so a prefix is always added to
     * the returning string. The encoder converts all non valid chars into a unicode hex string prefixed with '_' char.
     * For example _ is converted to _005F, + is converted to _002B and so on.
     * 
     * @param string
     * @param characterEncoding
     * @return
     */
    public static String encode(final String string)
    {
        StringBuilder sb = null;    //create later on demand
        String app;
        char c;
        boolean endLoop = false;
        for (int i = 0; i < string.length (); ++i)
        {
            app = null;
            c = string.charAt(i);
            
            if (( c >= '0' && c <='9') || (c >='A' && c <='Z') || (c >='a' && c <='z')
                || c == ':' || c == '.' ) //|| c == '-' // '-' used to indicate rowIndex
            {
                //No encoding, just do nothing, char will be added later.
            }
            else
            {
                
                app = "_" + HEX_CHARSET.charAt( ((c >> 0x0C) % 0x10)) +  HEX_CHARSET.charAt( ((c >> 0x8) % 0x10)) + HEX_CHARSET.charAt( ((c >> 0x4) % 0x10)) +HEX_CHARSET.charAt(c % 0x10);
            }
                        
            if (app != null)
            {
                if (sb == null)
                {
                    sb = new StringBuilder(string.substring(0, i));
                }
                sb.append(app);
            } else {
                if (sb != null)
                {
                    sb.append(c);
                }
            }
            if (endLoop)
            {
                break;
            }
        }
        if (sb == null)
        {
            return string;
        }
        else
        {
            return sb.toString();
        }
    }

    public static String decode(final String string)
    {
        StringBuilder sb = null;    //create later on demand
        String app;
        char c;
        boolean endLoop = false;
        for (int i = 0; i < string.length (); ++i)
        {
            app = null;
            c = string.charAt(i);
            
            if (c == '_')
            {
                int value = (toDigit(string.charAt(i+1)) << 0x0C) + (toDigit(string.charAt(i+2)) << 0x08) + (toDigit(string.charAt(i+3)) << 0x04) + toDigit(string.charAt(i+4));
                
                if (sb == null)
                {
                    sb = new StringBuilder(string.substring(0, i));
                }
                i += 4;
                app = ""+((char)value);
            }
            else
            {
                //No decoding
            }

            if (app != null)
            {
                if (sb == null)
                {
                    sb = new StringBuilder(string.substring(0, i));
                }
                sb.append(app);
            } else {
                if (sb != null)
                {
                    sb.append(c);
                }
            }
            if (endLoop)
            {
                break;
            }
        }
        if (sb == null)
        {
            return string;
        }
        else
        {
            return sb.toString();
        }
    }
    
    /**
     * Converts a hexadecimal character to an integer.
     * 
     * @param ch
     *            A character to convert to an integer digit
     * @param index
     *            The index of the character in the source
     * @return An integer
     * @throws DecoderException
     *             Thrown if ch is an illegal hex character
     */
    protected static int toDigit(char ch)
    {
        int digit = Character.digit(ch, 16);
        if (digit == -1)
        {
            throw new FacesException("Illegal hexadecimal charcter ");
        }
        return digit;
    }
}
