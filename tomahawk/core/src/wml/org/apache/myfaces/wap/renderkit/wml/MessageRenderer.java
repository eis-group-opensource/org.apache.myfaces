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
import org.apache.myfaces.wap.component.Message;
import org.apache.myfaces.wap.renderkit.WmlRenderer;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public class MessageRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(MessageRenderer.class);

    /** Creates a new instance of TextRenderer */
    public MessageRenderer() {
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

        Message comp = (Message)component;
        ResponseWriter writer = context.getResponseWriter();

        String _for = comp.getFor();
        if (_for == null) log.error("Attribute 'for' of UIMessage must not be null.");

        UIComponent forComponent = component.findComponent(_for);
        if (forComponent == null) log.error("Unable to find component '" + _for + "'. Can not render UIMessage component.");

        Iterator iter = context.getMessages(forComponent.getClientId(context));
        if (iter.hasNext()) {
            FacesMessage message = (FacesMessage)iter.next();
            if (comp.isShowSummary())
                writer.write(message.getSummary());
            if (comp.isShowDetail())
                writer.write(message.getDetail());
        }
    }

    public void decode(FacesContext context, UIComponent component) {
        if (component == null ) throw new NullPointerException();
    }
}

