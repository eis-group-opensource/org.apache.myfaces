/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;


import org.apache.myfaces.shared_impl.taglib.UIComponentELTagUtils;
import org.apache.myfaces.shared_impl.util.LocaleUtils;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.Locale;


/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 1178543 $ $Date: 2011-10-03 22:52:41 +0300 (Mon, 03 Oct 2011) $
 */
public class ConvertNumberTag
        extends ConverterTag
{
    private static final long serialVersionUID = -8365745569697171573L;
    private ValueExpression _currencyCode = null;
    private ValueExpression _currencySymbol = null;
    private ValueExpression _groupingUsed = null;
    private ValueExpression _integerOnly = null;
    private ValueExpression _locale = null;
    private ValueExpression _maxFractionDigits = null;
    private ValueExpression _maxIntegerDigits = null;
    private ValueExpression _minFractionDigits = null;
    private ValueExpression _minIntegerDigits = null;
    private ValueExpression _pattern = null;
    private ValueExpression _type = null;

    public ConvertNumberTag()
    {
        setConverterIdString(NumberConverter.CONVERTER_ID);
    }

    public void setCurrencyCode(ValueExpression currencyCode)
    {
        _currencyCode = currencyCode;
    }

    public void setCurrencySymbol(ValueExpression currencySymbol)
    {
        _currencySymbol = currencySymbol;
    }

    public void setGroupingUsed(ValueExpression groupingUsed)
    {
        _groupingUsed = groupingUsed;
    }

    public void setIntegerOnly(ValueExpression integerOnly)
    {
        _integerOnly = integerOnly;
    }

    public void setLocale(ValueExpression locale)
    {
        _locale = locale;
    }

    public void setMaxFractionDigits(ValueExpression maxFractionDigits)
    {
        _maxFractionDigits = maxFractionDigits;
    }

    public void setMaxIntegerDigits(ValueExpression maxIntegerDigits)
    {
        _maxIntegerDigits = maxIntegerDigits;
    }

    public void setMinFractionDigits(ValueExpression minFractionDigits)
    {
        _minFractionDigits = minFractionDigits;
    }

    public void setMinIntegerDigits(ValueExpression minIntegerDigits)
    {
        _minIntegerDigits = minIntegerDigits;
    }

    public void setPattern(ValueExpression pattern)
    {
        _pattern = pattern;
    }

    public void setType(ValueExpression type)
    {
        _type = type;
    }

    public void setPageContext(PageContext context)
    {
        super.setPageContext(context);
        setConverterIdString(NumberConverter.CONVERTER_ID);
    }

    protected Converter createConverter() throws JspException
    {
        NumberConverter converter = (NumberConverter) super.createConverter();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        if (null != _currencyCode)
        {
            converter.setCurrencyCode(
                    (String) UIComponentELTagUtils.evaluateValueExpression(elContext, _currencyCode));
        }
        if (null != _currencySymbol)
        {
            converter.setCurrencySymbol(
                    (String) UIComponentELTagUtils.evaluateValueExpression(elContext, _currencySymbol));
        }
        if (null != _groupingUsed)
        {
            converter.setGroupingUsed(UIComponentELTagUtils.getBooleanValue(elContext, _groupingUsed));
        } else
        {
            converter.setGroupingUsed(true);
        }
        if (null != _integerOnly)
        {
            converter.setIntegerOnly(UIComponentELTagUtils.getBooleanValue(elContext, _integerOnly));
        } else
        {
            converter.setIntegerOnly(false);
        }
        if (null != _locale)
        {
            Locale locale;
            if (_locale.isLiteralText())
            {
                locale = LocaleUtils.toLocale(_locale.getExpressionString());
            }
            else
            {
                Object localeValue = _locale.getValue(elContext);
                if (localeValue instanceof Locale)
                {
                    locale = (Locale)localeValue;
                }
                else
                {
                    locale = LocaleUtils.toLocale(localeValue.toString());
                }
                if (null == locale)
                {
                    locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
                }
            }
            converter.setLocale(locale);
        }
        if (null != _maxFractionDigits)
        {
            converter.setMaxFractionDigits(UIComponentELTagUtils.getIntegerValue(elContext, _maxFractionDigits));
        }
        if (null != _maxIntegerDigits)
        {
            converter.setMaxIntegerDigits(UIComponentELTagUtils.getIntegerValue(elContext, _maxIntegerDigits));
        }
        if (null != _minFractionDigits)
        {
            converter.setMinFractionDigits(UIComponentELTagUtils.getIntegerValue(elContext, _minFractionDigits));
        }
        if (null != _minIntegerDigits)
        {
            converter.setMinIntegerDigits(UIComponentELTagUtils.getIntegerValue(elContext, _minIntegerDigits));
        }
        if (null != _pattern)
        {
            converter.setPattern((String) UIComponentELTagUtils.evaluateValueExpression(elContext, _pattern));
        }
        if (null != _type)
        {
            converter.setType((String) UIComponentELTagUtils.evaluateValueExpression(elContext, _type));
        } else
        {
            converter.setType("number");
        }
        return converter;
    }
}
