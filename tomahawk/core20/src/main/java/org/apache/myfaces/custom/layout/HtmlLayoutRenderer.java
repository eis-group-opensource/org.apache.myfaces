﻿/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.layout;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.ResourceUtils;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.Layout"
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2008-05-24 15:59:15 -0500 (24 may 2008) $
 */
public class HtmlLayoutRenderer
        extends HtmlRenderer
{
    private static final Log log = LogFactory.getLog(HtmlLayoutRenderer.class);

    public static final String CLASSIC_LAYOUT = "classic";
    public static final String NAV_RIGHT_LAYOUT = "navigationRight";
    public static final String UPSIDE_DOWN_LAYOUT = "upsideDown";

    public boolean getRendersChildren()
    {
        return true;
    }

    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        super.decode(context, component);
        
        HtmlRendererUtils.decodeClientBehaviors(context, component);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException
    {
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException
    {
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, HtmlPanelLayout.class);

        HtmlPanelLayout panelLayout = (HtmlPanelLayout)component;

        Map<String, List<ClientBehavior>> behaviors = null;
        if (component instanceof ClientBehaviorHolder)
        {
            behaviors = ((ClientBehaviorHolder) component).getClientBehaviors();
            if (!behaviors.isEmpty())
            {
                ResourceUtils.renderDefaultJsfJsInlineIfNecessary(facesContext, facesContext.getResponseWriter());
            }
        }
        
        String layout = panelLayout.getLayout();
        if (layout == null || layout.equals(CLASSIC_LAYOUT))
        {
            renderClassic(facesContext, panelLayout);
        }
        else if (layout.equals(NAV_RIGHT_LAYOUT))
        {
            renderNavRight(facesContext, panelLayout);
        }
        else if (layout.equals(UPSIDE_DOWN_LAYOUT))
        {
            renderUpsideDown(facesContext, panelLayout);
        }
        else
        {
            log.error("Unknown panel layout '" + layout + "'!");
        }

        if (panelLayout.getChildCount() > 0)
        {
            log.error("PanelLayout must not have children, only facets allowed!");
        }
    }

    protected void renderClassic(FacesContext facesContext, HtmlPanelLayout panelLayout)
            throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        UIComponent header = panelLayout.getHeader();
        UIComponent navigation = panelLayout.getNavigation();
        UIComponent body = panelLayout.getBody();
        UIComponent footer = panelLayout.getFooter();

        writer.startElement(HTML.TABLE_ELEM, panelLayout);
        
        Map<String, List<ClientBehavior>> behaviors = panelLayout.getClientBehaviors();
        
        if (behaviors != null && !behaviors.isEmpty())
        {
            writer.writeAttribute(HTML.ID_ATTR, panelLayout.getClientId(facesContext),null);
            HtmlRendererUtils.renderBehaviorizedEventHandlers(facesContext, writer, panelLayout, behaviors);
            HtmlRendererUtils.renderHTMLAttributes(writer, panelLayout, HTML.TABLE_PASSTHROUGH_ATTRIBUTES_WITHOUT_EVENTS); 
        }
        else
        {
            HtmlRendererUtils.writeIdIfNecessary(writer, panelLayout, facesContext);
            HtmlRendererUtils.renderHTMLAttributes(writer, panelLayout, HTML.TABLE_PASSTHROUGH_ATTRIBUTES);            
        }
        if (header != null)
        {
            writer.startElement(HTML.TR_ELEM, panelLayout);
            renderTableCell(facesContext, writer, header,
                            (navigation != null && body != null) ? 2 : 1,
                            panelLayout.getHeaderClass(),
                            panelLayout.getHeaderStyle());
            writer.endElement(HTML.TR_ELEM);
        }
        if (navigation != null || body != null)
        {
            writer.startElement(HTML.TR_ELEM, panelLayout);
            if (navigation != null)
            {
                renderTableCell(facesContext, writer, navigation, 0,
                                panelLayout.getNavigationClass(),
                                panelLayout.getNavigationStyle());
            }
            if (body != null)
            {
                renderTableCell(facesContext, writer, body, 0,
                                panelLayout.getBodyClass(),
                                panelLayout.getBodyStyle());
            }
            writer.endElement(HTML.TR_ELEM);
        }
        if (footer != null)
        {
            writer.startElement(HTML.TR_ELEM, panelLayout);
            renderTableCell(facesContext, writer, footer,
                            (navigation != null && body != null) ? 2 : 1,
                            panelLayout.getFooterClass(),
                            panelLayout.getFooterStyle());
            writer.endElement(HTML.TR_ELEM);
        }
        writer.endElement(HTML.TABLE_ELEM);
    }


    protected void renderNavRight(FacesContext facesContext, HtmlPanelLayout panelLayout)
            throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        UIComponent header = panelLayout.getHeader();
        UIComponent navigation = panelLayout.getNavigation();
        UIComponent body = panelLayout.getBody();
        UIComponent footer = panelLayout.getFooter();

        writer.startElement(HTML.TABLE_ELEM, panelLayout);
        
        Map<String, List<ClientBehavior>> behaviors = panelLayout.getClientBehaviors();
        
        if (behaviors != null && !behaviors.isEmpty())
        {
            writer.writeAttribute(HTML.ID_ATTR, panelLayout.getClientId(facesContext),null);
            HtmlRendererUtils.renderBehaviorizedEventHandlers(facesContext, writer, panelLayout, behaviors);
            HtmlRendererUtils.renderHTMLAttributes(writer, panelLayout, HTML.TABLE_PASSTHROUGH_ATTRIBUTES_WITHOUT_EVENTS); 
        }
        else
        {
            HtmlRendererUtils.writeIdIfNecessary(writer, panelLayout, facesContext);            
            HtmlRendererUtils.renderHTMLAttributes(writer, panelLayout, HTML.TABLE_PASSTHROUGH_ATTRIBUTES);            
        }        
        if (header != null)
        {
            writer.startElement(HTML.TR_ELEM, panelLayout);
            renderTableCell(facesContext, writer, header,
                            (navigation != null && body != null) ? 2 : 1,
                            panelLayout.getHeaderClass(),
                            panelLayout.getHeaderStyle());
            writer.endElement(HTML.TR_ELEM);
        }
        if (navigation != null || body != null)
        {
            writer.startElement(HTML.TR_ELEM, panelLayout);
            if (body != null)
            {
                renderTableCell(facesContext, writer, body, 0,
                                panelLayout.getBodyClass(),
                                panelLayout.getBodyStyle());
            }
            if (navigation != null)
            {
                renderTableCell(facesContext, writer, navigation, 0,
                                panelLayout.getNavigationClass(),
                                panelLayout.getNavigationStyle());
            }
            writer.endElement(HTML.TR_ELEM);
        }
        if (footer != null)
        {
            writer.startElement(HTML.TR_ELEM, panelLayout);
            renderTableCell(facesContext, writer, footer,
                            (navigation != null && body != null) ? 2 : 1,
                            panelLayout.getFooterClass(),
                            panelLayout.getFooterStyle());
            writer.endElement(HTML.TR_ELEM);
        }
        writer.endElement(HTML.TABLE_ELEM);
    }


    protected void renderUpsideDown(FacesContext facesContext, HtmlPanelLayout panelLayout)
            throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        UIComponent header = panelLayout.getHeader();
        UIComponent navigation = panelLayout.getNavigation();
        UIComponent body = panelLayout.getBody();
        UIComponent footer = panelLayout.getFooter();

        writer.startElement(HTML.TABLE_ELEM, panelLayout);
        
        Map<String, List<ClientBehavior>> behaviors = panelLayout.getClientBehaviors();
        
        if (behaviors != null && !behaviors.isEmpty())
        {
            writer.writeAttribute(HTML.ID_ATTR, panelLayout.getClientId(facesContext),null);
            HtmlRendererUtils.renderBehaviorizedEventHandlers(facesContext, writer, panelLayout, behaviors);
            HtmlRendererUtils.renderHTMLAttributes(writer, panelLayout, HTML.TABLE_PASSTHROUGH_ATTRIBUTES_WITHOUT_EVENTS); 
        }
        else
        {
            HtmlRendererUtils.writeIdIfNecessary(writer, panelLayout, facesContext);
            HtmlRendererUtils.renderHTMLAttributes(writer, panelLayout, HTML.TABLE_PASSTHROUGH_ATTRIBUTES);            
        }
        if (footer != null)
        {
            writer.startElement(HTML.TR_ELEM, panelLayout);
            renderTableCell(facesContext, writer, footer,
                            (navigation != null && body != null) ? 2 : 1,
                            panelLayout.getFooterClass(),
                            panelLayout.getFooterStyle());
            writer.endElement(HTML.TR_ELEM);
        }
        if (navigation != null || body != null)
        {
            writer.startElement(HTML.TR_ELEM, panelLayout);
            if (navigation != null)
            {
                renderTableCell(facesContext, writer, navigation, 0,
                                panelLayout.getNavigationClass(),
                                panelLayout.getNavigationStyle());
            }
            if (body != null)
            {
                renderTableCell(facesContext, writer, body, 0,
                                panelLayout.getBodyClass(),
                                panelLayout.getBodyStyle());
            }
            writer.endElement(HTML.TR_ELEM);
        }
        if (header != null)
        {
            writer.startElement(HTML.TR_ELEM, panelLayout);
            renderTableCell(facesContext, writer, header,
                            (navigation != null && body != null) ? 2 : 1,
                            panelLayout.getHeaderClass(),
                            panelLayout.getHeaderStyle());
            writer.endElement(HTML.TR_ELEM);
        }
        writer.endElement(HTML.TABLE_ELEM);
    }


    protected void renderTableCell(FacesContext facesContext,
                                   ResponseWriter writer,
                                   UIComponent component,
                                   int colspan,
                                   String styleClass,
                                   String style)
            throws IOException
    {
        writer.startElement(HTML.TD_ELEM, component);
        if (colspan > 0)
        {
            writer.writeAttribute(HTML.COLSPAN_ATTR, Integer.toString(colspan), null);
        }
        if (styleClass != null)
        {
            writer.writeAttribute(HTML.CLASS_ATTR, styleClass, null);
        }
        if (style != null)
        {
            writer.writeAttribute(HTML.STYLE_ATTR, style, null);
        }

        RendererUtils.renderChild(facesContext, component);

        writer.endElement(HTML.TD_ELEM);
    }

}
