/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * Base class to handle the document family
 *
 * @author Mario Ivankovits (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public abstract class AbstractDocumentRenderer extends Renderer
{
    protected abstract String getHtmlTag();
    protected abstract Class getDocumentClass();

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent,
                getDocumentClass());

        AbstractDocument document = (AbstractDocument) uiComponent;

        ResponseWriter writer = facesContext.getResponseWriter();

        if (document.hasState() && document.isEndState())
        {
            closeTag(facesContext, writer);
        }
        else
        {
            openTag(writer, uiComponent);
        }
    }

    protected void openTag(ResponseWriter writer, UIComponent uiComponent)
        throws IOException
    {
        writer.startElement(getHtmlTag(), uiComponent);
    }

    protected void closeTag(FacesContext facesContext, ResponseWriter writer)
        throws IOException
    {
        writeBeforeEnd(facesContext);
        writer.endElement(getHtmlTag());
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        AbstractDocument document = (AbstractDocument) uiComponent;

        ResponseWriter writer = facesContext.getResponseWriter();

        if (!document.hasState())
        {
            closeTag(facesContext, writer);
        }
    }

    protected void writeBeforeEnd(FacesContext facesContext) throws IOException
    {
    }
}