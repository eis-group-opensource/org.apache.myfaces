/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.renderkit.wml;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.util.NullIterator;
import org.apache.myfaces.wap.component.Messages;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.WmlRenderer;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public class MessagesRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(MessagesRenderer.class);

    /** Creates a new instance of TextRenderer */
    public MessagesRenderer() {
        super();
        log.debug("created object " + this.getClass().getName());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeBegin(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeChildren(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeEnd(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        Messages comp = (Messages)component;
        ResponseWriter writer = context.getResponseWriter();

        MessagesIterator messagesIterator = new MessagesIterator(context, comp.isGlobalOnly());

        if (messagesIterator.hasNext()) {
            String layout = comp.getLayout();

            if (layout == null || layout.equalsIgnoreCase(Attributes.LIST)) {
                while(messagesIterator.hasNext()) {
                    renderMessage(context, comp, (FacesMessage)messagesIterator.next());

                    if (messagesIterator.hasNext())
                        writer.startElement(Attributes.BR, comp);
                }
            }
            else { // renderers messages as a table
                writer.startElement(Attributes.TABLE, comp);
                writer.writeAttribute(Attributes.COLUMNS, "1", null);

                while(messagesIterator.hasNext()) {
                    writer.startElement(Attributes.TR, comp);
                    writer.startElement(Attributes.TD, comp);
                    renderMessage(context, comp, (FacesMessage)messagesIterator.next());

                    if (messagesIterator.hasNext())
                        writer.startElement(Attributes.BR, comp);

                    writer.endElement(Attributes.TD);
                    writer.endElement(Attributes.TR);
                }
                writer.endElement(Attributes.TABLE);
            }
        }
    }

    private void renderMessage(FacesContext context, Messages comp, FacesMessage message) throws java.io.IOException{
        ResponseWriter writer = context.getResponseWriter();

        if (comp.isShowSummary())
            writer.write(message.getSummary());
        if (comp.isShowDetail())
            writer.write(message.getDetail());
    }

    public void decode(FacesContext context, UIComponent component) {
        if (component == null ) throw new NullPointerException();
    }

    private static class MessagesIterator implements Iterator {
        private FacesContext _facesContext;
        private Iterator _globalMessagesIterator;
        private Iterator _clientIdsWithMessagesIterator;
        private Iterator _componentMessagesIterator = null;
        private String _clientId = null;

        public MessagesIterator(FacesContext facesContext, boolean globalOnly) {
            _facesContext = facesContext;
            _globalMessagesIterator = facesContext.getMessages(null);
            if (globalOnly) {
                _clientIdsWithMessagesIterator = NullIterator.instance();
            }
            else {
                _clientIdsWithMessagesIterator = facesContext.getClientIdsWithMessages();
            }
            _componentMessagesIterator = null;
            _clientId = null;
        }

        public boolean hasNext() {
            return _globalMessagesIterator.hasNext() ||
            _clientIdsWithMessagesIterator.hasNext() ||
            (_componentMessagesIterator != null && _componentMessagesIterator.hasNext());
        }

        public Object next() {
            if (_globalMessagesIterator.hasNext()) {
                return _globalMessagesIterator.next();
            }
            else if (_componentMessagesIterator != null && _componentMessagesIterator.hasNext()) {
                return _componentMessagesIterator.next();
            }
            else {
                _clientId = (String)_clientIdsWithMessagesIterator.next();
                _componentMessagesIterator = _facesContext.getMessages(_clientId);
                return _componentMessagesIterator.next();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
        }

        public String getClientId() {
            return _clientId;
        }
    }

}

