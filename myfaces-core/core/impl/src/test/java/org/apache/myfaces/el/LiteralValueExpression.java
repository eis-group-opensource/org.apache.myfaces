/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import javax.el.ELContext;
import javax.el.ValueExpression;

public class LiteralValueExpression extends ValueExpression
{

    private final Object _value;

    public LiteralValueExpression(Object value)
    {
        _value = value;
    }

    @Override
    public Class<?> getExpectedType()
    {
        return _value == null ? Object.class : _value.getClass();
    }

    @Override
    public Class<?> getType(ELContext context)
    {
        return getExpectedType();
    }

    @Override
    public Object getValue(ELContext context)
    {
        return _value;
    }

    @Override
    public boolean isReadOnly(ELContext context)
    {
        return true;
    }

    @Override
    public void setValue(ELContext context, Object value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (_value != null && _value.equals(obj)) || _value == obj;
    }

    @Override
    public String getExpressionString()
    {
        return _value == null ? "" : _value.toString();
    }

    @Override
    public int hashCode()
    {
        return _value == null ? 0 : _value.hashCode();
    }

    @Override
    public boolean isLiteralText()
    {
        return true;
    }

}
