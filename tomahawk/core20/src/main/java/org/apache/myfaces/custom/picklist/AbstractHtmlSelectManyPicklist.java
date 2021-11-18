/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.picklist;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.component.html.ext.HtmlSelectManyListbox;

/**
 * A picklist component that allows to select items from one list to another
 * <p>
 * In other words, is a selection component where a set of items 
 * can be selected from a list that contains all the available items to a list
 * that contains the selected items.
 * </p>
 * <p>
 * The component is based on the t:selectManyListbox component, so it contains
 * the same attributes. Soon, more specific attributes will be added.
 * </p>
 * 
 * @since 1.1.7
 * @author Bruno Aranda (latest modification by $Author: jakobk $)
 * @version $Revision: 955214 $ $Date: 2010-06-16 15:16:02 +0300 (Wed, 16 Jun 2010) $
 */
@JSFComponent(
    name = "t:selectManyPicklist",
    clazz = "org.apache.myfaces.custom.picklist.HtmlSelectManyPicklist",
    tagClass = "org.apache.myfaces.custom.picklist.HtmlSelectManyPicklistTag")
public abstract class AbstractHtmlSelectManyPicklist extends HtmlSelectManyListbox
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSelectManyPicklist";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.PicklistRenderer";
    
    /**
     * Define the text that goes inside the add button
     * 
     * @JSFProperty
     */
    public abstract String getAddButtonText();
    
    /**
     * Define the text that goes inside the add all button
     * 
     * @JSFProperty
     */
    public abstract String getAddAllButtonText();    
    
    /**
     * Define the text that goes inside the remove button
     * 
     * @JSFProperty
     */
    public abstract String getRemoveButtonText();
    
    /**
     * Define the text that goes inside the remove all button
     * 
     * @JSFProperty
     */
    public abstract String getRemoveAllButtonText();    
    
    /**
     * CSS style to be applied to the add button
     * 
     * @JSFProperty
     */
    public abstract String getAddButtonStyle();
    
    /**
     * CSS style to be applied to the add all button
     * 
     * @JSFProperty
     */
    public abstract String getAddAllButtonStyle();    
    
    /**
     * CSS style to be applied to the remove button
     * 
     * @JSFProperty
     */
    public abstract String getRemoveButtonStyle();
    
    /**
     * CSS style to be applied to the remove all button
     * 
     * @JSFProperty
     */
    public abstract String getRemoveAllButtonStyle();    
    
    /**
     * CSS styleClass to be applied to the add button
     * 
     * @JSFProperty
     */
    public abstract String getAddButtonStyleClass();
    
    /**
     * CSS styleClass to be applied to the add all button
     * 
     * @JSFProperty
     */
    public abstract String getAddAllButtonStyleClass();    
    
    /**
     * CSS styleClass to be applied to the remove button
     * 
     * @JSFProperty
     */
    public abstract String getRemoveButtonStyleClass();
    
    /**
     * CSS styleClass to be applied to the remove all button
     * 
     * @JSFProperty
     */
    public abstract String getRemoveAllButtonStyleClass();    
    
    /**
     * Specifies the value type of the selectable items. This attribute is
     * similar to the collectionType attribute introduced in JSF 2.0. 
     * It can be used to declare the type of the selectable items when using
     * a Collection to store the values in the managed bean, because it is 
     * not possible in Java to get the value type of a type-safe Collection
     * (in contrast to arrays where this is possible). 
     * 
     * @since 2.0
     */
    @JSFProperty
    public abstract String getValueType(); 
}
