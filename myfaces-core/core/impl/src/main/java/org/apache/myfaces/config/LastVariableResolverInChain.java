/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config;

import javax.faces.el.VariableResolver;
import javax.faces.el.EvaluationException;
import javax.faces.context.FacesContext;

/**
 * Represents the last Variable resolver in the chain - is added to be able to
 * through an Evaluation Exception, even if any third-party Variable Resolver is
 * added to the mix.
 *
 * @author Martin Marinschek (latest modification by $Author: dennisbyrne $)
 * @version $Revision: 375880 $ $Date: 2006-02-08 08:27:18 +0100 (Mi, 08 Feb 2006) $
 */
public class LastVariableResolverInChain extends VariableResolver
{
    private VariableResolver delegate;

    public LastVariableResolverInChain(VariableResolver variableResolver)
    {
        delegate = variableResolver;
    }

    // METHODS
    public Object resolveVariable(FacesContext facesContext, String name) throws EvaluationException
    {
        return delegate.resolveVariable(facesContext, name);
    }
}
