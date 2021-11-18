/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import javax.faces.component.UIComponent;


/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @author Martin Marinschek
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlSelectBooleanCheckboxELTagBase} instead
 */
public abstract class HtmlSelectBooleanCheckboxTagBase
        extends org.apache.myfaces.shared.taglib.html.HtmlInputTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML input attributes relevant for checkbox-input
    private String _accesskey;
    private String _alt;
    private String _checked;
    private String _datafld;
    private String _datasrc;
    private String _dataformatas;
    private String _disabled;
    private String _onblur;
    private String _onchange;
    private String _onfocus;
    private String _onselect;
    private String _readonly;
    private String _tabindex;

    // UIInput attributes
    // --> already implemented in HtmlInputTagBase

    // UISelectBoolean attributes
    //private String _selected; //is already covered by checked attribute

    public void release() {
        super.release();
        _accesskey=null;
        _alt=null;
        _checked=null;
        _datafld=null;
        _datasrc=null;
        _dataformatas=null;
        _disabled=null;
        _onblur=null;
        _onchange=null;
        _onfocus=null;
        _onselect=null;
        _readonly=null;
        _tabindex=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ACCESSKEY_ATTR, _accesskey);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ALT_ATTR, _alt);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.CHECKED_ATTR, _checked);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.DISABLED_ATTR, _disabled);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONCHANGE_ATTR, _onchange);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONFOCUS_ATTR, _onfocus);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONSELECT_ATTR, _onselect);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.READONLY_ATTR, _readonly);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.TABINDEX_ATTR, _tabindex);
   }

    public void setAccesskey(String accesskey)
    {
        _accesskey = accesskey;
    }

    public void setAlt(String alt)
    {
        _alt = alt;
    }

    public void setChecked(String checked)
    {
        _checked = checked;
    }

    public void setDatafld(String datafld)
    {
        _datafld = datafld;
    }

    public void setDatasrc(String datasrc)
    {
        _datasrc = datasrc;
    }

    public void setDataformatas(String dataformatas)
    {
        _dataformatas = dataformatas;
    }

    public void setDisabled(String disabled)
    {
        _disabled = disabled;
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

    public void setTabindex(String tabindex)
    {
        _tabindex = tabindex;
    }

}
