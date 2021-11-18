/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import org.apache.myfaces.convert.ConverterUtils;

import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

/**
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @author Manfred Geiler
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public class ValidateDoubleRangeTag
    extends GenericMinMaxValidatorTag<Double>
{
    private static final long serialVersionUID = 6396116656224588016L;

    private static final String VALIDATOR_ID = "javax.faces.DoubleRange";

    protected Validator createValidator()
        throws JspException
    {
        setValidatorIdString(VALIDATOR_ID);
        DoubleRangeValidator validator = (DoubleRangeValidator)super.createValidator();
        if (null != _min){
            validator.setMinimum(_min);
        }
        if (null != _max){
            validator.setMaximum(_max);
        }
        return validator;
    }

    @Override
    protected boolean isMinLTMax()
    {
        return _min < _max;
    }

    @Override
    protected Double getValue(Object value)
    {
        return ConverterUtils.convertToDouble(value);
    }
}
