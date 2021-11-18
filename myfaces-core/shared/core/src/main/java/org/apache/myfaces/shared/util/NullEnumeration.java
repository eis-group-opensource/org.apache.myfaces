/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * Enumeration without elements
 *
 * @author Anton Koinov (latest modification by $Author: matzew $)
 * @version $Revision: 557350 $ $Date: 2007-07-18 21:19:50 +0300 (Tr, 18 Lie 2007) $
 */
public final class NullEnumeration implements Enumeration
{
    private static final NullEnumeration s_nullEnumeration = new NullEnumeration();

    public static final NullEnumeration instance()
    {
        return s_nullEnumeration;
    }

    public boolean hasMoreElements()
    {
        return false;
    }

    public Object nextElement()
    {
        throw new NoSuchElementException("NullEnumeration has no elements");
    }
}
