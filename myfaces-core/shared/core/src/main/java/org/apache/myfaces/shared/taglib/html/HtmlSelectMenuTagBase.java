/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.html.HTML;
import org.apache.myfaces.shared.renderkit.JSFAttr;

import javax.faces.component.UIComponent;


/**
 * Common base tag class for HtmlSelectOneMenu and HtmlSelectManyMenu components.
 *
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @author Martin Marinschek
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlSelectMenuELTagBase} instead
 */
public abstract class HtmlSelectMenuTagBase
        extends org.apache.myfaces.shared.taglib.html.HtmlInputTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML input attributes relevant for menu
    private String _datafld;
    private String _datasrc;
    private String _dataformatas;
    private String _disabled;
    private String _name;
    private String _onblur;
    private String _onchange;
    private String _onfocus;
    private String _onselect;
    private String _tabindex;

    // UIInput attributes
    // --> already implemented in HtmlInputTagBase

    // UISelectMany attributes
    //selectedValues cannot be set here, is set in JSP-parsing

    //HtmlSelectManyMenu Attributes
    private String _border;

    // HTMLSelectManyAttributes attributes
    private String _disabledClass;
    private String _enabledClass;


    public void release() {
        super.release();
        _datafld=null;
        _datasrc=null;
        _dataformatas=null;
        _disabled=null;
        _name=null;
        _onblur=null;
        _onchange=null;
        _onfocus=null;
        _onselect=null;
        _tabindex=null;
        _border=null;
        _disabledClass=null;
        _enabledClass=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setBooleanProperty(component, HTML.DISABLED_ATTR, _disabled);
        setStringProperty(component, HTML.NAME_ATTR, _name);
        setStringProperty(component, HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONCHANGE_ATTR, _onchange);
        setStringProperty(component, HTML.ONFOCUS_ATTR, _onfocus);
        setStringProperty(component, HTML.ONSELECT_ATTR, _onselect);
        setStringProperty(component, HTML.TABINDEX_ATTR, _tabindex);
        setStringProperty(component, JSFAttr.DISABLED_CLASS_ATTR, _disabledClass);
        setStringProperty(component, JSFAttr.ENABLED_CLASS_ATTR, _enabledClass);

        setIntegerProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.BORDER_ATTR, _border);
   }

    public void setBorder(String border)
    {
        _border = border;
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

    public void setName(String name)
    {
        _name = name;
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

    public void setTabindex(String tabindex)
    {
        _tabindex = tabindex;
    }

    public void setDisabledClass(String disabledClass)
    {
        _disabledClass = disabledClass;
    }

    public void setEnabledClass(String enabledClass)
    {
        _enabledClass = enabledClass;
    }
}
