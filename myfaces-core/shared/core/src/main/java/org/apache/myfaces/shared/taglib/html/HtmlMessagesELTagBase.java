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
public abstract class HtmlMessagesELTagBase
        extends org.apache.myfaces.shared.taglib.html.HtmlComponentELTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlOutputFormatTag.class);

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // UIMessages attributes
    private ValueExpression _showSummary;
    private ValueExpression _showDetail;
    private ValueExpression _globalOnly;

    // HtmlMessages attributes
    private ValueExpression _infoClass;
    private ValueExpression _infoStyle;
    private ValueExpression _warnClass;
    private ValueExpression _warnStyle;
    private ValueExpression _errorClass;
    private ValueExpression _errorStyle;
    private ValueExpression _fatalClass;
    private ValueExpression _fatalStyle;
    private ValueExpression _layout;
    private ValueExpression _tooltip;

    public void release() {
        super.release();
        _showSummary=null;
        _showDetail=null;
        _globalOnly=null;
        _infoClass=null;
        _infoStyle=null;
        _warnClass=null;
        _warnStyle=null;
        _errorClass=null;
        _errorStyle=null;
        _fatalClass=null;
        _fatalStyle=null;
        _layout=null;
        _tooltip=null;
    }
    
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.SHOW_SUMMARY_ATTR, _showSummary);
        setBooleanProperty(component, JSFAttr.SHOW_DETAIL_ATTR, _showDetail);
        setBooleanProperty(component, JSFAttr.GLOBAL_ONLY_ATTR, _globalOnly);

        setStringProperty(component, JSFAttr.INFO_CLASS_ATTR, _infoClass);
        setStringProperty(component, JSFAttr.INFO_STYLE_ATTR, _infoStyle);
        setStringProperty(component, JSFAttr.WARN_CLASS_ATTR, _warnClass);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.WARN_STYLE_ATTR, _warnStyle);
        setStringProperty(component, JSFAttr.ERROR_CLASS_ATTR, _errorClass);
        setStringProperty(component, JSFAttr.ERROR_STYLE_ATTR, _errorStyle);
        setStringProperty(component, JSFAttr.FATAL_CLASS_ATTR, _fatalClass);
        setStringProperty(component, JSFAttr.FATAL_STYLE_ATTR, _fatalStyle);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.LAYOUT_ATTR, _layout);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.TOOLTIP_ATTR, _tooltip);
    }

    public void setShowSummary(ValueExpression showSummary)
    {
        _showSummary = showSummary;
    }

    public void setShowDetail(ValueExpression showDetail)
    {
        _showDetail = showDetail;
    }

    public void setGlobalOnly(ValueExpression globalOnly)
    {
        _globalOnly = globalOnly;
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

    public void setLayout(ValueExpression layout)
    {
        _layout = layout;
    }

    public void setTooltip(ValueExpression tooltip)
    {
        _tooltip = tooltip;
    }
}
