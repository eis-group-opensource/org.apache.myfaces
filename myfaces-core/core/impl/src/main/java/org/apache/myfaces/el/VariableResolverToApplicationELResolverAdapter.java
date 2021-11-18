/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import javax.el.ELException;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.apache.myfaces.shared_impl.util.Assert;

/**
 * This class is used to delegate {@link #resolveVariable(FacesContext, String)} to the el resolver of the application.
 * 
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 524598 $ $Date: 2007-04-01 17:19:55 +0300 (Sun, 01 Apr 2007) $
 */
@SuppressWarnings("deprecation")
public class VariableResolverToApplicationELResolverAdapter extends VariableResolver
{
    @Override
    public Object resolveVariable(FacesContext facesContext, String name) throws EvaluationException
    {
        Assert.notNull(facesContext, "facesContext");
        Assert.notNull(name, "name");

        try
        {
            return facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, name);
        }
        catch (ELException e)
        {
            throw new EvaluationException(e);
        }
    }

}
