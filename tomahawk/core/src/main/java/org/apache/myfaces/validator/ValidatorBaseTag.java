/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.validator;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.faces.webapp.ValidatorTag;
import javax.servlet.jsp.JspException;

/**
 * ValidatorBaseTag provides support for ValidatorBase subclasses.
 * ValidatorBaseTag subclass tld entries should include the following to pick up attribute defintions.
 *         &ext_validator_base_attributes;
 * 
 * @author mkienenb (latest modification by $Author: lu4242 $)
 * @version $Revision: 680740 $
 */
public class ValidatorBaseTag extends ValidatorTag {
    private static final long serialVersionUID = 4416508071412794682L;
    private String _message = null;
    private String _detailMessage = null;
    private String _summaryMessage = null;

    public void setMessage(String string) {
        _message = string;
    }

    public void setDetailMessage(String detailMessage)
    {
        _detailMessage = detailMessage;
    }

    public void setSummaryMessage(String summaryMessage)
    {
        _summaryMessage = summaryMessage;
    }

    protected Validator createValidator() throws JspException {

        ValidatorBase validator = (ValidatorBase)super.createValidator();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if(_message != null && _detailMessage != null)
            throw new JspException("you may not set message and detailMessage together - they serve the same purpose.");

        String detailMessage = _message;

        if(_detailMessage != null)
            detailMessage = _detailMessage;

        if (detailMessage != null)
        {
            if (UIComponentTag.isValueReference(detailMessage))
            {
                ValueBinding vb = facesContext.getApplication().createValueBinding(detailMessage);
                validator.setValueBinding("detailMessage",vb);
            }
            else
            {
                validator.setDetailMessage(detailMessage);
            }
        }

        if (_summaryMessage != null)
        {
            if (UIComponentTag.isValueReference(_summaryMessage))
            {
                ValueBinding vb = facesContext.getApplication().createValueBinding(_summaryMessage);
                validator.setValueBinding("summaryMessage",vb);
            }
            else
            {
                validator.setSummaryMessage(_summaryMessage);
            }
        }

        return validator;
    }

    public void release()
    {
        super.release();
        _message= null;
        _detailMessage = null;
        _summaryMessage = null;
    }
}
