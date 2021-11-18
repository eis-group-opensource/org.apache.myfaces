/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.lifecycle;

import javax.faces.FacesException;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Manfred Geiler (latest modification by $Author: mmarinschek $)
 * @author Anton Koinov
 * @version $Revision: 580395 $ $Date: 2007-09-28 18:51:56 +0300 (Fri, 28 Sep 2007) $
 */
public class LifecycleFactoryImpl
        extends LifecycleFactory
{
    private final Map<String, Lifecycle> _lifecycles = new HashMap<String, Lifecycle>();

    public LifecycleFactoryImpl()
    {
        addLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE, new LifecycleImpl());
    }

    public void purgeLifecycle()
    {        
        _lifecycles.clear();
        addLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE, new LifecycleImpl());
    }

    public void addLifecycle(String id, Lifecycle lifecycle)
    {
        synchronized (_lifecycles)
        {
            if (_lifecycles.get(id) != null)
            {
                throw new IllegalArgumentException("Lifecycle with id '" + id + "' already exists.");
            }
            _lifecycles.put(id, lifecycle);
        }
    }

    public Lifecycle getLifecycle(String id)
            throws FacesException
    {
        synchronized (_lifecycles)
        {
            Lifecycle lifecycle = _lifecycles.get(id);
            if (lifecycle == null)
            {
                throw new IllegalArgumentException("Unknown lifecycle '" + id + "'.");
            }
            return lifecycle;
        }
    }

    public Iterator<String> getLifecycleIds()
    {
        return _lifecycles.keySet().iterator();
    }
}
