/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlTextareaRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.ResourceUtils;


/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Input"
 *   type = "org.apache.myfaces.Textarea"
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2008-05-24 15:59:15 -0500 (24 may 2008) $
 */
public class HtmlTextareaRenderer
        extends HtmlTextareaRendererBase
{
    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        if (!UserRoleUtils.isEnabledOnUserRole(uiComponent))
        {
            return true;
        }
        else
        {
            return super.isDisabled(facesContext, uiComponent);
        }
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIInput.class);

        if(HtmlRendererUtils.isDisplayValueOnly(uiComponent))
        {
            HtmlRendererUtils.renderDisplayValueOnly(facesContext, (UIInput) uiComponent);
        }
        else
        {
            Map<String, List<ClientBehavior>> behaviors = null;
            if (uiComponent instanceof ClientBehaviorHolder)
            {
                behaviors = ((ClientBehaviorHolder) uiComponent).getClientBehaviors();
                if (!behaviors.isEmpty())
                {
                    ResourceUtils.renderDefaultJsfJsInlineIfNecessary(facesContext, facesContext.getResponseWriter());
                }
            }
            
            encodeTextArea(facesContext, uiComponent);
        }
    }

}
