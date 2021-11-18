/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.element;



/**
 * Entry of the Collection returned by {@link NavigationRule#getNavigationCases()}.
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public interface NavigationCase
{
    // <!ELEMENT navigation-case (description*, display-name*, icon*, from-action?, from-outcome?, to-view-id, redirect?)>

    public String getFromAction();
    public String getFromOutcome();
    public boolean isRedirect();
    public String getToViewId();
}
