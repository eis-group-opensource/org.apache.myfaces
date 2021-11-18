/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared.renderkit.JSFAttr;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.UISelectMany;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @author Anton Koinov
 * @version $Revision: 684924 $ $Date: 2008-08-11 23:59:26 +0300 (Pr, 11 Rgp 2008) $
 */
public class HtmlCheckboxRendererBase extends HtmlRenderer {
    private static final Log log = LogFactory
            .getLog(HtmlCheckboxRendererBase.class);

    private static final String PAGE_DIRECTION = "pageDirection";

    private static final String LINE_DIRECTION = "lineDirection";

    private static final String EXTERNAL_TRUE_VALUE = "true";

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, uiComponent, null);
        if (uiComponent instanceof UISelectBoolean) {
            Boolean value = org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanValue( uiComponent );
            boolean isChecked = value != null ? value.booleanValue() : false;
            renderCheckbox(facesContext, uiComponent, EXTERNAL_TRUE_VALUE, false,isChecked, true, null); //TODO: the selectBoolean is never disabled
        } else if (uiComponent instanceof UISelectMany) {
            renderCheckboxList(facesContext, (UISelectMany) uiComponent);
        } else {
            throw new IllegalArgumentException("Unsupported component class "
                    + uiComponent.getClass().getName());
        }
    }

    public void renderCheckboxList(FacesContext facesContext,
            UISelectMany selectMany) throws IOException {

        String layout = getLayout(selectMany);
        boolean pageDirectionLayout = false; //Default to lineDirection
        if (layout != null) {
            if (layout.equals(PAGE_DIRECTION)) {
                pageDirectionLayout = true;
            } else if (layout.equals(LINE_DIRECTION)) {
                pageDirectionLayout = false;
            } else {
                log.error("Wrong layout attribute for component "
                        + selectMany.getClientId(facesContext) + ": " + layout);
            }
        }

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.TABLE_ELEM, selectMany);
        HtmlRendererUtils.renderHTMLAttributes(writer, selectMany,
                HTML.SELECT_TABLE_PASSTHROUGH_ATTRIBUTES);
        HtmlRendererUtils.writeIdIfNecessary(writer, selectMany, facesContext);

        if (!pageDirectionLayout)
            writer.startElement(HTML.TR_ELEM, selectMany);
        
        Converter converter;
        try {
            converter = org.apache.myfaces.shared.renderkit.RendererUtils.findUISelectManyConverter(facesContext,
                    selectMany);
        } catch (FacesException e) {
            log.error("Error finding Converter for component with id "
                    + selectMany.getClientId(facesContext));
            converter = null;
        }

        Set lookupSet = org.apache.myfaces.shared.renderkit.RendererUtils.getSubmittedValuesAsSet(facesContext, selectMany, converter, selectMany);
        boolean useSubmittedValues = lookupSet != null;

        if (!useSubmittedValues) {
            lookupSet = org.apache.myfaces.shared.renderkit.RendererUtils.getSelectedValuesAsSet(facesContext, selectMany, converter, selectMany);
        }

        int itemNum = 0;

        for (Iterator it = org.apache.myfaces.shared.renderkit.RendererUtils.getSelectItemList(selectMany)
                .iterator(); it.hasNext();) {
            SelectItem selectItem = (SelectItem) it.next();
            
            renderGroupOrItemCheckbox(facesContext, selectMany, 
                    selectItem, useSubmittedValues, lookupSet, 
                    converter, pageDirectionLayout, new Integer(itemNum));

            itemNum++;
        }

        if (!pageDirectionLayout)
            writer.endElement(HTML.TR_ELEM);
        writer.endElement(HTML.TABLE_ELEM);
    }

    protected String getLayout(UISelectMany selectMany) {
        if (selectMany instanceof HtmlSelectManyCheckbox) {
            return ((HtmlSelectManyCheckbox) selectMany).getLayout();
        } 
        
        return (String) selectMany.getAttributes().get(JSFAttr.LAYOUT_ATTR);
    }
    
    protected void renderGroupOrItemCheckbox(FacesContext facesContext,
                                             UIComponent uiComponent, SelectItem selectItem,
                                             boolean useSubmittedValues, Set lookupSet,
                                             Converter converter, boolean pageDirectionLayout, Integer itemNum) throws IOException
    {

        ResponseWriter writer = facesContext.getResponseWriter();

        boolean isSelectItemGroup = (selectItem instanceof SelectItemGroup);

        Object itemValue = selectItem.getValue(); // TODO : Check here for getSubmittedValue. Look at RendererUtils.getValue

        UISelectMany selectMany = (UISelectMany) uiComponent;

        String itemStrValue = org.apache.myfaces.shared.renderkit.RendererUtils.getConvertedStringValue(facesContext, selectMany, converter, itemValue);

        if (isSelectItemGroup)
        {
            if (pageDirectionLayout)
                writer.startElement(HTML.TR_ELEM, selectMany);

            writer.startElement(HTML.TD_ELEM, selectMany);
            writer.write(selectItem.getLabel());
            writer.endElement(HTML.TD_ELEM);

            if (pageDirectionLayout)
            {
                writer.endElement(HTML.TR_ELEM);
                writer.startElement(HTML.TR_ELEM, selectMany);
            }
            writer.startElement(HTML.TD_ELEM, selectMany);

            writer.startElement(HTML.TABLE_ELEM, selectMany);
            writer.writeAttribute(HTML.BORDER_ATTR, "0", null);

            SelectItemGroup group = (SelectItemGroup) selectItem;
            SelectItem[] selectItems = group.getSelectItems();

            for (int i = 0; i < selectItems.length; i++)
            {
                renderGroupOrItemCheckbox(facesContext, selectMany, selectItems[i], useSubmittedValues, lookupSet, converter, pageDirectionLayout, new Integer(i));
            }

            writer.endElement(HTML.TD_ELEM);
            writer.endElement(HTML.TR_ELEM);
            writer.endElement(HTML.TABLE_ELEM);
            writer.endElement(HTML.TD_ELEM);

            if (pageDirectionLayout)
                writer.endElement(HTML.TR_ELEM);

        }
        else
        {

            writer.write("\t\t");
            if (pageDirectionLayout)
                writer.startElement(HTML.TR_ELEM, selectMany);
            writer.startElement(HTML.TD_ELEM, selectMany);

            boolean checked = lookupSet.contains(itemStrValue);

            boolean disabled = selectItem.isDisabled();

            String itemId = renderCheckbox(facesContext, selectMany, itemStrValue, disabled, checked, false, itemNum);

            // label element after the input
            boolean componentDisabled = isDisabled(facesContext, selectMany);
            boolean itemDisabled = (componentDisabled || disabled);

            HtmlRendererUtils.renderLabel(writer, selectMany, itemId, selectItem, itemDisabled);

            writer.endElement(HTML.TD_ELEM);
            if (pageDirectionLayout)
                writer.endElement(HTML.TR_ELEM);
        }
    }

    @Deprecated
    protected void renderCheckbox(FacesContext facesContext,
            UIComponent uiComponent, String value, String label,
            boolean disabled, boolean checked, boolean renderId) throws IOException {
        renderCheckbox(facesContext, uiComponent, value, disabled, checked, renderId, 0);
    }

    /**
     * Renders the input item
     * @return the 'id' value of the rendered element
     */
    protected String renderCheckbox(FacesContext facesContext,
            UIComponent uiComponent, String value, boolean disabled, boolean checked, boolean renderId, Integer itemNum) throws IOException {
        String clientId = uiComponent.getClientId(facesContext);

        String itemId = (itemNum == null)? null : clientId + NamingContainer.SEPARATOR_CHAR + itemNum;

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.INPUT_ELEM, uiComponent);

        if (itemId != null)
        {
            writer.writeAttribute(HTML.ID_ATTR, itemId, null);
        }
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_CHECKBOX, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);
        
        if (renderId) {
            HtmlRendererUtils.writeIdIfNecessary(writer, uiComponent, facesContext);
        }

        if (checked) {
            writer.writeAttribute(HTML.CHECKED_ATTR, org.apache.myfaces.shared.renderkit.html.HTML.CHECKED_ATTR, null);
        }
        
        if (disabled) {
            writer.writeAttribute(HTML.DISABLED_ATTR, HTML.DISABLED_ATTR, null);
        }

        if ((value != null) && (value.length() > 0)) {
            writer.writeAttribute(HTML.VALUE_ATTR, value, null);
        }

        if (uiComponent instanceof UISelectBoolean)
        {
            HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
                HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        }
        else
        {
            HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
                HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_STYLE);
        }
        if (isDisabled(facesContext, uiComponent)) {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }
        
        writer.endElement(HTML.INPUT_ELEM);

        return itemId;
    }

    protected boolean isDisabled(FacesContext facesContext,
            UIComponent component) {
        //TODO: overwrite in extended HtmlCheckboxRenderer and check for
        // enabledOnUserRole
        if (component instanceof HtmlSelectBooleanCheckbox) {
            return ((HtmlSelectBooleanCheckbox) component).isDisabled();
        } else if (component instanceof HtmlSelectManyCheckbox) {
            return ((HtmlSelectManyCheckbox) component).isDisabled();
        } else {
            return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(component,
                    HTML.DISABLED_ATTR, false);
        }
    }

    public void decode(FacesContext facesContext, UIComponent component) {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, component, null);
        if (component instanceof UISelectBoolean) {
            HtmlRendererUtils.decodeUISelectBoolean(facesContext, component);
        } else if (component instanceof UISelectMany) {
            HtmlRendererUtils.decodeUISelectMany(facesContext, component);
        } else {
            throw new IllegalArgumentException("Unsupported component class "
                    + component.getClass().getName());
        }
    }

    public Object getConvertedValue(FacesContext facesContext,
            UIComponent component, Object submittedValue)
            throws ConverterException {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, component, null);
        if (component instanceof UISelectBoolean) {
            return submittedValue;
        } else if (component instanceof UISelectMany) {
            return org.apache.myfaces.shared.renderkit.RendererUtils.getConvertedUISelectManyValue(facesContext,
                    (UISelectMany) component, submittedValue);
        } else {
            throw new IllegalArgumentException("Unsupported component class "
                    + component.getClass().getName());
        }
    }
}
