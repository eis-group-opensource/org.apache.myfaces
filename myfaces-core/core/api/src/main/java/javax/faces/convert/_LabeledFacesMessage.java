/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.convert;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/** 
 * This class encapsulates a FacesMessage to evaluate the label
 * expression on render response, where f:loadBundle is available
 *
 * @author Leonardo Uribe (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
class _LabeledFacesMessage extends FacesMessage{
    
    public _LabeledFacesMessage() {
        super();
    }

    public _LabeledFacesMessage(Severity severity, String summary,
            String detail, Object args[]) {
        super(severity, summary, detail);
        
    }
    
    public _LabeledFacesMessage(Severity severity, String summary,
            String detail) {
        super(severity, summary, detail);
    }

    public _LabeledFacesMessage(String summary, String detail) {
        super(summary, detail);
    }

    public _LabeledFacesMessage(String summary) {
        super(summary);
    }

    @Override
    public String getDetail() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ValueExpression value = facesContext.getApplication().getExpressionFactory().
            createValueExpression(facesContext.getELContext(), super.getDetail(), String.class);
        return (String) value.getValue(facesContext.getELContext());
    }

    @Override
    public String getSummary() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ValueExpression value = facesContext.getApplication().getExpressionFactory().
            createValueExpression(facesContext.getELContext(), super.getSummary(), String.class);
        return (String) value.getValue(facesContext.getELContext());
    }
    
}

