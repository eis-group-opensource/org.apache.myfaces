/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.context.FacesContext;

import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;


/**
 * Extends standard commandLink by user role support and the HTML 
 * target attribute. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:commandLink"
 *   class = "org.apache.myfaces.component.html.ext.HtmlCommandLink"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlCommandLinkTag"
 * @since 1.1.7
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @author Manfred Geiler
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlCommandLink
        extends javax.faces.component.html.HtmlCommandLink
        implements UserRoleAware, ForceIdAware
{
        
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandLink";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Link";

    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }        
    
    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }

    /**
     *  Comma separated list of subForm-ids for which validation 
     *  and model update should take place when this command is 
     *  executed. You need to wrap your input components in 
     *  org.apache.myfaces.custom.subform.SubForm instances for 
     *  this to work.
     *  
     * @JSFProperty
     */
    public abstract String getActionFor();

    /**
     * When set instead of a Hyperlink a span tag is rendered in the corresponding Component
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isDisabled();

    /**
     * CSS-Style Attribute to render when disabled is true
     * 
     * @JSFProperty
     */
    public abstract String getDisabledStyle();

    /**
     * CSS-Style Class to use when disabled is true
     * 
     * @JSFProperty
     */
    public abstract String getDisabledStyleClass();

}
