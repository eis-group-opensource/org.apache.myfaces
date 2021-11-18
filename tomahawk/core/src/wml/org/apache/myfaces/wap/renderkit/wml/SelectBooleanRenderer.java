/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.renderkit.wml;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.wap.component.SelectBoolean;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.RendererUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderer;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public class SelectBooleanRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(SelectBooleanRenderer.class);
    private static String OPTION_VALUE = "bool";

    /** Creates a new instance of renderer */
    public SelectBooleanRenderer() {
        super();
        log.debug("created object " + this.getClass().getName());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeBegin(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        SelectBoolean comp = (SelectBoolean)component;

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(Attributes.SELECT, component);
        RendererUtils.writeAttribute(Attributes.ID, comp.getClientId(context), writer);
        RendererUtils.writeAttribute(Attributes.STYLE_CLASS, comp.getStyleClass(), writer);
        RendererUtils.writeAttribute(Attributes.XML_LANG, comp.getXmllang(), writer);

        /* attribute name is not required. If is not set, name value equals component id */
        if (comp.getName() == null) {
            log.debug("getName is null");
            comp.setName(comp.getClientId(context));
        }
        RendererUtils.writeAttribute(Attributes.NAME, comp.getName(), writer);

        RendererUtils.writeAttribute(Attributes.TABINDEX, comp.getTabindex(), writer);
        RendererUtils.writeAttribute(Attributes.TITLE, comp.getTitle(), writer);

        if (comp.isSelected())
            RendererUtils.writeAttribute(Attributes.VALUE, OPTION_VALUE, writer);
        else { /* don't check off */ }

        // multiple
        RendererUtils.writeAttribute(Attributes.MULTIPLE, "true", writer);

        // write option
        writer.startElement(Attributes.OPTION, component);
        RendererUtils.writeAttribute(Attributes.VALUE, OPTION_VALUE, writer);
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

        writer.endElement(Attributes.OPTION);
        writer.endElement(Attributes.SELECT);
    }

    public void decode(FacesContext context, UIComponent component) {
        log.debug("decode(" + component.getId() + ")");
        if (component == null || context == null) throw new NullPointerException();
        if (!(component instanceof SelectBoolean))
            log.error("Component " + component.getClass().getName() + " is no SelectMany component, cannot be converted!");

        SelectBoolean comp = (SelectBoolean)component;

        Map map = context.getExternalContext().getRequestParameterMap();

        // Set the submitted value of this UISelectOne component
        if (map.containsKey(comp.getName())){
            log.debug("Parameter:" + comp.getName() + " was found in the request. Value: " + (String)map.get(comp.getName()));

            String value = (String)map.get(comp.getName());
            if (OPTION_VALUE.equalsIgnoreCase(value)) comp.setSelected(true);
            else comp.setSelected(false);
        }
    }
}

