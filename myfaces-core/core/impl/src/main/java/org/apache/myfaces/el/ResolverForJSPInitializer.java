/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import java.util.Iterator;

import javax.faces.FactoryFinder;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.LifecycleFactory;

import org.apache.myfaces.el.unified.ELResolverBuilder;

/**
 * The class will initialize the resolver for JSP
 * 
 * @author Mathias Broekelmann (latest modification by $Author: bommel $)
 * @version $Revision: 693059 $ $Date: 2008-09-08 14:42:28 +0300 (Mon, 08 Sep 2008) $
 */
public final class ResolverForJSPInitializer implements PhaseListener
{
    private final ELResolverBuilder _resolverBuilder;
    private boolean initialized;
    private final javax.el.CompositeELResolver _resolverForJSP;

    public ResolverForJSPInitializer(final ELResolverBuilder resolverBuilder, final javax.el.CompositeELResolver resolverForJSP)
    {
        _resolverBuilder = resolverBuilder;
        _resolverForJSP = resolverForJSP;
    }

    public void beforePhase(final PhaseEvent event)
    {
        if (!initialized)
        {
            initialized = true;
            _resolverBuilder.build(_resolverForJSP);

            LifecycleFactory factory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
            for (Iterator<String> iter = factory.getLifecycleIds(); iter.hasNext();)
            {
                factory.getLifecycle(iter.next()).removePhaseListener(this);
            }
        }
    }

    public void afterPhase(final PhaseEvent event)
    {
    }

    public PhaseId getPhaseId()
    {
        return PhaseId.ANY_PHASE;
    }

}
