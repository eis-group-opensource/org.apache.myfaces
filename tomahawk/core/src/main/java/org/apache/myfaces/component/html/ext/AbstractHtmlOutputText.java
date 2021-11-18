/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.context.FacesContext;

import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * Extends standard outputText with user role support.
 *  
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:outputText"
 *   class = "org.apache.myfaces.component.html.ext.HtmlOutputText"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlOutputTextTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlOutputText
        extends javax.faces.component.html.HtmlOutputText
        implements UserRoleAware, ForceIdAware, EventAware,UniversalProperties
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlOutputText";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Text";

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

}
