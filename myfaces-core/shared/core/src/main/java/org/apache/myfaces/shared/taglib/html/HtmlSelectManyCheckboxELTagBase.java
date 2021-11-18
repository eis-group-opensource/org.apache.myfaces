/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.html.HTML;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;


/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @author Martin Marinschek
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 */
public abstract class HtmlSelectManyCheckboxELTagBase
        extends org.apache.myfaces.shared.taglib.html.HtmlInputELTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML input attributes relevant for checkbox-list
    private ValueExpression _accesskey;
    private ValueExpression _alt;
    private ValueExpression _datafld;
    private ValueExpression _datasrc;
    private ValueExpression _dataformatas;
    private ValueExpression _disabled;
    private ValueExpression _onblur;
    private ValueExpression _onchange;
    private ValueExpression _onfocus;
    private ValueExpression _onselect;
    private ValueExpression _readonly;
    private ValueExpression _size; //TODO: needed?
    private ValueExpression _tabindex;

    // UIInput attributes
    // --> already implemented in HtmlInputTagBase

    // UISelectMany attributes
    //selectedValues cannot be set here, is set in JSP-parsing

    // HTMLSelectManyAttributes attributes
    private ValueExpression _disabledClass;
    private ValueExpression _enabledClass;
    private ValueExpression _layout;

    //FIXME: here there is no border element, in the others
    // (HTMLSelectOneMenuTag, HtmlSelectOneRadioTag)
    //  there is... inconsistent...
    //private String _border;


    public void release() {
        super.release();
        _accesskey=null;
        _alt=null;
        _datafld=null;
        _datasrc=null;
        _dataformatas=null;
        _disabled=null;
        _onblur=null;
        _onchange=null;
        _onfocus=null;
        _onselect=null;
        _readonly=null;
        _size=null;
        _tabindex=null;
        _disabledClass=null;
        _enabledClass=null;
        _layout=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ACCESSKEY_ATTR, _accesskey);
        setStringProperty(component, HTML.ALT_ATTR, _alt);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.DISABLED_ATTR, _disabled);
        setStringProperty(component, HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, HTML.ONCHANGE_ATTR, _onchange);
        setStringProperty(component, HTML.ONFOCUS_ATTR, _onfocus);
        setStringProperty(component, HTML.ONSELECT_ATTR, _onselect);
        setBooleanProperty(component, HTML.READONLY_ATTR, _readonly);
        setIntegerProperty(component, HTML.SIZE_ATTR, _size);
        setStringProperty(component, HTML.TABINDEX_ATTR, _tabindex);

        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.DISABLED_CLASS_ATTR, _disabledClass);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.ENABLED_CLASS_ATTR, _enabledClass);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.LAYOUT_ATTR, _layout);
   }

    public void setAccesskey(ValueExpression accesskey)
    {
        _accesskey = accesskey;
    }

    public void setAlt(ValueExpression alt)
    {
        _alt = alt;
    }

    public void setDatafld(ValueExpression datafld)
    {
        _datafld = datafld;
    }

    public void setDatasrc(ValueExpression datasrc)
    {
        _datasrc = datasrc;
    }

    public void setDataformatas(ValueExpression dataformatas)
    {
        _dataformatas = dataformatas;
    }

    public void setDisabled(ValueExpression disabled)
    {
        _disabled = disabled;
    }

    public void setOnblur(ValueExpression onblur)
    {
        _onblur = onblur;
    }

    public void setOnchange(ValueExpression onchange)
    {
        _onchange = onchange;
    }

    public void setOnfocus(ValueExpression onfocus)
    {
        _onfocus = onfocus;
    }

    public void setOnselect(ValueExpression onselect)
    {
        _onselect = onselect;
    }

    public void setReadonly(ValueExpression readonly)
    {
        _readonly = readonly;
    }

    public void setSize(ValueExpression size)
    {
        _size = size;
    }

    public void setTabindex(ValueExpression tabindex)
    {
        _tabindex = tabindex;
    }

    public void setDisabledClass(ValueExpression disabledClass)
    {
        _disabledClass = disabledClass;
    }

    public void setEnabledClass(ValueExpression enabledClass)
    {
        _enabledClass = enabledClass;
    }

    public void setLayout(ValueExpression layout)
    {
        _layout = layout;
    }
}
