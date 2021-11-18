/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.validator;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspProperty;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFValidator;

/**
 * Creates a validator and associateds it with the nearest parent
 * UIComponent.  When invoked, the validator ensures that values are
 * valid doubles that lie within the minimum and maximum values specified.
 * 
 * Commonly associated with a h:inputText entity.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Thomas Spiegl
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFValidator(
    name="f:validateDoubleRange",
    bodyContent="empty",
    tagClass="org.apache.myfaces.taglib.core.ValidateDoubleRangeTag")
@JSFJspProperty(
    name="binding", 
    returnType = "javax.faces.validator.DoubleRangeValidator",
    longDesc = "A ValueExpression that evaluates to a DoubleRangeValidator.")
public class DoubleRangeValidator
        implements Validator, StateHolder
{
    // FIELDS
    public static final String VALIDATOR_ID       = "javax.faces.DoubleRange";
    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MAXIMUM";
    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MINIMUM";
    public static final String TYPE_MESSAGE_ID    = "javax.faces.validator.DoubleRangeValidator.TYPE";
    public static final String NOT_IN_RANGE_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE";
    
    private Double _minimum = null;
    private Double _maximum = null;
    private boolean _transient = false;

    // CONSTRUCTORS
    public DoubleRangeValidator()
    {
    }

    public DoubleRangeValidator(double maximum)
    {
        _maximum = new Double(maximum);
    }

    public DoubleRangeValidator(double maximum,
                                double minimum)
    {
        _maximum = new Double(maximum);
        _minimum = new Double(minimum);
    }

    // VALIDATE
    public void validate(FacesContext facesContext,
                         UIComponent uiComponent,
                         Object value)
            throws ValidatorException
    {
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

        if (value == null)
        {
            return;
        }

        double dvalue = parseDoubleValue(facesContext, uiComponent,value);
        if (_minimum != null && _maximum != null)
        {
            if (dvalue < _minimum.doubleValue() ||
                dvalue > _maximum.doubleValue())
            {
                Object[] args = {_minimum, _maximum,_MessageUtils.getLabel(facesContext, uiComponent)};
                throw new ValidatorException(_MessageUtils.getErrorMessage(facesContext, NOT_IN_RANGE_MESSAGE_ID, args));
            }
        }
        else if (_minimum != null)
        {
            if (dvalue < _minimum.doubleValue())
            {
                Object[] args = {_minimum,_MessageUtils.getLabel(facesContext, uiComponent)};
                throw new ValidatorException(_MessageUtils.getErrorMessage(facesContext, MINIMUM_MESSAGE_ID, args));
            }
        }
        else if (_maximum != null)
        {
            if (dvalue > _maximum.doubleValue())
            {
                Object[] args = {_maximum,_MessageUtils.getLabel(facesContext, uiComponent)};
                throw new ValidatorException(_MessageUtils.getErrorMessage(facesContext, MAXIMUM_MESSAGE_ID, args));
            }
        }
    }

    private double parseDoubleValue(FacesContext facesContext, UIComponent uiComponent, Object value)
        throws ValidatorException
    {
        if (value instanceof Number)
        {
            return ((Number)value).doubleValue();
        }
        
        try
        {
            return Double.parseDouble(value.toString());
        }
        catch (NumberFormatException e)
        {
            Object[] args = {_MessageUtils.getLabel(facesContext, uiComponent)};
            throw new ValidatorException(_MessageUtils.getErrorMessage(facesContext, TYPE_MESSAGE_ID, args));
        }
    }


    // GETTER & SETTER
    
    /** 
     * The largest value that should be considered valid.
     * 
     */
    @JSFProperty
    public double getMaximum()
    {
        return _maximum != null ? _maximum.doubleValue() : Double.MAX_VALUE;
    }

    public void setMaximum(double maximum)
    {
        _maximum = new Double(maximum);
    }

    /**
     * The smallest value that should be considered valid.
     *  
     */
    @JSFProperty
    public double getMinimum()
    {
        return _minimum != null ? _minimum.doubleValue() : Double.MIN_VALUE;
    }

    public void setMinimum(double minimum)
    {
        _minimum = new Double(minimum);
    }


    // RESTORE/SAVE STATE
    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[2];
        values[0] = _maximum;
        values[1] = _minimum;
        return values;
    }

    public void restoreState(FacesContext context,
                             Object state)
    {
        Object values[] = (Object[])state;
        _maximum = (Double)values[0];
        _minimum = (Double)values[1];
    }

    public boolean isTransient()
    {
        return _transient;
    }

    public void setTransient(boolean transientValue)
    {
        _transient = transientValue;
    }

    // MISC
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof DoubleRangeValidator)) return false;

        final DoubleRangeValidator doubleRangeValidator = (DoubleRangeValidator)o;

        if (_maximum != null ? !_maximum.equals(doubleRangeValidator._maximum) : doubleRangeValidator._maximum != null) return false;
        if (_minimum != null ? !_minimum.equals(doubleRangeValidator._minimum) : doubleRangeValidator._minimum != null) return false;

        return true;
    }

}
