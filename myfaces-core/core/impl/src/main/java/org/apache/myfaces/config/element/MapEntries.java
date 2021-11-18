/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.element;

import java.util.Iterator;

/**
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public interface MapEntries
{
    // <!ELEMENT map-entries (key-class?, value-class?, map-entry*)>

    public String getKeyClass();

    public String getValueClass();

    /**
     * @return Iterator over {@link MapEntry} entries
     */
    public Iterator getMapEntries();

}
