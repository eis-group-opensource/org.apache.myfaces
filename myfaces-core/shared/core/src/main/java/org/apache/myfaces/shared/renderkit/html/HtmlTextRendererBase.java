/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared.component.EscapeCapable;
import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.apache.myfaces.shared.renderkit.RendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import java.io.IOException;

/**
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @author Manfred Geiler
 * @version $Revision: 673827 $ $Date: 2008-07-04 00:46:23 +0300 (Pn, 04 Lie 2008) $
 */
public class HtmlTextRendererBase
        extends HtmlRenderer
{
    private static final Log log = LogFactory.getLog(HtmlTextRendererBase.class);

    private static final String AUTOCOMPLETE_VALUE_OFF = "off";

    public void encodeEnd(FacesContext facesContext, UIComponent component)
        throws IOException
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext,component,null);
        
        if (component instanceof UIInput)
        {
            renderInput(facesContext, component);
        }
        else if (component instanceof UIOutput)
        {
            renderOutput(facesContext, component);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported component class " + component.getClass().getName());
        }
    }


    protected static void renderOutput(FacesContext facesContext, UIComponent component)
        throws IOException
    {
        
        String text = org.apache.myfaces.shared.renderkit.RendererUtils.getStringValue(facesContext, component);
        if (log.isDebugEnabled()) log.debug("renderOutput '" + text + "'");
        boolean escape;
        if (component instanceof HtmlOutputText || component instanceof EscapeCapable)
        {
            escape = ((HtmlOutputText)component).isEscape();
        }
        else
        {
            escape = RendererUtils.getBooleanAttribute(component, org.apache.myfaces.shared.renderkit.JSFAttr.ESCAPE_ATTR,
                                                       true); //default is to escape
        }
        renderOutputText(facesContext, component, text, escape);
    }


    public static void renderOutputText(FacesContext facesContext,
                                        UIComponent component,
                                        String text,
                                        boolean escape)
        throws IOException
    {
        if (text != null)
        {
            ResponseWriter writer = facesContext.getResponseWriter();
            boolean span = false;

            if(component.getId()!=null && !component.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
            {
                span = true;

                writer.startElement(HTML.SPAN_ELEM, component);

                HtmlRendererUtils.writeIdIfNecessary(writer, component, facesContext);

                HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.COMMON_PASSTROUGH_ATTRIBUTES);

            }
            else
            {
                span = HtmlRendererUtils.renderHTMLAttributesWithOptionalStartElement(writer,component,
                        HTML.SPAN_ELEM,HTML.COMMON_PASSTROUGH_ATTRIBUTES);
            }

            if (escape)
            {
                if (log.isDebugEnabled()) log.debug("renderOutputText writing '" + text + "'");
                writer.writeText(text, org.apache.myfaces.shared.renderkit.JSFAttr.VALUE_ATTR);
            }
            else
            {
                writer.write(text);
            }

            if(span)
            {
                writer.endElement(org.apache.myfaces.shared.renderkit.html.HTML.SPAN_ELEM);
            }
        }
    }


    protected void renderInput(FacesContext facesContext, UIComponent component)
        throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();

        String clientId = component.getClientId(facesContext);
        String value = org.apache.myfaces.shared.renderkit.RendererUtils.getStringValue(facesContext, component);
        if (log.isDebugEnabled()) log.debug("renderInput '" + value + "'");
        writer.startElement(HTML.INPUT_ELEM, component);
        writer.writeAttribute(HTML.ID_ATTR, clientId, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_TEXT, null);
        if (value != null)
        {
            writer.writeAttribute(HTML.VALUE_ATTR, value, JSFAttr.VALUE_ATTR);
        }

        HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if (isDisabled(facesContext, component))
        {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }

        if (isAutocompleteOff(facesContext, component))
        {
            writer.writeAttribute(HTML.AUTOCOMPLETE_ATTR, AUTOCOMPLETE_VALUE_OFF, HTML.AUTOCOMPLETE_ATTR);
        }

        writer.endElement(HTML.INPUT_ELEM);
    }

    protected boolean isDisabled(FacesContext facesContext, UIComponent component)
    {
        //TODO: overwrite in extended HtmlTextRenderer and check for enabledOnUserRole
        if (component instanceof HtmlInputText)
        {
            return ((HtmlInputText)component).isDisabled();
        }

        return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(component, HTML.DISABLED_ATTR, false);
        
    }

    /**
     * If autocomplete is "on" or not set, do not render it
     */
    protected boolean isAutocompleteOff(FacesContext facesContext, UIComponent component)
    {
        if (component instanceof HtmlInputText)
        {
            String autocomplete = ((HtmlInputText)component).getAutocomplete();
            if (autocomplete != null)
            {
                return autocomplete.equals(AUTOCOMPLETE_VALUE_OFF);
            }
        }

        return false;
    }


    public void decode(FacesContext facesContext, UIComponent component)
    {
        RendererUtils.checkParamValidity(facesContext,component,null);

        if (component instanceof UIInput)
        {
            HtmlRendererUtils.decodeUIInput(facesContext, component);
        }
        else if (component instanceof UIOutput)
        {
            //nothing to decode
        }
        else
        {
            throw new IllegalArgumentException("Unsupported component class " + component.getClass().getName());
        }
    }


    public Object getConvertedValue(FacesContext facesContext, UIComponent component, Object submittedValue) throws ConverterException
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, component, UIOutput.class);
        return RendererUtils.getConvertedUIOutputValue(facesContext,
                                                       (UIOutput)component,
                                                       submittedValue);
    }

}
