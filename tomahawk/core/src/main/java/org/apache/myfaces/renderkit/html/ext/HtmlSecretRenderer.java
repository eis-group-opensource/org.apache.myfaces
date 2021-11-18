/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlSecretRendererBase;


/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Input"
 *   type = "org.apache.myfaces.Secret"
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Bruno Aranda
 * @version $Revision: 659874 $ $Date: 2008-05-24 23:59:15 +0300 (Sat, 24 May 2008) $
 */
public class HtmlSecretRenderer
        extends HtmlSecretRendererBase
{
    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        if (!UserRoleUtils.isEnabledOnUserRole(uiComponent))
        {
            return true;
        }
        else
        {
            return super.isDisabled(facesContext, uiComponent);
        }
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
    throws IOException
    {
        if(HtmlRendererUtils.isDisplayValueOnly(component))
        {
            renderInputValueOnly(facesContext,
                                 (UIInput) component);
        }
        else
        {
            super.encodeEnd(facesContext, component);
        }
    }

    protected void renderInputValueOnly(FacesContext facesContext, UIInput uiInput)
        throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.SPAN_ELEM, uiInput);

        HtmlRendererUtils.writeIdIfNecessary(writer, uiInput, facesContext);

        HtmlRendererUtils.renderDisplayValueOnlyAttributes(uiInput, writer);

        // renders five asterisks instead of the component value
        writer.writeText("*****", JSFAttr.VALUE_ATTR);

        writer.endElement(HTML.SPAN_ELEM);
    }
}
