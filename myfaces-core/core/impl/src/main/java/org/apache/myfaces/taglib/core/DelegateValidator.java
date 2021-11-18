/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.jsp.JspException;

/**
 * This class is used in conjunction with ValidatorImplTag. 
 * 
 * When a tag like this is in a jsp page:
 * 
 * <f:validator binding="#{mybean}"/>
 *  
 *  or
 *  
 * <f:validator validatorId="#{'anyid'}" binding="#{mybean}"/>
 * 
 * The value of mybean could be already on the context, so this
 * converter avoid creating a new variable and use the previous one.
 *
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 600199 $ $Date: 2007-12-01 23:28:15 +0200 (Sat, 01 Dec 2007) $
 *
 */
public class DelegateValidator implements Validator, StateHolder
{

    private ValueExpression _validatorId;
    private ValueExpression _binding;
    private String _validatorIdString = null;
    
    public DelegateValidator(){
        
    }
    
    public DelegateValidator(ValueExpression id, ValueExpression binding, String converterIdString)
    {
        super();
        _validatorId = id;
        _binding = binding;
        _validatorIdString = converterIdString;
    }

    public boolean isTransient()
    {
        return false;
    }

    public void restoreState(FacesContext facesContext, Object state)
    {
        Object[] values = (Object[]) state;
        _validatorId = (ValueExpression) values[0];
        _binding = (ValueExpression) values[1];
        _validatorIdString = (String) values[2];
    }

    public Object saveState(FacesContext facesContext)
    {
        Object[] values = new Object[3];
        values[0] = _validatorId;
        values[1] = _binding;
        values[2] = _validatorIdString;
        return values;
    }

    public void setTransient(boolean arg0)
    {
        // Do nothing        
    }

    private Validator _getDelegate()
    {
        return _createValidator();
    }

    private Validator _createValidator()
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
                throw new ValidatorException(new FacesMessage("Error while creating the Validator"), e);
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
            throw new ValidatorException(new FacesMessage("Error while creating the Validator"), e);
        }

        if (null != validator)
        {
            if (null != _binding)
            {
                _binding.setValue(elContext, validator);
            }
            return validator;
        }
        throw new ValidatorException(new FacesMessage("validatorId and/or binding must be specified"));
    }

    public void validate(FacesContext facesContext, UIComponent component, Object value)
            throws ValidatorException
    {
        _getDelegate().validate(facesContext, component, value);
    }

}
