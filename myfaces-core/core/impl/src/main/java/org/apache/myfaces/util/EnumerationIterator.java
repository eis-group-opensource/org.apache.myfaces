/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.util;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author Manfred Geiler (latest modification by $Author: bommel $)
 * @version $Revision: 693059 $ $Date: 2008-09-08 14:42:28 +0300 (Mon, 08 Sep 2008) $
 */
public final class EnumerationIterator
        implements Iterator
{
    //private static final Log log = LogFactory.getLog(EnumerationIterator.class);

    private final Enumeration _enumeration;

    public EnumerationIterator(final Enumeration enumeration)
    {
        _enumeration = enumeration;
    }

    public boolean hasNext()
    {
        return _enumeration.hasMoreElements();
    }

    public Object next()
    {
        return _enumeration.nextElement();
    }

    public void remove()
    {
        throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
    }
}
