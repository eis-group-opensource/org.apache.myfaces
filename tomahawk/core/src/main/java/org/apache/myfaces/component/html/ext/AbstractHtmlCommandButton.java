/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.context.FacesContext;

import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * Extends standard commandButton by user role support. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:commandButton"
 *   class = "org.apache.myfaces.component.html.ext.HtmlCommandButton"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlCommandButtonTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlCommandButton
        extends javax.faces.component.html.HtmlCommandButton
        implements UserRoleAware, ForceIdAware
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandButton";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Button";

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
    
}
