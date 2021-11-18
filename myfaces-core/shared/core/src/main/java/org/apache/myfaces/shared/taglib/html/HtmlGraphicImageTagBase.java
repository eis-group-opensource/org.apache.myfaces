/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.html.HTML;

import javax.faces.component.UIComponent;


/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @author Martin Marinschek
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlGraphicImageTagBase} instead
 */
public abstract class HtmlGraphicImageTagBase
    extends org.apache.myfaces.shared.taglib.html.HtmlComponentTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML img attributes relevant for graphic-image
    private String _alt;
    private String _height;
    private String _ismap;
    private String _longdesc;
    private String _onblur;
    private String _onchange;
    private String _onfocus;
    private String _usemap;
    private String _width;

    //UIGraphic attributes
    private String _url;

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

    public void setAlt(String alt)
    {
        _alt = alt;
    }

    public void setHeight(String height)
    {
        _height = height;
    }

    public void setIsmap(String ismap)
    {
        _ismap = ismap;
    }

    public void setLongdesc(String longdesc)
    {
        _longdesc = longdesc;
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

    public void setUsemap(String usemap)
    {
        _usemap = usemap;
    }

    public void setWidth(String width)
    {
        _width = width;
    }

    public void setUrl(String url)
    {
        _url = url;
    }
}
