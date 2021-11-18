/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.div;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.htmlTag.HtmlTagRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.ResourceUtils;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.DivRenderer"
 * @since 1.1.7
 * @author bdudney (latest modification by $Author: lu4242 $)
 * @version $Revision: 691871 $ $Date: 2008-09-03 23:32:08 -0500 (03 sep 2008) $
 */
public class DivRenderer extends HtmlTagRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.HtmlTagRenderer";

    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        super.decode(context, component);
        
        HtmlRendererUtils.decodeClientBehaviors(context, component);
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {
        if ((context == null) || (component == null))
        {
            throw new NullPointerException();
        }
        
        Div div = (Div) component;
        Map<String, List<ClientBehavior>> behaviors = div.getClientBehaviors();
        if (behaviors != null && !behaviors.isEmpty())
        {
            ResourceUtils.renderDefaultJsfJsInlineIfNecessary(context, context.getResponseWriter());
        }

        super.encodeBegin(context, component);

        if (div.isRendered())
        {
            ResponseWriter writer = context.getResponseWriter();
            //Previously, style and styleClass was rendered,
            //so on div renderer we need to add event and universal
            //attributes only
            if (behaviors != null && !behaviors.isEmpty())
            {
                HtmlRendererUtils.writeIdAndName(writer, component, context);
                HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.UNIVERSAL_ATTRIBUTES_WITHOUT_STYLE);
                HtmlRendererUtils.renderBehaviorizedEventHandlers(context, writer, component, behaviors);
            }
            else
            {
                HtmlRendererUtils.renderHTMLAttributes(writer, div, 
                        HTML.COMMON_PASSTROUGH_ATTRIBUTES_WITHOUT_STYLE);
            }
        }
    }

}