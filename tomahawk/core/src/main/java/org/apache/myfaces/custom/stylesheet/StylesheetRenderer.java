/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.stylesheet;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.stylesheet.TextResourceFilter.ResourceInfo;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.Stylesheet"
 * 
 * @author mwessendorf (latest modification by $Author: skitching $)
 * @version $Revision: 671131 $ $Date: 2008-06-24 14:19:43 +0300 (Tue, 24 Jun 2008) $
 */
public class StylesheetRenderer extends HtmlRenderer
{
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException, FacesException
    {

        if ((context == null) || (component == null))
        {
            throw new NullPointerException();
        }
        Stylesheet stylesheet = (Stylesheet) component;
        ResponseWriter writer = context.getResponseWriter();

        String path = stylesheet.getPath();

        if (stylesheet.isInline())
        {
            //include as inline css
            
            if (!path.startsWith("/"))
            {
                throw new FacesException("Inline stylesheets require absolute resource path");
            }

            writer.startElement("style", component);
            writer.writeAttribute("type", "text/css", null);
            if (stylesheet.getMedia() != null)
            {
                writer.writeAttribute("media", stylesheet.getMedia(), null);
            }

            Object text;
            if (stylesheet.isFiltered())
            {
                // Load, filter and cache the resource. Then return the cached data.
                ResourceInfo info = TextResourceFilter.getInstance(context).getOrCreateFilteredResource(context, path); 
                text = info.getText();
            }
            else
            {
                // Just load the data (not cached)
                text = RendererUtils.loadResourceFile(context, path);
            }
            if (text != null)
            {
                writer.writeText(text, null);
            }
            writer.endElement("style");
        }
        else
        {
            //refere as link-element
            writer.startElement("link", component);
            writer.writeAttribute("rel", "stylesheet", null);
            writer.writeAttribute("type", "text/css", null);
            if (stylesheet.getMedia() != null)
            {
                writer.writeAttribute("media", stylesheet.getMedia(), null);
            }

            String stylesheetPath;
            if (stylesheet.isFiltered())
            {
                if (!path.startsWith("/"))
                {
                    throw new FacesException("Filtered stylesheets require absolute resource path");
                }

                // Load, filter and cache the resource
                TextResourceFilter.getInstance(context).getOrCreateFilteredResource(context, path);
                
                // Compute a URL that goes via the tomahawk ExtensionsFilter and the
                // TextResourceFilterProvider to fetch the resource from the cache.
                //
                // Unfortunately the getResourceUri(context, Class, String, bool) api below is
                // really meant for serving resources out of the Tomahawk jarfile, relative to
                // some class that the resource belongs to. So it only expects to receive
                // relative paths. We are abusing it here to serve resources out of the
                // webapp, specified by an absolute path. So here, the leading slash is
                // stripped off and in the TextResourceFilterProvider a matching hack
                // puts it back on again. A better solution would be to write a custom
                // ResourceHandler class and pass that to the getResourceUri method...
                // TODO: fixme
                String nastyPathHack = path.substring(1);
                stylesheetPath = AddResourceFactory.getInstance(context).getResourceUri(
                        context,
                        TextResourceFilterProvider.class,
                        nastyPathHack,
                        true);
            }
            else
            {
                stylesheetPath = context.getApplication().getViewHandler().getResourceURL(context, path);
            }

            writer.writeURIAttribute("href", stylesheetPath, "path");
            writer.endElement("link");
        }
    }
}
