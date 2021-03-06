/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * MyFaces extension to the standard messages tag: see showInputLabel attribute.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:messages"
 *   class = "org.apache.myfaces.component.html.ext.HtmlMessages"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlMessagesTag"
 * @since 1.1.7 
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @author Manfred Geiler
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public abstract class AbstractHtmlMessages
        extends javax.faces.component.html.HtmlMessages
        implements UserRoleAware, MessageProperties, ForceIdAware,
        EventAware, UniversalProperties,
        ClientBehaviorHolder
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlMessages";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Messages";

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
    
    /**
     *  Like summaryFormat, but applies to global messages 
     *  (i.e. messages not associated with a component). If no 
     *  globalSummaryFormat is given, the summaryFormat is used 
     *  for global messages. Example: "{0}:"
     * 
     * @JSFProperty 
     */
    public abstract String getGlobalSummaryFormat();

}
