/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.text.MessageFormat;
import java.util.Locale;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/** 
 * This class encapsulates a FacesMessage to evaluate the label
 * expression on render response, where f:loadBundle is available
 * 
 * @author Leonardo Uribe (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 05:31:48 -0500 (Dom, 13 Jul 2008) $
 */
class _ParametrizableFacesMessage extends FacesMessage
{
    /**
     * 
     */
    private static final long serialVersionUID = 7792947730961657948L;

    private final Object _args[];
    private String _evaluatedDetail;
    private String _evaluatedSummary;
    private transient Object _evaluatedArgs[];
    private Locale _locale;

    public _ParametrizableFacesMessage(
            String summary, String detail, Object[] args, Locale locale)
    {
        super(summary, detail);
        if(locale == null) throw new NullPointerException("locale");
        _locale = locale;
        _args = args;
    }

    public _ParametrizableFacesMessage(FacesMessage.Severity severity,
            String summary, String detail, Object[] args, Locale locale)
    {
        super(severity, summary, detail);
        if(locale == null) throw new NullPointerException("locale");
        _locale = locale;
        _args = args;
    }

    @Override
    public String getDetail()
    {
        if (_evaluatedArgs == null && _args != null)
        {
            evaluateArgs();
        }
        if (_evaluatedDetail == null)
        {
            MessageFormat format = new MessageFormat(super.getDetail(), _locale);
            _evaluatedDetail = format.format(_evaluatedArgs);
        }
        return _evaluatedDetail;
    }

    @Override
    public void setDetail(String detail)
    {
        super.setDetail(detail);
        _evaluatedDetail = null;
    }
    
    public String getUnformattedDetail()
    {
        return super.getDetail();
    }

    @Override
    public String getSummary()
    {
        if (_evaluatedArgs == null && _args != null)
        {
            evaluateArgs();
        }
        if (_evaluatedSummary == null)
        {
            MessageFormat format = new MessageFormat(super.getSummary(), _locale);
            _evaluatedSummary = format.format(_evaluatedArgs);
        }
        return _evaluatedSummary;
    }

    @Override
    public void setSummary(String summary)
    {
        super.setSummary(summary);
        _evaluatedSummary = null;
    }
    
    public String getUnformattedSummary()
    {
        return super.getSummary();
    }

    private void evaluateArgs()
    {
        _evaluatedArgs = new Object[_args.length];
        FacesContext facesContext = null;
        for (int i = 0; i < _args.length; i++)
        {
            if (_args[i] == null)
            {
                continue;
            }
            else if (_args[i] instanceof ValueBinding)
            {
                if (facesContext == null)
                {
                    facesContext = FacesContext.getCurrentInstance();
                }
                _evaluatedArgs[i] = ((ValueBinding)_args[i]).getValue(facesContext);
            }
            else if (_args[i] instanceof ValueExpression)
            {
                if (facesContext == null)
                {
                    facesContext = FacesContext.getCurrentInstance();
                }
                _evaluatedArgs[i] = ((ValueExpression)_args[i]).getValue(facesContext.getELContext());
            }
            else 
            {
                _evaluatedArgs[i] = _args[i];
            }
        }
    }
}
