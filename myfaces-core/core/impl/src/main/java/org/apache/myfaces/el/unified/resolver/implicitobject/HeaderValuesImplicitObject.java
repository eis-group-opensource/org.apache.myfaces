/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified.resolver.implicitobject;

import java.beans.FeatureDescriptor;
import java.util.Map;
import javax.el.ELContext;

/**
 * Encapsulates information needed by the ImplicitObjectResolver
 * 
 * @author Stan Silvert
 */
public class HeaderValuesImplicitObject extends ImplicitObject
{

    private static final String NAME = "headerValues";

    /** Creates a new instance of HeaderValuesImplicitObject */
    public HeaderValuesImplicitObject()
    {
    }

    @Override
    public Object getValue(ELContext context)
    {
        return externalContext(context).getRequestHeaderValuesMap();
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public Class<?> getType()
    {
        return null;
    }

    @Override
    public FeatureDescriptor getDescriptor()
    {
        return makeDescriptor(NAME,
                              "Map whose keys are a set of request header names and whose values are all of the values (of type String[]) for each header name.",
                              Map.class);
    }

}
