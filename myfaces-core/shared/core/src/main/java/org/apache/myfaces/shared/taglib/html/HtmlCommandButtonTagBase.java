/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.apache.myfaces.shared.renderkit.html.HTML;

import javax.faces.component.UIComponent;


/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @author Martin Marinschek
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlCommandButtonELTagBase} instead
 */
public abstract class HtmlCommandButtonTagBase
    extends HtmlComponentTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlCommandButtonTag.class);

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML input attributes relevant for command-button
    private String _accesskey;
    private String _alt;
    private String _disabled;
    private String _onblur;
    private String _onchange;
    private String _onfocus;
    private String _onselect;
    private String _size;
    private String _tabindex;
    private String _type;

    // UICommand attributes
    private String _action;
    private String _immediate;
    private String _actionListener;

    // HtmlCommandButton attributes
    private String _image;

    public void release() {
        super.release();
        _accesskey=null;
        _alt=null;
        _disabled=null;
        _onblur=null;
        _onchange=null;
        _onfocus=null;
        _onselect=null;
        _size=null;
        _tabindex=null;
        _type=null;
        _action=null;
        _immediate=null;
        _actionListener=null;
        _image=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HTML.ACCESSKEY_ATTR, _accesskey);
        setStringProperty(component, HTML.ALT_ATTR, _alt);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.DISABLED_ATTR, _disabled);
        setStringProperty(component, HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONCHANGE_ATTR, _onchange);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONFOCUS_ATTR, _onfocus);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONSELECT_ATTR, _onselect);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.SIZE_ATTR, _size);
        setStringProperty(component, HTML.TABINDEX_ATTR, _tabindex);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.TYPE_ATTR, _type);
        setActionProperty(component, _action);
        setActionListenerProperty(component, _actionListener);
        setBooleanProperty(component, JSFAttr.IMMEDIATE_ATTR, _immediate);
        setStringProperty(component, JSFAttr.IMAGE_ATTR, _image);
   }

    public void setAccesskey(String accesskey)
    {
        _accesskey = accesskey;
    }

    public void setAlt(String alt)
    {
        _alt = alt;
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

    public void setSize(String size)
    {
        _size = size;
    }

    public void setTabindex(String tabindex)
    {
        _tabindex = tabindex;
    }

    public void setType(String type)
    {
        _type = type;
    }

    public void setAction(String action)
    {
        _action = action;
    }

    public void setImmediate(String immediate)
    {
        _immediate = immediate;
    }

    public void setImage(String image)
    {
        _image = image;
    }

    public void setActionListener(String actionListener)
    {
        _actionListener = actionListener;
    }
}
