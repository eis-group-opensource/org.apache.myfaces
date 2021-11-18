/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import javax.el.ValueExpression;
import javax.faces.el.ValueBinding;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * A component that allows the user to select or unselect an object.
 * <p>
 * This can also be used to choose between two states such as true/false or on/off.
 * </p>
 * <p>
 * See the javadoc for this class in the
 * <a href="http://java.sun.com/j2ee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 * for further details.
 * </p>
 */
@JSFComponent(defaultRendererType = "javax.faces.Checkbox")
public class UISelectBoolean extends UIInput
{
    public static final String COMPONENT_TYPE = "javax.faces.SelectBoolean";
    public static final String COMPONENT_FAMILY = "javax.faces.SelectBoolean";

    public UISelectBoolean()
    {
        setRendererType("javax.faces.Checkbox");
    }

    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void setSelected(boolean selected)
    {
        setValue(Boolean.valueOf(selected));
    }

    public boolean isSelected()
    {
        Boolean value = (Boolean) getSubmittedValue();
        if (value == null)
        {
            value = (Boolean) getValue();
        }

        return value != null ? value.booleanValue() : false;
    }

    /**
     * @deprecated Use getValueExpression instead
     */
    public ValueBinding getValueBinding(String name)
    {
        if (name == null)
        {
            throw new NullPointerException("name");
        }
        if (name.equals("selected"))
        {
            return super.getValueBinding("value");
        }
        else
        {
            return super.getValueBinding(name);
        }
    }

    /**
     * @deprecated Use setValueExpression instead
     */
    public void setValueBinding(String name, ValueBinding binding)
    {
        if (name == null)
        {
            throw new NullPointerException("name");
        }
        if (name.equals("selected"))
        {
            super.setValueBinding("value", binding);
        }
        else
        {
            super.setValueBinding(name, binding);
        }
    }

    public ValueExpression getValueExpression(String name)
    {
        if (name == null)
        {
            throw new NullPointerException("name");
        }
        if (name.equals("selected"))
        {
            return super.getValueExpression("value");
        }
        else
        {
            return super.getValueExpression(name);
        }
    }

    public void setValueExpression(String name, ValueExpression binding)
    {
        if (name == null)
        {
            throw new NullPointerException("name");
        }
        if (name.equals("selected"))
        {
            super.setValueExpression("value", binding);
        }
        else
        {
            super.setValueExpression(name, binding);
        }
    }
}
