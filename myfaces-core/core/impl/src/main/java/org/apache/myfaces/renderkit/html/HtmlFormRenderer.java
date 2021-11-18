/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.shared_impl.config.MyfacesConfig;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlFormRendererBase;
import org.apache.myfaces.shared_impl.renderkit.html.util.JavascriptUtils;


/**
 *   
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Thomas Spiegl
 * @author Anton Koinov
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFRenderer(
    renderKitId="HTML_BASIC",
    family="javax.faces.Form",
    type="javax.faces.Form")
public class HtmlFormRenderer
        extends HtmlFormRendererBase
{
    //private static final Log log = LogFactory.getLog(HtmlFormRenderer.class);
    
    @Override
    protected void afterFormElementsEnd(FacesContext facesContext,
            UIComponent component) throws IOException {
        super.afterFormElementsEnd(facesContext, component);
        
        ResponseWriter writer = facesContext.getResponseWriter();
        ExternalContext extContext = facesContext.getExternalContext();
        
        // If javascript viewstate is enabled write empty hidden input in forms 
        if (JavascriptUtils.isJavascriptAllowed(extContext) && MyfacesConfig.getCurrentInstance(extContext).isViewStateJavascript()) {
            writer.startElement(HTML.INPUT_ELEM, null);
            writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_HIDDEN, null);
            writer.writeAttribute(HTML.NAME_ATTR, HtmlResponseStateManager.VIEW_STATE_PARAM, null);
            writer.writeAttribute(HTML.ID_ATTR, HtmlResponseStateManager.VIEW_STATE_PARAM, null);
            writer.writeAttribute(HTML.VALUE_ATTR, "", null);
            writer.endElement(HTML.INPUT_ELEM);
        }
    }
}
