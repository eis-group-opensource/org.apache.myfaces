/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.div;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

import org.apache.myfaces.custom.htmlTag.HtmlTagRenderer;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.DivRenderer"
 * @since 1.1.7
 * @author bdudney (latest modification by $Author: lu4242 $)
 * @version $Revision: 691871 $ $Date: 2008-09-04 07:32:08 +0300 (Thu, 04 Sep 2008) $
 */
public class DivRenderer extends HtmlTagRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.HtmlTagRenderer";

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {
        if ((context == null) || (component == null))
        {
            throw new NullPointerException();
        }
        
        super.encodeBegin(context, component);
        
        Div div = (Div) component;

        if (div.isRendered())
        {
            ResponseWriter writer = context.getResponseWriter();
            //Previously, style and styleClass was rendered,
            //so on div renderer we need to add event and universal
            //attributes only
            HtmlRendererUtils.renderHTMLAttributes(writer, div, 
                    HTML.COMMON_PASSTROUGH_ATTRIBUTES_WITHOUT_STYLE);
        }
    }

}