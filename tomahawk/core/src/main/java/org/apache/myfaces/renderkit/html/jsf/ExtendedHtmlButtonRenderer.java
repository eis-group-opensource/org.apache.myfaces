/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.renderkit.html.util.DummyFormUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlButtonRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.FormInfo;


/**
 * Add dummyForm functionality 
 * 
 * @author Mario Ivankovits (latest modification by $Author: lu4242 $)
 * @version $Revision: 709236 $ $Date: 2008-10-30 22:22:57 +0200 (Thu, 30 Oct 2008) $
 */
public class ExtendedHtmlButtonRenderer
    extends HtmlButtonRendererBase
{
    protected void addHiddenCommandParameter(FacesContext facesContext, UIComponent nestingForm, String hiddenFieldName)
    {
        if (nestingForm != null)
        {
            super.addHiddenCommandParameter(facesContext, nestingForm, hiddenFieldName);
        }
        else
        {
            DummyFormUtils.addDummyFormParameter(facesContext, hiddenFieldName);
        }
    }
    
    protected FormInfo findNestingForm(UIComponent uiComponent, FacesContext facesContext)
    {
        return DummyFormUtils.findNestingForm(uiComponent, facesContext);
    }
    
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
}
