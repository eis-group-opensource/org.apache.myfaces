/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.radio;

import javax.faces.component.UIComponentBase;

import org.apache.myfaces.component.AccesskeyProperty;
import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.AltProperty;
import org.apache.myfaces.component.ChangeSelectProperties;
import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.FocusBlurProperties;
import org.apache.myfaces.component.StyleAware;
import org.apache.myfaces.component.TabindexProperty;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.UserRoleAware;

/**
 * This tag is used in conjunction with the extended selectOneRadio 
 * tag when the "spread" layout is selected. It specifies the 
 * position within the document that the radio button corresponding 
 * to a specific SelectItem should be rendered. All HTML pass-through 
 * attributes for this input are taken from the associated 
 * selectOneRadio. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:radio"
 *   class = "org.apache.myfaces.custom.radio.HtmlRadio"
 *   tagClass = "org.apache.myfaces.custom.radio.HtmlRadioTag"
 * @since 1.1.7
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @version $Revision: 1084267 $ $Date: 2011-03-22 19:49:43 +0200 (Tue, 22 Mar 2011) $
 */
public abstract class AbstractHtmlRadio
    extends UIComponentBase implements UserRoleAware, 
    FocusBlurProperties, ChangeSelectProperties, 
    UniversalProperties, EventAware, AltProperty, 
    AlignProperty, StyleAware, AccesskeyProperty, TabindexProperty
{
    //private static final Log log = LogFactory.getLog(HtmlRadio.class);

    public static final String FOR_ATTR = "for".intern();
    public static final String INDEX_ATTR = "index".intern();


    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlRadio";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.Radio";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Radio";

    /**
     * The id of the referenced extended selectOneRadio component. 
     * This value is resolved to the particular component using 
     * the standard UIComponent.findComponent() searching algorithm.
     * 
     * @JSFProperty
     *   required="true"
     */
    public abstract String getFor();
    
    /**
     * The index of the corresponding SelectItem, where 0 represents the first SelectItem.
     * 
     * @JSFProperty
     *   defaultValue = "Integer.MIN_VALUE"
     *   required="true"
     */
    public abstract int getIndex();

}
