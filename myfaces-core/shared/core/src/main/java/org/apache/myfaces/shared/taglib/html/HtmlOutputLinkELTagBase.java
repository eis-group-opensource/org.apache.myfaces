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
public abstract class HtmlOutputLinkELTagBase
    extends org.apache.myfaces.shared.taglib.html.HtmlComponentELTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML anchor attributes relevant for command link
    private ValueExpression _accesskey;
    private ValueExpression _charset;
    private ValueExpression _coords;
    private ValueExpression _disabled;
    private ValueExpression _hreflang;
    private ValueExpression _rel;
    private ValueExpression _rev;
    private ValueExpression _shape;
    private ValueExpression _tabindex;
    private ValueExpression _target;
    private ValueExpression _type;
    //FIXME: is mentioned in JSF API, but is no official anchor-attribute of HTML 4.0... what to do?
    private ValueExpression _onblur;
    //FIXME: is mentioned in JSF API, but is no official anchor-attribute of HTML 4.0... what to do?
    private ValueExpression _onfocus;

    // UIOutput attributes
    // value and converterId --> already implemented in UIComponentTagBase

    //HtmlCommandLink Attributes

    public void release() {
        super.release();
        _accesskey=null;
        _charset=null;
        _coords=null;
        _disabled=null;
        _hreflang=null;
        _rel=null;
        _rev=null;
        _shape=null;
        _tabindex=null;
        _target=null;
        _type=null;
        _onblur=null;
        _onfocus=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HTML.ACCESSKEY_ATTR, _accesskey);
        setStringProperty(component, HTML.CHARSET_ATTR, _charset);
        setStringProperty(component, HTML.COORDS_ATTR, _coords);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.DISABLED_ATTR, _disabled);
        setStringProperty(component, HTML.HREFLANG_ATTR, _hreflang);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.REL_ATTR, _rel);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.REV_ATTR, _rev);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.SHAPE_ATTR, _shape);
        setStringProperty(component, HTML.TABINDEX_ATTR, _tabindex);
        setStringProperty(component, HTML.TARGET_ATTR, _target);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.TYPE_ATTR, _type);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONFOCUS_ATTR, _onfocus);
   }

    public void setAccesskey(ValueExpression accesskey)
    {
        _accesskey = accesskey;
    }

    public void setCharset(ValueExpression charset)
    {
        _charset = charset;
    }

    public void setCoords(ValueExpression coords)
    {
        _coords = coords;
    }

    public void setDisabled(ValueExpression disabled)
    {
        _disabled = disabled;
    }

    public void setHreflang(ValueExpression hreflang)
    {
        _hreflang = hreflang;
    }

    public void setOnblur(ValueExpression onblur)
    {
        _onblur = onblur;
    }

    public void setOnfocus(ValueExpression onfocus)
    {
        _onfocus = onfocus;
    }

    public void setRel(ValueExpression rel)
    {
        _rel = rel;
    }

    public void setRev(ValueExpression rev)
    {
        _rev = rev;
    }

    public void setShape(ValueExpression shape)
    {
        _shape = shape;
    }

    public void setTabindex(ValueExpression tabindex)
    {
        _tabindex = tabindex;
    }

    public void setTarget(ValueExpression target)
    {
        _target = target;
    }

    public void setType(ValueExpression type)
    {
        _type = type;
    }
}
