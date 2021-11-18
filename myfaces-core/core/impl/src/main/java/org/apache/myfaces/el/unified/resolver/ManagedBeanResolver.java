/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified.resolver;

import org.apache.myfaces.config.ManagedBeanBuilder;
import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.config.element.ManagedBean;
import org.apache.myfaces.context.servlet.StartupServletExternalContextImpl;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * See JSF 1.2 spec section 5.6.1.2
 * 
 * @author Stan Silvert
 */
public class ManagedBeanResolver extends ELResolver
{
    private static final Log log              = LogFactory.getLog(ManagedBeanResolver.class);
    private static final String BEANS_UNDER_CONSTRUCTION =
            "org.apache.myfaces.el.unified.resolver.managedbean.beansUnderConstruction";

    // adapted from Manfred's JSF 1.1 VariableResolverImpl
    protected static final Map<String, Scope> s_standardScopes = new HashMap<String, Scope>(16);

    static
    {
        s_standardScopes.put("request", new Scope()
        {
            public void put(FacesContext facesContext, ExternalContext extContext, String name, Object obj)
            {
                extContext.getRequestMap().put(name, obj);
            }
        });

        s_standardScopes.put("session", new Scope()
        {
            public void put(FacesContext facesContext, ExternalContext extContext, String name, Object obj)
            {
                extContext.getSessionMap().put(name, obj);
            }
        });

        s_standardScopes.put("application", new Scope()
        {
            public void put(FacesContext facesContext, ExternalContext extContext, String name, Object obj)
            {
                extContext.getApplicationMap().put(name, obj);
            }
        });

        s_standardScopes.put("none", new Scope()
        {
            public void put(FacesContext facesContext, ExternalContext extContext, String name, Object obj)
            {
                // do nothing
            }
        });
    }

    /**
     * Stores all scopes defined for this instance of <code>VariableResolver</code>
     * <p>
     * Can store instances of <code>Scope</code> which have the ability to dynamically resolve against ExternalContext
     * for put operations.
     * </p>
     * <p>
     * WARNING: this implementation is not serialized as it is thread safe because it does not update/add to _scopes
     * after object initialization. If you need to add your own scopes, either extend and add more in an initialization
     * block, or add proper sychronization
     * </p>
     */
    protected final Map<String, Scope> _scopes = new HashMap<String, Scope>(16);
    {
        _scopes.putAll(s_standardScopes);
    }

    /**
     * RuntimeConfig is instantiated once per servlet and never changes--we can safely cache it
     */
    private RuntimeConfig runtimeConfig;

    private ManagedBeanBuilder beanBuilder = new ManagedBeanBuilder();

    /** Creates a new instance of ManagedBeanResolver */
    public ManagedBeanResolver()
    {
    }

    @Override
    public void setValue(final ELContext context, final Object base, final Object property, final Object value)
        throws NullPointerException, PropertyNotFoundException, PropertyNotWritableException, ELException
    {

        if ((base == null) && (property == null))
        {
            throw new PropertyNotFoundException();
        }

    }

