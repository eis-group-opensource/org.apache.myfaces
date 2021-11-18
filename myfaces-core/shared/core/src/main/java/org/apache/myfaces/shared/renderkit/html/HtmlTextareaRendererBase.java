/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import org.apache.myfaces.shared.renderkit.RendererUtils;
import org.apache.myfaces.shared.renderkit.html.HTML;
import org.apache.myfaces.shared.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import java.io.IOException;


/**
 * @author Manfred Geiler (latest modification by $Author: matzew $)
 * @author Anton Koinov
 * @version $Revision: 557350 $ $Date: 2007-07-18 21:19:50 +0300 (Tr, 18 Lie 2007) $
 */
public class HtmlTextareaRendererBase
        extends HtmlRenderer
{
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIInput.class);

        encodeTextArea(facesContext, uiComponent);

    }

    protected void encodeTextArea(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML.TEXTAREA_ELEM, uiComponent);

        String clientId = uiComponent.getClientId(facesContext);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);
        HtmlRendererUtils.writeIdIfNecessary(writer, uiComponent, facesContext);

        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.TEXTAREA_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if (isDisabled(facesContext, uiComponent))
        {
            writer.writeAttribute(org.apache.myfaces.shared.renderkit.html.HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }

        String strValue = org.apache.myfaces.shared.renderkit.RendererUtils.getStringValue(facesContext, uiComponent);
        writer.writeText(strValue, org.apache.myfaces.shared.renderkit.JSFAttr.VALUE_ATTR);

        writer.endElement(HTML.TEXTAREA_ELEM);
    }

    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        //TODO: overwrite in extended HtmlTextareaRenderer and check for enabledOnUserRole
        if (uiComponent instanceof HtmlInputTextarea)
        {
            return ((HtmlInputTextarea)uiComponent).isDisabled();
        }

        return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(uiComponent, HTML.DISABLED_ATTR, false);
        
    }

    public void decode(FacesContext facesContext, UIComponent component)
    {
        RendererUtils.checkParamValidity(facesContext, component, UIInput.class);
        HtmlRendererUtils.decodeUIInput(facesContext, component);
    }

    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue) throws ConverterException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIOutput.class);
        return org.apache.myfaces.shared.renderkit.RendererUtils.getConvertedUIOutputValue(facesContext,
                                                       (UIOutput)uiComponent,
                                                       submittedValue);
    }

}
