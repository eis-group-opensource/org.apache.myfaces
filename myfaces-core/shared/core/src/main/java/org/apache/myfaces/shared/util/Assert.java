/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

/**
 * @author Mathias Broekelmann (latest modification by $Author: skitching $)
 * @version $Revision: 676197 $ $Date: 2008-07-12 19:07:17 +0300 (12 Lie 2008) $
 */
public class Assert
{
    public static void notNull(Object value)
    {
        if (value == null)
            throw new NullPointerException("The instance is null.");
    }

    public static void notNull(Object value, String fieldName)
    {
        if (value == null)
            throw new NullPointerException(fieldName + " is null.");
    }
}
