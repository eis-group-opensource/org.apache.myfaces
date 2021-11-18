/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.fieldset;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.htmlTag.HtmlTagRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.FieldsetRenderer"
 *
 * @author svieujot (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2005-05-11 11:47:12 -0400 (Wed, 11 May 2005) $
 */
public class FieldsetRenderer extends HtmlTagRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.FieldsetRenderer";

    public boolean getRendersChildren() 
    {
        return true;
    }
    
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {

        Fieldset fieldset = (Fieldset) component;

        if (fieldset.isRendered())
        {
            super.encodeBegin(context, component);
            String legend = fieldset.getLegend();
            if( legend == null || legend.trim().length() == 0 ) // Don't render the legend
                return;
            
            ResponseWriter writer = context.getResponseWriter();

            writer.startElement("legend", fieldset);
            writer.write( legend );
            writer.endElement( "legend" );
        }  
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        RendererUtils.renderChildren(context, component);
    }
    
}
