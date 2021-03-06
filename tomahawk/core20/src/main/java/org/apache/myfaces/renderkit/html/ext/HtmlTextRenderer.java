/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.apache.myfaces.shared_tomahawk.component.EscapeCapable;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlTextRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.JavascriptUtils;


/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Input"
 *   type = "org.apache.myfaces.Text"
 *   
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.Text"
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2008-05-24 15:59:15 -0500 (24 may 2008) $
 */
public class HtmlTextRenderer
        extends HtmlTextRendererBase
{
    private static final String ONCHANGE_PREFIX = "onChange_";
    private static final String HIDDEN_SUFFIX   = "_hidden";
     
    //private static final Log log = LogFactory.getLog(HtmlTextRenderer.class);
    private static final Logger log = Logger.getLogger(HtmlTextRenderer.class.getName());
    
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
    /**
     *
     */
    protected boolean isDisabledOnClientSide(FacesContext facesContext, UIComponent component)
    {
        if (component instanceof HtmlInputText)
        {
            return ((HtmlInputText)component).isDisabledOnClientSide();
        }
        
        return false;
    }      

    public void encodeEnd(FacesContext facesContext, UIComponent component)
        throws IOException
    {
        if (HtmlRendererUtils.isDisplayValueOnly(component))
        {
            renderInputValueOnly(facesContext, component);
        }
        else
        {
            renderNormal(facesContext, component);
        }
    }
    
    @Override
    protected void renderOutput(FacesContext facesContext, UIComponent component)
        throws IOException
    {
        String text = org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils.getStringValue(facesContext, component);
        if (log.isLoggable(Level.FINE)) log.fine("renderOutput '" + text + "'");
        boolean escape;
        if (component instanceof HtmlOutputText || component instanceof EscapeCapable)
        {
            escape = ((HtmlOutputText)component).isEscape();
        }
        else
        {
            escape = RendererUtils.getBooleanAttribute(component, org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr.ESCAPE_ATTR,
                                                       true); //default is to escape
        }

        if (text != null)
        {
            ResponseWriter writer = facesContext.getResponseWriter();
            boolean span = false;

            Map<String, List<ClientBehavior>> behaviors = null;
            if (component instanceof ClientBehaviorHolder)
            {
                behaviors = ((ClientBehaviorHolder) component).getClientBehaviors();
            }
            
            if (behaviors != null && !behaviors.isEmpty())
            {
                span = true;
                writer.startElement(HTML.SPAN_ELEM, component);
                writer.writeAttribute(HTML.ID_ATTR, component.getClientId(facesContext),null);
                HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.UNIVERSAL_ATTRIBUTES);
                HtmlRendererUtils.renderBehaviorizedEventHandlers(facesContext, writer, component, behaviors);
            }
            else
            {
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
            }

            if (escape)
            {
                Logger log = Logger.getLogger(HtmlTextRendererBase.class.getName());
                if (log.isLoggable(Level.FINE)) log.fine("renderOutputText writing '" + text + "'");
                writer.writeText(text, org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr.VALUE_ATTR);
            }
            else
            {
                writer.write(text);
            }

            if(span)
            {
                writer.endElement(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SPAN_ELEM);
            }
        }
    }
    
    @Override
    protected void renderInput(FacesContext facesContext, UIComponent component) throws IOException 
    {
        if (!isDisabledOnClientSide(facesContext, component) || isDisabled(facesContext, component))
        {
            super.renderInput(facesContext, component);
            return;
        }               
        
        //render the input component as if it was disabled
        ResponseWriter writer = facesContext.getResponseWriter();

        String clientId = component.getClientId(facesContext);
        String value = RendererUtils.getStringValue(facesContext, component);

        writer.startElement(HTML.INPUT_ELEM, component);
        writer.writeAttribute(HTML.ID_ATTR, clientId, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_TEXT, null);
        if (value != null)
        {
            writer.writeAttribute(HTML.VALUE_ATTR, value, JSFAttr.VALUE_ATTR);
        }

        HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        //render as disabled on the client side
        writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        //render the JS function that will change the hidden input field's value when his changes        
        writer.writeAttribute(HTML.ONCHANGE_ATTR, getOnChangeFunctionName(clientId)+"();", null);
        
        writer.endElement(HTML.INPUT_ELEM);
        
        //render the hidden input field that will hold this component's value
        renderHiddenInput(facesContext, (UIInput)component);
    }    
    /**
     *
     */
    protected void renderHiddenInput(FacesContext facesContext, UIComponent component) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        
        String disabledInputClientId = component.getClientId(facesContext);
        
        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        
        writer.startElement(HTML.SCRIPT_ELEM, null);
        writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);                        
        writer.writeText(createOnChangeListenerJS(disabledInputClientId), null);        
        writer.endElement(HTML.SCRIPT_ELEM);
        
        HtmlRendererUtils.writePrettyLineSeparator(facesContext);  
        
        writer.startElement(HTML.INPUT_ELEM, null);
        writer.writeAttribute(HTML.ID_ATTR, disabledInputClientId + HIDDEN_SUFFIX, null);
        writer.writeAttribute(HTML.NAME_ATTR, disabledInputClientId + HIDDEN_SUFFIX, null);
        writer.writeAttribute(HTML.VALUE_ATTR, RendererUtils.getStringValue(facesContext, component), null);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_HIDDEN, null);        
        writer.endElement(HTML.INPUT_ELEM);
        
        HtmlRendererUtils.writePrettyLineSeparator(facesContext);       
    }

    protected void renderInputValueOnly(FacesContext facesContext, UIComponent component)
        throws IOException
    {
            HtmlRendererUtils.renderDisplayValueOnly(facesContext,
                                                     (UIInput) component);
    }

    protected void renderNormal(FacesContext facesContext, UIComponent component)
        throws IOException
    {
            super.encodeEnd(facesContext, component);
    }    
    
    /**
     *
     */ 
    protected String getOnChangeFunctionName(String inputDisabledClientId)
    {
        StringBuffer buf = new StringBuffer();
        buf.append(ONCHANGE_PREFIX);
        buf.append(JavascriptUtils.getValidJavascriptName(inputDisabledClientId, true));
        
        return buf.toString();
    }     
    /**
     *
     */
    protected String createOnChangeListenerJS(String inputDisabledClientId)
    {
        StringBuffer script = new StringBuffer();
        script.append("function ");
        script.append(getOnChangeFunctionName(inputDisabledClientId));            
        script.append("() {\n");                
        script.append("var hiddenInput = document.getElementById(\"").append(inputDisabledClientId + HIDDEN_SUFFIX).append("\");\n");
        script.append("var disabledInput = document.getElementById(\"").append(inputDisabledClientId).append("\");\n");
        script.append("hiddenInput.value=disabledInput.value;\n");        
        script.append("}\n");        
        
        return script.toString();
    }

    public void decode(FacesContext facesContext, UIComponent component) 
    {        
        if (isDisabledOnClientSide(facesContext, component) && !isDisabled(facesContext, component))
        {            
            //in this case we don't need to check the request parameters validity, 
            //the submitted value comes from the hidden input field
            Map paramValuesMap = facesContext.getExternalContext().getRequestParameterMap();            
            Object reqValue = paramValuesMap.get(component.getClientId(facesContext) + HIDDEN_SUFFIX);  
                       
            ((UIInput)component).setSubmittedValue(reqValue); 
            return;
        }
        
        if (!(component instanceof UIInput) && (component instanceof UIOutput))
        {
            HtmlRendererUtils.decodeClientBehaviors(facesContext, component);
        }
        super.decode(facesContext, component);        
    }
}
