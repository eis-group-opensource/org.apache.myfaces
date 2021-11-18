/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.htmlTag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.HtmlTagRenderer"
 * 
 * @author bdudney (latest modification by $Author: lu4242 $)
 * @version $Revision: 779441 $ $Date: 2009-05-28 08:27:46 +0300 (Thu, 28 May 2009) $
 */
public class HtmlTagRenderer extends HtmlRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.HtmlTagRenderer";

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {
        if ((context == null) || (component == null))
        {
            throw new NullPointerException();
        }
        HtmlTag htmlTag = (HtmlTag) component;

        if (htmlTag.isRendered())
        {
            String tag = htmlTag.getValue().toString();
            if( tag.trim().length() == 0 ) // Don't render the tag, but render the children.
                return;

            ResponseWriter writer = context.getResponseWriter();

            writer.startElement(tag, htmlTag);
            HtmlRendererUtils.writeIdIfNecessary(writer, htmlTag, context);

            // TODO : Use HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.COMMON_PASSTROUGH_ATTRIBUTES);
            String[] supportedAttributes = {HTML.STYLE_CLASS_ATTR, HTML.STYLE_ATTR};
            HtmlRendererUtils.renderHTMLAttributes(writer, htmlTag, supportedAttributes);
            
            if (htmlTag.getClass().equals(HtmlTag.class))
            {
                // write additional attributes supplied by f:param tags
                // Components that extend from HtmlTag component should render attributes
                // on a proper encodeBegin (see Div component for details) 
                Map params = getParameterMap(htmlTag);
                for(Iterator iter = params.entrySet().iterator(); iter.hasNext();)
                {
                    Entry param = (Entry) iter.next();
                    if (null != param.getValue())
                    {
                        writer.writeAttribute(param.getKey().toString(), param.getValue().toString(), null);
                    }
                }
            }
        }
    }
    
    public Map getParameterMap(UIComponent component) {
        Map result = new HashMap();
        for (Iterator iter = component.getChildren().iterator(); iter.hasNext();) {
            UIComponent child = (UIComponent) iter.next();
            if (child.getClass().equals(UIParameter.class))  {
                UIParameter uiparam = (UIParameter) child;
                Object value = uiparam.getValue();
                if (value != null) {
                    result.put(uiparam.getName(), value);
                }
            }
        }
        return result;
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException
    {
        RendererUtils.renderChildren(context, component);
    }
    
    public boolean getRendersChildren()
    {
        return true;
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        if ((context == null) || (component == null))
        {
            throw new NullPointerException();
        }
        HtmlTag htmlTag = (HtmlTag) component;

        if (htmlTag.isRendered())
        {
            String tag = htmlTag.getValue().toString();
            if( tag.trim().length() == 0 )
                return;

            ResponseWriter writer = context.getResponseWriter();
            // force separate end tag
            writer.writeText("", null);            
            writer.endElement( tag );
        }
    }
}
