/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import org.apache.myfaces.shared_impl.util.ClassUtils;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.faces.webapp.UIComponentClassicTagBase;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Andreas Berger (latest modification by $Author: lu4242 $)
 * @version $Revision: 695050 $ $Date: 2008-09-14 01:38:43 +0300 (Sun, 14 Sep 2008) $
 * @since 1.2
 */
public abstract class GenericListenerTag<_Holder, _Listener>
        extends TagSupport
{
    private ValueExpression _type = null;
    private ValueExpression _binding = null;
    private Class<_Holder> _holderClazz;

    protected GenericListenerTag(Class<_Holder> holderClazz)
    {
        super();
        _holderClazz = holderClazz;
    }

    public void setType(ValueExpression type)
    {
        _type = type;
    }

    public void setBinding(ValueExpression binding)
    {
        _binding = binding;
    }

    public void release()
    {
        super.release();
        _type = null;
        _binding = null;
    }

    protected abstract void addListener(_Holder holder, _Listener listener);

    protected abstract _Listener createDelegateListener(ValueExpression type, ValueExpression binding);
    
    public int doStartTag() throws JspException
    {
        UIComponentClassicTagBase componentTag = UIComponentELTag.getParentUIComponentClassicTagBase(pageContext);
        if (componentTag == null)
        {
            throw new JspException("no parent UIComponentTag found");
        }

        if (_type == null && _binding == null)
        {
            throw new JspException("\"actionListener\" must have binding and/or type attribute.");
        }

        if (!componentTag.getCreated())
        {
            return Tag.SKIP_BODY;
        }

        _Holder holder = null;
        UIComponent component = componentTag.getComponentInstance();
        try
        {
            holder = (_Holder) component;
        } catch (ClassCastException e)
        {
            throw new JspException(
                    "Component " + ((UIComponent) holder).getId() + " is not instance of " + _holderClazz.getName());
        }
        
        if (_type != null && _type.isLiteralText())
        {
            createListener(holder,component);
        }else{
            addListener(holder, createDelegateListener(_type,_binding));
        }
        
        return Tag.SKIP_BODY;
    }
    
    protected void createListener(_Holder holder, UIComponent component) 
        throws JspException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        _Listener listener;
        // type and/or binding must be specified
        try
        {
            if (null != _binding)
            {
                try
                {
                    listener = (_Listener) _binding.getValue(facesContext.getELContext());
                    if (null != listener)
                    {
                        addListener(holder, listener);
                        // no need for further processing
                        return;
                    }
                }
                catch (ELException e)
                {
                    throw new JspException("Exception while evaluating the binding attribute of Component "
                            + component.getId(), e);
                }
            }
            if (null != _type)
            {
                String className;
                if (_type.isLiteralText())
                {
                    className = _type.getExpressionString();
                    //If type is literal text we should create
                    //a new instance
                    listener = (_Listener) ClassUtils.newInstance(className);
                } else
                {
                    className = (String) _type.getValue(facesContext.getELContext());
                    listener = null;
                }
                                
                if (null != _binding)
                {
                    try
                    {
                        _binding.setValue(facesContext.getELContext(), listener);
                    } catch (ELException e)
                    {
                        throw new JspException("Exception while evaluating the binding attribute of Component "
                                + component.getId(), e);
                    }
                }else{
                    //Type is a EL expression, and there is
                    //no binding property so we should create
                    //a new instance
                    listener = (_Listener) ClassUtils.newInstance(className);
                }
                addListener(holder, listener);
            }
        } catch (ClassCastException e)
        {
            throw new JspException(e);
        }        
    }
    
}
