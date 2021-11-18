/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * Displays a value to the user.
 */
@JSFComponent(defaultRendererType = "javax.faces.Text")
public class UIOutput extends UIComponentBase implements ValueHolder
{
    public static final String COMPONENT_TYPE = "javax.faces.Output";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    private Object _value;
    private Converter _converter;

    /**
     * Construct an instance of the UIOutput.
     */
    public UIOutput()
    {
        setRendererType("javax.faces.Text");
    }

    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public Object getLocalValue()
    {
        return _value;
    }

    /**
     * Gets The initial value of this component.
     * 
     * @return the new value value
     */
    @JSFProperty
    public Object getValue()
    {
        if (_value != null)
        {
            return _value;
        }
        ValueExpression expression = getValueExpression("value");
        if (expression != null)
        {
            return expression.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The initial value of this component.
     */
    public void setValue(Object value)
    {
        this._value = value;
    }

    /**
     * An expression that specifies the Converter for this component.
     * <p>
     * The value can either be a static value (ID) or an EL expression. When a static id is
     * specified, an instance of the converter type registered with that id is used. When this
     * is an EL expression, the result of evaluating the expression must be an object that
     * implements the Converter interface.
     * </p>
     */
    @JSFProperty(stateHolder=true)
    public Converter getConverter()
    {
        if (_converter != null)
        {
            return _converter;
        }
        ValueExpression expression = getValueExpression("converter");
        if (expression != null)
        {
            return (Converter) expression.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    public void setConverter(Converter converter)
    {
        this._converter = converter;
    }

    @Override
    public Object saveState(FacesContext facesContext)
    {
        Object[] values = new Object[3];
        values[0] = super.saveState(facesContext);
        values[1] = _value;
        values[2] = saveAttachedState(facesContext, _converter);

        return values;
    }

    @Override
    public void restoreState(FacesContext facesContext, Object state)
    {
        Object[] values = (Object[]) state;
        super.restoreState(facesContext, values[0]);
        _value = values[1];
        _converter = (Converter) restoreAttachedState(facesContext, values[2]);
    }
}
