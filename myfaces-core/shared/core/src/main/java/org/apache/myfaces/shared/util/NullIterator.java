/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Iterator without elements
 *
 * @author Anton Koinov (latest modification by $Author: matzew $)
 * @version $Revision: 557350 $ $Date: 2007-07-18 21:19:50 +0300 (Tr, 18 Lie 2007) $
 */
public final class NullIterator implements Iterator
{
    //~ Static fields/initializers -----------------------------------------------------------------

    private static final NullIterator INSTANCE = new NullIterator();

    //~ Methods ------------------------------------------------------------------------------------

    public static final Iterator instance()
    {
        return INSTANCE;
    }

    public boolean hasNext()
    {
        return false;
    }

    public Object next()
    {
        throw new NoSuchElementException();
    }

    public void remove()
    {
        throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
    }
}
