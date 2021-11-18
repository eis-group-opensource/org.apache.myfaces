/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified;

import javax.el.ELContext;
import javax.el.ELResolver;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 511514 $ $Date: 2007-02-25 15:47:48 +0200 (Sun, 25 Feb 2007) $
 */
public class NoOpElResolver extends ELResolver
{

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base)
    {
        return null;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base)
    {
        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property)
    {
        return null;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property)
    {
        return null;
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property)
    {
        return false;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value)
    {
    }

}
