/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectOneRow;

import javax.faces.component.UIInput;

import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.ChangeSelectProperties;
import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.FocusBlurProperties;

/**
 * Enhancement for a data-table to select one Row with a radio button. The row-index is stored in the vealu-binding
 * 
 * @JSFComponent
 *   name = "t:selectOneRow"
 *   class = "org.apache.myfaces.custom.selectOneRow.SelectOneRow"
 *   tagClass = "org.apache.myfaces.custom.selectOneRow.SelectOneRowTag"
 * @since 1.1.7
 */
public abstract class AbstractSelectOneRow extends UIInput 
    implements AlignProperty, EventAware, FocusBlurProperties, ChangeSelectProperties
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.SelectOneRow";

    public static final String COMPONENT_FAMILY = "org.apache.myfaces.SelectOneRow";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SelectOneRow";

    /**
     * The Name of the radio-button-group to use. If EL expressions are used,
     * note that every time this is evaluated should lead to the same value
     * in the scope used, that means the UIData instance used, otherwise
     * it could lead to unwanted side effects.
     * 
     * @JSFProperty
     */
    public abstract String getGroupName();
    
    /**
     * HTML: When true, this element cannot receive focus.
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isDisabled();   
    
    /**
     * HTML: When true, indicates that this component cannot be modified by the user.
     * The element may receive focus unless it has also been disabled.
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isReadonly();    
    
}
