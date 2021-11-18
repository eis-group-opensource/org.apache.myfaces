/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.shared_impl.component.EscapeCapable;
import org.apache.myfaces.shared_impl.renderkit.JSFAttr;
import org.apache.myfaces.shared_impl.renderkit.RendererUtils;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlRendererUtils;


/**
 *   
 * @author Thomas Spiegl (latest modification by $Author: jakobk $)
 * @author Anton Koinov
 * @author Martin Marinschek
 * @version $Revision: 954252 $ $Date: 2010-06-13 18:56:59 +0300 (Sun, 13 Jun 2010) $
 */
@JSFRenderer(
    renderKitId="HTML_BASIC",
    family="javax.faces.Output",
    type="javax.faces.Label")
public class HtmlLabelRenderer
extends HtmlRenderer
{
    private static final Log log = LogFactory.getLog(HtmlLabelRenderer.class);

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        super.encodeBegin(facesContext, uiComponent);   //check for NP

        ResponseWriter writer = facesContext.getResponseWriter();

        encodeBefore(facesContext, writer, uiComponent);

        writer.startElement(HTML.LABEL_ELEM, uiComponent);
        HtmlRendererUtils.writeIdIfNecessary(writer, uiComponent, facesContext);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.LABEL_PASSTHROUGH_ATTRIBUTES);

        String forAttr = getFor(uiComponent);

        if (forAttr != null)
        {
          writer.writeAttribute(HTML.FOR_ATTR,
                                getClientId(facesContext, uiComponent, forAttr), JSFAttr.FOR_ATTR);
        }
        else
        {
            if (log.isWarnEnabled()) {
                log.warn("Attribute 'for' of label component with id " + uiComponent.getClientId(facesContext)+" is not defined");
            }
        }


        //MyFaces extension: Render a label text given by value
        //TODO: Move to extended component
        if (uiComponent instanceof ValueHolder)
        {
            String text = RendererUtils.getStringValue(facesContext, uiComponent);
            if (text != null)
            {
                boolean escape;
                if (uiComponent instanceof HtmlOutputLabel || uiComponent instanceof EscapeCapable)
                {
                    escape = ((HtmlOutputLabel)uiComponent).isEscape();
                }
                else
                {
                    escape = RendererUtils.getBooleanAttribute(uiComponent, org.apache.myfaces.shared_impl.renderkit.JSFAttr.ESCAPE_ATTR,
                                                               true); //default is to escape
                }                
                if (escape)
                {
                    writer.writeText(text, org.apache.myfaces.shared_impl.renderkit.JSFAttr.VALUE_ATTR);
                }
                else
                {
                    writer.write(text);
                }
            }
        }

        writer.flush(); // close start tag

        encodeAfterStart(facesContext,writer,uiComponent);
    }

    protected void encodeAfterStart(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent)
        throws IOException
    {
    }

    protected void encodeBefore(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent)
        throws IOException
    {
    }


    protected String getFor(UIComponent component)
    {
        if (component instanceof HtmlOutputLabel)
        {
            return ((HtmlOutputLabel)component).getFor();
        }
        
        return (String) component.getAttributes().get(JSFAttr.FOR_ATTR);
        
    }

    protected String getClientId(FacesContext facesContext,
                                 UIComponent uiComponent, String forAttr)
    {
        return RendererUtils.getClientId(facesContext, uiComponent, forAttr);
    }


    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        super.encodeEnd(facesContext, uiComponent); //check for NP

        ResponseWriter writer = facesContext.getResponseWriter();

        encodeBeforeEnd(facesContext, writer, uiComponent);

        writer.endElement(HTML.LABEL_ELEM);

        encodeAfter(facesContext, writer, uiComponent);
    }

    protected void encodeBeforeEnd(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent)
        throws IOException
    {
    }

    protected void encodeAfter(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent)
        throws IOException
    {
    }
}
