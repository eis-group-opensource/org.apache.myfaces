/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared.config.MyfacesConfig;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Anton Koinov
 * @version $Revision: 684009 $ $Date: 2008-08-08 19:07:53 +0300 (Pn, 08 Rgp 2008) $
 */
public final class JavascriptUtils
{
    private static final Log log = LogFactory.getLog(JavascriptUtils.class);

    public static final String JAVASCRIPT_DETECTED = JavascriptUtils.class.getName() + ".JAVASCRIPT_DETECTED";

    private static final String AUTO_SCROLL_PARAM = "autoScroll";
    private static final String AUTO_SCROLL_FUNCTION = "getScrolling()";

    private static final String OLD_VIEW_ID = JavascriptUtils.class + ".OLD_VIEW_ID";


    private JavascriptUtils()
    {
        // utility class, do not instantiate
    }

    private static final Set RESERVED_WORDS =
        new HashSet(Arrays.asList(new String[]{
            "abstract",
            "boolean",
            "break",
            "byte",
            "case",
            "catch",
            "char",
            "class",
            "const",
            "continue",
            "default",
            "delete",
            "do",
            "double",
            "else",
            "export",
            "extends",
            "false",
            "final",
            "finally",
            "float",
            "for",
            "function",
            "goto",
            "if",
            "implements",
            "in",
            "instanceof",
            "int",
            "long",
            "native",
            "new",
            "null",
            "package",
            "private",
            "protected",
            "public",
            "return",
            "short",
            "static",
            "super",
            "switch",
            "synchronized",
            "this",
            "throw",
            "throws",
            "transient",
            "true",
            "try",
            "typeof",
            "var",
            "void",
            "while",
            "with"
        }));

    /**Don't use this function - except when compatibility with the RI is a must,
     * as in the name for the clear form parameters script.
     */
    public static String getValidJavascriptNameAsInRI(String origIdentifier)
    {
        return origIdentifier.replaceAll("-", "\\$_");
    }

    public static String getValidJavascriptName(String s, boolean checkForReservedWord)
    {
        if (checkForReservedWord && RESERVED_WORDS.contains(s))
        {
            return s + "_";
        }

        StringBuffer buf = null;
        for (int i = 0, len = s.length(); i < len; i++)
        {
            char c = s.charAt(i);

            if (Character.isLetterOrDigit(c))
            {
                // allowed char
                if (buf != null) buf.append(c);
            }
            else
            {
                if (buf == null)
                {
                    buf = new StringBuffer(s.length() + 10);
                    buf.append(s.substring(0, i));
                }

                buf.append('_');
                if (c < 16)
                {
                    // pad single hex digit values with '0' on the left
                    buf.append('0');
                }

                if (c < 128)
                {
                    // first 128 chars match their byte representation in UTF-8
                    buf.append(Integer.toHexString(c).toUpperCase());
                }
                else
                {
                    byte[] bytes;
                    try
                    {
                        bytes = Character.toString(c).getBytes("UTF-8");
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        throw new RuntimeException(e);
                    }

                    for (int j = 0; j < bytes.length; j++)
                    {
                        int intVal = bytes[j];
                        if (intVal < 0)
                        {
                            // intVal will be >= 128
                            intVal = 256 + intVal;
                        }
                        else if (intVal < 16)
                        {
                            // pad single hex digit values with '0' on the left
                            buf.append('0');
                        }
                        buf.append(Integer.toHexString(intVal).toUpperCase());
                    }
                }
            }

        }

        return buf == null ? s : buf.toString();
    }


    public static String encodeString(String string)
    {
        if (string == null)
        {
            return "";
        }
        StringBuffer sb = null;    //create later on demand
        String app;
        char c;
        for (int i = 0; i < string.length (); ++i)
        {
            app = null;
            c = string.charAt(i);
            switch (c)
            {
                case '\\' : app = "\\\\";  break;
                case '"' : app = "\\\"";  break;
                case '\'' : app = "\\'";  break;
                case '\n' : app = "\\n";  break;
                case '\r' : app = "\\r";  break;
            }
            if (app != null)
            {
                if (sb == null)
                {
                    sb = new StringBuffer(string.substring(0, i));
                }
                sb.append(app);
            } else {
                if (sb != null)
                {
                    sb.append(c);
                }
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

    public static boolean isJavascriptAllowed(ExternalContext externalContext)
    {
        MyfacesConfig myfacesConfig = MyfacesConfig.getCurrentInstance(externalContext);
        if (myfacesConfig.isAllowJavascript())
        {
            if (myfacesConfig.isDetectJavascript())
            {
                return isJavascriptDetected(externalContext);
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
    
    public static boolean isRenderClearJavascriptOnButton(ExternalContext externalContext)
    {
        MyfacesConfig myfacesConfig = MyfacesConfig.getCurrentInstance(externalContext);
        if (myfacesConfig.isRenderClearJavascriptOnButton())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static boolean isSaveFormSubmitLinkIE(ExternalContext externalContext)
    {
        MyfacesConfig myfacesConfig = MyfacesConfig.getCurrentInstance(externalContext);
        if (myfacesConfig.isSaveFormSubmitLinkIE())
        {
            return true;
        }
        else
        {
            return false;
        }
    }    

    public static void setJavascriptDetected(HttpSession session, boolean value)
    {
        session.setAttribute(JAVASCRIPT_DETECTED, Boolean.valueOf(value));
    }
    
    public static boolean isJavascriptDetected(ExternalContext externalContext)
    {
        //TODO/FIXME (manolito): This info should be better stored in the viewroot component and not in the session
        Boolean sessionValue = (Boolean)externalContext.getSessionMap().get(JAVASCRIPT_DETECTED);
        return sessionValue != null && sessionValue.booleanValue();
    }


    public static void setOldViewId(ExternalContext externalContext, String viewId)
    {
        externalContext.getRequestMap().put(OLD_VIEW_ID, viewId);
    }

    public static String getOldViewId(ExternalContext externalContext)
    {
        return (String)externalContext.getRequestMap().get(OLD_VIEW_ID);
    }
}