    @Override
    public boolean isReadOnly(final ELContext context, final Object base, final Object property)
        throws NullPointerException, PropertyNotFoundException, ELException
    {

        if ((base == null) && (property == null))
        {
            throw new PropertyNotFoundException();
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object getValue(final ELContext context, final Object base, final Object property)
        throws NullPointerException, PropertyNotFoundException, ELException
    {
        // we only resolve ManagedBean instances, not properties of those  
        if (base != null)
        {
            return null;
        }

        if (property == null)
        {
            throw new PropertyNotFoundException();
        }

        final FacesContext facesContext = facesContext(context);
        if (facesContext == null)
        {
            return null;
        }
        final ExternalContext extContext = facesContext.getExternalContext();
        if (extContext == null)
        {
            return null;
        }

        final boolean startup = (extContext instanceof StartupServletExternalContextImpl);
        
        // request scope (not available at startup)
        if (!startup)
        {
            if (extContext.getRequestMap().containsKey(property))
            {
                return null;
            }
        }

        // session scope (not available at startup)
        if (!startup)
        {
            if (extContext.getSessionMap().containsKey(property))
            {
                return null;
            }
        }

        // application scope
        if (extContext.getApplicationMap().containsKey(property))
        {
            return null;
        }

        // not found in standard scopes - get ManagedBean metadata object
        // In order to get the metadata object, we need property to be the managed bean name (--> String)
        if (!(property instanceof String))
        {
            return null;
        }
        final String strProperty = (String) property;

        final ManagedBean managedBean = runtimeConfig(context).getManagedBean(strProperty);
        Object beanInstance = null;
        if (managedBean != null)
        {
            context.setPropertyResolved(true);
            
            beanInstance = createManagedBean(managedBean, facesContext);
        }

        return beanInstance;
    }

    // Create a managed bean. If the scope of the bean is "none" then
    // return it right away. Otherwise store the bean in the appropriate
    // scope and return null.
    //
    // adapted from Manfred's JSF 1.1 VariableResolverImpl
    @SuppressWarnings("unchecked")
    private Object createManagedBean(final ManagedBean managedBean, final FacesContext facesContext) throws ELException
    {

        final ExternalContext extContext = facesContext.getExternalContext();
        final Map requestMap = extContext.getRequestMap();

        // check for cyclic references
        List beansUnderConstruction = (List<String>) requestMap.get(BEANS_UNDER_CONSTRUCTION);
        if (beansUnderConstruction == null)
        {
            beansUnderConstruction = new ArrayList<String>();
            requestMap.put(BEANS_UNDER_CONSTRUCTION, beansUnderConstruction);
        }

        final String managedBeanName = managedBean.getManagedBeanName();
        if (beansUnderConstruction.contains(managedBeanName))
        {
            throw new ELException("Detected cyclic reference to managedBean " + managedBeanName);
        }

        beansUnderConstruction.add(managedBeanName);

        Object obj = null;
        try
        {
            obj = beanBuilder.buildManagedBean(facesContext, managedBean);
        }
        finally
        {
            beansUnderConstruction.remove(managedBeanName);
        }

        putInScope(managedBean, facesContext, extContext, obj);

        return obj;
    }

    @SuppressWarnings("unchecked")
    private void putInScope(final ManagedBean managedBean, final FacesContext facesContext,
            final ExternalContext extContext, final Object obj)
    {

        final String managedBeanName = managedBean.getManagedBeanName();

        if (obj == null)
        {
            if (log.isDebugEnabled())
                log.debug("Variable '" + managedBeanName + "' could not be resolved.");
        }
        else
        {
            final String scopeKey = managedBean.getManagedBeanScope();

            // find the scope handler object
            final Scope scope = _scopes.get(scopeKey);
            if (scope != null)
            {
                scope.put(facesContext, extContext, managedBeanName, obj);
            }
            else

            {
                log.error("Managed bean '" + managedBeanName + "' has illegal scope: " + scopeKey);
            }
        }

    }

    // get the FacesContext from the ELContext
    private static FacesContext facesContext(final ELContext context)
    {
        return (FacesContext)context.getContext(FacesContext.class);
    }

    @Override
    public Class<?> getType(final ELContext context, final Object base, final Object property)
        throws NullPointerException, PropertyNotFoundException, ELException
    {

        if ((base == null) && (property == null))
        {
            throw new PropertyNotFoundException();
        }

        return null;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(final ELContext context, final Object base)
    {

        if (base != null)
            return null;

        final ArrayList<FeatureDescriptor> descriptors = new ArrayList<FeatureDescriptor>();

        final Map<String, ManagedBean> managedBeans = runtimeConfig(context).getManagedBeans();
        for (Map.Entry<String, ManagedBean> managedBean : managedBeans.entrySet())
        {
            descriptors.add(makeDescriptor(managedBean.getKey(), managedBean.getValue()));
        }

        return descriptors.iterator();
    }

    private static FeatureDescriptor makeDescriptor(final String beanName, final ManagedBean managedBean)
    {
        final FeatureDescriptor fd = new FeatureDescriptor();
        fd.setValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME, Boolean.TRUE);
        fd.setValue(ELResolver.TYPE, managedBean.getManagedBeanClass());
        fd.setName(beanName);
        fd.setDisplayName(beanName);
        fd.setShortDescription(managedBean.getDescription());
        fd.setExpert(false);
        fd.setHidden(false);
        fd.setPreferred(true);
        return fd;
    }

    protected RuntimeConfig runtimeConfig(final ELContext context)
    {
        final FacesContext facesContext = facesContext(context);

        // application-level singleton - we can safely cache this
        if (this.runtimeConfig == null)
        {
            this.runtimeConfig = RuntimeConfig.getCurrentInstance(facesContext.getExternalContext());
        }

        return runtimeConfig;
    }

    @Override
    public Class<?> getCommonPropertyType(final ELContext context, final Object base)
    {
        if (base == null)
        {
            return Object.class;
        }

        return null;
    }

    interface Scope
    {
        public void put(FacesContext facesContext, ExternalContext extContext, String name, Object obj);
    }
}
