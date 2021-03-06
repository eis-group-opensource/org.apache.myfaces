/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tabbedpane;

import javax.faces.component.NamingContainer;
import javax.faces.component.html.HtmlPanelGroup;

import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;

/**
 * TODO: Document this component.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:panelTab"
 *   class = "org.apache.myfaces.custom.tabbedpane.HtmlPanelTab"
 *   tagClass = "org.apache.myfaces.custom.tabbedpane.HtmlPanelTabTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlPanelTab
        extends HtmlPanelGroup  implements NamingContainer, 
        UserRoleAware, EventAware, UniversalProperties
{
    //private static final Log log = LogFactory.getLog(HtmlPanelTab.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelTab";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Group";
    private static final boolean DEFAULT_DISABLED = false;

    /**
     * Label of this tab.
     * 
     * @JSFProperty
     */
    public abstract String getLabel();

    /**
     * HTML: When true, this element cannot receive focus.
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isDisabled();

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) {
            return false;
        }
        return super.isRendered();
    }
}
