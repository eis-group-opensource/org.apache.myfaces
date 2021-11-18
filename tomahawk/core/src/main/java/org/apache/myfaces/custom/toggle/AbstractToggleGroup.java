/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.toggle;

import javax.faces.component.html.HtmlPanelGroup;

import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.UniversalProperties;

/**
 * Container class allows user to toggle between view/edit mode.
 * 
 * Extends PanelGroup. Allows user to have several toggleLink in a group. 
 * When the togglePanel is toggled, the toggleGroup will be hidden.
 * 
 * @JSFComponent
 *   name = "t:toggleGroup"
 *   class = "org.apache.myfaces.custom.toggle.ToggleGroup"
 *   tagClass = "org.apache.myfaces.custom.toggle.ToggleGroupTag"
 *   
 * @author Sharath
 * 
 */
public abstract class AbstractToggleGroup extends HtmlPanelGroup
    implements EventAware, UniversalProperties
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.ToggleGroup";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.ToggleGroup";

    public AbstractToggleGroup()
    {
        super();
        setRendererType(AbstractToggleGroup.DEFAULT_RENDERER_TYPE);
    }
    
    /**
     * HTML: Flag to define the toggle status.
     * 
     * @JSFProperty
     */
     public abstract boolean isToggled();
    
     public abstract void setToggled(boolean toggleMode);
    
}