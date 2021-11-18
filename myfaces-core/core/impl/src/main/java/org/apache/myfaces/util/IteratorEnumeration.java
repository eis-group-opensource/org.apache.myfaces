/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.util;

import java.util.Enumeration;
import java.util.Iterator;


/**
 * @author Anton Koinov (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public class IteratorEnumeration implements Enumeration
{
    Iterator _it;

    public IteratorEnumeration(Iterator it)
    {
        _it = it;
    }

    public boolean hasMoreElements()
    {
        return _it.hasNext();
    }

    public Object nextElement()
    {
        return _it.next();
    }
}
