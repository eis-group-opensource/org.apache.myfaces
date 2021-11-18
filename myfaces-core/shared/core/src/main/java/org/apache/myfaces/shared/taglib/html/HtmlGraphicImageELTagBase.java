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
public abstract class HtmlGraphicImageELTagBase
    extends org.apache.myfaces.shared.taglib.html.HtmlComponentELTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML img attributes relevant for graphic-image
    private ValueExpression _alt;
    private ValueExpression _height;
    private ValueExpression _ismap;
    private ValueExpression _longdesc;
    private ValueExpression _onblur;
    private ValueExpression _onchange;
    private ValueExpression _onfocus;
    private ValueExpression _usemap;
    private ValueExpression _width;

    //UIGraphic attributes
    private ValueExpression _url;

    // HtmlGraphicImage attributes
    //none so far

    public void release() {
        super.release();
        _alt=null;
        _height=null;
        _ismap=null;
        _longdesc=null;
        _onblur=null;
        _onchange=null;
        _onfocus=null;
        _usemap=null;
        _width=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HTML.ALT_ATTR, _alt);
        setStringProperty(component, HTML.HEIGHT_ATTR, _height);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ISMAP_ATTR, _ismap);
        setStringProperty(component, HTML.LONGDESC_ATTR, _longdesc);
        setStringProperty(component, HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, HTML.ONCHANGE_ATTR, _onchange);
        setStringProperty(component, HTML.ONFOCUS_ATTR, _onfocus);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.USEMAP_ATTR, _usemap);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.WIDTH_ATTR, _width);

        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.URL_ATTR, _url);
   }

    public void setAlt(ValueExpression alt)
    {
        _alt = alt;
    }

    public void setHeight(ValueExpression height)
    {
        _height = height;
    }

    public void setIsmap(ValueExpression ismap)
    {
        _ismap = ismap;
    }

    public void setLongdesc(ValueExpression longdesc)
    {
        _longdesc = longdesc;
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

    public void setUsemap(ValueExpression usemap)
    {
        _usemap = usemap;
    }

    public void setWidth(ValueExpression width)
    {
        _width = width;
    }

    public void setUrl(ValueExpression url)
    {
        _url = url;
    }
}
