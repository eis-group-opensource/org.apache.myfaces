/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.element;

import java.util.Iterator;

import org.apache.myfaces.config.element.ListEntries;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public interface ManagedBean
{
    // <!ELEMENT managed-bean (description*, display-name*, icon*, managed-bean-name, managed-bean-class, managed-bean-scope, (managed-property* | map-entries | list-entries))>

    public static final int INIT_MODE_NO_INIT = 0;
    public static final int INIT_MODE_PROPERTIES = 1;
    public static final int INIT_MODE_MAP = 2;
    public static final int INIT_MODE_LIST = 3;

    public String getDescription();
    public String getManagedBeanName();
    public String getManagedBeanClassName();
    public Class getManagedBeanClass();
    public String getManagedBeanScope();

    public int getInitMode();

    /**
     * @return Iterator over {@link ManagedProperty} entries
     */
    public Iterator getManagedProperties();

    public MapEntries getMapEntries();

    public ListEntries getListEntries();
}
