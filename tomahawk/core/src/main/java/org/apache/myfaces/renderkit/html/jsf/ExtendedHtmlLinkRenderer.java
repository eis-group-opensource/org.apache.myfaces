/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.jsf;

import org.apache.myfaces.renderkit.html.util.DummyFormUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlLinkRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.FormInfo;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Add dummyForm functionality
 * 
 * @author Mario Ivankovits (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class ExtendedHtmlLinkRenderer
        extends HtmlLinkRendererBase
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
}
