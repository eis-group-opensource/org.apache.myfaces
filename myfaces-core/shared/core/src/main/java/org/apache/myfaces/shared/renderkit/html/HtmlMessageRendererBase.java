/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.apache.myfaces.shared.renderkit.RendererUtils;
import org.apache.myfaces.shared.renderkit.html.HTML;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 778858 $ $Date: 2009-05-26 22:44:18 +0300 (An, 26 Geg 2009) $
 */
public abstract class HtmlMessageRendererBase
        extends HtmlRenderer
{
    private static final Log log = LogFactory.getLog(HtmlMessageRendererBase.class);

    protected abstract String getSummary(FacesContext facesContext,
                                         UIComponent message,
                                         FacesMessage facesMessage,
                                         String msgClientId);

    protected abstract String getDetail(FacesContext facesContext,
                                        UIComponent message,
                                        FacesMessage facesMessage,
                                        String msgClientId);


    protected void renderMessage(FacesContext facesContext,
                                 UIComponent message)
            throws IOException
    {
        renderMessage(facesContext, message, false);
    }

    /**
     * @param facesContext
     * @param message
     * @param alwaysRenderSpan if true will render a span even if there is no message
     */
    protected void renderMessage(FacesContext facesContext, UIComponent message, boolean alwaysRenderSpan) throws IOException
    {
        String forAttr = getFor(message);
        if (forAttr == null)
        {
            log.error("Attribute 'for' of UIMessage must not be null");
            return;
        }

        UIComponent forComponent = message.findComponent(forAttr);
        if (forComponent == null)
        {
            log.error("Could not render Message. Unable to find component '" + forAttr + "' (calling findComponent on component '" + message.getClientId(facesContext) + "'). If the provided id was correct, wrap the message and its component into an h:panelGroup or h:panelGrid.");
            return;
        }

        String clientId = forComponent.getClientId(facesContext);

        Iterator messageIterator = facesContext.getMessages(clientId);
        if (!messageIterator.hasNext())
        {
            // No associated message, nothing to render
            if (alwaysRenderSpan)
            {
                // show span anyways in case there's a client side update, ie: ajax
                ResponseWriter writer = facesContext.getResponseWriter();
                writer.startElement(HTML.SPAN_ELEM, message);
                writer.writeAttribute(HTML.ID_ATTR, message.getId(), null);
                writer.endElement(HTML.SPAN_ELEM);
            }
            return;
        }

        // get first message
        FacesMessage facesMessage = (FacesMessage) messageIterator.next();

        // and render it
        renderSingleFacesMessage(facesContext, message, facesMessage, clientId);
    }


    protected void renderSingleFacesMessage(FacesContext facesContext,
                                            UIComponent message,
                                            FacesMessage facesMessage,
                                            String messageClientId)
            throws IOException
    {
        renderSingleFacesMessage(facesContext, message, facesMessage, messageClientId,true);
    }
    
    protected void renderSingleFacesMessage(FacesContext facesContext,
            UIComponent message,
            FacesMessage facesMessage,
            String messageClientId,
            boolean renderId)
    throws IOException
    {
        renderSingleFacesMessage(facesContext, message, facesMessage, messageClientId, renderId, true);
    }

    protected void renderSingleFacesMessage(FacesContext facesContext,
                                            UIComponent message,
                                            FacesMessage facesMessage,
                                            String messageClientId,
                                            boolean renderId,
                                            boolean renderStyleAndStyleClass)
            throws IOException
    {
        // determine style and style class
        String[] styleAndClass = HtmlMessageRendererBase.getStyleAndStyleClass(message, facesMessage.getSeverity());
        String style = styleAndClass[0];
        String styleClass = styleAndClass[1];

        String summary = getSummary(facesContext, message, facesMessage, messageClientId);
        String detail = getDetail(facesContext, message, facesMessage, messageClientId);

        String title = getTitle(message);
        boolean tooltip = isTooltip(message);

        if (title == null && tooltip)
        {
            title = summary;
        }

        ResponseWriter writer = facesContext.getResponseWriter();

        boolean span = false;


        if (message.getId() != null && !message.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
        {
            span = true;

            writer.startElement(HTML.SPAN_ELEM, message);

            if (renderId)
            {
                HtmlRendererUtils.writeIdIfNecessary(writer, message, facesContext);
            }

            HtmlRendererUtils.renderHTMLAttributes(writer, message, HTML.MESSAGE_PASSTHROUGH_ATTRIBUTES_WITHOUT_TITLE_STYLE_AND_STYLE_CLASS);
        }
        else
        {
            span = HtmlRendererUtils.renderHTMLAttributesWithOptionalStartElement(
                    writer, message, HTML.SPAN_ELEM, HTML.MESSAGE_PASSTHROUGH_ATTRIBUTES_WITHOUT_TITLE_STYLE_AND_STYLE_CLASS);
        }

        span |= HtmlRendererUtils.renderHTMLAttributeWithOptionalStartElement(writer, message, HTML.SPAN_ELEM, HTML.TITLE_ATTR, title, span);
        if (renderStyleAndStyleClass)
        {
            span |= HtmlRendererUtils.renderHTMLAttributeWithOptionalStartElement(writer, message, HTML.SPAN_ELEM, HTML.STYLE_ATTR, style, span);
            span |= HtmlRendererUtils.renderHTMLAttributeWithOptionalStartElement(writer, message, HTML.SPAN_ELEM, HTML.STYLE_CLASS_ATTR, styleClass, span);
        }


        boolean showSummary = isShowSummary(message) && (summary != null);
        boolean showDetail = isShowDetail(message) && (detail != null);

        if (showSummary && !(title == null && tooltip))
        {
            writer.writeText(summary, null);
            if (showDetail)
            {
                writer.writeText(" ", null);
            }
        }

        if (showDetail)
        {
            writer.writeText(detail, null);
        }

        if (span)
        {
            writer.endElement(org.apache.myfaces.shared.renderkit.html.HTML.SPAN_ELEM);
        }
    }


    public static String[] getStyleAndStyleClass(UIComponent message,
                                                 FacesMessage.Severity severity)
    {
        String style = null;
        String styleClass = null;
        if (message instanceof HtmlMessage)
        {
            if (severity == FacesMessage.SEVERITY_INFO)
            {
                style = ((HtmlMessage) message).getInfoStyle();
                styleClass = ((HtmlMessage) message).getInfoClass();
            }
            else if (severity == FacesMessage.SEVERITY_WARN)
            {
                style = ((HtmlMessage) message).getWarnStyle();
                styleClass = ((HtmlMessage) message).getWarnClass();
            }
            else if (severity == FacesMessage.SEVERITY_ERROR)
            {
                style = ((HtmlMessage) message).getErrorStyle();
                styleClass = ((HtmlMessage) message).getErrorClass();
            }
            else if (severity == FacesMessage.SEVERITY_FATAL)
            {
                style = ((HtmlMessage) message).getFatalStyle();
                styleClass = ((HtmlMessage) message).getFatalClass();
            }

            if (style == null)
            {
                style = ((HtmlMessage) message).getStyle();
            }

            if (styleClass == null)
            {
                styleClass = ((HtmlMessage) message).getStyleClass();
            }
        }
        else
        {
            Map attr = message.getAttributes();
            if (severity == FacesMessage.SEVERITY_INFO)
            {
                style = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.INFO_STYLE_ATTR);
                styleClass = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.INFO_CLASS_ATTR);
            }
            else if (severity == FacesMessage.SEVERITY_WARN)
            {
                style = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.WARN_STYLE_ATTR);
                styleClass = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.WARN_CLASS_ATTR);
            }
            else if (severity == FacesMessage.SEVERITY_ERROR)
            {
                style = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.ERROR_STYLE_ATTR);
                styleClass = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.ERROR_CLASS_ATTR);
            }
            else if (severity == FacesMessage.SEVERITY_FATAL)
            {
                style = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.FATAL_STYLE_ATTR);
                styleClass = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.FATAL_CLASS_ATTR);
            }

            if (style == null)
            {
                style = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.STYLE_ATTR);
            }

            if (styleClass == null)
            {
                styleClass = (String) attr.get(org.apache.myfaces.shared.renderkit.JSFAttr.STYLE_CLASS_ATTR);
            }
        }

        return new String[]{style, styleClass};
    }

    protected String getFor(UIComponent component)
    {
        if (component instanceof UIMessage)
        {
            return ((UIMessage) component).getFor();
        }
 
        return (String) component.getAttributes().get(JSFAttr.FOR_ATTR);
        
    }

    protected String getTitle(UIComponent component)
    {
        if (component instanceof HtmlMessage)
        {
            return ((HtmlMessage) component).getTitle();
        }

        return (String) component.getAttributes().get(JSFAttr.TITLE_ATTR);
        
    }

    protected boolean isTooltip(UIComponent component)
    {
        if (component instanceof HtmlMessage)
        {
            return ((HtmlMessage) component).isTooltip();
        }

        return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(component, org.apache.myfaces.shared.renderkit.JSFAttr.TOOLTIP_ATTR, false);
        
    }

    protected boolean isShowSummary(UIComponent component)
    {
        if (component instanceof UIMessage)
        {
            return ((UIMessage) component).isShowSummary();
        }

        return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(component, org.apache.myfaces.shared.renderkit.JSFAttr.SHOW_SUMMARY_ATTR, false);
        
    }

    protected boolean isShowDetail(UIComponent component)
    {
        if (component instanceof UIMessage)
        {
            return ((UIMessage) component).isShowDetail();
        }

        return RendererUtils.getBooleanAttribute(component, org.apache.myfaces.shared.renderkit.JSFAttr.SHOW_DETAIL_ATTR, false);
        
    }

}
