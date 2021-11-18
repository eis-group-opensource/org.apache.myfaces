/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.core;

import org.apache.myfaces.shared.renderkit.JSFAttr;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

/**
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @version $Revision: 684924 $ $Date: 2008-08-11 23:59:26 +0300 (Pr, 11 Rgp 2008) $
 */
public class SelectItemTagBase
    extends org.apache.myfaces.shared.taglib.UIComponentELTagBase
{
    //private static final Log log = LogFactory.getLog(SelectItemTag.class);

    public String getComponentType()
    {
        return "javax.faces.SelectItem";
    }

    public String getRendererType()
    {
        return null;
    }

    // UISelectItem attributes
    private ValueExpression _itemDisabled;
    private ValueExpression _itemDescription;
    private ValueExpression _itemLabel;
    private ValueExpression _itemValue;
    private ValueExpression _escape;

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setBooleanProperty(component, JSFAttr.ITEM_DISABLED_ATTR, _itemDisabled);
        setStringProperty(component, JSFAttr.ITEM_DESCRIPTION_ATTR, _itemDescription);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.ITEM_LABEL_ATTR, _itemLabel);
        setStringProperty(component, JSFAttr.ITEM_VALUE_ATTR, _itemValue);
        setBooleanProperty(component, JSFAttr.ITEM_ESCAPED_ATTR, _escape, Boolean.TRUE);
    }

    public void setItemDisabled(ValueExpression itemDisabled)
    {
        _itemDisabled = itemDisabled;
    }

    public void setItemDescription(ValueExpression itemDescription)
    {
        _itemDescription = itemDescription;
    }

    public void setItemLabel(ValueExpression itemLabel)
    {
        _itemLabel = itemLabel;
    }

    @Deprecated
    protected void setItemValue(String itemValue)
    {
        _itemValue = getFacesContext().getApplication().getExpressionFactory().createValueExpression(
                    getFacesContext().getELContext(),itemValue,String.class);
    }

    public void setItemValue(ValueExpression itemValue)
    {
        _itemValue = itemValue;
    }

    public void setEscape(ValueExpression escape)
    {
        _escape = escape;
    }

    protected ValueExpression getItemValue()
    {
        return _itemValue;
    }

}
