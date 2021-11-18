/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.webapp.UIComponentELTag;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public class SelectItemsTag
        extends UIComponentELTag {
    //private static final Log log = LogFactory.getLog(SelectItemsTag.class);

    private ValueExpression _value;

    public void setValue(ValueExpression value) {
        this._value = value;
    }

    public String getComponentType() {
        return "javax.faces.SelectItems";
    }

    public String getRendererType() {
        return null;
    }


    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        if ( _value != null ) {
            if (!_value.isLiteralText()) {
                component.setValueExpression("value", _value);
            } else {
                ((UISelectItems) component).setValue(
                        _value.getExpressionString());
            }
        }
    }

    // UISelectItems attributes
    // --> binding, id, value already handled by UIComponentTagBase

}
