/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.util;

/**
 * Utilities for determining the current container and for the unified
 * expression language.
 * 
 */
public class ContainerUtils
{
    /**
     * Determines whether we're running in a Servlet 2.5/JSP 2.1 environment.
     * 
     * @return <code>true</code> if we're running in a JSP 2.1 environment,
     *         <code>false</code> otherwise
     */
    public static boolean isJsp21()
    {
        try 
        {
            // simply check if the class JspApplicationContext is available
            Class.forName("javax.servlet.jsp.JspApplicationContext");
            return true;
        } 
        catch (ClassNotFoundException ex) 
        {
            ; // expected exception in a JSP 2.0 (or less) environment
        }
        
        return false;
    }
    
    /**
     * Return true if the specified string contains an EL expression.
     * 
     * <p>
     * <strong>NOTICE</strong> This method is just a copy of
     * {@link UIComponentTag#isValueReference(String)}, but it's required
     * because the class UIComponentTag depends on a JSP 2.1 container 
     * (for example, it indirectly implements the interface JspIdConsumer)
     * and therefore internal classes shouldn't access this class. That's
     * also the reason why this method is inside the class ContainerUtils,
     * because it allows MyFaces to be independent of a JSP 2.1 container.
     * </p>
     */
    public static boolean isValueReference(String value) 
    {
        if (value == null) {
            throw new NullPointerException("value");
        }

        int start = value.indexOf("#{");
        if (start < 0) {
            return false;
        }

        int end = value.lastIndexOf('}');
        return (end >=0 && start < end);
    }
}
