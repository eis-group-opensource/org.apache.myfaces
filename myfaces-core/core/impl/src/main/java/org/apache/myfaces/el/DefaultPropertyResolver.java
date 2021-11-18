/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

/**
 * Implements the default property resolver see spec section 5.8.2.
 * 
 * @author Mathias Broekelmann (latest modification by $Author: bommel $)
 * @version $Revision: 693059 $ $Date: 2008-09-08 14:42:28 +0300 (Mon, 08 Sep 2008) $
 */
@SuppressWarnings("deprecation")
public final class DefaultPropertyResolver extends PropertyResolver
{
    private void updatePropertyResolved()
    {
        FacesContext.getCurrentInstance().getELContext().setPropertyResolved(false);
    }

    @Override
    public Class getType(Object base, int index) throws EvaluationException, PropertyNotFoundException
    {
        updatePropertyResolved();
        return null;
    }

    @Override
    public Class getType(Object base, Object property) throws EvaluationException, PropertyNotFoundException
    {
        updatePropertyResolved();
        return null;
    }

    @Override
    public Object getValue(Object base, int index) throws EvaluationException, PropertyNotFoundException
    {
        updatePropertyResolved();
        return null;
    }

    @Override
    public Object getValue(Object base, Object property) throws EvaluationException, PropertyNotFoundException
    {
        updatePropertyResolved();
        return null;
    }

    @Override
    public boolean isReadOnly(Object base, int index) throws EvaluationException, PropertyNotFoundException
    {
        updatePropertyResolved();
        return false;
    }

    @Override
    public boolean isReadOnly(Object base, Object property) throws EvaluationException, PropertyNotFoundException
    {
        updatePropertyResolved();
        return false;
    }

    @Override
    public void setValue(Object base, int index, Object value) throws EvaluationException, PropertyNotFoundException
    {
        updatePropertyResolved();
    }

    @Override
    public void setValue(Object base, Object property, Object value) throws EvaluationException,
            PropertyNotFoundException
    {
        updatePropertyResolved();
    }

}
