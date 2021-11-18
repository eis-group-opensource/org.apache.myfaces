/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree;

import javax.faces.component.UISelectItem;

/**
 * Renders a HTML input of type "treeCheckbox". 
 * 
 * The associated comes from the treeCheckbox itemLabel and itemValue. 
 * The selected items come from an extended selectManyCheckbox 
 * component with layout "spread". The selectManyCheckbox is 
 * referenced by the "for" attribute. 
 * 
 * All HTML pass-through attributes for this input are taken from 
 * the associated selectManyCheckbox. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:treeCheckbox"
 *   class = "org.apache.myfaces.custom.tree.HtmlTreeCheckbox"
 *   tagClass = "org.apache.myfaces.custom.tree.taglib.TreeCheckboxTag"
 * @since 1.1.7
 * @author <a href="mailto:dlestrat@yahoo.com">David Le Strat</a>
 */
public abstract class AbstractHtmlTreeCheckbox extends UISelectItem
{
    
    /** The for attribute declaration. */
    public static final String FOR_ATTR = "for".intern();
    
    /** The component type. */
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTreeCheckbox";
    
    /** The component family. */
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.HtmlTreeCheckbox";
    
    /** The default renderer type. */
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlTreeCheckbox";
        
    /**
     * id of the referenced extended selectManyCheckbox component
     * 
     * @JSFProperty
     * @return The for attribute.
     */
    public abstract String getFor();
    
}
