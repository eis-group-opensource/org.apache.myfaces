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
public abstract class HtmlPanelGroupELTagBase
        extends org.apache.myfaces.shared.taglib.html.HtmlComponentBodyELTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlPanelGroupTag.class);

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // GroupRenderer specific attributes
    private ValueExpression _layout;

    public void release() {
        super.release();
        _layout = null;
    }
    
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, JSFAttr.LAYOUT_ATTR, _layout);
    }

    public void setLayout(ValueExpression layout)
    {
        this._layout = layout;
    }
}
