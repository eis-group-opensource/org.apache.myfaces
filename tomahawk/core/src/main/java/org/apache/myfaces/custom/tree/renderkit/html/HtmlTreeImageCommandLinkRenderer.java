/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.renderkit.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.tree.HtmlTreeImageCommandLink;
import org.apache.myfaces.custom.tree.HtmlTreeNode;
import org.apache.myfaces.renderkit.html.jsf.ExtendedHtmlLinkRenderer;
import org.apache.myfaces.renderkit.html.util.DummyFormUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;


/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "org.apache.myfaces.HtmlTree"
 *   type = "org.apache.myfaces.HtmlTreeImageCommandLink"
 * 
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 659874 $ $Date: 2008-05-24 23:59:15 +0300 (Sat, 24 May 2008) $
 */
public class HtmlTreeImageCommandLinkRenderer
    extends ExtendedHtmlLinkRenderer {

    private static final Integer ZERO = new Integer(0);


    public void decode(FacesContext facesContext, UIComponent component) {
        super.decode(facesContext, component);
        String clientId = component.getClientId(facesContext);
        String reqValue = (String) facesContext
            .getExternalContext()
            .getRequestParameterMap().get(HtmlRendererUtils
            .getHiddenCommandLinkFieldName(DummyFormUtils.findNestingForm(component, facesContext)));
        if (reqValue != null && reqValue.equals(clientId)) {
            HtmlTreeNode node = (HtmlTreeNode) component.getParent();

            node.toggleExpanded();
        }
    }


    protected void renderCommandLinkStart(FacesContext facesContext,
                                          UIComponent component,
                                          String clientId,
                                          Object value,
                                          String style,
                                          String styleClass) throws IOException {

        super.renderCommandLinkStart(facesContext, component, clientId, value, style, styleClass);

        String url = ((HtmlTreeImageCommandLink) component).getImage();

        if ((url != null) && (url.length() > 0)) {
            ResponseWriter writer = facesContext.getResponseWriter();
            writer.startElement(HTML.IMG_ELEM, component);
            String src = facesContext.getApplication().getViewHandler().getResourceURL(facesContext, url);
            //String src;
            //if (url.startsWith(HTML.HREF_PATH_SEPARATOR))
            //{
            //    String path = facesContext.getExternalContext().getRequestContextPath();
            //    src = path + url;
            //}
            //else
            //{
            //    src = url;
            //}
            //Encode URL
            //Although this is an url url, encodeURL is no nonsense, because the
            //actual url url could also be a dynamic servlet request:
            //src = facesContext.getExternalContext().encodeResourceURL(src);
            writer.writeAttribute(HTML.SRC_ATTR, src, null);
            writer.writeAttribute(HTML.BORDER_ATTR, ZERO, null);

            HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.IMG_PASSTHROUGH_ATTRIBUTES);

            writer.endElement(HTML.IMG_ELEM);
        }
    }
}
