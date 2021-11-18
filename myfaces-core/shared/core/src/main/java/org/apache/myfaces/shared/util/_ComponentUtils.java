/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared.renderkit.RendererUtils;
import org.apache.myfaces.shared.renderkit.html.util.FormInfo;

/**
 * @author Mathias Br&ouml;kelmann (latest modification by $Author: matzew $)
 * @version $Revision: 557356 $ $Date: 2007-07-18 21:28:59 +0300 (Tr, 18 Lie 2007) $
 */
public final class _ComponentUtils
{

    private _ComponentUtils()
    {
    }

    public static String getStringValue(FacesContext context, ValueBinding vb)
    {
        Object value = vb.getValue(context);
        if (value != null)
        {
            return value.toString();
        }
        return null;
    }

    public static FormInfo findNestingForm(UIComponent uiComponent, FacesContext facesContext)
    {
      return RendererUtils.findNestingForm(uiComponent, facesContext);
    }
}
