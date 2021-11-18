/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified.resolver.implicitobject;

import java.beans.FeatureDescriptor;
import javax.el.ELContext;
import javax.faces.component.UIViewRoot;

/**
 * Encapsulates information needed by the ImplicitObjectResolver
 * 
 * @author Stan Silvert
 */
public class ViewImplicitObject extends ImplicitObject
{

    private static final String NAME = "view";

    /** Creates a new instance of ViewImplicitObject */
    public ViewImplicitObject()
    {
    }

    @Override
    public Object getValue(ELContext context)
    {
        return facesContext(context).getViewRoot();
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
        return makeDescriptor(NAME, "The root object of a JSF component tree", UIViewRoot.class);
    }

}
