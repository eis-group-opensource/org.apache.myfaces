/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.navmenu;

import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.component.UserRoleUtils;

/**
 * A tree of menu items as returned by a value-expression. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * @since 1.1.7
 * @JSFComponent
 *   name = "t:navigationMenuItems"
 *   class = "org.apache.myfaces.custom.navmenu.HtmlNavigationMenuItems"
 *   bodyContent = "JSP"
 *   tagClass = "org.apache.myfaces.custom.navmenu.HtmlNavigationMenuItemsTag" 
 */
public abstract class AbstractHtmlNavigationMenuItems extends UISelectItems
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlNavigationMenuItems";
    public static final String COMPONENT_FAMILY = "javax.faces.SelectItems";

    /**
     * A boolean value that indicates whether this component should be rendered.
     * Default value: true.
     * 
     * @JSFProperty
     *   inherited= "false"
     *   tagExcluded= "true"
     *   defaultValue = "true"
     */
    public abstract boolean isRendered();
    
}
