/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

/**
 * Behavioral interface.
 * By default, displayValueOnly is false, and the components have the default behaviour.
 * When displayValueOnly is true, the renderer should not render any input widget.
 * Only the text corresponding to the component's value should be rendered instead.
 * 
 * org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable 
 * are available on shared, but we need something on tomahawk
 * to define and generate properties.
 * 
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 *
 */
public interface DisplayValueOnlyAware
{
    /**
     *  If true, renders only the value of the component, 
     *  but no input widget. Default is false.
     * 
     * @JSFProperty
     */
    public Boolean getDisplayValueOnly();

    public void setDisplayValueOnly(Boolean b);
    
    /**
     * Style used when displayValueOnly is true.
     * 
     * @JSFProperty
     */
    public String getDisplayValueOnlyStyle();
    
    public void setDisplayValueOnlyStyle(String displayValueOnlyStyle);
    
    /**
     * Style class used when displayValueOnly is true.
     * 
     * @JSFProperty
     */
    public String getDisplayValueOnlyStyleClass();
    
    public void setDisplayValueOnlyStyleClass(String displayValueOnlyStyleClass);    
    
}
