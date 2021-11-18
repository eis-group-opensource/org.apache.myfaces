/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.toggle;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlGroupRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.ToggleGroup"
 * 
 */
public class ToggleGroupRenderer extends HtmlGroupRendererBase {

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ToggleGroup toggleGroup = (ToggleGroup) component;
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement( org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SPAN_ELEM, component );
        writer.writeAttribute(HTML.ID_ATTR, component.getClientId(context), null);

        HtmlRendererUtils.renderHTMLAttributes( writer, component, HTML.COMMON_PASSTROUGH_ATTRIBUTES );

        RendererUtils.renderChildren( context, component );

        writer.endElement( HTML.SPAN_ELEM );
    }
}
