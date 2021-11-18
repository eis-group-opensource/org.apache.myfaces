/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

/**
 * This is the default VariableResolver. See JSF 1.2 spec section 5.8.1
 * 
 * @author Stan Silvert
 */
public class NullVariableResolver extends VariableResolver
{

    /** Creates a new instance of NullVariableResolver */
    public NullVariableResolver()
    {
    }

    @Override
    public Object resolveVariable(FacesContext facesContext, String name) throws EvaluationException
    {
        facesContext.getELContext().setPropertyResolved(false);

        return null;
    }

}
