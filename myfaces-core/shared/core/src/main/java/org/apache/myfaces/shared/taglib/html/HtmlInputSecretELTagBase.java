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
public abstract class HtmlInputSecretELTagBase
        extends HtmlInputELTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML input attributes relevant for password-input
    private ValueExpression _accesskey;
    private ValueExpression _align;
    private ValueExpression _alt;
    private ValueExpression _datafld;
    private ValueExpression _datasrc;
    private ValueExpression _dataformatas;
    private ValueExpression _disabled;
    private ValueExpression _maxlength;
    private ValueExpression _onblur;
    private ValueExpression _onchange;
    private ValueExpression _onfocus;
    private ValueExpression _onselect;
    private ValueExpression _readonly;
    private ValueExpression _size;
    private ValueExpression _tabindex;

    // UIOutput attributes
    // value and converterId --> already implemented in UIComponentTagBase

    // UIInput attributes
    // --> already implemented in HtmlInputTagBase

    // HTMLInputSecret attributes
    private ValueExpression _redisplay;

    public void release() {
        super.release();
        _accesskey=null;
        _align=null;
        _alt=null;
        _datafld=null;
        _datasrc=null;
        _dataformatas=null;
        _disabled=null;
        _maxlength=null;
        _onblur=null;
        _onchange=null;
        _onfocus=null;
        _onselect=null;
        _readonly=null;
        _size=null;
        _tabindex=null;
        _redisplay=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ACCESSKEY_ATTR, _accesskey);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ALIGN_ATTR, _align);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ALT_ATTR, _alt);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.DISABLED_ATTR, _disabled);
        setIntegerProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.MAXLENGTH_ATTR, _maxlength);
        setStringProperty(component, HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONCHANGE_ATTR, _onchange);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONFOCUS_ATTR, _onfocus);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONSELECT_ATTR, _onselect);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.READONLY_ATTR, _readonly);
        setIntegerProperty(component, HTML.SIZE_ATTR, _size);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.TABINDEX_ATTR, _tabindex);

        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.REDISPLAY_ATTR, _redisplay);
   }

    public void setAccesskey(ValueExpression accesskey)
    {
        _accesskey = accesskey;
    }

    public void setAlign(ValueExpression align)
    {
        _align = align;
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

    public void setMaxlength(ValueExpression maxlength)
    {
        _maxlength = maxlength;
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

    public void setRedisplay(ValueExpression redisplay)
    {
        _redisplay = redisplay;
    }
}
