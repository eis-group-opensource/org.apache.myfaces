/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.JSFAttr;

import javax.faces.component.UIComponent;


/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlInputELTagBase} instead
 */
public abstract class HtmlInputTagBase
    extends org.apache.myfaces.shared.taglib.html.HtmlComponentTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // UIOutput attributes
    // value and converterId --> already implemented in UIComponentTagBase

    // UIInput attributes
    private String _immediate;
    private String _required;
    private String _validator;
    private String _valueChangeListener;
    private String _readonly;

    public void release() {
        super.release();

        _immediate=null;
        _required=null;
        _validator=null;
        _valueChangeListener=null;
        _readonly=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.IMMEDIATE_ATTR, _immediate);
        setBooleanProperty(component, JSFAttr.REQUIRED_ATTR, _required);
        setValidatorProperty(component, _validator);
        setValueChangedListenerProperty(component, _valueChangeListener);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.READONLY_ATTR, _readonly);
    }

    public void setImmediate(String immediate)
    {
        _immediate = immediate;
    }

    public void setRequired(String required)
    {
        _required = required;
    }

    public void setValidator(String validator)
    {
        _validator = validator;
    }

    public void setValueChangeListener(String valueChangeListener)
    {
        _valueChangeListener = valueChangeListener;
    }
    public void setReadonly(String _readonly) {
        this._readonly = _readonly;
    }

}
