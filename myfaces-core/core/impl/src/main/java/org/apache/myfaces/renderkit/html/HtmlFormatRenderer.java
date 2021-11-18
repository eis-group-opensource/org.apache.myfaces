/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputFormat;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.shared_impl.renderkit.JSFAttr;
import org.apache.myfaces.shared_impl.renderkit.RendererUtils;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlTextRendererBase;

/**
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Thomas Spiegl
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFRenderer(
    renderKitId="HTML_BASIC",
    family="javax.faces.Output",
    type="javax.faces.Format")
public class HtmlFormatRenderer
        extends HtmlRenderer
{
    private static final Log log = LogFactory.getLog(HtmlFormatRenderer.class);

    private static final Object[] EMPTY_ARGS = new Object[0];

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
    }

    public void encodeChildren(FacesContext facescontext, UIComponent uicomponent)
            throws IOException
    {
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, UIOutput.class);

        String text = getOutputFormatText(facesContext, component);
        boolean isEscape;
        if (component instanceof HtmlOutputFormat)
        {
            isEscape = ((HtmlOutputFormat)component).isEscape();
        }
        else
        {
            isEscape = RendererUtils.getBooleanAttribute(component, JSFAttr.ESCAPE_ATTR, true);
        }
        HtmlTextRendererBase.renderOutputText(facesContext, component, text, isEscape);
    }

    private String getOutputFormatText(FacesContext facesContext,
                                       UIComponent htmlOutputFormat)
    {
        String pattern = RendererUtils.getStringValue(facesContext, htmlOutputFormat);
        Object[] args;
        if (htmlOutputFormat.getChildCount() == 0)
        {
            args = EMPTY_ARGS;
        }
        else
        {
            List argsList = new ArrayList();
            for (Iterator it = htmlOutputFormat.getChildren().iterator(); it.hasNext(); )
            {
                UIComponent child = (UIComponent)it.next();
                if (child instanceof UIParameter)
                {
                    argsList.add(((UIParameter)child).getValue());
                }
            }
            args = argsList.toArray(new Object[argsList.size()]);
        }

        MessageFormat format = new MessageFormat(pattern, facesContext.getViewRoot().getLocale());
        try
        {
            return format.format(args);
        }
        catch (Exception e)
        {
            log.error("Error formatting message of component " + htmlOutputFormat.getClientId(facesContext));
            return "";
        }
    }

}
