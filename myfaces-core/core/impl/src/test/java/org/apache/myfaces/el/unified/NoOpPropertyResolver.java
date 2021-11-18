/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified;

import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 511514 $ $Date: 2007-02-25 15:47:48 +0200 (Sun, 25 Feb 2007) $
 */
@SuppressWarnings("deprecation")
public class NoOpPropertyResolver extends PropertyResolver
{

    @Override
    public Class getType(Object base, int index) throws EvaluationException, PropertyNotFoundException
    {
        return null;
    }

    @Override
    public Class getType(Object base, Object property) throws EvaluationException, PropertyNotFoundException
    {
        return null;
    }

    @Override
    public Object getValue(Object base, int index) throws EvaluationException, PropertyNotFoundException
    {
        return null;
    }

    @Override
    public Object getValue(Object base, Object property) throws EvaluationException, PropertyNotFoundException
    {
        return null;
    }

    @Override
    public boolean isReadOnly(Object base, int index) throws EvaluationException, PropertyNotFoundException
    {
        return false;
    }

    @Override
    public boolean isReadOnly(Object base, Object property) throws EvaluationException, PropertyNotFoundException
    {
        return false;
    }

    @Override
    public void setValue(Object base, int index, Object value) throws EvaluationException, PropertyNotFoundException
    {
    }

    @Override
    public void setValue(Object base, Object property, Object value) throws EvaluationException,
            PropertyNotFoundException
    {
    }

}
