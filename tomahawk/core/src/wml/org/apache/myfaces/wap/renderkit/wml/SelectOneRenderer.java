/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.renderkit.wml;

import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.wap.component.SelectOne;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.RendererUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderer;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public class SelectOneRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(SelectOneRenderer.class);

    /** Creates a new instance of TextRenderer */
    public SelectOneRenderer() {
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

        SelectOne comp = (SelectOne)component;

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(Attributes.SELECT, component);
        RendererUtils.writeAttribute(Attributes.ID, comp.getClientId(context), writer);
        RendererUtils.writeAttribute(Attributes.STYLE_CLASS, comp.getStyleClass(), writer);
        RendererUtils.writeAttribute(Attributes.XML_LANG, comp.getXmllang(), writer);
        /* TODO: attribute "required" */

        /* attribute name is not required. If is not set, name value equals component id */
        if (comp.getName() == null) {
            log.debug("getName is null");
            comp.setName(comp.getClientId(context));
        }
        RendererUtils.writeAttribute(Attributes.NAME, comp.getName(), writer);

        RendererUtils.writeAttribute(Attributes.TABINDEX, comp.getTabindex(), writer);
        RendererUtils.writeAttribute(Attributes.TITLE, comp.getTitle(), writer);
        RendererUtils.writeAttribute(Attributes.VALUE, comp.getValue(), writer);

        // "false" is default. Don't must be written.
        //RenderUtils.writeAttribute(Attributes.MULTIPLE, "false", writer);

        List child = component.getChildren();
        for (int i = 0; i < child.size(); i++){
            if (child.get(i) instanceof UISelectItem){
                UISelectItem item = (UISelectItem)child.get(i);
                WmlRenderUtils.writeOption((SelectItem)item.getValue(), comp, writer);
            }
            if (child.get(i) instanceof UISelectItems){
                UISelectItems item = (UISelectItems)child.get(i);
                WmlRenderUtils.writeOptions(item.getValue(), comp, writer);
            }
        }

        writer.endElement(Attributes.SELECT);
    }

    public void decode(FacesContext context, UIComponent component) {
        log.debug("decode(" + component.getId() + ")");
        if (component == null || context == null) throw new NullPointerException();
        if (!(component instanceof SelectOne))
            log.error("Component " + component.getClass().getName() + " is no SelectOne component, cannot be converted!");

        SelectOne comp = (SelectOne)component;

        Map map = context.getExternalContext().getRequestParameterMap();

        // Set the submitted value of this UISelectOne component
        if (map.containsKey(comp.getName())){
            log.debug("Parameter:" + comp.getName() + " was found in the request. Value: " + (String)map.get(comp.getName()));
            comp.setSubmittedValue(map.get(comp.getName()));
            //comp.setValue(map.get(comp.getName()));
        }
    }

    /** Overrides method getConvertedValue */
    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws javax.faces.convert.ConverterException {
        return(RendererUtils.convertToObject(context, component));
    }
}

