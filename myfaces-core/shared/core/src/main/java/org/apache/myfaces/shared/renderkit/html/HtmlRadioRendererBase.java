/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.apache.myfaces.shared.renderkit.RendererUtils;

import javax.faces.component.*;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Thomas Spiegl
 * @version $Revision: 684924 $ $Date: 2008-08-11 23:59:26 +0300 (Pr, 11 Rgp 2008) $
 */
public class HtmlRadioRendererBase
        extends HtmlRenderer
{
    private static final Log log = LogFactory.getLog(HtmlRadioRendererBase.class);

    private static final String PAGE_DIRECTION = "pageDirection";
    private static final String LINE_DIRECTION = "lineDirection";

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, uiComponent, UISelectOne.class);

        UISelectOne selectOne = (UISelectOne)uiComponent;

        String layout = getLayout(selectOne);

        boolean pageDirectionLayout = false; // Defaults to LINE_DIRECTION
        if (layout != null)
        {
            if (layout.equals(PAGE_DIRECTION))
            {
                pageDirectionLayout = true;
            }
            else if (layout.equals(LINE_DIRECTION))
            {
                pageDirectionLayout = false;
            }
            else
            {
                log.error("Wrong layout attribute for component " + selectOne.getClientId(facesContext) + ": " + layout);
            }
        }

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.TABLE_ELEM, selectOne);
        HtmlRendererUtils.renderHTMLAttributes(writer, selectOne,
                                               HTML.SELECT_TABLE_PASSTHROUGH_ATTRIBUTES);
        HtmlRendererUtils.writeIdIfNecessary(writer, uiComponent, facesContext);

        if (!pageDirectionLayout) writer.startElement(HTML.TR_ELEM, selectOne);

        Converter converter;
        List selectItemList = org.apache.myfaces.shared.renderkit.RendererUtils.getSelectItemList(selectOne);
        converter = HtmlRendererUtils.findUIOutputConverterFailSafe(facesContext, selectOne);

        Object currentValue = org.apache.myfaces.shared.renderkit.RendererUtils.getObjectValue(selectOne);

        currentValue = org.apache.myfaces.shared.renderkit.RendererUtils.getConvertedStringValue(facesContext, selectOne, converter, currentValue);

        int itemNum = 0;

        for (Iterator it = selectItemList.iterator(); it.hasNext(); )
        {
            SelectItem selectItem = (SelectItem)it.next();

            renderGroupOrItemRadio(facesContext, selectOne,
                                   selectItem, currentValue,
                                   converter, pageDirectionLayout, new Integer(itemNum));

            itemNum++;
        }

        if (!pageDirectionLayout) writer.endElement(HTML.TR_ELEM);
        writer.endElement(HTML.TABLE_ELEM);
    }


    protected String getLayout(UIComponent selectOne)
    {
        if (selectOne instanceof HtmlSelectOneRadio)
        {
            return ((HtmlSelectOneRadio)selectOne).getLayout();
        }

        return (String)selectOne.getAttributes().get(JSFAttr.LAYOUT_ATTR);
    }


    protected String getStyleClass(UISelectOne selectOne)
     {
         if (selectOne instanceof HtmlSelectOneRadio)
         {
             return ((HtmlSelectOneRadio)selectOne).getStyleClass();
         }

         return (String)selectOne.getAttributes().get(JSFAttr.STYLE_CLASS_ATTR);
     }


    protected void renderGroupOrItemRadio(FacesContext facesContext,
                                          UIComponent uiComponent, SelectItem selectItem,
                                          Object currentValue,
                                          Converter converter, boolean pageDirectionLayout,
                                          Integer itemNum) throws IOException{

        ResponseWriter writer = facesContext.getResponseWriter();

        boolean isSelectItemGroup = (selectItem instanceof SelectItemGroup);

        // TODO : Check here for getSubmittedValue. Look at RendererUtils.getValue
        // this is useless object creation
//        Object itemValue = selectItem.getValue();

        UISelectOne selectOne = (UISelectOne)uiComponent;

        if (isSelectItemGroup) {
            if (pageDirectionLayout)
                writer.startElement(HTML.TR_ELEM, selectOne);

            writer.startElement(HTML.TD_ELEM, selectOne);
            writer.write(selectItem.getLabel());
            writer.endElement(HTML.TD_ELEM);

            if (pageDirectionLayout) {
                writer.endElement(HTML.TR_ELEM);
                writer.startElement(HTML.TR_ELEM, selectOne);
            }
            writer.startElement(HTML.TD_ELEM, selectOne);

            writer.startElement(HTML.TABLE_ELEM, selectOne);
            writer.writeAttribute(HTML.BORDER_ATTR, "0", null);

            SelectItemGroup group = (SelectItemGroup) selectItem;
            SelectItem[] selectItems = group.getSelectItems();

            for (int i=0; i<selectItems.length; i++) {
                renderGroupOrItemRadio(facesContext, selectOne, selectItems[i], currentValue, converter,
                        pageDirectionLayout, new Integer(i));
            }

            writer.endElement(HTML.TD_ELEM);
            writer.endElement(HTML.TR_ELEM);
            writer.endElement(HTML.TABLE_ELEM);
            writer.endElement(HTML.TD_ELEM);

            if (pageDirectionLayout)
                writer.endElement(HTML.TR_ELEM);

        } else {

        writer.write("\t\t");
        if (pageDirectionLayout)
            writer.startElement(HTML.TR_ELEM, selectOne);
        writer.startElement(HTML.TD_ELEM, selectOne);

        String itemStrValue = org.apache.myfaces.shared.renderkit.RendererUtils.getConvertedStringValue(facesContext, selectOne, converter, selectItem.getValue());
        boolean itemDisabled = selectItem.isDisabled();

        boolean itemChecked = itemStrValue.equals(currentValue);

        String itemId = renderRadio(facesContext, selectOne, itemStrValue, itemDisabled, itemChecked, false, itemNum);

        // label element after the input
        boolean componentDisabled = isDisabled(facesContext, selectOne);
        boolean disabled = (componentDisabled || itemDisabled);

        HtmlRendererUtils.renderLabel(writer, selectOne, itemId, selectItem, disabled);

        writer.endElement(HTML.TD_ELEM);
        if (pageDirectionLayout)
            writer.endElement(HTML.TR_ELEM);
        }
    }

    @Deprecated
    protected void renderRadio(FacesContext facesContext,
                               UIComponent uiComponent,
                               String value,
                               String label,
                               boolean disabled,
                               boolean checked, boolean renderId)
            throws IOException
    {
        renderRadio(facesContext, (UIInput) uiComponent, value, disabled, checked, renderId, 0);
    }

    /**
     * Renders the input item
     * @return the 'id' value of the rendered element
     */
    protected String renderRadio(FacesContext facesContext,
                               UIInput uiComponent,
                               String value,
                               boolean disabled,
                               boolean checked,
                               boolean renderId,
                               Integer itemNum)
            throws IOException
    {
        String clientId = uiComponent.getClientId(facesContext);

        String itemId = (itemNum == null)? null : clientId + NamingContainer.SEPARATOR_CHAR + itemNum;

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.INPUT_ELEM, uiComponent);

        if (itemId != null)
        {
            writer.writeAttribute(HTML.ID_ATTR, itemId, null);
        }
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_RADIO, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);

        if (disabled) {
            writer.writeAttribute(HTML.DISABLED_ATTR, HTML.DISABLED_ATTR, null);
        }

        if (renderId) {
            writer.writeAttribute(HTML.ID_ATTR, clientId, null);
        }

        if (checked)
        {
            writer.writeAttribute(HTML.CHECKED_ATTR, HTML.CHECKED_ATTR, null);
        }

        if (value != null)
        {
            writer.writeAttribute(HTML.VALUE_ATTR, value, null);
        }

        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_STYLE);
        if (isDisabled(facesContext, uiComponent))
        {
            writer.writeAttribute(org.apache.myfaces.shared.renderkit.html.HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }

        writer.endElement(HTML.INPUT_ELEM);

        return itemId;
    }


    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        //TODO: overwrite in extended HtmlRadioRenderer and check for enabledOnUserRole
        if (uiComponent instanceof HtmlSelectOneRadio)
        {
            return ((HtmlSelectOneRadio)uiComponent).isDisabled();
        }

        return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(uiComponent, HTML.DISABLED_ATTR, false);
    }


    public void decode(FacesContext facesContext, UIComponent uiComponent)
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, uiComponent, null);
        if (uiComponent instanceof UIInput)
        {
            HtmlRendererUtils.decodeUISelectOne(facesContext, uiComponent);
        }
    }


    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue) throws ConverterException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UISelectOne.class);
        return org.apache.myfaces.shared.renderkit.RendererUtils.getConvertedUISelectOneValue(facesContext,
                                                                                               (UISelectOne)uiComponent,
                                                                                               submittedValue);
    }

}
