/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.swapimage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.navigation.HtmlCommandNavigation;
import org.apache.myfaces.shared_tomahawk.renderkit.ClientBehaviorEvents;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.ResourceUtils;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Graphic"
 *   type = "org.apache.myfaces.SwapImage"
 * 
 * @author Thomas Spiegl
 * @version $Revision: 709234 $ $Date: 2008-10-30 15:15:18 -0500 (jue, 30 oct 2008) $
 */
@ResourceDependency(library="oam.custom.swapimage",name="swapimage.js")
public class HtmlSwapImageRenderer
        extends HtmlRenderer
{
    private static final Log log = LogFactory.getLog(HtmlSwapImageRenderer.class);

    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        super.decode(context, component);
        
        HtmlRendererUtils.decodeClientBehaviors(context, component);
    }
    
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIGraphic.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        //AddResourceFactory.getInstance(facesContext).addJavaScriptAtPosition(facesContext, AddResource.HEADER_BEGIN,
        //        HtmlSwapImage.class, "swapimage.js");
        
        HtmlSwapImage swapImage = (HtmlSwapImage) uiComponent;
        
        Map<String, List<ClientBehavior>> behaviors = swapImage.getClientBehaviors();
        if (!behaviors.isEmpty())
        {
            ResourceUtils.renderDefaultJsfJsInlineIfNecessary(facesContext, facesContext.getResponseWriter());
        }

        String url;
        if (uiComponent.getParent() instanceof HtmlCommandNavigation)
        {
            url = ((HtmlCommandNavigation) uiComponent.getParent()).isActive() ?
                ((HtmlSwapImage) uiComponent).getActiveImageUrl() : ((HtmlSwapImage)uiComponent).getUrl();
        }
        else
        {
            url = ((HtmlSwapImage)uiComponent).getUrl();
        }

        if ((url != null) && (url.length() > 0))
        {
            writer.startElement(HTML.IMG_ELEM, uiComponent);

            renderId(facesContext, uiComponent);

            String src = facesContext.getApplication()
                    .getViewHandler().getResourceURL(facesContext, url);
            writer.writeURIAttribute(HTML.SRC_ATTR,
                                     facesContext.getExternalContext().encodeResourceURL(src),
                                     null);

            String swapImageUrl = ((HtmlSwapImage) uiComponent).getSwapImageUrl();
            swapImageUrl = facesContext.getApplication()
            .getViewHandler().getResourceURL(facesContext, swapImageUrl);

            if (behaviors != null && !behaviors.isEmpty())
            {
                if (swapImageUrl != null)
                {
                    HtmlRendererUtils.renderBehaviorizedAttribute(facesContext, writer, HTML.ONMOUSEOVER_ATTR, uiComponent, 
                            ClientBehaviorEvents.MOUSEOVER, null, behaviors, HTML.ONMOUSEOVER_ATTR, swapImage.getOnmouseover(),
                            "SI_MM_swapImage('" + getClientId(facesContext, uiComponent) + "','','" + facesContext.getExternalContext().encodeResourceURL(swapImageUrl) + "',1);");
                    HtmlRendererUtils.renderBehaviorizedAttribute(facesContext, writer, HTML.ONMOUSEOUT_ATTR, uiComponent, 
                            ClientBehaviorEvents.MOUSEOUT, null, behaviors, HTML.ONMOUSEOUT_ATTR, swapImage.getOnmouseout(),
                            "SI_MM_swapImgRestore();");
                }
                else
                {
                    HtmlRendererUtils.renderBehaviorizedAttribute(facesContext, writer, HTML.ONMOUSEOVER_ATTR, uiComponent, 
                            ClientBehaviorEvents.MOUSEOVER, null, behaviors, HTML.ONMOUSEOVER_ATTR, swapImage.getOnmouseover(), null);
                    HtmlRendererUtils.renderBehaviorizedAttribute(facesContext, writer, HTML.ONMOUSEOUT_ATTR, uiComponent, 
                            ClientBehaviorEvents.MOUSEOUT, null, behaviors, HTML.ONMOUSEOUT_ATTR, swapImage.getOnmouseout(), null);
                }
                
                HtmlRendererUtils.renderBehaviorizedEventHandlersWithoutOnmouseoverAndOnmouseout(facesContext, writer, uiComponent, behaviors);

                HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.UNIVERSAL_ATTRIBUTES);
                HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.IMG_ATTRIBUTES);
            }
            else
            {
                if (swapImageUrl != null)
                {
                    writer.writeAttribute(HTML.ONMOUSEOVER_ATTR, "SI_MM_swapImage('" + getClientId(facesContext, uiComponent) + "','','" + facesContext.getExternalContext().encodeResourceURL(swapImageUrl) + "',1);", null);
                    writer.writeAttribute(HTML.ONMOUSEOUT_ATTR, "SI_MM_swapImgRestore();", null);
                }
                else
                {
                    HtmlRendererUtils.renderHTMLAttribute(writer, uiComponent, HTML.ONMOUSEOVER_ATTR, HTML.ONMOUSEOVER_ATTR);
                    HtmlRendererUtils.renderHTMLAttribute(writer, uiComponent, HTML.ONMOUSEOUT_ATTR, HTML.ONMOUSEOUT_ATTR);
                }
                
                HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.IMG_PASSTHROUGH_ATTRIBUTES_WITHOUT_ONMOUSEOVER_AND_ONMOUSEOUT);
            }

            writer.endElement(HTML.IMG_ELEM);
        }
        else
        {
            if (log.isWarnEnabled()) log.warn("Graphic with id " + uiComponent.getClientId(facesContext) + " has no value (url).");
        }
    }
    
    protected boolean shouldRenderId(
            FacesContext context,
            UIComponent  component)
    {
        //Always return true, since javascript needs it rendered
        return true;
    }
}
