/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.renderkit.html.util.ExtensionsPhaseListener;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * Document to enclose the whole document. If not otherwise possible you can use
 * state="start|end" to demarkate the document boundaries
 *
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Data"
 *   type = "org.apache.myfaces.DocumentBody"
 *
 * @author Mario Ivankovits (latest modification by $Author: lu4242 $)
 * @version $Revision: 943960 $ $Date: 2010-05-13 21:10:45 +0300 (Thu, 13 May 2010) $
 */
public class DocumentBodyRenderer extends AbstractDocumentRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.DocumentBody";
    private String BODY_ELEM = "body";
    private String[] ATTRS = new String[] {"onload", "onunload", "onresize", "onkeypress", "style", "styleClass", "id"};

    protected String getHtmlTag()
    {
        return BODY_ELEM;
    }

    protected Class getDocumentClass()
    {
        return DocumentBody.class;
    }

    protected void openTag(ResponseWriter writer, UIComponent uiComponent)
    throws IOException
    {
        super.openTag(writer, uiComponent);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, ATTRS);
    }

    protected void writeBeforeEnd(FacesContext facesContext) throws IOException
    {
        super.writeBeforeEnd(facesContext);
        
        AddResource addResource = AddResourceFactory.getInstance(facesContext);
        if (!addResource.requiresBuffer())
        {
            // This code is rendered only if this request don't require
            // buffering, because when it is buffered, the buffer is responsible
            // of render it.
            ExtensionsPhaseListener.writeCodeBeforeBodyEnd(facesContext);

            // fake string, so the ExtensionsPhaseListener will not create the javascript again
            facesContext.getExternalContext().getRequestMap().put(ExtensionsPhaseListener.ORG_APACHE_MYFACES_MY_FACES_JAVASCRIPT, "");
        }
    }
}
