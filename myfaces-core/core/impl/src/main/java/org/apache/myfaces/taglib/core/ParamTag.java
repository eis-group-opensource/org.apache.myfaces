/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import org.apache.myfaces.shared_impl.taglib.UIComponentELTagBase;

import javax.faces.component.UIComponent;
import javax.el.ValueExpression;

/**
 * DOCUMENT ME!
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @author Bruno Aranda (JSR-252)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public class ParamTag
    extends UIComponentELTagBase
{
    public String getComponentType()
    {
        return "javax.faces.Parameter";
    }

    public String getRendererType()
    {
        return null;
    }

    // UIComponent attributes --> already implemented in UIComponentELTagBase

    // UIParameter attributes
    private ValueExpression _name;

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        
        setStringProperty(component, "name", _name);
    }

    public void setName(ValueExpression name)
    {
        _name = name;
    }
}
