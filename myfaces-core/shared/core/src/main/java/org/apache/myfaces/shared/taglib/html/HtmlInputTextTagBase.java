/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.html.HTML;

import javax.faces.component.UIComponent;


/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlInputTextELTagBase} instead
 */
public abstract class HtmlInputTextTagBase
        extends org.apache.myfaces.shared.taglib.html.HtmlInputTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML input attributes
    private String _accesskey;
    private String _align;
    private String _alt; //FIXME: not in API, HTML 4.0 transitional attribute and not in strict... what to do?
    private String _disabled;
    private String _maxlength;
    private String _onblur;
    private String _onchange;
    private String _onfocus;
    private String _onselect;
    private String _readonly;
    private String _size;
    private String _tabindex;

    // UIOutput attributes
    // value and converterId --> already implemented in UIComponentTagBase

    // UIInput attributes
    // --> already implemented in HtmlInputTagBase

    public void release() {
        super.release();
        _accesskey=null;
        _align=null;
        _alt=null;
        _disabled=null;
        _maxlength=null;
        _onblur=null;
        _onchange=null;
        _onfocus=null;
        _onselect=null;
        _readonly=null;
        _size=null;
        _tabindex=null;
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
        setIntegerProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.SIZE_ATTR, _size);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.TABINDEX_ATTR, _tabindex);
    }

    public void setAccesskey(String accesskey)
    {
        _accesskey = accesskey;
    }

    public void setAlign(String align)
    {
        _align = align;
    }

    public void setAlt(String alt)
    {
        _alt = alt;
    }

    public void setDisabled(String disabled)
    {
        _disabled = disabled;
    }

    public void setMaxlength(String maxlength)
    {
        _maxlength = maxlength;
    }

    public void setOnblur(String onblur)
    {
        _onblur = onblur;
    }

    public void setOnchange(String onchange)
    {
        _onchange = onchange;
    }

    public void setOnfocus(String onfocus)
    {
        _onfocus = onfocus;
    }

    public void setOnselect(String onselect)
    {
        _onselect = onselect;
    }

    public void setReadonly(String readonly)
    {
        _readonly = readonly;
    }

    public void setSize(String size)
    {
        _size = size;
    }

    public void setTabindex(String tabindex)
    {
        _tabindex = tabindex;
    }
}
