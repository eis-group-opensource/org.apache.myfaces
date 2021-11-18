/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.el.ELContext;
import javax.el.ELResolver;

/**
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 1136229 $ $Date: 2011-06-16 01:38:34 +0300 (Thu, 16 Jun 2011) $
 */
public class CompositeELResolver extends javax.el.CompositeELResolver
{
    private Collection<ELResolver> _elResolvers;

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(final ELContext context, final Object base)
    {
        Collection<ELResolver> resolvers = _elResolvers;
        if (resolvers == null)
        {
            resolvers = Collections.emptyList();
        }
        
        return new CompositeIterator(context, base, resolvers.iterator());
    }

    /**
     * @param elResolver
     */
    @Override
    public final synchronized void add(final ELResolver elResolver)
    {
        super.add(elResolver);

        if (_elResolvers == null)
        {
            _elResolvers = new ArrayList<ELResolver>();
        }

        _elResolvers.add(elResolver);
    }

    private static class CompositeIterator implements Iterator<FeatureDescriptor>
    {
        private final ELContext _context;
        private final Object _base;
        private final Iterator<ELResolver> _elResolvers;

        private FeatureDescriptor _nextFD;

        private Iterator<FeatureDescriptor> _currentFDIter;

        public CompositeIterator(final ELContext context, final Object base, final Iterator<ELResolver> elResolvers)
        {
            _context = context;
            _base = base;
            _elResolvers = elResolvers;
        }

        public boolean hasNext()
        {
            if (_nextFD != null)
                return true;
            if (_currentFDIter != null)
            {
                while (_nextFD == null && _currentFDIter.hasNext())
                {
                    _nextFD = _currentFDIter.next();
                }
            }
            if (_nextFD == null)
            {
                if (_elResolvers.hasNext())
                {
                    _currentFDIter = _elResolvers.next().getFeatureDescriptors(_context, _base);
                }
                else
                {
                    return false;
                }
            }
            return hasNext();
        }

        public FeatureDescriptor next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            FeatureDescriptor next = _nextFD;
            _nextFD = null;
            return next;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

    }
}
