/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared.renderkit.JSFAttr;

import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;


/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @author Thomas Spiegl
 * @author Anton Koinov
 * @version $Revision$ $Date: 2005-05-11 18:45:06 +0200 (Wed, 11 May 2005) $
 */
public class HtmlImageRendererBase
        extends HtmlRenderer
{
    private static final Log log = LogFactory.getLog(HtmlImageRendererBase.class);

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, uiComponent, UIGraphic.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        String url;
        if (uiComponent instanceof HtmlGraphicImage)
        {
            url = ((HtmlGraphicImage) uiComponent).getUrl();
        }
        else
        {
            url = (String) uiComponent.getAttributes().get(JSFAttr.URL_ATTR);
        }

        writer.startElement(HTML.IMG_ELEM, uiComponent);

        HtmlRendererUtils.writeIdIfNecessary(writer, uiComponent, facesContext);

        if (url != null && url.length() > 0)
        {
            String src = facesContext.getApplication()
                    .getViewHandler().getResourceURL(facesContext, url);
            writer.writeURIAttribute(HTML.SRC_ATTR,
                    facesContext.getExternalContext().encodeResourceURL(src),
                    JSFAttr.VALUE_ATTR);
        }
        else
        {
            if (log.isWarnEnabled()) log.warn("Graphic with id " + uiComponent.getClientId(facesContext) + " has no value (url).");
        }

        /* 
         * Warn the user if the ALT attribute is missing.
         */                
        if (uiComponent.getAttributes().get(HTML.ALT_ATTR) == null) 
        {
            log.warn("ALT attribute is missing for : " + uiComponent.getId());
        }

        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.IMG_PASSTHROUGH_ATTRIBUTES);

        writer.endElement(org.apache.myfaces.shared.renderkit.html.HTML.IMG_ELEM);

    }

}
