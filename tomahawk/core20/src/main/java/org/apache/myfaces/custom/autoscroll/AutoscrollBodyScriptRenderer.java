/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.autoscroll;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.config.MyfacesConfig;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
@JSFRenderer(renderKitId = "HTML_BASIC",
        family = "org.apache.myfaces.custom.autoscroll.AutoscrollBodyScript",
        type = "org.apache.myfaces.custom.autoscroll.AutoscrollBodyScript")
public class AutoscrollBodyScriptRenderer extends HtmlRenderer
{    
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        //Check npe
        super.encodeEnd(context, component);
        
        //AddResource addResource = AddResourceFactory.getInstance(context);
        
        //Only render it if we are not buffering the request by addResource
        //if (MyfacesConfig.getCurrentInstance(context.getExternalContext()).isAutoScroll())
        //{
            ResponseWriter writer = context.getResponseWriter();
            HtmlRendererUtils.renderAutoScrollFunction(context, writer);
            
            HtmlRendererUtils.renderFormSubmitScript(context);
        //}
    }
}
