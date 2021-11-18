/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.renderkit.html.util.ExtensionsPhaseListener;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.JavascriptUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.ResourceUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.ClientBehaviorEvents;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.tomahawk.util.TomahawkResourceUtils;

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
 * @version $Revision: 943798 $ $Date: 2010-05-13 00:00:09 -0500 (Jue, 13 May 2010) $
 */
public class DocumentBodyRenderer extends AbstractDocumentRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.DocumentBody";
    private String BODY_ELEM = "body";
    private String[] ATTRS = new String[] {"onload", "onunload", "onresize", "onkeypress", "style", "styleClass", "id"};
    
    private final String ONRESIZE = "onresize";
    private final String RESIZE = "resize";

    protected String getHtmlTag()
    {
        return BODY_ELEM;
    }

    protected Class getDocumentClass()
    {
        return DocumentBody.class;
    }

    protected void openTag(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent)
        throws IOException
    {
        //HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, ATTRS);
        
        Map<String, List<ClientBehavior>> behaviors = null;
        if (uiComponent instanceof ClientBehaviorHolder && JavascriptUtils.isJavascriptAllowed(facesContext.getExternalContext()))
        {
            behaviors = ((ClientBehaviorHolder) uiComponent).getClientBehaviors();
            if (!behaviors.isEmpty())
            {
                ResourceUtils.renderDefaultJsfJsInlineIfNecessary(facesContext, writer);
            }
            super.openTag(facesContext, writer, uiComponent);
            
            if (behaviors.isEmpty())
            {
                HtmlRendererUtils.writeIdIfNecessary(writer, uiComponent, facesContext);
            }
            else
            {
                writer.writeAttribute(HTML.ID_ATTR, uiComponent.getClientId(facesContext), null);
            }
            
            HtmlRendererUtils.renderBehaviorizedEventHandlers(facesContext, writer, uiComponent, behaviors);
            HtmlRendererUtils.renderBehaviorizedAttribute(facesContext, writer, HTML.ONLOAD_ATTR, uiComponent,
                    ClientBehaviorEvents.LOAD, behaviors, HTML.ONLOAD_ATTR);
            HtmlRendererUtils.renderBehaviorizedAttribute(facesContext, writer, HTML.ONUNLOAD_ATTR, uiComponent,
                    ClientBehaviorEvents.UNLOAD, behaviors, HTML.ONUNLOAD_ATTR);
            HtmlRendererUtils.renderBehaviorizedAttribute(facesContext, writer, ONRESIZE, uiComponent,
                    RESIZE, behaviors, ONRESIZE);
            
            HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
                    HTML.BODY_PASSTHROUGH_ATTRIBUTES_WITHOUT_EVENTS);
        }
        else
        {
            super.openTag(facesContext, writer, uiComponent);
            HtmlRendererUtils.writeIdIfNecessary(writer, uiComponent, facesContext);
            HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
                    HTML.BODY_PASSTHROUGH_ATTRIBUTES);
            HtmlRendererUtils.renderHTMLAttribute(writer, uiComponent, ONRESIZE, ONRESIZE);
        }
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
        
        UIViewRoot root = facesContext.getViewRoot();
        for (UIComponent child : root.getComponentResources(facesContext,
                TomahawkResourceUtils.BODY_LOCATION))
        {
            child.encodeAll(facesContext);
        }
    }
}
