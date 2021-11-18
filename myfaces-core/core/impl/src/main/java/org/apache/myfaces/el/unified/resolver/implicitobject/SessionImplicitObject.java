/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified.resolver.implicitobject;

import java.beans.FeatureDescriptor;
import javax.el.ELContext;

/**
 * Encapsulates information needed by the ImplicitObjectResolver
 * 
 * @author Stan Silvert
 */
public class SessionImplicitObject extends ImplicitObject
{

    private static final String NAME = "session";

    /** Creates a new instance of SessionImplicitObject */
    public SessionImplicitObject()
    {
    }

    @Override
    public Object getValue(ELContext context)
    {
        return externalContext(context).getSession(false);
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
        return makeDescriptor(NAME, "Session instance for the current request or null if no session exists", Object.class);
    }

}
