/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.component.html.ext.HtmlPanelGroup;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlGroupRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.ResourceUtils;


/**
 * 
 * @author Martin Marinschek (latest modification by $Author: mmarinschek $)
 * @version $Revision: 167446 $ $Date: 2004-12-23 13:03:09Z $
 */
@JSFRenderer(
   renderKitId = "HTML_BASIC",
   family = "javax.faces.Panel",
   type = "org.apache.myfaces.Group")
public class HtmlGroupRenderer
    extends HtmlGroupRendererBase
{

    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        super.decode(context, component);
        
        HtmlRendererUtils.decodeClientBehaviors(context, component);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        boolean span = false;
        String element = getHtmlElement(component);
        
        Map<String, List<ClientBehavior>> behaviors = null;
        if (component instanceof ClientBehaviorHolder)
        {
            behaviors = ((ClientBehaviorHolder) component).getClientBehaviors();
            if (!behaviors.isEmpty())
            {
                ResourceUtils.renderDefaultJsfJsInlineIfNecessary(context, writer);
            }
        }

        if (behaviors != null && !behaviors.isEmpty())
        {
            //Render element and id to make javascript work
            span = true;
            writer.startElement(element, component);
            writer.writeAttribute(HTML.ID_ATTR, component.getClientId(context),null);
            HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.UNIVERSAL_ATTRIBUTES);
            HtmlRendererUtils.renderBehaviorizedEventHandlers(context, writer, component, behaviors);
        }
        else
        {
            //No behaviors, do it as usual
            if(component.getId()!=null && !component.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
            {
                span = true;

                writer.startElement(element, component);

                HtmlRendererUtils.writeIdIfNecessary(writer, component, context);

                HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.COMMON_PASSTROUGH_ATTRIBUTES);
            }
            else
            {
                span=HtmlRendererUtils.renderHTMLAttributesWithOptionalStartElement(writer,
                                                                                 component,
                                                                                 element,
                                                                                 HTML.COMMON_PASSTROUGH_ATTRIBUTES);
            }
        }

        RendererUtils.renderChildren(context, component);
        if (span)
        {
            writer.endElement(element);
        }
    }
    
    private String getHtmlElement(UIComponent component) {
        if (component instanceof HtmlPanelGroup) {
            HtmlPanelGroup group = (HtmlPanelGroup) component;
            if (HtmlPanelGroup.BLOCK_LAYOUT.equals(group.getLayout())) {
                return HTML.DIV_ELEM;
            }
        }
        return HTML.SPAN_ELEM;
    }
}
