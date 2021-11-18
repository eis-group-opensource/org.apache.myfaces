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
public abstract class HtmlFormELTagBase
        extends org.apache.myfaces.shared.taglib.html.HtmlComponentELTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlFormTag.class);

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML form attributes

    private ValueExpression _accept;
    private ValueExpression _acceptCharset;
    private ValueExpression _enctype;
    private ValueExpression _name;
    private ValueExpression _onreset;
    private ValueExpression _onsubmit;
    private ValueExpression _target;

    // UIForm attributes --> none so far
    public void release() {
        super.release();
        _accept=null;
        _acceptCharset=null;
        _enctype=null;
        _name=null;
        _onreset=null;
        _onsubmit=null;
        _target=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ACCEPT_ATTR, _accept);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ACCEPT_CHARSET_ATTR, _acceptCharset);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ENCTYPE_ATTR, _enctype);
        setStringProperty(component, HTML.NAME_ATTR, _name);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONRESET_ATTR, _onreset);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONSUMBIT_ATTR, _onsubmit);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.TARGET_ATTR, _target);
    }

    public void setAccept(ValueExpression accept)
    {
        _accept = accept;
    }

    public void setAcceptCharset(ValueExpression acceptCharset)
    {
        _acceptCharset = acceptCharset;
    }

    public void setEnctype(ValueExpression enctype)
    {
        _enctype = enctype;
    }

    public void setName(ValueExpression name)
    {
        _name = name;
    }

    public void setOnreset(ValueExpression onreset)
    {
        _onreset = onreset;
    }

    public void setOnsubmit(ValueExpression onsubmit)
    {
        _onsubmit = onsubmit;
    }

    public void setTarget(ValueExpression target)
    {
        _target = target;
    }

}
