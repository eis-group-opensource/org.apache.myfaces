/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFWebConfigParam;
import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.el.convert.PropertyResolverToELResolver;
import org.apache.myfaces.el.convert.VariableResolverToELResolver;

/**
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 1136898 $ $Date: 2011-06-17 17:49:48 +0300 (Fri, 17 Jun 2011) $
 */
@SuppressWarnings("deprecation")
public class ResolverBuilderBase
{
    
    private static final Logger log = Logger.getLogger(ResolverBuilderBase.class.getName());
    
    @JSFWebConfigParam(since = "1.2.10, 2.0.2",
            desc = "The Class of an Comparator<ELResolver> implementation.")
    public static final String EL_RESOLVER_COMPARATOR = "org.apache.myfaces.EL_RESOLVER_COMPARATOR";
    
    private final RuntimeConfig _config;

    public ResolverBuilderBase(RuntimeConfig config)
    {
        _config = config;
    }

    /**
     * add the el resolvers from the faces config, the el resolver wrapper for variable resolver, the el resolver
     * wrapper for the property resolver and the el resolvers added by {@link Application#addELResolver(ELResolver)}.
     * The resolvers where only added if they are not null
     * 
     * @param elResolver
     *            the composite el resolver to which the resolvers where added
     */
    protected void addFromRuntimeConfig(List<ELResolver> resolvers)
    {
        if (_config.getFacesConfigElResolvers() != null)
        {
            for (ELResolver resolver : _config.getFacesConfigElResolvers())
            {
                resolvers.add(resolver);
            }
        }

        if (_config.getVariableResolver() != null)
        {
            resolvers.add(createELResolver(_config.getVariableResolver()));
        }
        else if (_config.getVariableResolverChainHead() != null)
        {
            resolvers.add(createELResolver(_config.getVariableResolverChainHead()));
        }

        if (_config.getPropertyResolver() != null)
        {
            resolvers.add(createELResolver(_config.getPropertyResolver()));
        }
        else if (_config.getPropertyResolverChainHead() != null)
        {
            resolvers.add(createELResolver(_config.getPropertyResolverChainHead()));
        }

        if (_config.getApplicationElResolvers() != null)
        {
            for (ELResolver resolver : _config.getApplicationElResolvers())
            {
                resolvers.add(resolver);
            }
        }
    }
    
    /**
     * Sort the ELResolvers with a custom Comparator provided by the user.
     * @param resolvers
     * @since 1.2.10, 2.0.2
     */
    @SuppressWarnings("unchecked")
    protected void sortELResolvers(List<ELResolver> resolvers)
    {
        if (_config.getELResolverComparator() != null)
        {
            try
            {
                // sort the resolvers
                Collections.sort(resolvers, _config.getELResolverComparator());
            }
            catch (Exception e)
            {
                log.log(Level.WARNING, 
                        "Could not sort ELResolvers with custom Comparator", e);
            }
        }
    }

    protected ELResolver createELResolver(VariableResolver resolver)
    {
        return new VariableResolverToELResolver(resolver);
    }

    protected ELResolver createELResolver(PropertyResolver resolver)
    {
        return new PropertyResolverToELResolver(resolver);
    }

}
