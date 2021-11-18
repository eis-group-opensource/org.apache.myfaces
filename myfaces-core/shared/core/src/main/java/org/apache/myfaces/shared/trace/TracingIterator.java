/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.trace;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Trace method calls to an iterator
 * 
 * @author Mathias Broekelmann (latest modification by $Author: skitching $)
 * @version $Revision: 676197 $ $Date: 2008-07-12 19:07:17 +0300 (12 Lie 2008) $
 */
public class TracingIterator<E> implements Iterator<E>
{
    private final Iterator<E> _iterator;

    private final TracingSupport _tracer;

    public TracingIterator(Iterator<E> iterator)
    {
        _iterator = iterator;
        _tracer = new TracingSupport(iterator.getClass());
    }

    public static <E> TracingIterator<E> create(Iterator<E> iterator)
    {
        return new TracingIterator<E>(iterator);
    }

    /**
     * @return the iterator
     */
    public Iterator<E> getIterator()
    {
        return _iterator;
    }

    public boolean hasNext()
    {
        return _tracer.trace("hasNext", new Closure<Boolean>()
        {
            public Boolean call()
            {
                return _iterator.hasNext();
            }
        });
    }

    public E next()
    {
        return _tracer.trace("next", new Closure<E>()
        {
            public E call()
            {
                return _iterator.next();
            }
        });
    }

    public void remove()
    {
        _tracer.trace("remove", new Closure<Object>()
        {
            public Object call()
            {
                _iterator.remove();
                return Void.class;
            }
        });
    }

    /**
     * @return
     * @see org.apache.myfaces.shared.trace.TracingSupport#getLevel()
     */
    public Level getLevel()
    {
        return _tracer.getLevel();
    }

    /**
     * @return
     * @see org.apache.myfaces.shared.trace.TracingSupport#getLogger()
     */
    public Logger getLogger()
    {
        return _tracer.getLogger();
    }

    /**
     * @return
     * @see org.apache.myfaces.shared.trace.TracingSupport#getSourceClass()
     */
    public String getSourceClass()
    {
        return _tracer.getSourceClass();
    }

    /**
     * @param level
     * @see org.apache.myfaces.shared.trace.TracingSupport#setLevel(java.util.logging.Level)
     */
    public void setLevel(Level level)
    {
        _tracer.setLevel(level);
    }

    /**
     * @param logger
     * @see org.apache.myfaces.shared.trace.TracingSupport#setLogger(java.util.logging.Logger)
     */
    public void setLogger(Logger logger)
    {
        _tracer.setLogger(logger);
    }

    /**
     * @param className
     * @see org.apache.myfaces.shared.trace.TracingSupport#setSourceClass(java.lang.String)
     */
    public void setSourceClass(String className)
    {
        _tracer.setSourceClass(className);
    }
}
