/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.div;

import javax.faces.component.behavior.ClientBehaviorHolder;

import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.custom.htmlTag.HtmlTag;

/**
 * Places a div around its children. Unless otherwise specified, 
 * all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:div"
 *   class = "org.apache.myfaces.custom.div.Div"
 *   tagClass = "org.apache.myfaces.custom.div.DivTag"
 * @since 1.1.7
 * @author bdudney (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public abstract class AbstractDiv extends HtmlTag implements EventAware, UniversalProperties, ClientBehaviorHolder
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.Div";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.DivRenderer";

    /**
     * @JSFProperty
     *   tagExcluded = "true"
     */
    public Object getValue()
    {
        return "div";
    }
}