/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * 
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public interface EventAware
{
    
    /**
     * HTML: Script to be invoked when the element is clicked.
     * 
     */
    @JSFProperty(clientEvent="click")
    public String getOnclick();

    /**
     * HTML: Script to be invoked when the element is double-clicked.
     * 
     */
    @JSFProperty(clientEvent="dblclick")
    public String getOndblclick();

    /**
     * HTML: Script to be invoked when a key is pressed down over this element.
     * 
     */
    @JSFProperty(clientEvent="keydown")
    public String getOnkeydown();

    /**
     * HTML: Script to be invoked when a key is pressed over this element.
     * 
     */
    @JSFProperty(clientEvent="keypress")
    public String getOnkeypress();

    /**
     * HTML: Script to be invoked when a key is released over this element.
     * 
     */
    @JSFProperty(clientEvent="keyup")
    public String getOnkeyup();

    /**
     * HTML: Script to be invoked when the pointing device is pressed over this element.
     * 
     */
    @JSFProperty(clientEvent="mousedown")
    public String getOnmousedown();

    /**
     * HTML: Script to be invoked when the pointing device is moved while it is in this element.
     * 
     */
    @JSFProperty(clientEvent="mousemove")
    public String getOnmousemove();

    /**
     * HTML: Script to be invoked when the pointing device is moves out of this element.
     * 
     */
    @JSFProperty(clientEvent="mouseout")
    public String getOnmouseout();

    /**
     * HTML: Script to be invoked when the pointing device is moved into this element.
     * 
     */
    @JSFProperty(clientEvent="mouseover")
    public String getOnmouseover();

    /**
     * HTML: Script to be invoked when the pointing device is released over this element.
     * 
     */
    @JSFProperty(clientEvent="mouseup")
    public String getOnmouseup();

}
