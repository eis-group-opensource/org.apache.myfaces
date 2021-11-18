/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

/**
 * 
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public interface EventAware
{
    
    /**
     * HTML: Script to be invoked when the element is clicked.
     * 
     * @JSFProperty
     */
    public String getOnclick();

    /**
     * HTML: Script to be invoked when the element is double-clicked.
     * 
     * @JSFProperty
     */
    public String getOndblclick();

    /**
     * HTML: Script to be invoked when a key is pressed down over this element.
     * 
     * @JSFProperty
     */
    public String getOnkeydown();

    /**
     * HTML: Script to be invoked when a key is pressed over this element.
     * 
     * @JSFProperty
     */
    public String getOnkeypress();

    /**
     * HTML: Script to be invoked when a key is released over this element.
     * 
     * @JSFProperty
     */
    public String getOnkeyup();

    /**
     * HTML: Script to be invoked when the pointing device is pressed over this element.
     * 
     * @JSFProperty
     */
    public String getOnmousedown();

    /**
     * HTML: Script to be invoked when the pointing device is moved while it is in this element.
     * 
     * @JSFProperty
     */
    public String getOnmousemove();

    /**
     * HTML: Script to be invoked when the pointing device is moves out of this element.
     * 
     * @JSFProperty
     */
    public String getOnmouseout();

    /**
     * HTML: Script to be invoked when the pointing device is moved into this element.
     * 
     * @JSFProperty
     */
    public String getOnmouseover();

    /**
     * HTML: Script to be invoked when the pointing device is released over this element.
     * 
     * @JSFProperty
     */
    public String getOnmouseup();

}
