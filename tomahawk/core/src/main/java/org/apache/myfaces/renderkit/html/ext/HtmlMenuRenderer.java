/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlMenuRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;


/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.SelectOne"
 *   type = "org.apache.myfaces.Menu"
 *   
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.SelectMany"
 *   type = "org.apache.myfaces.Menu"
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2008-05-24 23:59:15 +0300 (Sat, 24 May 2008) $
 */
public class HtmlMenuRenderer
        extends HtmlMenuRendererBase
{
    //private static final Log log = LogFactory.getLog(HtmlMenuRenderer.class);
    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        if (!UserRoleUtils.isEnabledOnUserRole(uiComponent))
        {
            return true;
        }
        else
        {
            return super.isDisabled(facesContext, uiComponent);
        }
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        if(HtmlRendererUtils.isDisplayValueOnly(component))
        {
            HtmlRendererUtils.renderDisplayValueOnlyForSelects(facesContext, component);
        }
        else
        {
            super.encodeEnd(facesContext, component);
        }
    }

}
