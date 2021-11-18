/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

/**
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public interface UniversalProperties
{
    /**
     * HTML: The direction of text display, either 'ltr' (left-to-right) or 'rtl' (right-to-left).
     * 
     * @JSFProperty
     */
    public abstract String getDir();

    /**
     * HTML: The base language of this document.
     * 
     * @JSFProperty
     */
    public abstract String getLang();

    /**
     * HTML: An advisory title for this element.  Often used by the user agent as a tooltip.
     * 
     * @JSFProperty
     */
    public abstract String getTitle();


}
