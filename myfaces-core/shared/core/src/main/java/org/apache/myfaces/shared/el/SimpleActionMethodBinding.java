/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.el;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

/**
 * Convenient method binding that does nothing other than returning a fixed outcome String when invoked.
 *
 * @author Manfred Geiler
 */
public class SimpleActionMethodBinding
        extends MethodBinding
        implements StateHolder
{
    private String _outcome;

    public SimpleActionMethodBinding(String outcome)
    {
        _outcome = outcome;
    }

    public Object invoke(FacesContext facescontext, Object aobj[]) throws EvaluationException, MethodNotFoundException
    {
        return _outcome;
    }

    public Class getType(FacesContext facescontext) throws MethodNotFoundException
    {
        return String.class;
    }


    //~ StateHolder support ----------------------------------------------------------------------------

    private boolean _transient = false;

    /**
     * Empty constructor, so that new instances can be created when restoring state.
     */
    public SimpleActionMethodBinding()
    {
        _outcome = null;
    }

    public Object saveState(FacesContext facescontext)
    {
        return _outcome;
    }

    public void restoreState(FacesContext facescontext, Object obj)
    {
        _outcome = (String)obj;
    }

    public boolean isTransient()
    {
        return _transient;
    }

    public void setTransient(boolean flag)
    {
        _transient = flag;
    }

    public String toString()
    {
        return _outcome;
    }
}
