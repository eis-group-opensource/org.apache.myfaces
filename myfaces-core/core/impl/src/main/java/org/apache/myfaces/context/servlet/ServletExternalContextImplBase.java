/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;

import org.apache.myfaces.context.ReleaseableExternalContext;

/**
 * Provides a base implementation of the ExternalContext for Servlet
 * environments. This impl provides all methods which only rely on the
 * ServletContext and thus are also provided at startup and shutdown.
 * 
 * @author Jakob Korherr (latest modification by $Author: jakobk $)
 * @version $Revision: 956589 $ $Date: 2010-06-21 08:43:21 -0500 (Lun, 21 Jun 2010) $
 */
public abstract class ServletExternalContextImplBase extends ExternalContext
        implements ReleaseableExternalContext
{
    
    private static final String INIT_PARAMETER_MAP_ATTRIBUTE = InitParameterMap.class.getName();
    
    private ServletContext _servletContext;
    private Map<String, Object> _applicationMap;
    private Map<String, String> _initParameterMap;
    
    public ServletExternalContextImplBase(ServletContext servletContext)
    {
        _servletContext = servletContext;
        _applicationMap = null;
        _initParameterMap = null;
    }
    
    public void release()
    {
        _servletContext = null;
        _applicationMap = null;
        _initParameterMap = null;
    }
    
    // ~ Methods which only rely on the ServletContext-------------------------
    
    @Override
    public Map<String, Object> getApplicationMap()
    {
        if (_applicationMap == null)
        {
            _applicationMap = new ApplicationMap(_servletContext);
        }
        return _applicationMap;
    }
    
    @Override
    public Object getContext()
    {
        return _servletContext;
    }
    
    @Override
    public String getInitParameter(final String s)
    {
        return _servletContext.getInitParameter(s);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> getInitParameterMap()
    {
        if (_initParameterMap == null)
        {
            // We cache it as an attribute in ServletContext itself (is this circular reference a problem?)
            if ((_initParameterMap = (Map<String, String>) _servletContext.getAttribute(INIT_PARAMETER_MAP_ATTRIBUTE)) == null)
            {
                _initParameterMap = new InitParameterMap(_servletContext);
                _servletContext.setAttribute(INIT_PARAMETER_MAP_ATTRIBUTE, _initParameterMap);
            }
        }
        return _initParameterMap;
    }
    
    @Override
    public URL getResource(final String path) throws MalformedURLException
    {
        checkNull(path, "path");
        return _servletContext.getResource(path);
    }

    @Override
    public InputStream getResourceAsStream(final String path)
    {
        checkNull(path, "path");
        return _servletContext.getResourceAsStream(path);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getResourcePaths(final String path)
    {
        checkNull(path, "path");
        return _servletContext.getResourcePaths(path);
    }

    @Override
    public void log(final String message)
    {
        checkNull(message, "message");
        _servletContext.log(message);
    }

    @Override
    public void log(final String message, final Throwable exception)
    {
        checkNull(message, "message");
        checkNull(exception, "exception");
        _servletContext.log(message, exception);
    }
    
    // ~ Methods which verify some required behavior---------------------------
    
    protected void checkNull(final Object o, final String param)
    {
        if (o == null)
        {
            throw new NullPointerException(param + " can not be null.");
        }
    }
    
}
