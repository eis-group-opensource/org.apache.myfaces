/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.webapp;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.faces.FactoryFinder;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFWebConfigParam;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 888211 $ $Date: 2009-12-08 03:07:19 +0200 (Tue, 08 Dec 2009) $
 */
public final class FacesServlet
        implements Servlet
{
    private static final Log log = LogFactory.getLog(FacesServlet.class);
    
    /**
     * Comma separated list of URIs of (additional) faces config files.
     * (e.g. /WEB-INF/my-config.xml)See JSF 1.0 PRD2, 10.3.2
     * Attention: You do not need to put /WEB-INF/faces-config.xml in here.
     */
    @JSFWebConfigParam(since="1.1")
    public static final String CONFIG_FILES_ATTR = "javax.faces.CONFIG_FILES";

    /**
     * Identify the Lifecycle instance to be used.
     */
    @JSFWebConfigParam(since="1.1")
    public static final String LIFECYCLE_ID_ATTR = "javax.faces.LIFECYCLE_ID";

    private static final String SERVLET_INFO = "FacesServlet of the MyFaces API implementation";
    
    /**
     * Indicate if myfaces is responsible to handle errors. 
     * See http://wiki.apache.org/myfaces/Handling_Server_Errors for details. 
     */
    @JSFWebConfigParam(defaultValue="true",expectedValues="true,false", since="1.2.4")
    private static final String ERROR_HANDLING_PARAMETER = "org.apache.myfaces.ERROR_HANDLING";
    
    /**
     * If you want to choose a different class for handling the exception.
     * <p> 
     * The error-handler needs to include the following methods:
     * </p>
     * <ul>
     * <li>handleException(FacesContext fc, Exception ex)</li>
     * <li>handleExceptionList(FacesContext facesContext, List exceptionList)</li>
     * <li>handleThrowable(FacesContext facesContext, Throwable ex)</li>
     * </ul>
     */
    @JSFWebConfigParam(since="1.2.4")
    private static final String ERROR_HANDLER_PARAMETER = "org.apache.myfaces.ERROR_HANDLER";
    private static final String ERROR_HANDLING_EXCEPTION_LIST = "org.apache.myfaces.errorHandling.exceptionList";


    private ServletConfig _servletConfig;
    private FacesContextFactory _facesContextFactory;
    private Lifecycle _lifecycle;

    public FacesServlet()
    {
        super();
    }

    public void destroy()
    {
        _servletConfig = null;
        _facesContextFactory = null;
        _lifecycle = null;
        if(log.isTraceEnabled()) log.trace("destroy");
    }

    public ServletConfig getServletConfig()
    {
        return _servletConfig;
    }

    public String getServletInfo()
    {
        return SERVLET_INFO;
    }

    private String getLifecycleId()
    {
        //1. check for Servlet's init-param
        //2. check for global context parameter
        //3. use default Lifecycle Id, if none of them was provided
        String serLifecycleId = _servletConfig.getInitParameter(LIFECYCLE_ID_ATTR);
        String appLifecycleId = _servletConfig.getServletContext().getInitParameter(LIFECYCLE_ID_ATTR);
        appLifecycleId = serLifecycleId == null ? appLifecycleId : serLifecycleId;
        return appLifecycleId != null ? appLifecycleId : LifecycleFactory.DEFAULT_LIFECYCLE;
    }

    public void init(ServletConfig servletConfig)
            throws ServletException
    {
        if(log.isTraceEnabled()) log.trace("init begin");
        _servletConfig = servletConfig;
        _facesContextFactory = (FacesContextFactory)FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        //TODO: null-check for Weblogic, that tries to initialize Servlet before ContextListener

        //Javadoc says: Lifecycle instance is shared across multiple simultaneous requests, it must be implemented in a thread-safe manner.
        //So we can acquire it here once:
        LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        _lifecycle = lifecycleFactory.getLifecycle(getLifecycleId());
        if(log.isTraceEnabled()) log.trace("init end");
    }

    public void service(ServletRequest request,
                        ServletResponse response)
            throws IOException,
                   ServletException
    {

        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        String pathInfo = httpRequest.getPathInfo();

        // if it is a prefix mapping ...
        if (pathInfo != null
                && (pathInfo.startsWith("/WEB-INF") || pathInfo
                        .startsWith("/META-INF")))
        {
            StringBuffer buffer = new StringBuffer();

            buffer.append(" Someone is trying to access a secure resource : ").append(pathInfo);
            buffer.append("\n remote address is ").append(httpRequest.getRemoteAddr());
            buffer.append("\n remote host is ").append(httpRequest.getRemoteHost());
            buffer.append("\n remote user is ").append(httpRequest.getRemoteUser());
            buffer.append("\n request URI is ").append(httpRequest.getRequestURI());

            log.warn(buffer.toString());

            // Why does RI return a 404 and not a 403, SC_FORBIDDEN ?

            ((HttpServletResponse) response)
                    .sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if(log.isTraceEnabled()) log.trace("service begin");

        FacesContext facesContext = prepareFacesContext(request, response);

        try {
            _lifecycle.execute(facesContext);

            if (!handleQueuedExceptions(facesContext))
            {
                _lifecycle.render(facesContext);
            }
        }
        catch (Exception e)
        {
            handleLifecycleException(facesContext, e);
        }
        catch (Throwable e)
        {
            handleLifecycleThrowable(facesContext, e);
        }
        finally
        {
            facesContext.release();
        }
        if(log.isTraceEnabled()) log.trace("service end");
    }

    /**This method makes sure we see an exception page also
     * if an exception has been thrown in UIInput.updateModel();
     * In this method, according to the spec, we may not rethrow the
     * exception, so we add it to a list and process it here.
     *
     * Attention: if you use redirects, the exceptions will get lost - exactly
     * like in the case of FacesMessages. If you want them to be taken over to the
     * next request, you should try the redirect-tracker of MyFaces.
     *
     * @param facesContext
     * @throws FacesException
     */
    private boolean handleQueuedExceptions(FacesContext facesContext) throws IOException, ServletException {
        List li = (List)
                facesContext.getExternalContext().getRequestMap().get(ERROR_HANDLING_EXCEPTION_LIST);

        if(li != null && li.size()>=1)  {
            //todo: for now, we only handle the first exception out of the list - we just rethrow this
            //first exception.
            //in the end, we should enable the error handler to show all the exceptions at once
            boolean errorHandling = getBooleanValue(facesContext.getExternalContext().getInitParameter(ERROR_HANDLING_PARAMETER), true);

            if(errorHandling) {
                String errorHandlerClass = facesContext.getExternalContext().getInitParameter(ERROR_HANDLER_PARAMETER);            
                if(errorHandlerClass != null) {
                    try {
                        Class clazz = Class.forName(errorHandlerClass);

                        Object errorHandler = clazz.newInstance();

                        Method m = clazz.getMethod("handleExceptionList", new Class[]{FacesContext.class,List.class});
                        m.invoke(errorHandler, new Object[]{facesContext, li});
                    }
                    catch(ClassNotFoundException ex) {
                        throw new ServletException("Error-Handler : " +errorHandlerClass+ " was not found. Fix your web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                    } catch (IllegalAccessException ex) {
                        throw new ServletException("Constructor of error-Handler : " +errorHandlerClass+ " is not accessible. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                    } catch (InstantiationException ex) {
                        throw new ServletException("Error-Handler : " +errorHandlerClass+ " could not be instantiated. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                    } catch (NoSuchMethodException ex) {
                        //Handle in the old way, since no custom method handleExceptionList found,
                        //throwing the first FacesException on the list.
                        throw (FacesException) li.get(0);
                    } catch (InvocationTargetException ex) {
                        throw new ServletException("Excecution of method handleException in Error-Handler : " +errorHandlerClass+ " threw an exception. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                    }
                }
                else {
                    _ErrorPageWriter.handleExceptionList(facesContext, li);
                }
            }
            else {
                _ErrorPageWriter.throwException((Exception) li.get(0));
            }
            return true;
        }
        return false;
    }

    private void handleLifecycleException(FacesContext facesContext, Exception e) throws IOException, ServletException {

        boolean errorHandling = getBooleanValue(facesContext.getExternalContext().getInitParameter(ERROR_HANDLING_PARAMETER), true);

        if(errorHandling) {
            String errorHandlerClass = facesContext.getExternalContext().getInitParameter(ERROR_HANDLER_PARAMETER);
            if(errorHandlerClass != null) {
                try {
                    Class clazz = Class.forName(errorHandlerClass);

                    Object errorHandler = clazz.newInstance();

                    Method m = clazz.getMethod("handleException", new Class[]{FacesContext.class,Exception.class});
                    m.invoke(errorHandler, new Object[]{facesContext, e});
                }
                catch(ClassNotFoundException ex) {
                    throw new ServletException("Error-Handler : " +errorHandlerClass+ " was not found. Fix your web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                } catch (IllegalAccessException ex) {
                    throw new ServletException("Constructor of error-Handler : " +errorHandlerClass+ " is not accessible. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                } catch (InstantiationException ex) {
                    throw new ServletException("Error-Handler : " +errorHandlerClass+ " could not be instantiated. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                } catch (NoSuchMethodException ex) {
                    log.error("Error-Handler : " +errorHandlerClass+ " did not have a method with name : handleException and parameters : javax.faces.context.FacesContext, java.lang.Exception. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                    //Try to look if it is implemented more general method handleThrowable
                    handleLifecycleThrowable(facesContext, e);
                } catch (InvocationTargetException ex) {
                    throw new ServletException("Excecution of method handleException in Error-Handler : " +errorHandlerClass+ " caused an exception. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                }
            }
            else {
                _ErrorPageWriter.handleException(facesContext, e);
            }
        }
        else {
            _ErrorPageWriter.throwException(e);
        }
    }
    
    private void handleLifecycleThrowable(FacesContext facesContext, Throwable e) throws IOException, ServletException {

        boolean errorHandling = getBooleanValue(facesContext.getExternalContext().getInitParameter(ERROR_HANDLING_PARAMETER), true);

        if(errorHandling) {
            String errorHandlerClass = facesContext.getExternalContext().getInitParameter(ERROR_HANDLER_PARAMETER);            
            if(errorHandlerClass != null) {
                try {
                    Class clazz = Class.forName(errorHandlerClass);

                    Object errorHandler = clazz.newInstance();

                    Method m = clazz.getMethod("handleThrowable", new Class[]{FacesContext.class,Throwable.class});
                    m.invoke(errorHandler, new Object[]{facesContext, e});
                }
                catch(ClassNotFoundException ex) {
                    throw new ServletException("Error-Handler : " +errorHandlerClass+ " was not found. Fix your web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                } catch (IllegalAccessException ex) {
                    throw new ServletException("Constructor of error-Handler : " +errorHandlerClass+ " is not accessible. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                } catch (InstantiationException ex) {
                    throw new ServletException("Error-Handler : " +errorHandlerClass+ " could not be instantiated. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                } catch (NoSuchMethodException ex) {
                    throw new ServletException("Error-Handler : " +errorHandlerClass+ " did not have a method with name : handleException and parameters : javax.faces.context.FacesContext, java.lang.Exception. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                } catch (InvocationTargetException ex) {
                    throw new ServletException("Excecution of method handleException in Error-Handler : " +errorHandlerClass+ " threw an exception. Error-Handler is specified in web.xml-parameter : "+ERROR_HANDLER_PARAMETER,ex);
                }
            }
            else {
                _ErrorPageWriter.handleThrowable(facesContext, e);
            }
        }
        else {
            _ErrorPageWriter.throwException(e);
        }
    }

    private static boolean getBooleanValue(String initParameter, boolean defaultVal) {

        if(initParameter == null || initParameter.trim().length()==0)
            return defaultVal;

        return (initParameter.equalsIgnoreCase("on") || initParameter.equals("1") || initParameter.equalsIgnoreCase("true"));
    }

    private FacesContext prepareFacesContext(ServletRequest request, ServletResponse response) {
        FacesContext facesContext
                = _facesContextFactory.getFacesContext(_servletConfig.getServletContext(),
                                                       request,
                                                       response,
                                                       _lifecycle);
        return facesContext;
    }
}
