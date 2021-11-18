/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.myfaces.shared_impl.util.ClassUtils;


/**
 * This class is used in conjunction with ActionListenerTag. 
 * 
 * When a tag like this is in a jsp page:
 * 
 * <f:actionListener binding="#{mybean}"/>
 *  
 *  or
 *  
 * <f:actionListener type="#{'anyid'}" binding="#{mybean}"/>
 * 
 * The value of mybean could be already on the context, so this
 * converter avoid creating a new variable and use the previous one.
 * 
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 600206 $ $Date: 2007-12-02 00:41:50 +0200 (Sun, 02 Dec 2007) $
 */
public class DelegateActionListener implements ActionListener, StateHolder
{

    private ValueExpression _type;
    private ValueExpression _binding;

    public DelegateActionListener()
    {
    }

    public DelegateActionListener(ValueExpression type, ValueExpression binding)
    {
        super();
        _type = type;
        _binding = binding;
    }

    public boolean isTransient()
    {
        return false;
    }

    public void restoreState(FacesContext facesContext, Object state)
    {
        Object[] values = (Object[]) state;
        _type = (ValueExpression) values[0];
        _binding = (ValueExpression) values[1];
    }

    public Object saveState(FacesContext facesContext)
    {
        Object[] values = new Object[2];
        values[0] = _type;
        values[1] = _binding;
        return values;
    }

    public void setTransient(boolean arg0)
    {
        // Do nothing        
    }

    private ActionListener _getDelegate()
    {
        return _createActionListener();
    }

    private ActionListener _createActionListener()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ActionListener listener = null;
        // type and/or binding must be specified
        try
        {
            if (null != _binding)
            {
                try
                {
                    listener = (ActionListener) _binding.getValue(facesContext
                            .getELContext());
                    if (null != listener)
                    {
                        return listener;
                    }
                }
                catch (ELException e)
                {
                    throw new ELException("Exception while evaluating the binding attribute", e);
                }
            }
            if (null != _type)
            {
                String className;
                if (_type.isLiteralText())
                {
                    className = _type.getExpressionString();
                }
                else
                {
                    className = (String) _type.getValue(facesContext
                            .getELContext());
                }

                listener = (ActionListener) ClassUtils.newInstance(className);
                if (null != _binding)
                {
                    try
                    {
                        _binding
                                .setValue(facesContext.getELContext(), listener);
                    }
                    catch (ELException e)
                    {
                        throw new ELException("Exception while evaluating the binding attribute ");
                    }
                }
                return listener;
            }
        }
        catch (ClassCastException e)
        {
            throw new FacesException(e);
        }
        return listener;
    }

    public void processAction(ActionEvent event)
            throws AbortProcessingException
    {
        _getDelegate().processAction(event);
    }

}
