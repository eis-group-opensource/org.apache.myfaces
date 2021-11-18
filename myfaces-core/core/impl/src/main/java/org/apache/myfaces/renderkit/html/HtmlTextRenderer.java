/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderers;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlTextRendererBase;


/**
 *   
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFRenderers(renderers={
    @JSFRenderer(
        renderKitId="HTML_BASIC",
        family="javax.faces.Input",
        type="javax.faces.Text"),
    @JSFRenderer(
        renderKitId="HTML_BASIC",
        family="javax.faces.Output",
        type="javax.faces.Text")
})
public class HtmlTextRenderer
        extends HtmlTextRendererBase
{
    //private static final Log log = LogFactory.getLog(HtmlTextRenderer.class);
}
