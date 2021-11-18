/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.element;

import java.util.Collection;

/**
 * Entry of the Collection returned by
 * {@link org.apache.myfaces.config.RuntimeConfig#getNavigationRules()}.
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public interface NavigationRule
{
    // <!ELEMENT navigation-rule (description*, display-name*, icon*, from-view-id?, navigation-case*)>

    public String getFromViewId();

    /**
     * @return a Collection of {@link org.apache.myfaces.config.element.NavigationCase}s
     */
    public Collection getNavigationCases();
}
