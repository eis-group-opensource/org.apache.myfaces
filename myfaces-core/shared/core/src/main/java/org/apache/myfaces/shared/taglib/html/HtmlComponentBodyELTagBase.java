/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.html.HTML;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 */
public abstract class HtmlComponentBodyELTagBase
        extends org.apache.myfaces.shared.taglib.UIComponentBodyELTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlComponentTag.class);

    //HTML universal attributes
    private ValueExpression _dir;
    private ValueExpression _lang;
    private ValueExpression _style;
    private ValueExpression _styleClass;
    private ValueExpression _title;

    //HTML event handler attributes
    private ValueExpression _onclick;
    private ValueExpression _ondblclick;
    private ValueExpression _onkeydown;
    private ValueExpression _onkeypress;
    private ValueExpression _onkeyup;
    private ValueExpression _onmousedown;
    private ValueExpression _onmousemove;
    private ValueExpression _onmouseout;
    private ValueExpression _onmouseover;
    private ValueExpression _onmouseup;

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

    public void setStyleClass(ValueExpression styleClass)
    {
        _styleClass = styleClass;
    }

    public void setDir(ValueExpression dir)
    {
        _dir = dir;
    }

    public void setLang(ValueExpression lang)
    {
        _lang = lang;
    }

    public void setStyle(ValueExpression style)
    {
        _style = style;
    }

    public void setTitle(ValueExpression title)
    {
        _title = title;
    }

    public void setOnclick(ValueExpression onclick)
    {
        _onclick = onclick;
    }

    public void setOndblclick(ValueExpression ondblclick)
    {
        _ondblclick = ondblclick;
    }

    public void setOnmousedown(ValueExpression onmousedown)
    {
        _onmousedown = onmousedown;
    }

    public void setOnmouseup(ValueExpression onmouseup)
    {
        _onmouseup = onmouseup;
    }

    public void setOnmouseover(ValueExpression onmouseover)
    {
        _onmouseover = onmouseover;
    }

    public void setOnmousemove(ValueExpression onmousemove)
    {
        _onmousemove = onmousemove;
    }

    public void setOnmouseout(ValueExpression onmouseout)
    {
        _onmouseout = onmouseout;
    }

    public void setOnkeypress(ValueExpression onkeypress)
    {
        _onkeypress = onkeypress;
    }

    public void setOnkeydown(ValueExpression onkeydown)
    {
        _onkeydown = onkeydown;
    }

    public void setOnkeyup(ValueExpression onkeyup)
    {
        _onkeyup = onkeyup;
    }
}
