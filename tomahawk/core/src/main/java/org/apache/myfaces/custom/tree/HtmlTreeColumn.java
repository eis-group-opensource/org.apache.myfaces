/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree;

import javax.faces.component.UIColumn;

/**
 * Renders a HTML input of type "treeColumn". 
 * <p>
 * This tag outlines the column where the tree structure will be 
 * render as part of the tree table. Unless otherwise specified, 
 * all attributes accept static values or EL expressions.
 * </p>
 * <p>
 * Tree column model. This column is used to provide the place holder for the
 * tree.  This is used in conjunction with the table format display.
 * </p>
 * 
 * @JSFComponent
 *   name = "t:treeColumn"
 *   tagClass = "org.apache.myfaces.custom.tree.taglib.TreeColumnTag"
 * 
 * @author <a href="mailto:dlestrat@apache.org">David Le Strat</a>
 */
public class HtmlTreeColumn extends UIColumn
{
    /** The component type. */
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTreeColumn";
    
    /** The component family. */
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.HtmlTreeColumn";
    
    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public HtmlTreeColumn()
    {
        super();
    }
    
    /**
     * @see javax.faces.component.UIComponent#getFamily()
     */
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }
}
