/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.toggle;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlLinkRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.ClientBehaviorEvents;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * Renderer for component HtmlAjaxChildComboBox
 * 
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.ToggleLink"
 * 
 * @author Sharath Reddy
 */
public class ToggleLinkRenderer extends HtmlLinkRenderer {
    public static final int DEFAULT_MAX_SUGGESTED_ITEMS = 200;

    protected void renderOutputLinkStart(FacesContext facesContext,
            UIOutput output) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
    
        String clientId = output.getClientId(facesContext);
        
        ToggleLink toggleLink = (ToggleLink) output;

        //write anchor
        writer.startElement(HTML.ANCHOR_ELEM, output);
        writer.writeAttribute(HTML.ID_ATTR, clientId, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);
        writer.writeURIAttribute(HTML.HREF_ATTR, "javascript:void(0);", null);

        Map<String, List<ClientBehavior>> behaviors = toggleLink.getClientBehaviors();        
        if (behaviors != null && !behaviors.isEmpty())
        {
            HtmlRendererUtils.renderBehaviorizedEventHandlersWithoutOnclick(facesContext, writer, output, behaviors);
            HtmlRendererUtils.renderBehaviorizedFieldEventHandlersWithoutOnchangeAndOnselect(facesContext, writer, output, behaviors);
            HtmlRendererUtils
                    .renderHTMLAttributes(
                            writer,
                            output,
                            HTML.ANCHOR_PASSTHROUGH_ATTRIBUTES_WITHOUT_STYLE_AND_EVENTS);
            HtmlRendererUtils.renderBehaviorizedAttribute(facesContext, writer, HTML.ONCLICK_ATTR, output,
                    ClientBehaviorEvents.CLICK, null, behaviors, HTML.ONCLICK_ATTR, null, buildOnclickToggleFunction(facesContext,output));
        }
        else
        {
            HtmlRendererUtils
                    .renderHTMLAttributes(
                            writer,
                            output,
                            HTML.ANCHOR_PASSTHROUGH_ATTRIBUTES_WITHOUT_ONCLICK_WITHOUT_STYLE);
            HtmlRendererUtils.renderHTMLAttribute(writer, HTML.ONCLICK_ATTR, HTML.ONCLICK_ATTR, 
                    buildOnclickToggleFunction(facesContext,output));
        }

        HtmlRendererUtils.renderHTMLAttribute(writer, HTML.STYLE_ATTR, HTML.STYLE_ATTR,
                output.getAttributes().get(HTML.STYLE_ATTR));
        HtmlRendererUtils.renderHTMLAttribute(writer, HTML.STYLE_CLASS_ATTR, HTML.STYLE_CLASS_ATTR,
                output.getAttributes().get(HTML.STYLE_CLASS_ATTR));
        
        writer.flush();
    }
    
    protected void renderOutputLinkEnd(FacesContext facesContext, UIComponent component)
        throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        // force separate end tag
        writer.writeText("", null);
        writer.endElement(HTML.ANCHOR_ELEM);
    }
    
    private String buildOnclickToggleFunction(FacesContext facesContext,
            UIOutput output) throws IOException
    {
        ToggleLink toggleLink = (ToggleLink) output;
        TogglePanel togglePanel = getParentTogglePanel(facesContext, toggleLink);
        String[] componentsToToggle = toggleLink.getFor().split(",");
        String idsToHide = getIdsToHide(facesContext, togglePanel);
        StringBuffer idsToShow = new StringBuffer();
        for (int i = 0; i < componentsToToggle.length; i++) {
            String componentId = componentsToToggle[i].trim();
            UIComponent componentToShow = toggleLink.findComponent(componentId);
            if (componentToShow == null) {
                Log log = LogFactory.getLog(ToggleLinkRenderer.class);
                log.error("Unable to find component with id " + componentId);
                continue;
            }
            if( idsToShow.length() > 0 )
                idsToShow.append( ',' );
            idsToShow.append( componentToShow.getClientId(facesContext) );
        }
        
        String outputOnclick = toggleLink.getOnclick();
        StringBuffer onClick = new StringBuffer();
        if(outputOnclick != null)
        {
            onClick.append("var cf = function(){");
            onClick.append(outputOnclick);
            onClick.append('}');
            onClick.append(';');
            onClick.append("var oamSF = function(){");            
        }

        String onClickFocusClientId = toggleLink.getOnClickFocusId() != null ? toggleLink.findComponent(toggleLink.getOnClickFocusId()).getClientId(facesContext) : "";
        onClick.append(getToggleJavascriptFunctionName(facesContext, toggleLink) + "('"+idsToShow+"','" + idsToHide + "','" + getHiddenFieldId(facesContext, togglePanel) + "','" + onClickFocusClientId + "');");
                
        if (outputOnclick != null)
        {
            onClick.append('}');
            onClick.append(';');
            onClick.append("return (cf()==false)? false : oamSF();");        
        }        

        return onClick.toString();
    }
            
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        RendererUtils.checkParamValidity(context, component, ToggleLink.class);

        ToggleLink toggleLink = (ToggleLink) component;
        if(isDisabled(context, toggleLink))
            return;

        super.encodeEnd(context, component);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        RendererUtils.checkParamValidity(context, component, ToggleLink.class);

        ToggleLink toggleLink = (ToggleLink) component;
        if(isDisabled(context, toggleLink))
            return;


        super.encodeBegin(context, component);
    }
    
    private String getToggleJavascriptFunctionName(FacesContext context,ToggleLink toggleLink){
        for(UIComponent component = toggleLink.getParent(); component != null; component = component.getParent())
            if( component instanceof TogglePanel )
                return TogglePanelRenderer.getToggleJavascriptFunctionName( context, (TogglePanel)component );

        Log log = LogFactory.getLog(ToggleLinkRenderer.class);
        log.error("The ToggleLink component with id " + toggleLink.getClientId( context )+" isn't enclosed in a togglePanel.");
        return null;
    }
    
    private String getIdsToHide(FacesContext facesContext, TogglePanel togglePanel) {
         StringBuffer idsToHide = new StringBuffer();
         int idsToHideCount = 0;
         for(Iterator it = togglePanel.getChildren().iterator(); it.hasNext(); ) {
             UIComponent component = (UIComponent) it.next();
             if ( TogglePanelRenderer.isHiddenWhenToggled( component ) ) {
                 if( idsToHideCount > 0 )
                     idsToHide.append( ',' );
                 
                 idsToHide.append( component.getClientId( facesContext ) );
                 idsToHideCount++;
             }
         }
         
         return idsToHide.toString();
    }
    
    private TogglePanel getParentTogglePanel(FacesContext facesContext, ToggleLink toggleLink) {
         for(UIComponent component = toggleLink.getParent(); component != null; component = component.getParent()) {
             if( component instanceof TogglePanel )
                 return (TogglePanel) component;
         }
         
         return null;
    }
    
    private String getHiddenFieldId(FacesContext context, TogglePanel togglePanel){
        return togglePanel.getClientId(context) + "_hidden";
    }
    
    private boolean isDisabled(FacesContext facesContext, ToggleLink link) {
        TogglePanel panel = getParentTogglePanel(facesContext, link);

        return panel.isDisabled() || link.isDisabled() || !UserRoleUtils.isEnabledOnUserRole(link);
    }
}
