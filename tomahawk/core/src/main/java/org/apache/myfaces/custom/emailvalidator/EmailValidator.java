/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.emailvalidator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.GenericValidator;
import org.apache.myfaces.validator.ValidatorBase;


/**
 * A custom validator for email address format, based upons Jakarta Commons.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFValidator
 *   name = "t:validateEmail"
 *   tagClass = "org.apache.myfaces.custom.emailvalidator.ValidateEmailTag"
 *   serialuidtag = "6041422002721046221L"
 * @since 1.1.7
 * @deprecated use myfaces commons mcv:validateEmail instead
 * @author mwessendorf (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public class EmailValidator extends ValidatorBase {

    /**
     * <p>The standard converter id for this converter.</p>
     */
    public static final String     VALIDATOR_ID        = "org.apache.myfaces.validator.Email";
    /**
     * <p>The message identifier of the {@link FacesMessage} to be created if
     * the maximum length check fails.</p>
     */
    public static final String EMAIL_MESSAGE_ID = "org.apache.myfaces.Email.INVALID";

    public EmailValidator(){
    }

    /**
     * methode that validates an email-address.
     * it uses the commons-validator
     */
    public void validate(
        FacesContext facesContext,
        UIComponent uiComponent,
        Object value)
        throws ValidatorException {


            if (facesContext == null) throw new NullPointerException("facesContext");
            if (uiComponent == null) throw new NullPointerException("uiComponent");

            if (value == null)
            {
                return;
            }
            if (!GenericValidator.isEmail(value.toString().trim())) {
                Object[] args = {value.toString()};
                throw new ValidatorException(getFacesMessage(EMAIL_MESSAGE_ID, args));
            }

    }

}
