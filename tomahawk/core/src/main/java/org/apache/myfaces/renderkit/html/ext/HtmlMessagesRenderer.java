/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import org.apache.myfaces.component.html.ext.HtmlMessage;
import org.apache.myfaces.component.html.ext.HtmlMessages;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlMessagesRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Messages"
 *   type = "org.apache.myfaces.Messages"
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2008-05-24 23:59:15 +0300 (Sat, 24 May 2008) $
 */
public class HtmlMessagesRenderer
        extends HtmlMessagesRendererBase
{
    //private static final Log log = LogFactory.getLog(HtmlMessagesRenderer.class);


    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        super.encodeEnd(facesContext, component);   //check for NP
        renderMessages(facesContext, component);

        if (component instanceof HtmlMessages
                && ((HtmlMessages) component).getForceSpan())
        {
            ResponseWriter writer = facesContext.getResponseWriter();

            HtmlMessages htmlMessages = (HtmlMessages) component;

            writer.startElement(HTML.SPAN_ELEM, null);
            writer.writeAttribute(HTML.ID_ATTR,component.getClientId(facesContext),null);
            if(htmlMessages.getStyleClass()!=null)
            writer.writeAttribute(HTML.CLASS_ATTR,htmlMessages.getStyleClass(),null);
            if(htmlMessages.getStyle()!=null)
            writer.writeAttribute(HTML.STYLE_ATTR,htmlMessages.getStyle(),null);
            writer.endElement(HTML.SPAN_ELEM);
        }
    }

    protected String getSummary(FacesContext facesContext,
                                UIComponent message,
                                FacesMessage facesMessage,
                                String msgClientId)
    {
        String msgSummary = facesMessage.getSummary();
        if (msgSummary == null) return null;

        String inputLabel = null;
        if (msgClientId != null) inputLabel = HtmlMessageRenderer.findInputLabel(facesContext, msgClientId);
        if (inputLabel == null) inputLabel = "";

        if(((message instanceof HtmlMessages && ((HtmlMessages) message).isReplaceIdWithLabel()) ||
                (message instanceof HtmlMessage && ((HtmlMessage) message).isReplaceIdWithLabel()))&&
                inputLabel.length()!=0)
            msgSummary = msgSummary.replaceAll(HtmlMessageRenderer.findInputId(facesContext, msgClientId),inputLabel);


        String summaryFormat;
        if (msgClientId == null)
        {
            summaryFormat = getGlobalSummaryFormat(message);
            if (summaryFormat == null)
            {
                summaryFormat = getSummaryFormat(message);
            }
        }
        else
        {
            summaryFormat = getSummaryFormat(message);
        }
        if (summaryFormat == null) return msgSummary;

        MessageFormat format = new MessageFormat(summaryFormat, facesContext.getViewRoot().getLocale());
        return format.format(new Object[] {msgSummary, inputLabel});
    }


    private String getSummaryFormat(UIComponent message)
    {
        if (message instanceof HtmlMessages)
        {
            return ((HtmlMessages)message).getSummaryFormat();
        }
        else
        {
            return (String)message.getAttributes().get("summaryFormat");
        }
    }

    private String getGlobalSummaryFormat(UIComponent message)
    {
        if (message instanceof HtmlMessages)
        {
            return ((HtmlMessages)message).getGlobalSummaryFormat();
        }
        else
        {
            return (String)message.getAttributes().get("globalSummaryFormat");
        }
    }

    protected String getDetail(FacesContext facesContext,
                               UIComponent message,
                               FacesMessage facesMessage,
                               String msgClientId)
    {
        String msgDetail = facesMessage.getDetail();
        if (msgDetail == null) return null;

        String inputLabel = null;
        if (msgClientId != null) inputLabel = HtmlMessageRenderer.findInputLabel(facesContext, msgClientId);
        if (inputLabel == null) inputLabel = "";

        if(((message instanceof HtmlMessages && ((HtmlMessages) message).isReplaceIdWithLabel()) ||
                (message instanceof HtmlMessage && ((HtmlMessage) message).isReplaceIdWithLabel()))&&
                inputLabel.length()!=0)
            msgDetail = msgDetail.replaceAll(HtmlMessageRenderer.findInputId(facesContext, msgClientId),inputLabel);

        String detailFormat;
        if (message instanceof HtmlMessage)
        {
            detailFormat = ((HtmlMessage)message).getDetailFormat();
        }
        else
        {
            detailFormat = (String)message.getAttributes().get("detailFormat");
        }

        if (detailFormat == null) return msgDetail;

        MessageFormat format = new MessageFormat(detailFormat, facesContext.getViewRoot().getLocale());
        return format.format(new Object[] {msgDetail, inputLabel});
    }


}
