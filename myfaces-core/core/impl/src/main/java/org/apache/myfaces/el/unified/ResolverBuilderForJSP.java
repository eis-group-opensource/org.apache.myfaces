/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified;

import java.util.ArrayList;
import java.util.List;

import javax.el.CompositeELResolver;
import javax.el.ELResolver;

import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.el.unified.resolver.ManagedBeanResolver;
import org.apache.myfaces.el.unified.resolver.ResourceBundleResolver;
import org.apache.myfaces.el.unified.resolver.implicitobject.ImplicitObjectResolver;

/**
 * build the el resolver for jsp. see 1.2 spec section 5.6.1
 * 
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 1136229 $ $Date: 2011-06-16 01:38:34 +0300 (Thu, 16 Jun 2011) $
 */
@SuppressWarnings("deprecation")
public class ResolverBuilderForJSP extends ResolverBuilderBase implements ELResolverBuilder
{
    public ResolverBuilderForJSP(RuntimeConfig config)
    {
        super(config);
    }

    public void build(CompositeELResolver compositeElResolver)
    {
        // add the ELResolvers to a List first to be able to sort them
        List<ELResolver> list = new ArrayList<ELResolver>();
        
        list.add(ImplicitObjectResolver.makeResolverForJSP());
        list.add(new ManagedBeanResolver());
        list.add(new ResourceBundleResolver());

        addFromRuntimeConfig(list);
        
        // give the user a chance to sort the resolvers
        sortELResolvers(list);
        
        // add the resolvers from the list to the CompositeELResolver
        for (ELResolver resolver : list)
        {
            compositeElResolver.add(resolver);
        }
    }

}
