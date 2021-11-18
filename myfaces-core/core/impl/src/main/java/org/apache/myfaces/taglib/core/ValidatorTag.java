/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorELTag;
import javax.servlet.jsp.JspException;

/**
 * Basic Validator implementation.
 *
 * @author Andreas Berger (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 * @since 1.2
 */
public class ValidatorTag
        extends ValidatorELTag
{
    private ValueExpression _validatorId;
    private ValueExpression _binding;
    private String _validatorIdString = null;

    public void setValidatorId(ValueExpression validatorId)
    {
        _validatorId = validatorId;
    }

    public void setBinding(ValueExpression binding)
    {
        _binding = binding;
    }

    /**
     * Use this method to specify the validatorId programmatically.
     *
     * @param validatorIdString
     */
    public void setValidatorIdString(String validatorIdString)
    {
        _validatorIdString = validatorIdString;
    }

    public void release()
    {
        super.release();
        _validatorId = null;
        _binding = null;
        _validatorIdString = null;
    }

    protected Validator createValidator() throws javax.servlet.jsp.JspException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        if (null != _binding)
        {
            Object validator;
            try
            {
                validator = _binding.getValue(elContext);
            } catch (Exception e)
            {
                throw new JspException("Error while creating the Validator", e);
            }
            if (validator instanceof Validator)
            {
                return (Validator) validator;
            }
        }
        Application application = facesContext.getApplication();
        Validator validator = null;
        try
        {
            // first check if an ValidatorId was set by a method
            if (null != _validatorIdString)
            {
                validator = application.createValidator(_validatorIdString);
            } else if (null != _validatorId)
            {
                String validatorId = (String) _validatorId.getValue(elContext);
                validator = application.createValidator(validatorId);
            }
        } catch (Exception e)
        {
            throw new JspException("Error while creating the Validator", e);
        }

        if (null != validator)
        {
            if (null != _binding)
            {
                _binding.setValue(elContext, validator);
            }
            return validator;
        }
        throw new JspException("validatorId and/or binding must be specified");
    }

}
