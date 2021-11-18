/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * This class is used in conjunction with ConverterImplTag. 
 * 
 * When a tag like this is in a jsp page:
 * 
 * <f:converter binding="#{mybean}"/>
 *  
 *  or
 *  
 * <f:converter converterId="#{'anyid'}" binding="#{mybean}"/>
 * 
 * The value of mybean could be already on the context, so this
 * converter avoid creating a new variable and use the previous one.
 * 
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 600199 $ $Date: 2007-12-01 23:28:15 +0200 (Sat, 01 Dec 2007) $ 
 */
public class DelegateConverter implements Converter, StateHolder
{

    private ValueExpression _converterId;
    private ValueExpression _binding;
    private String _converterIdString = null;
    
    public DelegateConverter(){
        
    }
    
    public DelegateConverter(ValueExpression id, ValueExpression binding, String converterIdString)
    {
        super();
        _converterId = id;
        _binding = binding;
        _converterIdString = converterIdString;
    }

    public boolean isTransient()
    {
        return false;
    }

    public void restoreState(FacesContext facesContext, Object state)
    {
        Object[] values = (Object[]) state;
        _converterId = (ValueExpression) values[0];
        _binding = (ValueExpression) values[1];
        _converterIdString = (String) values[2];
    }

    public Object saveState(FacesContext facesContext)
    {
        Object[] values = new Object[3];
        values[0] = _converterId;
        values[1] = _binding;
        values[2] = _converterIdString;
        return values;
    }

    public void setTransient(boolean arg0)
    {
        // Do nothing        
    }

    public Object getAsObject(FacesContext facesContext, UIComponent component,
            String value)
    {
        return _getDelegate().getAsObject(facesContext, component, value);
    }

    public String getAsString(FacesContext facesContext, UIComponent component,
            Object value)
    {
        return _getDelegate().getAsString(facesContext, component, value);
    }

    private Converter _getDelegate()
    {
        return _createConverter();
    }

    private Converter _createConverter()
    {
        Converter converter = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();

        // try to create the converter from the binding expression first, and then from
        // the converterId
        if (_binding != null)
        {
            try
            {
                converter = (Converter) _binding.getValue(elContext);

                if (converter != null)
                {
                    return converter;
                }
            }
            catch (Exception e)
            {
                throw new ConverterException("Exception creating converter using binding", e);
            }
        }

        if ((_converterId != null) || (_converterIdString != null))
        {
            try
            {
                if (null != _converterIdString)
                {
                    converter = facesContext.getApplication().createConverter(_converterIdString);
                } else 
                {
                    String converterId = (String) _converterId.getValue(elContext);
                    converter = facesContext.getApplication().createConverter(converterId);
                }

                // with binding no converter was created, set its value with the converter
                // created using the converterId
                if (converter != null && _binding != null)
                {
                    _binding.setValue(elContext, converter);
                }
            }
            catch (Exception e)
            {
                throw new ConverterException("Exception creating converter with converterId: " + _converterId, e);
            }
        }

        return converter;
    }

}
