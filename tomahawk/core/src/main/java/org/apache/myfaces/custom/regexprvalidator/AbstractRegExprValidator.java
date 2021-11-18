/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.regexprvalidator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.GenericValidator;
import org.apache.myfaces.validator.ValidatorBase;

/**
 * A custom validator for reg. expr., based upons Jakarta Commons. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFValidator
 *   name = "t:validateRegExpr"
 *   class = "org.apache.myfaces.custom.regexprvalidator.RegExprValidator"
 *   tagClass = "org.apache.myfaces.custom.regexprvalidator.ValidateRegExprTag"
 *   serialuidtag = "-449945949876262076L"
 * @since 1.1.7
 * @deprecated use myfaces commons mcv:validateRegExpr instead
 * @author mwessendorf (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */

public abstract class AbstractRegExprValidator extends ValidatorBase {
    /**
     * <p>The standard converter id for this converter.</p>
     */
    public static final String     VALIDATOR_ID        = "org.apache.myfaces.validator.RegExpr";

    /**
     * <p>The message identifier of the {@link FacesMessage} to be created if
     * the regex check fails.</p>
     */
    public static final String REGEXPR_MESSAGE_ID = "org.apache.myfaces.Regexpr.INVALID";

    public AbstractRegExprValidator(){
    }

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
        Object[] args = {value.toString()};
        if(!GenericValidator.matchRegexp(value.toString(),"^"+getPattern()+"$")){
            throw new ValidatorException(getFacesMessage(REGEXPR_MESSAGE_ID, args));
        }
    }

    // -------------------------------------------------------- GETTER & SETTER

    /**
     * the pattern, which is the base of the validation
     * 
     * @JSFProperty
     *   literalOnly = "true"
     * @return the pattern, on which a value should be validated
     */
    public abstract String getPattern();

    /**
     * @param string the pattern, on which a value should be validated
     */
    public abstract void setPattern(String string);

}
