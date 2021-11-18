/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.validator;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorELTag;
import javax.servlet.jsp.JspException;

/**
 * ValidatorBaseTag provides support for ValidatorBase subclasses.
 * ValidatorBaseTag subclass tld entries should include the following to pick up attribute defintions.
 *         &ext_validator_base_attributes;
 * 
 * @author mkienenb (latest modification by $Author: lu4242 $)
 * @version $Revision: 706422 $
 */
public abstract class ValidatorBaseTag extends ValidatorELTag {
    private static final long serialVersionUID = 4416508071412794682L;
    private ValueExpression _message = null;
    private ValueExpression _detailMessage = null;
    private ValueExpression _summaryMessage = null;

    public void setMessage(ValueExpression string) {
        _message = string;
    }

    public void setDetailMessage(ValueExpression detailMessage)
    {
        _detailMessage = detailMessage;
    }

    public void setSummaryMessage(ValueExpression summaryMessage)
    {
        _summaryMessage = summaryMessage;
    }

    protected void _setProperties(Validator v) throws JspException {

        ValidatorBase validator = (ValidatorBase) v;

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if(_message != null && _detailMessage != null)
            throw new JspException("you may not set detailMessage and detailMessage together - they serve the same purpose.");

        ValueExpression detailMessage = _message;

        if(_detailMessage != null)
            detailMessage = _detailMessage;

        if (detailMessage != null)
        {
            if (!detailMessage.isLiteralText())
            {
                validator.setValueExpression("detailMessage",detailMessage);
            }
            else
            {
                validator.setDetailMessage((String)detailMessage.getValue(facesContext.getELContext()));
            }
        }

        if (_summaryMessage != null)
        {
            if (!_summaryMessage.isLiteralText())
            {
                validator.setValueExpression("summaryMessage",_summaryMessage);
            }
            else
            {
                validator.setSummaryMessage((String)_summaryMessage.getValue(facesContext.getELContext()));
            }
        }
    }

    public void release()
    {
        super.release();
        _message= null;
        _detailMessage = null;
        _summaryMessage = null;
    }
}
