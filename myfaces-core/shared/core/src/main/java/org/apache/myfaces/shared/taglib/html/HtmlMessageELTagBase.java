/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.JSFAttr;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 */
public abstract class HtmlMessageELTagBase
        extends org.apache.myfaces.shared.taglib.html.HtmlComponentELTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlOutputFormatTag.class);

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // UIMessage attributes
    private ValueExpression _for;
    private ValueExpression _showSummary;
    private ValueExpression _showDetail;

    // HtmlOutputMessage attributes
    private ValueExpression _infoClass;
    private ValueExpression _infoStyle;
    private ValueExpression _warnClass;
    private ValueExpression _warnStyle;
    private ValueExpression _errorClass;
    private ValueExpression _errorStyle;
    private ValueExpression _fatalClass;
    private ValueExpression _fatalStyle;
    private ValueExpression _tooltip;

    public void release() {
        super.release();
        _for=null;
        _showSummary=null;
        _showDetail=null;
        _infoClass=null;
        _infoStyle=null;
        _warnClass=null;
        _warnStyle=null;
        _errorClass=null;
        _errorStyle=null;
        _fatalClass=null;
        _fatalStyle=null;
        _tooltip=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, JSFAttr.FOR_ATTR, _for);
        setBooleanProperty(component, JSFAttr.SHOW_SUMMARY_ATTR, _showSummary);
        setBooleanProperty(component, JSFAttr.SHOW_DETAIL_ATTR, _showDetail);

        setStringProperty(component, JSFAttr.INFO_CLASS_ATTR, _infoClass);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.INFO_STYLE_ATTR, _infoStyle);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.WARN_CLASS_ATTR, _warnClass);
        setStringProperty(component, JSFAttr.WARN_STYLE_ATTR, _warnStyle);
        setStringProperty(component, JSFAttr.ERROR_CLASS_ATTR, _errorClass);
        setStringProperty(component, JSFAttr.ERROR_STYLE_ATTR, _errorStyle);
        setStringProperty(component, JSFAttr.FATAL_CLASS_ATTR, _fatalClass);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.FATAL_STYLE_ATTR, _fatalStyle);
        setBooleanProperty(component, JSFAttr.TOOLTIP_ATTR, _tooltip);
    }

    public void setFor(ValueExpression aFor)
    {
        _for = aFor;
    }

    public ValueExpression getFor()
    {
        return _for;
    }

    public void setShowSummary(ValueExpression showSummary)
    {
        _showSummary = showSummary;
    }

    public void setShowDetail(ValueExpression showDetail)
    {
        _showDetail = showDetail;
    }

    public void setErrorClass(ValueExpression errorClass)
    {
        _errorClass = errorClass;
    }

    public void setErrorStyle(ValueExpression errorStyle)
    {
        _errorStyle = errorStyle;
    }

    public void setFatalClass(ValueExpression fatalClass)
    {
        _fatalClass = fatalClass;
    }

    public void setFatalStyle(ValueExpression fatalStyle)
    {
        _fatalStyle = fatalStyle;
    }

    public void setInfoClass(ValueExpression infoClass)
    {
        _infoClass = infoClass;
    }

    public void setInfoStyle(ValueExpression infoStyle)
    {
        _infoStyle = infoStyle;
    }

    public void setWarnClass(ValueExpression warnClass)
    {
        _warnClass = warnClass;
    }

    public void setWarnStyle(ValueExpression warnStyle)
    {
        _warnStyle = warnStyle;
    }

    public void setTooltip(ValueExpression tooltip)
    {
        _tooltip = tooltip;
    }
}
