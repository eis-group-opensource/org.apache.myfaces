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
 * MyFaces extension to the standard messages tag: see summaryDetailSeparator attribute. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:message"
 *   class = "org.apache.myfaces.component.html.ext.HtmlMessage"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlMessageTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlMessage
        extends javax.faces.component.html.HtmlMessage
        implements UserRoleAware, MessageProperties, ForceIdAware,
        EventAware, UniversalProperties
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlMessage";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Message";

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

    public boolean getForceSpan(){
        return isForceSpan();
    }
}
