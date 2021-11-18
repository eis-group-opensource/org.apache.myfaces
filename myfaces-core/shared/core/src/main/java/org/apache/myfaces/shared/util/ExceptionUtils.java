/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Various helpers to deal with exception
 * 
 * @author imario@apache.org
 */
public final class ExceptionUtils
{
    private ExceptionUtils()
    {
    }

    /**
     * <p>
     * returns a list of all throwables (including the one you passed in) wrapped by the given throwable.
     * In contrast to a simple call to <code>getClause()</code> on each throwable it will also check if the throwable class
     * contain a method <code>getRootCause()</code> (e.g. ServletException or JspException) and call it instead.
     * </p>
     * <p>
     * The first list element will your passed in exception, the last list element is the cause. 
     * </p>
     */
    public static List getExceptions(Throwable cause)
    {
        List exceptions = new ArrayList(10);
        exceptions.add(cause);
        
        do
        {
            Throwable nextCause;
            try
            {
                Method rootCause = cause.getClass().getMethod("getRootCause", new Class[]{});
                nextCause = (Throwable) rootCause.invoke(cause, new Object[]{});
            }
            catch(Exception e)
            {
                nextCause = cause.getCause();
            }
            if (cause == nextCause)
            {
                break;
            }
            
            if (nextCause != null)
            {
                exceptions.add(nextCause);
            }
            
            cause = nextCause;
        }
        while (cause != null);
        
        return exceptions;
    }

    /**
     * Find a throwable message starting with the last element.<br />
     * Returns the first throwable message where <code>throwable.getMessage() != null</code> 
     */
    public static String getExceptionMessage(List throwables)
    {
        if (throwables == null)
        {
            return null;
        }

        for (int i = throwables.size()-1; i>0; i--)
        {
            Throwable t = (Throwable) throwables.get(i);
            if (t.getMessage() != null)
            {
                return t.getMessage();
            }
        }
        
        return null;
    }
}
