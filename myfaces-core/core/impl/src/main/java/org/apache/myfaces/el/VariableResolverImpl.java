/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.apache.myfaces.el.unified.resolver.FacesCompositeELResolver;
import org.apache.myfaces.el.unified.resolver.FacesCompositeELResolver.Scope;

/**
 * This variable resolver will be used for legacy variable resolvers which are registered through the faces config. If
 * it is invoked through the faces chain it will use the el resolver of {@link Application#getELResolver()}. If it is
 * invoked through the jsp chain it will create an value expression through {@link Application#getExpressionFactory()}
 * to resolve the variable.
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Anton Koinov
 * @author Mathias Broekelmann
 * @version $Revision: 938283 $ $Date: 2010-04-27 04:18:21 +0300 (Tue, 27 Apr 2010) $
 */
@SuppressWarnings("deprecation")
public final class VariableResolverImpl extends VariableResolver
{
    @Override
    public Object resolveVariable(final FacesContext context, final String name) throws EvaluationException
    {
        if (context == null)
        {
            throw new NullPointerException("context must not be null");
        }
        if (name == null)
        {
            throw new NullPointerException("name must not be null");
        }

        try
        {
            final Scope scope = getScope(context);
            final ELContext elcontext = context.getELContext();
            final Application application = context.getApplication();
            if (Scope.Faces.equals(scope))
            {
                return application.getELResolver().getValue(elcontext, null, name);
            }
            else if (Scope.JSP.equals(scope))
            {
                ValueExpression expression = application.getExpressionFactory().createValueExpression(elcontext,
                        "#{" + name + "}", Object.class);
                return expression.getValue(elcontext);
            }
            else
            {
                throw new IllegalStateException("unknown scope defined: " + scope);
            }
        }
        catch (ELException e)
        {
            throw new EvaluationException(e.getMessage(), e);
        }
    }

    protected Scope getScope(final FacesContext context)
    {
        return (Scope) context.getExternalContext().getRequestMap().get(FacesCompositeELResolver.SCOPE);
    }
}