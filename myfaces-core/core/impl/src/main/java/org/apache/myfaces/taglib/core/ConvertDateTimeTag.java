/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import org.apache.myfaces.shared_impl.taglib.UIComponentELTagUtils;
import org.apache.myfaces.shared_impl.util.LocaleUtils;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.servlet.jsp.JspException;
import java.util.Locale;
import java.util.TimeZone;


/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public class ConvertDateTimeTag
        extends ConverterTag
{

    /**
     * serial version id for correct serialisation versioning
     */
    private static final long serialVersionUID = 54366768002181L;

    private static final String DEFAULT_DATE_STYLE = "default";
    private static final String DEFAULT_TIME_STYLE = "default";

    private static final String TYPE_DATE = "date";
    private static final String TYPE_TIME = "time";
    private static final String TYPE_BOTH = "both";

    private static final String DEFAULT_TYPE = TYPE_DATE;

    private ValueExpression _dateStyle;
    private ValueExpression _locale;
    private ValueExpression _pattern;
    private ValueExpression _timeStyle;
    private ValueExpression _timeZone;
    private ValueExpression _type;

    private static final ValueExpression CONVERTER_ID;

    static
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null)
        {
            CONVERTER_ID = facesContext.getApplication().getExpressionFactory().createValueExpression(
                    facesContext.getELContext(),"javax.faces.DateTime",String.class);
        }
        else
        {
            // Handle null facesContext because some tools (eg the tlddoc generator)
            // load classes in order to introspect them. Of course this class will
            // never work correctly in its normal JSF environment if this case is used.
            CONVERTER_ID = null;
        }
    }
        
    public void release()
    {
        super.release();
        _dateStyle = null;
        _locale = null;
        _pattern = null;
        _timeStyle = null;
        _timeZone = null;
        _type = null;
    }

    public void setDateStyle(ValueExpression dateStyle)
    {
        _dateStyle = dateStyle;
    }

    public void setLocale(ValueExpression locale)
    {
        _locale = locale;
    }

    public void setPattern(ValueExpression pattern)
    {
        _pattern = pattern;
    }

    public void setTimeStyle(ValueExpression timeStyle)
    {
        _timeStyle = timeStyle;
    }

    public void setTimeZone(ValueExpression timeZone)
    {
        _timeZone = timeZone;
    }

    public void setType(ValueExpression type)
    {
        _type = type;
    }

    public int doStartTag() throws JspException {
        super.setConverterId(CONVERTER_ID);
        return super.doStartTag();
    }

    protected Converter createConverter() throws JspException
        {
            DateTimeConverter converter = (DateTimeConverter)super.createConverter();

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            setConverterDateStyle(elContext, converter, _dateStyle);
            setConverterLocale(elContext, converter, _locale);
            setConverterPattern(elContext, converter, _pattern);
            setConverterTimeStyle(elContext, converter, _timeStyle);
            setConverterTimeZone(elContext, converter, _timeZone);
            setConverterType(elContext, converter, _type);

            return converter;
        }

        private void setConverterLocale(ELContext eLContext,
                                                 DateTimeConverter converter,
                                                 ValueExpression value)
        {
            if (value == null) return;

            Object objLocale = UIComponentELTagUtils.evaluateValueExpression(eLContext, value);
            Locale locale;

            if (objLocale == null) return;

            if (objLocale instanceof Locale)
            {
                locale = (Locale) objLocale;
            }
            else
            {
                locale = LocaleUtils.toLocale(objLocale.toString());
            }
            converter.setLocale(locale);
        }

        private void setConverterDateStyle(ELContext elContext,
                                                 DateTimeConverter converter,
                                                 ValueExpression value)
        {
            if (value == null) return;

            String dateStyle = (String) UIComponentELTagUtils.evaluateValueExpression(elContext, value);

            if (dateStyle == null)
            {
                dateStyle = DEFAULT_DATE_STYLE;
            }

            converter.setDateStyle(dateStyle);
        }

        private void setConverterPattern(ELContext elContext,
                                                 DateTimeConverter converter,
                                                 ValueExpression value)
        {
            if (value == null) return;

            String pattern = (String) UIComponentELTagUtils.evaluateValueExpression(elContext, value);
            converter.setPattern(pattern);
        }

        private void setConverterTimeStyle(ELContext elContext,
                                                 DateTimeConverter converter,
                                                 ValueExpression value)
        {
            if (value == null) return;

            String timeStyle = (String) UIComponentELTagUtils.evaluateValueExpression(elContext, value);

            if (timeStyle == null)
            {
                timeStyle = DEFAULT_TIME_STYLE;
            }

            converter.setTimeStyle(timeStyle);
        }

        private void setConverterTimeZone(ELContext elContext,
                                                 DateTimeConverter converter,
                                                 ValueExpression value)
        {
            if (value == null) return;

            Object objTimeZone = UIComponentELTagUtils.evaluateValueExpression(elContext, value);
            TimeZone timeZone;

            if (objTimeZone == null) return;

            if (objTimeZone instanceof TimeZone)
            {
                timeZone = (TimeZone) objTimeZone;
            }
            else
            {
                timeZone = TimeZone.getTimeZone(objTimeZone.toString());
            }
            converter.setTimeZone(timeZone);
        }

        private void setConverterType(ELContext elContext,
                                             DateTimeConverter converter,
                                             ValueExpression value)
        {
            String type;
            
            if (value == null)
            {
                type = null;
            }
            else
            {
                type = (String) UIComponentELTagUtils.evaluateValueExpression(elContext, value);
            }

            if (type == null)
            {
                //Now check the conditions on the spec, for type is not defined
                // page 9-20
                String timeStyle = (_timeStyle == null) ? null : 
                    (String) UIComponentELTagUtils.evaluateValueExpression(elContext, _timeStyle);
                String dateStyle = (_dateStyle == null) ? null : 
                    (String) UIComponentELTagUtils.evaluateValueExpression(elContext, _dateStyle);
                if (dateStyle == null)
                {
                    if (timeStyle == null)
                    {
                        // if none type defaults to DEFAULT_TYPE
                        type = DEFAULT_TYPE;
                    }
                    else
                    {
                        // if timeStyle is set and dateStyle is not, type defaults to TYPE_TIME
                        type = TYPE_TIME;
                    }
                }
                else
                {
                    if (timeStyle == null)
                    {
                        // if dateStyle is set and timeStyle is not, type defaults to TYPE_DATE
                        type = TYPE_DATE;
                    }
                    else
                    {
                        // if both dateStyle and timeStyle are set, type defaults to TYPE_BOTH                        
                        type = TYPE_BOTH;
                    }
                }
            }else {
                if (!TYPE_DATE.equals(type) && 
                    !TYPE_TIME.equals(type) &&
                    !TYPE_BOTH.equals(type))
                {
                    type = DEFAULT_TYPE;
                }
            }

            converter.setType(type);
        }


}
