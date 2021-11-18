/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

/**
 * This is the base Tag for all ValidatorTags which got a minimum and a maimum attribute.
 * 
 * @author Andreas Berger (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 * @since 1.2
 */
public abstract class GenericMinMaxValidatorTag<T>
    extends ValidatorTag
{
    protected  ValueExpression _minimum;
    protected  ValueExpression _maximum;
    protected T _min = null;
    protected T _max = null;

    public void setMinimum(ValueExpression minimum)
    {
        _minimum = minimum;
    }

    public void setMaximum(ValueExpression maximum)
    {
        _maximum = maximum;
    }

    public void release()
    {
        _minimum = null;
        _maximum = null;
        _min = null;
        _max = null;
    }

    /**
     * This method returns the Validator, you have to cast it to the correct type
     * and apply the min and max values.
     *  
     * @return
     * @throws JspException
     */
    protected Validator createValidator() throws JspException
    {
        if (null == _minimum && null == _maximum){
            throw new JspException("a minimum and / or a maximum have to be specified");
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        if (null != _minimum){
            _min = getValue(_minimum.getValue(elContext));
        }
        if (null != _maximum){
            _max = getValue(_maximum.getValue(elContext));
        }
        if (null != _minimum && null != _maximum){
            if (!isMinLTMax()){
                throw new JspException("maximum limit must be greater than the minimum limit");
            }
        }
        return super.createValidator();
    }

    /**
     * @return true if min is lower than max
     */
    protected abstract boolean isMinLTMax();

    /**
     * Wrapper method.
     * @param value
     * @return
     */
    protected abstract T getValue(Object value);
}
