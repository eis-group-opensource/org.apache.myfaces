﻿/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.autoscroll;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.shared_tomahawk.config.MyfacesConfig;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlFormRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
@JSFRenderer(renderKitId = "HTML_BASIC",
    family = "org.apache.myfaces.custom.autoscroll.AutoscrollHiddenField",
    type = "org.apache.myfaces.custom.autoscroll.AutoscrollHiddenField")
public class AutoscrollHiddenFieldRenderer extends HtmlRenderer
{
    private static final String SCROLL_HIDDEN_INPUT = "org.apache.myfaces.SCROLL_HIDDEN_INPUT";
    
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        //Check npe
        super.encodeEnd(context, component);
        
        //It is necessary a small to obtain the renderer parent of this field.
        //In theory the current component is called from a form renderer,
        //because this component is only added by
        //facesContext.getViewRoot().addComponentResource()
        //with target="form".
        component.popComponentFromEL(context);
        try
        {
            UIComponent form = UIComponent.getCurrentComponent(context);

            if (form instanceof UIForm)
            {
                ResponseWriter writer = context.getResponseWriter();
                if (MyfacesConfig.getCurrentInstance(context.getExternalContext()).isAutoScroll())
                {
                    //Render as original, to prevent duplicate hidden input rendering
                    HtmlFormRendererBase.renderScrollHiddenInputIfNecessary(form, context, writer);
                }
                else
                {
                    // No automatic scroll, but use of t:autoscroll, so force the rendering of the hidden field.
                    // It is not necessary to check if the hidden input has been rendered, because there is no
                    // other component that renders it.
                    HtmlRendererUtils.renderAutoScrollHiddenInput(context, writer);
                    context.getExternalContext().getRequestMap().put(getScrollHiddenInputName(context, form), Boolean.TRUE);
                }
            }
        }
        finally
        {
            //Restore the stack
            component.pushComponentToEL(context, component);
        }
    }
    
    private static String getScrollHiddenInputName(FacesContext facesContext, UIComponent form) {
        StringBuffer buf = new StringBuffer();
        buf.append(SCROLL_HIDDEN_INPUT);
        buf.append("_");
        buf.append(form.getClientId(facesContext));
        return buf.toString();
    }
}
