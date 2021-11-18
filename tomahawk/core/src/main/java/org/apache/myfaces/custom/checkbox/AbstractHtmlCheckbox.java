/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.checkbox;

import javax.faces.component.UIComponentBase;

import org.apache.myfaces.component.UserRoleAware;

/**
 * Renders a HTML input of type "checkbox". 
 * The associated SelectItem comes from an extended selectManyCheckbox 
 * component with layout "spread". The selectManyCheckbox is 
 * referenced by the "for" attribute.
 * 
 * All HTML pass-through attributes for this input 
 * are taken from the associated selectManyCheckbox. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:checkbox"
 *   class = "org.apache.myfaces.custom.checkbox.HtmlCheckbox"
 *   tagClass = "org.apache.myfaces.custom.checkbox.HtmlCheckboxTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlCheckbox
    extends UIComponentBase implements UserRoleAware
{

    public static final String FOR_ATTR = "for".intern();
    public static final String INDEX_ATTR = "index".intern();

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCheckbox";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.Checkbox";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Checkbox";

    /**
     * id of the referenced extended selectManyCheckbox component
     * 
     * @JSFProperty
     *   required="true"
     */
    public abstract String getFor();
    
    /**
     * n-th SelectItem of referenced UISelectMany starting with 0.
     * 
     * @JSFProperty
     *   defaultValue = "Integer.MIN_VALUE"
     *   required="true"
     */
    public abstract int getIndex();

}
