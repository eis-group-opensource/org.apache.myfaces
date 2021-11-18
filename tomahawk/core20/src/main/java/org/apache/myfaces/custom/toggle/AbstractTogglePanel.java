/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.toggle;

import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.UniversalProperties;

/**
 * Container class allows user to toggle between view/edit mode.
 * 
 * Extends PanelGroup. Allows user to toggle between 'view' mode and 'edit' mode. 
 * In the togglePanel, include a toggleLink. When the toggleLink is clicked, 
 * the rest of the group is shown, and the link is hidden.
 * 
 * @JSFComponent
 *   name = "t:togglePanel"
 *   class = "org.apache.myfaces.custom.toggle.TogglePanel"
 *   tagClass = "org.apache.myfaces.custom.toggle.TogglePanelTag"
 *   
 * @author Sharath
 * 
 */
public abstract class AbstractTogglePanel extends HtmlPanelGroup
    implements EventAware, UniversalProperties, ClientBehaviorHolder
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.TogglePanel";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TogglePanel";

    public static final boolean DEFAULT_TOGGLED = false;

    public static final boolean DEFAULT_DISABLED = false;

    /**
     * You can set toggled to true to force the toggleGroup to always be in toggle 
     * mode. Default is false.
     * 
     * @JSFProperty
     *   defaultValue="false"
     * @return
     */
    public abstract boolean isToggled();
    
    public abstract void setToggled(boolean toggleMode);
    
    /**
     * @JSFProperty
     *   defaultValue="false"
     * @return
     */
    public abstract boolean isDisabled();
    
    public void processDecodes(FacesContext context)
    {
        super.processDecodes(context);

        String hiddenFieldId = this.getClientId(context) + "_hidden";
        String toggleMode = (String) context.getExternalContext().getRequestParameterMap().get(hiddenFieldId);

        if (toggleMode != null && toggleMode.trim().equals("1")) {
            this.setToggled(true);
        }
    }

    public void processUpdates(FacesContext context)
    {
        super.processUpdates(context);
        this.setToggled(false);
    }
}
