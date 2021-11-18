/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import org.apache.myfaces.shared.config.MyfacesConfig;
import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.apache.myfaces.shared.renderkit.RendererUtils;
import org.apache.myfaces.shared.renderkit.html.util.FormInfo;
import org.apache.myfaces.shared.renderkit.html.util.JavascriptUtils;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.util.Map;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @author Thomas Spiegl
 * @author Anton Koinov
 * @version $Revision: 673827 $ $Date: 2008-07-04 00:46:23 +0300 (Pn, 04 Lie 2008) $
 */
public class HtmlButtonRendererBase
    extends HtmlRenderer
{
    private static final String IMAGE_BUTTON_SUFFIX_X = ".x";
    private static final String IMAGE_BUTTON_SUFFIX_Y = ".y";

    public static final String ACTION_FOR_LIST = "org.apache.myfaces.ActionForList";

    public void decode(FacesContext facesContext, UIComponent uiComponent)
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, uiComponent, UICommand.class);

        //super.decode must not be called, because value is handled here
        if (!isReset(uiComponent) && isSubmitted(facesContext, uiComponent))
        {
            uiComponent.queueEvent(new ActionEvent(uiComponent));

            org.apache.myfaces.shared.renderkit.RendererUtils.initPartialValidationAndModelUpdate(uiComponent, facesContext);
        }
    }

    private static boolean isReset(UIComponent uiComponent)
    {
        return "reset".equals((String) uiComponent.getAttributes().get(HTML.TYPE_ATTR));
    }

    private static boolean isSubmitted(FacesContext facesContext, UIComponent uiComponent)
    {
        String clientId = uiComponent.getClientId(facesContext);
        Map paramMap = facesContext.getExternalContext().getRequestParameterMap();
        return paramMap.containsKey(clientId) || paramMap.containsKey(clientId + IMAGE_BUTTON_SUFFIX_X) || paramMap.containsKey(clientId + IMAGE_BUTTON_SUFFIX_Y);
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, uiComponent, UICommand.class);

        String clientId = uiComponent.getClientId(facesContext);

        ResponseWriter writer = facesContext.getResponseWriter();
        
        // If we have javascript enabled, and autoscroll is enabled, 
        // we should write the form submit script
        // (define oamSetHiddenInput, oamClearHiddenInput, oamSubmitForm)
        // because oamSetHiddenInput is called on onclick function
        if (JavascriptUtils.isJavascriptAllowed(facesContext.getExternalContext()))
        {        
            if (MyfacesConfig.getCurrentInstance(facesContext.getExternalContext()).isAutoScroll()) {
                HtmlRendererUtils.renderFormSubmitScript(facesContext);
            }
        }

        writer.startElement(HTML.INPUT_ELEM, uiComponent);

        writer.writeAttribute(HTML.ID_ATTR, clientId, org.apache.myfaces.shared.renderkit.JSFAttr.ID_ATTR);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, JSFAttr.ID_ATTR);

        String image = getImage(uiComponent);

        ExternalContext externalContext = facesContext.getExternalContext();

        if (image != null)
        {
            writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_IMAGE, org.apache.myfaces.shared.renderkit.JSFAttr.TYPE_ATTR);
            String src = facesContext.getApplication().getViewHandler().getResourceURL(
                    facesContext, image);
            writer.writeURIAttribute(HTML.SRC_ATTR, externalContext.encodeResourceURL(src),
                                     org.apache.myfaces.shared.renderkit.JSFAttr.IMAGE_ATTR);
        }
        else
        {
            String type = getType(uiComponent);

            if (type == null || !isReset(uiComponent))
            {
                type = HTML.INPUT_TYPE_SUBMIT;
            }
            writer.writeAttribute(HTML.TYPE_ATTR, type, org.apache.myfaces.shared.renderkit.JSFAttr.TYPE_ATTR);
            Object value = getValue(uiComponent);
            if (value != null)
            {
                writer.writeAttribute(org.apache.myfaces.shared.renderkit.html.HTML.VALUE_ATTR, value, org.apache.myfaces.shared.renderkit.JSFAttr.VALUE_ATTR);
            }
        }
        if (JavascriptUtils.isJavascriptAllowed(externalContext))
        {
            StringBuffer onClick = buildOnClick(uiComponent, facesContext, writer);
            if (onClick.length() != 0){
                writer.writeAttribute(HTML.ONCLICK_ATTR, onClick.toString(), null);
            }
            HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
                                                   HTML.BUTTON_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_ONCLICK);
        }
        else
        {
            HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
                                                   HTML.BUTTON_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        }

        if (isDisabled(facesContext, uiComponent))
        {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, org.apache.myfaces.shared.renderkit.JSFAttr.DISABLED_ATTR);
        }
        
        if (isReadonly(facesContext, uiComponent))
        {
            writer.writeAttribute(HTML.READONLY_ATTR, Boolean.TRUE, org.apache.myfaces.shared.renderkit.JSFAttr.READONLY_ATTR);
        }

        writer.endElement(HTML.INPUT_ELEM);
        
        HtmlFormRendererBase.renderScrollHiddenInputIfNecessary(
            findNestingForm(uiComponent, facesContext).getForm(), facesContext, writer);
    }


    protected StringBuffer buildOnClick(UIComponent uiComponent, FacesContext facesContext, ResponseWriter writer)
        throws IOException
    {
        /* DUMMY STUFF
        //Find form
        UIComponent parent = uiComponent.getParent();
        while (parent != null && !(parent instanceof UIForm))
        {
            parent = parent.getParent();
        }

        UIForm nestingForm = null;
        String formName;

        if (parent != null)
        {
            //link is nested inside a form
            nestingForm = (UIForm)parent;
            formName = nestingForm.getClientId(facesContext);

        }
        else
        {
            //not nested in form, we must add a dummy form at the end of the document
            formName = DummyFormUtils.DUMMY_FORM_NAME;
            //dummyFormResponseWriter = DummyFormUtils.getDummyFormResponseWriter(facesContext);
            //dummyFormResponseWriter.setWriteDummyForm(true);
            DummyFormUtils.setWriteDummyForm(facesContext, true);
        }
        */
        FormInfo formInfo = findNestingForm(uiComponent, facesContext);
        if (formInfo == null)
        {
            throw new IllegalArgumentException("Component " + uiComponent.getClientId(facesContext) + " must be embedded in an form");
        }
        String formName = formInfo.getFormName();
        UIComponent nestingForm = formInfo.getForm();
        
        StringBuffer onClick = new StringBuffer();
        String commandOnClick = (String)uiComponent.getAttributes().get(HTML.ONCLICK_ATTR);

        if (commandOnClick != null)
        {
            onClick.append(commandOnClick);
            onClick.append(';');
        }

        if (JavascriptUtils.isRenderClearJavascriptOnButton(facesContext.getExternalContext()))
        {
            //call the script to clear the form (clearFormHiddenParams_<formName>) method
            HtmlRendererUtils.appendClearHiddenCommandFormParamsFunctionCall(onClick, formName);
        }

        if (MyfacesConfig.getCurrentInstance(facesContext.getExternalContext()).isAutoScroll()) {
            HtmlRendererUtils.appendAutoScrollAssignment(onClick, formName);
        }

        //The hidden field has only sense if isRenderClearJavascriptOnButton is
        //set to true. In other case, this hidden field should not be rendered.
        //if (JavascriptUtils.isRenderClearJavascriptOnButton(facesContext.getExternalContext()))
        //{
            //add hidden field for the case there is no commandLink in the form
            //String hiddenFieldName = HtmlRendererUtils.getHiddenCommandLinkFieldName(formInfo);
            //addHiddenCommandParameter(facesContext, nestingForm, hiddenFieldName);
        //}

        return onClick;
    }

    protected void addHiddenCommandParameter(FacesContext facesContext, UIComponent nestingForm, String hiddenFieldName)
    {
        if (nestingForm != null)
        {
            HtmlFormRendererBase.addHiddenCommandParameter(facesContext, nestingForm, hiddenFieldName);
        }
    }

    /**
     * find nesting form<br />
     * need to be overrideable to deal with dummyForm stuff in tomahawk.
     */
    protected FormInfo findNestingForm(UIComponent uiComponent, FacesContext facesContext)
    {
        return RendererUtils.findNestingForm(uiComponent, facesContext);
    }

    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        //TODO: overwrite in extended HtmlButtonRenderer and check for enabledOnUserRole
        if (uiComponent instanceof HtmlCommandButton)
        {
            return ((HtmlCommandButton)uiComponent).isDisabled();
        }

        return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(uiComponent, HTML.DISABLED_ATTR, false);
        
    }

    protected boolean isReadonly(FacesContext facesContext, UIComponent uiComponent)
    {
        if (uiComponent instanceof HtmlCommandButton)
        {
            return ((HtmlCommandButton)uiComponent).isReadonly();
        }
        return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(uiComponent, HTML.READONLY_ATTR, false);
    }

    private String getImage(UIComponent uiComponent)
    {
        if (uiComponent instanceof HtmlCommandButton)
        {
            return ((HtmlCommandButton)uiComponent).getImage();
        }
        return (String)uiComponent.getAttributes().get(JSFAttr.IMAGE_ATTR);
    }

    private String getType(UIComponent uiComponent)
    {
        if (uiComponent instanceof HtmlCommandButton)
        {
            return ((HtmlCommandButton)uiComponent).getType();
        }
        return (String)uiComponent.getAttributes().get(org.apache.myfaces.shared.renderkit.JSFAttr.TYPE_ATTR);
    }

    private Object getValue(UIComponent uiComponent)
    {
        if (uiComponent instanceof ValueHolder)
        {
            return ((ValueHolder)uiComponent).getValue();
        }
        return uiComponent.getAttributes().get(JSFAttr.VALUE_ATTR);
    }
}
