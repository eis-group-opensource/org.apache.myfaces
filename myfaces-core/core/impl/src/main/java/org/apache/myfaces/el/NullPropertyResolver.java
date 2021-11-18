/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

/**
 * Default PropertyResolver.  See JSF 1.2 spec section 5.8.2
 *
 * @author Stan Silvert
 */
public final class NullPropertyResolver extends PropertyResolver {
    
    /** Creates a new instance of NullPropertyResolver */
    public NullPropertyResolver() {
    }

    @Override
    public boolean isReadOnly(Object base, int index) throws EvaluationException, PropertyNotFoundException {
        elContext().setPropertyResolved(false);
        return false;
    }
    
    @Override
    public boolean isReadOnly(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
        elContext().setPropertyResolved(false);
        return false;
    }

    @Override
    public Object getValue(Object base, int index) throws EvaluationException, PropertyNotFoundException {
        elContext().setPropertyResolved(false);
        return null;
    }
    
    @Override
    public Object getValue(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
        elContext().setPropertyResolved(false);
        return null;
    }

    @Override
    public Class getType(Object base, int index) throws EvaluationException, PropertyNotFoundException {
        elContext().setPropertyResolved(false);
        return null;
    }
    
     @Override
    public Class getType(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
        elContext().setPropertyResolved(false);
        return null;
    }

    @Override
    public void setValue(Object base, Object property, Object value) throws EvaluationException, PropertyNotFoundException {
        elContext().setPropertyResolved(false);
    }

    @Override
    public void setValue(Object base, int index, Object value) throws EvaluationException, PropertyNotFoundException {
        elContext().setPropertyResolved(false);
    }

    private ELContext elContext() {
        return FacesContext.getCurrentInstance().getELContext();
    }
    
}
