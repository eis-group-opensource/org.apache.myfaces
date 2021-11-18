/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified.resolver;

import org.apache.myfaces.el.VariableResolverImpl;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspApplicationContext;
import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;

/**
 * <p>
 * This composite el resolver will be used at the top level resolver for faces ({@link Application#getELResolver()})
 * and jsp (the one we add with {@link JspApplicationContext#addELResolver(javax.el.ELResolver)}. It keeps track of its
 * scope to let the variable resolver {@link VariableResolverImpl} know in which scope it is executed. This is
 * necessarry to call either the faces or the jsp resolver head.
 * </p>
 * <p>
 * This implementation does nothing if there is no actual faces context. This is necessarry since we registered our
 * resolvers into the jsp engine. Therefore we have to make sure that jsp only pages where no faces context is available
 * are still working
 * </p>
 *
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 1136612 $ $Date: 2011-06-16 22:13:30 +0300 (Thu, 16 Jun 2011) $
 */
public final class FacesCompositeELResolver extends org.apache.myfaces.el.CompositeELResolver
{
    private final Scope _scope;

    public enum Scope
    {
        Faces, JSP
    }
    
    public static final String SCOPE = "org.apache.myfaces.el.unified.resolver.FacesCompositeELResolver.Scope";
    
    public FacesCompositeELResolver(final Scope scope)
    {
        if (scope == null)
        {
            throw new IllegalArgumentException("scope must not be one of " + Arrays.toString(Scope.values()));
        }
        _scope = scope;
    }

    @Override
    public Class<?> getCommonPropertyType(final ELContext context, final Object base)
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
        {
            return null;
        }
        final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
        Scope prevScope = null;
        try
        {
            prevScope = getScope(requestMap);
            setScope(requestMap);
            return super.getCommonPropertyType(context, base);
        }
        finally
        {
            if(prevScope != null)
            {
                setScope(requestMap, prevScope);
            }
            else
            {
                unsetScope(requestMap);
            }
        }

    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(final ELContext context, final Object base)
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
        {
            return null;
        }
        final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
        Scope prevScope = null;
        try
        {
            prevScope = getScope(requestMap);
            setScope(requestMap);
            return super.getFeatureDescriptors(context, base);

        }
        finally
        {
            if(prevScope != null)
            {
                setScope(requestMap, prevScope);
            }
            else
            {
                unsetScope(requestMap);
            }
        }
    }

    @Override
    public Class<?> getType(final ELContext context, final Object base, final Object property)
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
        {
            return null;
        }
        final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
        Scope prevScope = null;
        try
        {
            prevScope = getScope(requestMap);
            setScope(requestMap);
            return super.getType(context, base, property);
        }
        finally
        {
            if(prevScope != null)
            {
                setScope(requestMap, prevScope);
            }
            else
            {
                unsetScope(requestMap);
            }
        }
    }

    @Override
    public Object getValue(final ELContext context, final Object base, final Object property)
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
        {
            return null;
        }
        final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
        Scope prevScope = null;
        try
        {
            prevScope = getScope(requestMap);
            setScope(requestMap);
            return super.getValue(context, base, property);
        }
        finally
        {
            if(prevScope != null)
            {
                setScope(requestMap, prevScope);
            }
            else
            {
                unsetScope(requestMap);
            }
        }
    }

    @Override
    public boolean isReadOnly(final ELContext context, final Object base, final Object property)
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
        {
            return false;
        }
        final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
        Scope prevScope = null;
        try
        {
            prevScope = getScope(requestMap);
            setScope(requestMap);
            return super.isReadOnly(context, base, property);
        }
        finally
        {
            if(prevScope != null)
            {
                setScope(requestMap, prevScope);
            }
            else
            {
                unsetScope(requestMap);
            }
        }
    }

    @Override
    public void setValue(final ELContext context, final Object base, final Object property, final Object val)
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
        {
            return;
        }
        final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
        Scope prevScope = null;
        try
        {
            prevScope = getScope(requestMap);
            setScope(requestMap);
            super.setValue(context, base, property, val);

        }
        finally
        {
            if(prevScope != null)
            {
                setScope(requestMap, prevScope);
            }
            else
            {
                unsetScope(requestMap);
            }
        }
    }

    private void setScope(final Map<String, Object> requestMap)
    {
        requestMap.put(SCOPE, _scope);
    }
    
    private Scope getScope(final Map<String, Object> requestMap)
    {
        return (Scope) requestMap.get(Scope.class.getName());
    }

    private void setScope(final Map<String, Object> requestMap, Scope prevScope)
    {
        requestMap.put(Scope.class.getName(), prevScope);
    }

    private static void unsetScope(final Map<String, Object> requestMap)
    {
        requestMap.remove(SCOPE);
    }
}
