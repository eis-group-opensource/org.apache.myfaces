/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.panelstack;

import javax.faces.component.UIPanel;


/**
 * A stack of panels, to switch panels dynamically. 
 * 
 * Manage a stack of JSF components and allow for one child component to be choosen for rendering. The behaviour
 * is similar to the CardLayout of Java Swing. Property <code>selectedPanel</code> defines the id of the child
 * to be rendered. If no child panel is selected or if the selected panel can not be found the first child is rendered.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 *
 * @JSFComponent
 *   name = "t:panelStack"
 *   class = "org.apache.myfaces.custom.panelstack.HtmlPanelStack"
 *   tagClass = "org.apache.myfaces.custom.panelstack.PanelStackTag"
 * @since 1.1.7
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlPanelStack extends UIPanel
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelStack";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.PanelStack";

    /**
     * @JSFProperty
     *   required="true"
     */
    public abstract String getSelectedPanel();
    
}
