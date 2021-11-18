/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.layout;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlPanelGroup;

import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.PanelProperties;
import org.apache.myfaces.component.UniversalProperties;

/**
 * Determines where its children are positioned within the page 
 * relative to each other, similar to a Swing layout component. 
 * 
 * Unless otherwise specified, all attributes accept static values 
 * or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:panelLayout"
 *   class = "org.apache.myfaces.custom.layout.HtmlPanelLayout"
 *   tagClass = "org.apache.myfaces.custom.layout.HtmlPanelLayoutTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public abstract class AbstractHtmlPanelLayout
        extends HtmlPanelGroup implements
        AlignProperty, UniversalProperties, EventAware, DataProperties,
        PanelProperties, ClientBehaviorHolder
{
    //private static final Log log = LogFactory.getLog(HtmlPanelLayout.class);
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelLayout";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Layout";

    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;
    private static final String DEFAULT_LAYOUT = "classic";
    
    // typesafe facet getters

    /**
     * @JSFFacet
     */
    public UIComponent getHeader()
    {
        return (UIComponent)getFacet("header");
    }

    /**
     * @JSFFacet
     */
    public UIComponent getNavigation()
    {
        return (UIComponent)getFacet("navigation");
    }

    /**
     * @JSFFacet
     */
    public UIComponent getBody()
    {
        return (UIComponent)getFacet("body");
    }

    /**
     * @JSFFacet
     */
    public UIComponent getFooter()
    {
        return (UIComponent)getFacet("footer");
    }

    /**
     * The CSS class to be applied to footer cells.
     * 
     * @JSFProperty
     */
    public abstract String getFooterClass();
    
    /**
     * The CSS class to be applied to header cells.
     * 
     * @JSFProperty
     */
    public abstract String getHeaderClass();

    /**
     * <pre>
     * layout           | header | navigation | body  | footer
     * =================|========|============|=======|========
     * classic (Default)| top    | left       | right | bottom
     * navigationRight  | top    | right      | left  | bottom
     * upsideDown       | bottom | left       | right | top
     * </pre> 
     * 
     * @JSFProperty
     *   defaultValue="classic" 
     */
    public abstract String getLayout();
    
    /**
     * CSS class to be used for the table cell.
     * 
     * @JSFProperty
     */
    public abstract String getNavigationClass();

    /**
     * CSS class to be used for the table cell.
     * 
     * @JSFProperty
     */
    public abstract String getBodyClass();

    /**
     * CSS class to be used for the table cell.
     * 
     * @JSFProperty
     */
    public abstract String getHeaderStyle();

    /**
     * CSS class to be used for the table cell.
     * 
     * @JSFProperty
     */
    public abstract String getNavigationStyle();

    /**
     * CSS class to be used for the table cell.
     * 
     * @JSFProperty
     */
    public abstract String getBodyStyle();

    /**
     * CSS class to be used for the table cell.
     * 
     * @JSFProperty
     */
    public abstract String getFooterStyle();

}
