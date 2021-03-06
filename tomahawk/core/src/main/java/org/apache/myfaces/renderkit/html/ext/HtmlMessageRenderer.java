/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlMessage;
import org.apache.myfaces.component.html.ext.HtmlMessages;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlMessageRendererBase;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Message"
 *   type = "org.apache.myfaces.Message"
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 940143 $ $Date: 2010-05-02 04:39:12 +0300 (Sun, 02 May 2010) $
 */
public class HtmlMessageRenderer
        extends HtmlMessageRendererBase
{
    private static final Log log = LogFactory.getLog(HtmlMessageRenderer.class);

    private static final String OUTPUT_LABEL_MAP = HtmlMessageRenderer.class.getName() + ".OUTPUT_LABEL_MAP";

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        super.encodeEnd(facesContext, component);   //check for NP
        renderMessage(facesContext, component);

        if (component instanceof HtmlMessage
                && ((HtmlMessage)component).getForceSpan())
        {
            String forAttr = getFor(component);
            HtmlMessage htmlMessage = (HtmlMessage) component;

            UIComponent forComponent = component.findComponent(forAttr);

            if (forComponent != null)
            {
                String forCompclientId = forComponent.getClientId(facesContext);

                ResponseWriter writer = facesContext.getResponseWriter();
                writer.startElement(HTML.SPAN_ELEM, null);
                writer.writeAttribute(HTML.ID_ATTR, forCompclientId + "_msgFor", null);
                if(htmlMessage.getStyleClass()!=null)
                writer.writeAttribute(HTML.CLASS_ATTR,htmlMessage.getStyleClass(),null);
                if(htmlMessage.getStyle()!=null)
                writer.writeAttribute(HTML.STYLE_ATTR,htmlMessage.getStyle(),null);
                writer.endElement(HTML.SPAN_ELEM);
            }
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
        if (msgClientId != null) inputLabel = findInputLabel(facesContext, msgClientId);
        if (inputLabel == null) inputLabel = "";

        if(((message instanceof HtmlMessages && ((HtmlMessages) message).isReplaceIdWithLabel()) ||
                (message instanceof HtmlMessage && ((HtmlMessage) message).isReplaceIdWithLabel()))&&
                inputLabel.length()!=0)
            msgSummary = msgSummary.replaceAll(findInputId(facesContext, msgClientId),inputLabel);


        String summaryFormat;
        if (message instanceof HtmlMessage)
        {
            summaryFormat = ((HtmlMessage)message).getSummaryFormat();
        }
        else
        {
            summaryFormat = (String)message.getAttributes().get("summaryFormat");
        }

        if (summaryFormat == null) return msgSummary;

        MessageFormat format = new MessageFormat(summaryFormat, facesContext.getViewRoot().getLocale());

        return format.format(new Object[] {msgSummary, inputLabel});
    }

    protected String getDetail(FacesContext facesContext,
                               UIComponent message,
                               FacesMessage facesMessage,
                               String msgClientId)
    {
        String msgDetail = facesMessage.getDetail();
        if (msgDetail == null) return null;

        String inputLabel = null;
        if (msgClientId != null) inputLabel = findInputLabel(facesContext, msgClientId);
        if (inputLabel == null) inputLabel = "";

        if(((message instanceof HtmlMessages && ((HtmlMessages) message).isReplaceIdWithLabel()) ||
                (message instanceof HtmlMessage && ((HtmlMessage) message).isReplaceIdWithLabel()))&&
                inputLabel.length()!=0)
            msgDetail = msgDetail.replaceAll(findInputId(facesContext, msgClientId),inputLabel);

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


    public static String findInputLabel(FacesContext facesContext, String inputClientId)
    {
        Map outputLabelMap = getOutputLabelMap(facesContext);
        MessageLabelInfo info = ((MessageLabelInfo)outputLabelMap.get(inputClientId));

        if(info == null)
        {
            UIComponent comp = facesContext.getViewRoot().findComponent(inputClientId);

            UIComponent parent=comp;

            while(parent != null && !((parent=parent.getParent())instanceof UIColumn));

            if(parent != null)
            {
                UIColumn column = (UIColumn) parent;

                if(column.getHeader()!=null)
                {
                    UIComponent header = column.getHeader();

                    return getComponentText(facesContext, header);
                }
            }
        }

        return info==null?null:info.getText(facesContext);
    }

    public static String findInputId(FacesContext facesContext, String inputClientId)
    {
        Map outputLabelMap = getOutputLabelMap(facesContext);
        MessageLabelInfo info = ((MessageLabelInfo)outputLabelMap.get(inputClientId));

        if(info == null)
        {
            UIComponent comp = facesContext.getViewRoot().findComponent(inputClientId);

            if(comp!=null)
            {
                return comp.getId();
            }
        }

        return info==null?null:(info.getForComponent()==null?null:info.getForComponent().getId());
    }

    /**
     * @param facesContext
     * @return a Map that reversely maps clientIds of components to their
     *         corresponding OutputLabel component
     */
    private static Map getOutputLabelMap(FacesContext facesContext)
    {
        Map map = (Map)facesContext.getExternalContext().getRequestMap().get(OUTPUT_LABEL_MAP);
        if (map == null)
        {
            map = new HashMap();
            createOutputLabelMap(facesContext, facesContext.getViewRoot(), map);
            facesContext.getExternalContext().getRequestMap().put(OUTPUT_LABEL_MAP, map);
        }
        return map;
    }

    private static void createOutputLabelMap(FacesContext facesContext,
                                             UIComponent root,
                                             Map map)
    {
        for (Iterator it = root.getFacetsAndChildren(); it.hasNext(); )
        {
            UIComponent child = (UIComponent)it.next();
            if (child instanceof HtmlOutputLabel)
            {
                String forAttr = ((HtmlOutputLabel)child).getFor();
                if (forAttr != null)
                {
                    UIComponent input = child.findComponent(forAttr);
                    if (input == null)
                    {
                        log.warn("Unable to find component '" + forAttr + "' (calling findComponent on component '" + child.getClientId(facesContext) + "')");
                    }
                    else
                    {
                        if (child.getValueBinding("value") != null)
                        {
                            // If the child uses a ValueExpression, do not evaluate the text
                            // right now. When getText(FacesContext) is called, do it there.
                            map.put(input.getClientId(facesContext),
                                    new MessageDefferedLabelInfo(
                                            input, child));
                        }
                        else
                        {
                            map.put(input.getClientId(facesContext),
                                    new MessageTextLabelInfo(
                                            input,getComponentText(facesContext, (HtmlOutputLabel)child)));
                        }
                    }
                }
            }
            else
            {
                createOutputLabelMap(facesContext, child, map);
            }
        }
    }

    private static String getComponentText(FacesContext facesContext, UIComponent component)
    {
        String text = null;

        if(component instanceof ValueHolder)
        {
            text= RendererUtils.getStringValue(facesContext, component);
        }

        if (text == null || text.length() < 1)
        {
            StringBuffer buf = new StringBuffer();
            List li = component.getChildren();

            for (int i = 0; i < li.size(); i++)
            {
                UIComponent child = (UIComponent) li.get(i);

                if(child instanceof HtmlOutputText)
                {
                    String str = RendererUtils.getStringValue(facesContext, child);

                    if(str!=null)
                        buf.append(str);
                }
            }

            text = buf.toString();
        }
        return text;
    }

    public static interface MessageLabelInfo
    {
        public UIComponent getForComponent();
        public String getText(FacesContext context);
    }
    
    public final static class MessageTextLabelInfo implements MessageLabelInfo
    {
        private final UIComponent _forComponent;
        private final String _text;

        public MessageTextLabelInfo(final UIComponent forComponent, final String text)
        {
            _forComponent = forComponent;
            _text = text;
        }

        public UIComponent getForComponent()
        {
            return _forComponent;
        }

        public String getText(FacesContext context)
        {
            return _text;
        }
    }
    
    public final static class MessageDefferedLabelInfo implements MessageLabelInfo
    {
        private final UIComponent _forComponent;
        private final UIComponent _labelComponent;
        private String _text;
        
        public MessageDefferedLabelInfo(final UIComponent forComponent, final UIComponent labelComponent)
        {
            _forComponent = forComponent;
            _labelComponent = labelComponent;
        }

        
        public UIComponent getForComponent()
        {
            return _forComponent;
        }

        public String getText(FacesContext context)
        {
            if (_text == null)
            {
                _text = getComponentText(context, (HtmlOutputLabel)_labelComponent); 
            }
            return _text; 
        }
    }
}
