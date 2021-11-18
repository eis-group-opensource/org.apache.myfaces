/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified.resolver.implicitobject;

import java.beans.FeatureDescriptor;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Implementors of this class encapsulate the information needed to resolve the implicit object.
 * 
 * @author Stan Silvert
 */
public abstract class ImplicitObject
{

    public abstract Object getValue(ELContext context);

    public abstract FeatureDescriptor getDescriptor();

    /**
     * Returns an interned String representing the name of the implicit object.
     */
    public abstract String getName();

    /**
     * Returns the most general type allowed for a future call to setValue()
     */
    public abstract Class<?> getType();

    protected FeatureDescriptor makeDescriptor(String name, String description, Class<?> elResolverType)
    {
        FeatureDescriptor fd = new FeatureDescriptor();
        fd.setValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME, Boolean.TRUE);
        fd.setValue(ELResolver.TYPE, elResolverType);
        fd.setName(name);
        fd.setDisplayName(name);
        fd.setShortDescription(description);
        fd.setExpert(false);
        fd.setHidden(false);
        fd.setPreferred(true);
        return fd;
    }

    // get the FacesContext from the ELContext
    protected FacesContext facesContext(ELContext context)
    {
        return (FacesContext) context.getContext(FacesContext.class);
    }

    protected ExternalContext externalContext(ELContext context)
    {
        return facesContext(context).getExternalContext();
    }

}
