/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.renderkit.wml;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.wap.component.GraphicImage;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.RendererUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderer;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public class ImageRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(ImageRenderer.class);

    /** Creates a new instance of ImageRenderer */
    public ImageRenderer() {
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

        GraphicImage comp = (GraphicImage)component;
        String contextPath = context.getExternalContext().getRequestContextPath();
        //String url = (String)component.getAttributes().get("url");

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(Attributes.IMG, comp);

        RendererUtils.writeAttribute(Attributes.ID, comp.getClientId(context), writer);
        RendererUtils.writeAttribute(Attributes.STYLE_CLASS, comp.getStyleClass(), writer);
        RendererUtils.writeAttribute(Attributes.XML_LANG, comp.getXmllang(), writer);
        RendererUtils.writeAttribute(Attributes.ALT, comp.getAlt(), writer);

        // src
        String url = null;
        if (comp.getValue() != null) url = (String)comp.getValue(); // 'value' have a priority
        else if (comp.getUrl() != null) url = (String)comp.getUrl();
        RendererUtils.writeAttribute(Attributes.SRC, getURL(context, url), writer);

        RendererUtils.writeAttribute(Attributes.LOCAL_SRC, comp.getLocalsrc(), writer);
        RendererUtils.writeAttribute(Attributes.VSPACE, comp.getVspace(), writer);
        RendererUtils.writeAttribute(Attributes.HSPACE, comp.getHspace(), writer);
        RendererUtils.writeAttribute(Attributes.ALIGN, comp.getAlign(), writer);
        RendererUtils.writeAttribute(Attributes.HEIGHT, comp.getHeight(), writer);
        RendererUtils.writeAttribute(Attributes.WIDTH, comp.getWidth(), writer);

        writer.endElement(Attributes.IMG);
    }


    public void decode(FacesContext context, UIComponent component) {
        if (component == null) throw new NullPointerException();
    }

    /** Passing it to the getResourceURL() method of the ViewHandler for this application,
     *  and passing the result through the encodeResourceURL() method of the ExternalContext
     */
    private String getURL(FacesContext context, String url) throws java.io.IOException {
        if (url ==  null) throw new java.io.WriteAbortedException("URL parameter in tag graphicImage is null.", new java.lang.NullPointerException());
        url = context.getApplication().getViewHandler().getResourceURL(context, url);
        url = context.getExternalContext().encodeResourceURL(url);
        return (url);
    }

}

