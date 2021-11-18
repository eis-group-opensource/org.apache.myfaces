/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html.util;

import javax.faces.component.UIComponent;

public class FormInfo
{
    private final UIComponent form;
    private final String formName;
    
    public FormInfo(final UIComponent form, final String formName)
    {
        this.form = form;
        this.formName = formName;
    }

    public UIComponent getForm()
    {
        return form;
    }

    public String getFormName()
    {
        return formName;
    }
}
