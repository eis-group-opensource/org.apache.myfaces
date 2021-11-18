/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlMessagesRendererBase;

/**
 *
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Thomas Spiegl
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFRenderer(
    renderKitId="HTML_BASIC",
    family="javax.faces.Messages",
    type="javax.faces.Messages")
public class HtmlMessagesRenderer
        extends HtmlMessagesRendererBase
{
    //private static final Log log = LogFactory.getLog(HtmlMessagesRenderer.class);

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        super.encodeEnd(facesContext, component);   //check for NP
        renderMessages(facesContext, component);
    }

    protected String getSummary(FacesContext facesContext,
                                UIComponent message,
                                FacesMessage facesMessage,
                                String msgClientId)
    {
        return facesMessage.getSummary();
    }

    protected String getDetail(FacesContext facesContext,
                               UIComponent message,
                               FacesMessage facesMessage,
                               String msgClientId)
    {
        return facesMessage.getDetail();
    }
}
