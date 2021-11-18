/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.webapp.FacesServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_impl.webapp.webxml.DelegatedFacesServlet;
import org.apache.myfaces.util.ContainerUtils;

/**
 * Derived FacesServlet that can be used for debugging purpose
 * and to fix the Weblogic startup issue (FacesServlet is initialized before ServletContextListener).
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 1067544 $ $Date: 2011-02-06 00:33:47 +0200 (Sun, 06 Feb 2011) $
 */
public class MyFacesServlet implements Servlet, DelegatedFacesServlet
{
    private static final Log log = LogFactory.getLog(MyFacesServlet.class);

    private final FacesServlet delegate = new FacesServlet();
    
    private FacesInitializer _facesInitializer;
    
    protected FacesInitializer getFacesInitializer()
    {
        if (_facesInitializer == null)
        {
            if (ContainerUtils.isJsp21()) 
            {
                _facesInitializer = new Jsp21FacesInitializer();
            } 
            else 
            {
                _facesInitializer = new Jsp20FacesInitializer();
            }
        }
        
        return _facesInitializer;
    }
    
    public void setFacesInitializer(FacesInitializer facesInitializer)
    {
        _facesInitializer = facesInitializer;
    }

    public void destroy()
    {
        delegate.destroy();
    }

    public ServletConfig getServletConfig()
    {
        return delegate.getServletConfig();
    }

    public String getServletInfo()
    {
        return delegate.getServletInfo();
    }

    public void init(ServletConfig servletConfig)
        throws ServletException
    {
        //Check, if ServletContextListener already called
        ServletContext servletContext = servletConfig.getServletContext();
        
        FacesInitializer facesInitializer = getFacesInitializer();
        
        // Create startup FacesContext before initializing
        FacesContext facesContext = facesInitializer.initStartupFacesContext(servletContext);

        Boolean b = (Boolean)servletContext.getAttribute(StartupServletContextListener.FACES_INIT_DONE);
        if (b == null || b.booleanValue() == false)
        {
            if(log.isWarnEnabled())
            {
                log.warn("ServletContextListener not yet called");
            }
            facesInitializer.initFaces(servletConfig.getServletContext());
        }
        
        // Destroy startup FacesContext
        facesInitializer.destroyStartupFacesContext(facesContext);
        
        delegate.init(servletConfig);
        log.info("MyFacesServlet for context '" + servletConfig.getServletContext().getRealPath("/") + "' initialized.");
    }
    
    public void service(ServletRequest request, ServletResponse response)
            throws IOException,
                   ServletException
    {
        if (log.isTraceEnabled()) log.trace("MyFacesServlet service start");
        delegate.service(request, response);
        if (log.isTraceEnabled()) log.trace("MyFacesServlet service finished");
    }

}
