/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.renderkit.wml;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.wap.component.OutputLink;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.RendererUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderer;


/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public class OutputLinkRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(OutputLinkRenderer.class);

    /** Creates a new instance of TextRenderer */
    public OutputLinkRenderer() {
        super();
        log.debug("created object " + this.getClass().getName());
    }

    public boolean getRendersChildren(){
        return(true);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeBegin(" + component.getId() + ")");

        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        ResponseWriter writer = context.getResponseWriter();
        OutputLink comp = (OutputLink)component;

        String href = RendererUtils.convertToString(context, component);

        List child = component.getChildren();
        for (int i = 0; i < child.size(); i++){ // insert parameters from UIParameter components
            if (child.get(i) instanceof UIParameter){
                href = insertParam(href, (UIParameter)child.get(i), writer.getCharacterEncoding());
            }
        }
        href = context.getExternalContext().encodeResourceURL(href);

        writer.startElement(Attributes.A, component);
        RendererUtils.writeAttribute(Attributes.HREF, href, writer);
        RendererUtils.writeAttribute(Attributes.ID, comp.getClientId(context), writer);
        RendererUtils.writeAttribute(Attributes.CLASS, comp.getStyleClass(), writer);
        RendererUtils.writeAttribute(Attributes.XML_LANG, comp.getXmllang(), writer);
        RendererUtils.writeAttribute(Attributes.TITLE, comp.getTitle(), writer);
        writer.flush();
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeChildren(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        RendererUtils.renderChildren(context, component);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeEnd(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(Attributes.A);
    }

    public void decode(FacesContext context, UIComponent component) {
        if (component == null ) throw new NullPointerException();
    }

    private String insertParam(String href, UIParameter param, String encoding) throws java.io.IOException {
        String name = (String)param.getName();
        String value = (String)param.getValue();

        return(RendererUtils.insertGetParam(href, name, value, encoding));
    }

    /** Overrides method getConvertedValue */
    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws javax.faces.convert.ConverterException {
        return(RendererUtils.convertToObject(context, component));
    }
}

