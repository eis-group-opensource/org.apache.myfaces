/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.htmlTag;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.HtmlTagRenderer"
 * 
 * @author bdudney (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2005-05-11 11:47:12 -0400 (Wed, 11 May 2005) $
 */
public class HtmlTagRenderer extends HtmlRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.HtmlTagRenderer";

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {
        if ((context == null) || (component == null))
        {
            throw new NullPointerException();
        }
        HtmlTag htmlTag = (HtmlTag) component;

        if (htmlTag.isRendered())
        {
            String tag = htmlTag.getValue().toString();
            if( tag.trim().length() == 0 ) // Don't render the tag, but render the children.
                return;

            ResponseWriter writer = context.getResponseWriter();

            writer.startElement(tag, htmlTag);
            HtmlRendererUtils.writeIdIfNecessary(writer, htmlTag, context);

            // TODO : Use HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.COMMON_PASSTROUGH_ATTRIBUTES);
            String[] supportedAttributes = {HTML.STYLE_CLASS_ATTR, HTML.STYLE_ATTR};
            HtmlRendererUtils.renderHTMLAttributes(writer, htmlTag, supportedAttributes);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        if ((context == null) || (component == null))
        {
            throw new NullPointerException();
        }
        HtmlTag htmlTag = (HtmlTag) component;

        if (htmlTag.isRendered())
        {
            String tag = htmlTag.getValue().toString();
            if( tag.trim().length() == 0 )
                return;

            ResponseWriter writer = context.getResponseWriter();
            writer.endElement( tag );
        }
    }
}