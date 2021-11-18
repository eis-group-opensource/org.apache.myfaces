/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.html.HTML;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlComponentBodyELTagBase} instead
 */
public abstract class HtmlComponentBodyTagBase
        extends org.apache.myfaces.shared.taglib.UIComponentBodyTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlComponentTag.class);

    //HTML universal attributes
    private String _dir;
    private String _lang;
    private String _style;
    private String _styleClass;
    private String _title;

    //HTML event handler attributes
    private String _onclick;
    private String _ondblclick;
    private String _onkeydown;
    private String _onkeypress;
    private String _onkeyup;
    private String _onmousedown;
    private String _onmousemove;
    private String _onmouseout;
    private String _onmouseover;
    private String _onmouseup;

    public void release() {
        super.release();
        _dir=null;
        _lang=null;
        _style=null;
        _styleClass=null;
        _title=null;
        _onclick=null;
        _ondblclick=null;
        _onkeydown=null;
        _onkeypress=null;
        _onkeyup=null;
        _onmousedown=null;
        _onmousemove=null;
        _onmouseout=null;
        _onmouseover=null;
        _onmouseup=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.DIR_ATTR, _dir);
        setStringProperty(component, HTML.LANG_ATTR, _lang);
        setStringProperty(component, HTML.STYLE_ATTR, _style);
        setStringProperty(component, HTML.TITLE_ATTR, _title);
        setStringProperty(component, HTML.STYLE_CLASS_ATTR, _styleClass);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONCLICK_ATTR, _onclick);
        setStringProperty(component, HTML.ONDBLCLICK_ATTR, _ondblclick);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONMOUSEDOWN_ATTR, _onmousedown);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONMOUSEUP_ATTR, _onmouseup);
        setStringProperty(component, HTML.ONMOUSEOVER_ATTR, _onmouseover);
        setStringProperty(component, HTML.ONMOUSEMOVE_ATTR, _onmousemove);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONMOUSEOUT_ATTR, _onmouseout);
        setStringProperty(component, HTML.ONKEYPRESS_ATTR, _onkeypress);
        setStringProperty(component, HTML.ONKEYDOWN_ATTR, _onkeydown);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONKEYUP_ATTR, _onkeyup);
    }

    public void setStyleClass(String styleClass)
    {
        _styleClass = styleClass;
    }

    public void setDir(String dir)
    {
        _dir = dir;
    }

    public void setLang(String lang)
    {
        _lang = lang;
    }

    public void setStyle(String style)
    {
        _style = style;
    }

    public void setTitle(String title)
    {
        _title = title;
    }

    public void setOnclick(String onclick)
    {
        _onclick = onclick;
    }

    public void setOndblclick(String ondblclick)
    {
        _ondblclick = ondblclick;
    }

    public void setOnmousedown(String onmousedown)
    {
        _onmousedown = onmousedown;
    }

    public void setOnmouseup(String onmouseup)
    {
        _onmouseup = onmouseup;
    }

    public void setOnmouseover(String onmouseover)
    {
        _onmouseover = onmouseover;
    }

    public void setOnmousemove(String onmousemove)
    {
        _onmousemove = onmousemove;
    }

    public void setOnmouseout(String onmouseout)
    {
        _onmouseout = onmouseout;
    }

    public void setOnkeypress(String onkeypress)
    {
        _onkeypress = onkeypress;
    }

    public void setOnkeydown(String onkeydown)
    {
        _onkeydown = onkeydown;
    }

    public void setOnkeyup(String onkeyup)
    {
        _onkeyup = onkeyup;
    }
}
