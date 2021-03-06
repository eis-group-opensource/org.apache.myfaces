/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.trace;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Support class for tracing method calls providing params, exceptions and return types
 * 
 * @author Mathias Broekelmann (latest modification by $Author: skitching $)
 * @version $Revision: 676197 $ $Date: 2008-07-12 19:07:17 +0300 (12 Lie 2008) $
 */
public class TracingSupport
{
    private static final Object[] EMPTY_PARAMS = new Object[0];

    private Logger _logger;
    private Level _level = Level.FINE;
    private String _sourceClass;

    public TracingSupport()
    {
        this(Logger.getAnonymousLogger());
    }

    public TracingSupport(Class clazz)
    {
        this(clazz.getName());
    }

    public TracingSupport(String className)
    {
        this(Logger.getLogger(className));
    }

    public TracingSupport(Logger logger)
    {
        _logger = logger;
        _sourceClass = logger.getName();
    }

    /**
     * @return the log level
     */
    public Level getLevel()
    {
        return _level;
    }

    /**
     * 
     * @param level
     *            the log level to use
     */
    public void setLevel(Level level)
    {
        if (level == null)
        {
            throw new IllegalArgumentException("log level can not be null");
        }
        _level = level;
    }

    public <T> T trace(String methodName, Closure<T> closure)
    {
        return trace(methodName, closure, EMPTY_PARAMS);
    }

    public <T> T trace(String methodName, Closure<T> closure, Object... params)
    {
        if (_logger.isLoggable(_level))
        {
            _logger.logp(_level, _sourceClass, methodName, "ENTRY" + prepareParams(params), params);
            try
            {
                T result = closure.call();
                if (!Void.class.equals(result))
                {
                    _logger.logp(_level, _sourceClass, methodName, "RETURN {0}", result);
                }
                else
                {
                    _logger.logp(_level, _sourceClass, methodName, "RETURN");
                }
                return result;
            }
            catch (RuntimeException e)
            {
                _logger.logp(_level, _sourceClass, methodName, "THROW", e);
                throw e;
            }
        }
        else
        {
            return closure.call();
        }
    }

    private String prepareParams(Object[] params)
    {
        if (params == null || params.length == 0)
        {
            return "";
        }
        StringBuilder builder = new StringBuilder(" ");
        for (int i = 0, size = params.length; i < size; i++)
        {
            builder.append("{");
            builder.append(i);
            builder.append("}");
            if (i + 1 < size)
            {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    public Logger getLogger()
    {
        return _logger;
    }

    /**
     * @param logger
     *            the logger to set
     */
    public void setLogger(Logger logger)
    {
        if (logger == null)
        {
            throw new IllegalArgumentException("logger must not be null");
        }
        _logger = logger;
    }

    /**
     * @return the className
     */
    public String getSourceClass()
    {
        return _sourceClass;
    }

    /**
     * @param className
     *            the className to set
     */
    public void setSourceClass(String className)
    {
        if (className == null)
        {
            throw new IllegalArgumentException("className must not be null");
        }
        _sourceClass = className;
    }
}
