/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;


/**
 * @JSFRenderer
 *   renderKitId="HTML_BASIC"
 *   family="javax.faces.Input"
 *   type="org.apache.myfaces.Hidden"
 * 
 * @since 1.1.7
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @author Anton Koinov
 * @version $Revision: 691871 $ $Date: 2008-09-04 07:32:08 +0300 (Thu, 04 Sep 2008) $
 */
public class HtmlHiddenRenderer
extends HtmlRenderer
{
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
        throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIInput.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.INPUT_ELEM, uiComponent);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_HIDDEN, null);

        String clientId = uiComponent.getClientId(facesContext);
        writer.writeAttribute(HTML.ID_ATTR, clientId, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);

        String value = RendererUtils.getStringValue(facesContext, uiComponent);
        if (value != null)
        {
            writer.writeAttribute(HTML.VALUE_ATTR, value, JSFAttr.VALUE_ATTR);
        }

        writer.endElement(HTML.INPUT_ELEM);
    }

    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue) throws ConverterException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIOutput.class);
        return RendererUtils.getConvertedUIOutputValue(facesContext,
                                                       (UIOutput)uiComponent,
                                                       submittedValue);
    }


    public void decode(FacesContext facesContext, UIComponent component)
     {
         RendererUtils.checkParamValidity(facesContext,component,UIInput.class);

         HtmlRendererUtils.decodeUIInput(facesContext, component);

     }

}
