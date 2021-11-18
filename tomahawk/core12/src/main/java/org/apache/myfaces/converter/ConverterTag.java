/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.converter;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.webapp.ConverterELTag;
import javax.servlet.jsp.JspException;

/**
 * Implementation of ConverterELTag
 * 
 * This is a copy of org.apache.myfaces.taglib.core.ConverterTag from 
 * myfaces core impl 1.2. This is copied here because we need a base class
 * where all custom converters must inherit.
 *  
 * @since 1.1.7
 * @author Bruno Aranda (latest modification by $Author: lu4242 $)
 * @version $Revision: 691871 $ $Date: 2008-09-04 07:32:08 +0300 (Thu, 04 Sep 2008) $
 */
public class ConverterTag extends ConverterELTag
{

    private static final long serialVersionUID = -4506829108081L;
    private ValueExpression _converterId;
    private ValueExpression _binding;
    private String _converterIdString = null;

    public ConverterTag()
    {
        super();
    }

    public void setConverterId(ValueExpression converterId)
    {
        _converterId = converterId;
    }

    public void setBinding(ValueExpression binding)
    {
        _binding = binding;
    }

    /**
     * Use this method to specify the converterId programmatically.
     *
     * @param converterIdString
     */
    public void setConverterIdString(String converterIdString)
    {
        _converterIdString = converterIdString;
    }

    public void release()
    {
        super.release();
        _converterId = null;
        _binding = null;
        _converterIdString = null;
    }

    protected Converter createConverter()
            throws JspException
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
                throw new JspException("Exception creating converter using binding", e);
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
                throw new JspException("Exception creating converter with converterId: " + _converterId, e);
            }
        }

        return converter;
    }

}
