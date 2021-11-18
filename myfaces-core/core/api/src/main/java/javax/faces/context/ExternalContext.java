/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.context;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 827879 $ $Date: 2009-10-21 06:10:03 +0300 (Wed, 21 Oct 2009) $
 */
public abstract class ExternalContext
{
    public static final String BASIC_AUTH = "BASIC";
    public static final String CLIENT_CERT_AUTH = "CLIENT_CERT";
    public static final String DIGEST_AUTH = "DIGEST";
    public static final String FORM_AUTH = "FORM";
    
    private static ThreadLocal<ExternalContext> _firstInstance = new ThreadLocal<ExternalContext>();

    public abstract void dispatch(String path)
            throws java.io.IOException;

    public abstract String encodeActionURL(String url);

    public abstract String encodeNamespace(String name);

    public abstract String encodeResourceURL(String url);

    public abstract Map<String, Object> getApplicationMap();

    public abstract String getAuthType();

    public abstract Object getContext();

    public abstract String getInitParameter(String name);

    public abstract Map getInitParameterMap();

    public abstract String getRemoteUser();

    public abstract Object getRequest();
    
    public String getRequestCharacterEncoding()
    {
        ExternalContext ctx = _firstInstance.get();
        
        if (ctx == null)
        {
            throw new UnsupportedOperationException();
        }
        
        return ctx.getRequestCharacterEncoding();
    }
    
    public String getRequestContentType()
    {
        ExternalContext ctx = _firstInstance.get();
        
        if (ctx == null)
        {
            throw new UnsupportedOperationException();
        }
        
        return ctx.getRequestContentType();
    }
    
    public abstract String getRequestContextPath();

    public abstract Map<String, Object> getRequestCookieMap();

    public abstract Map<String, String> getRequestHeaderMap();

    public abstract Map<String, String[]> getRequestHeaderValuesMap();

    public abstract Locale getRequestLocale();

    public abstract Iterator<Locale> getRequestLocales();

    public abstract Map<String, Object> getRequestMap();

    public abstract Map<String, String> getRequestParameterMap();

    public abstract Iterator<String> getRequestParameterNames();

    public abstract Map<String, String[]> getRequestParameterValuesMap();

    public abstract String getRequestPathInfo();

    public abstract String getRequestServletPath();

    public abstract java.net.URL getResource(String path)
            throws java.net.MalformedURLException;

    public abstract java.io.InputStream getResourceAsStream(String path);

    public abstract Set<String> getResourcePaths(String path);

    public abstract Object getResponse();
    
    /**
     * throws <code>UnsupportedOperationException</code> by default.
     * @since JSF 1.2
     */
    public String getResponseContentType()
    {
        ExternalContext ctx = _firstInstance.get();
        
        if (ctx == null)
        {
            throw new UnsupportedOperationException();
        }
        
        return ctx.getResponseContentType();
    }

    public abstract Object getSession(boolean create);

    public abstract Map<String, Object> getSessionMap();

    public abstract java.security.Principal getUserPrincipal();

    /**
     * throws <code>UnsupportedOperationException</code> by default.
     * @since JSF 1.2
     * @param request
     */
    public void setRequest(java.lang.Object request)
    {
        ExternalContext ctx = _firstInstance.get();
        
        if (ctx == null)
        {
            throw new UnsupportedOperationException();
        }
        
        ctx.setRequest(request);
    }
    
    /**
     * throws <code>UnsupportedOperationException</code> by default.
     * @since JSF 1.2
     * @param encoding
     * @throws java.io.UnsupportedEncodingException
     */
    public void setRequestCharacterEncoding(java.lang.String encoding)
            throws java.io.UnsupportedEncodingException
    {
        ExternalContext ctx = _firstInstance.get();
        
        if (ctx == null)
        {
            throw new UnsupportedOperationException();
        }
        
        ctx.setRequestCharacterEncoding(encoding);
    }
    
    /**
     * throws <code>UnsupportedOperationException</code> by default.
     * @since JSF 1.2
     * @param response
     */
    public void setResponse(java.lang.Object response)
    {
        ExternalContext ctx = _firstInstance.get();
        
        if (ctx == null)
        {
            throw new UnsupportedOperationException();
        }
        
        ctx.setResponse(response);
    }
    
    /**
     * throws <code>UnsupportedOperationException</code> by default.
     * @since JSF 1.2
     * @param encoding
     */
    public void setResponseCharacterEncoding(java.lang.String encoding)
    {
        ExternalContext ctx = _firstInstance.get();
        
        if (ctx == null)
        {
            throw new UnsupportedOperationException();
        }
        
        ctx.setResponseCharacterEncoding(encoding);
    }
    
    public String getResponseCharacterEncoding()
    {
        ExternalContext ctx = _firstInstance.get();
        
        if (ctx == null)
        {
            throw new UnsupportedOperationException("JSF 1.2 : figure out how to tell if this is a Portlet request");
        }
        
        return ctx.getResponseCharacterEncoding();
    }
    
    public abstract boolean isUserInRole(String role);

    public abstract void log(String message);

    public abstract void log(String message,
                             Throwable exception);

    public abstract void redirect(String url)
            throws java.io.IOException;
}
