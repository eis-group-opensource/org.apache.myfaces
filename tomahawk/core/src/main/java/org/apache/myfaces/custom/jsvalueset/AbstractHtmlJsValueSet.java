/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.jsvalueset;

import javax.faces.component.UIOutput;

import org.apache.myfaces.component.UserRoleUtils;

/**
 * Setting a value from the model in java-script so that it can be 
 * used (e.g. by the value change listener) afterwards. 
 * 
 * Unless otherwise specified, all attributes accept static values 
 * or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:jsValueSet"
 *   class = "org.apache.myfaces.custom.jsvalueset.HtmlJsValueSet"
 *   tagClass = "org.apache.myfaces.custom.jsvalueset.HtmlJsValueSetTag"
 * @JSFJspProperty name = "id" tagExcluded = "true"
 * @JSFJspProperty name = "binding" tagExcluded = "true"
 * @JSFJspProperty name = "rendered" tagExcluded = "true"
 * @JSFJspProperty name = "converter" tagExcluded = "true"
 * @since 1.1.7
 * @author Martin Marinschek (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlJsValueSet extends UIOutput
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlJsValueSet";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.JsValueSet";

    /**
     * javascript variable to be set.
     * 
     * @JSFProperty
     *   required="true"
     */
    public abstract String getName();

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }
    
    /**
     * value to be set in the variable.
     * 
     * @JSFProperty
     *   required="true"
     */
    public Object getValue()
    {
        return super.getValue();
    }

}


