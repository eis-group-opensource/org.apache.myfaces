/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import org.apache.myfaces.shared.renderkit.RendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * @author Martin Marinschek
 * @version $Revision: $ $Date: $
 *          <p/>
 *          $Log: $
 */
public class HtmlGroupRendererBase
        extends HtmlRenderer 
{
    private static final String LAYOUT_BLOCK_VALUE = "block";

    public boolean getRendersChildren()
    {
        return true;
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {
    }

    public void encodeChildren(FacesContext context, UIComponent component)
        throws IOException
    {
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();
        boolean span = false;

        // will be SPAN or DIV, depending on the layout attribute value
        String layoutElement = HTML.SPAN_ELEM;

        HtmlPanelGroup panelGroup = (HtmlPanelGroup) component;

        // if layout is 'block', render DIV instead SPAN
        String layout = panelGroup.getLayout();
        if (layout != null && layout.equals(LAYOUT_BLOCK_VALUE))
        {
            layoutElement = HTML.DIV_ELEM;
        }

        if(component.getId()!=null && !component.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
        {
            span = true;

            writer.startElement(layoutElement, component);

            HtmlRendererUtils.writeIdIfNecessary(writer, component, context);

            HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.COMMON_PASSTROUGH_ATTRIBUTES);
        }
        else
        {
            span=HtmlRendererUtils.renderHTMLAttributesWithOptionalStartElement(writer,
                                                                             component,
                                                                             layoutElement,
                                                                             HTML.COMMON_PASSTROUGH_ATTRIBUTES);
        }

        RendererUtils.renderChildren(context, component);
        if (span)
        {
            writer.endElement(layoutElement);
        }
    }

}
