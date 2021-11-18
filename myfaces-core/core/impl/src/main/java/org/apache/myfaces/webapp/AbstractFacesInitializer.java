/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import java.util.List;
import java.util.Locale;

import javax.el.ExpressionFactory;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.application.ApplicationImpl;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFWebConfigParam;
import org.apache.myfaces.config.FacesConfigValidator;
import org.apache.myfaces.config.FacesConfigurator;
import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.context.ReleaseableExternalContext;
import org.apache.myfaces.context.servlet.ServletExternalContextImpl;
import org.apache.myfaces.context.servlet.StartupFacesContextImpl;
import org.apache.myfaces.context.servlet.StartupServletExternalContextImpl;
import org.apache.myfaces.shared_impl.util.StateUtils;
import org.apache.myfaces.shared_impl.webapp.webxml.WebXml;

/**
 * Performs common initialization tasks.
 *
 */
public abstract class AbstractFacesInitializer implements FacesInitializer
{
    /**
     * The logger instance for this class.
     */
    private static final Log log = LogFactory.getLog(AbstractFacesInitializer.class);
    
    /**
     * This parameter specifies the ExpressionFactory implementation to use.
     */
    @JSFWebConfigParam(since="1.2.7")
    protected static final String EXPRESSION_FACTORY = "org.apache.myfaces.EXPRESSION_FACTORY";

    /**
     * Performs all necessary initialization tasks like configuring this JSF
     * application.
     */
    public void initFaces(ServletContext servletContext)
    {
        try {
            if (log.isTraceEnabled()) {
                log.trace("Initializing MyFaces");
            }

            // Some parts of the following configuration tasks have been implemented 
            // by using an ExternalContext. However, that's no problem as long as no 
            // one tries to call methods depending on either the ServletRequest or 
            // the ServletResponse.
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                //new ServletExternalContextImpl(servletContext, null, null);

            // Parse and validate the web.xml configuration file
            WebXml webXml = WebXml.getWebXml(externalContext);
            if (webXml == null) {
                if (log.isWarnEnabled()) {
                    log.warn("Couldn't find the web.xml configuration file. "
                            + "Abort initializing MyFaces.");
                }

                return;
            } else if (webXml.getFacesServletMappings().isEmpty()) {
                if (log.isWarnEnabled()) {
                    log.warn("No mappings of FacesServlet found. Abort initializing MyFaces.");
                }

                return;
            }
            
            initContainerIntegration(servletContext, externalContext);

            String useEncryption = servletContext.getInitParameter(StateUtils.USE_ENCRYPTION);
            if (!"false".equals(useEncryption)) { // the default value is true
                StateUtils.initSecret(servletContext);
            }

            if (log.isInfoEnabled()) {
                log.info("ServletContext '" + servletContext.getRealPath("/") + "' initialized.");
            }
        } catch (Exception ex) {
            log.error("An error occured while initializing MyFaces: "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * Cleans up all remaining resources (well, theoretically).
     * 
     */
    public void destroyFaces(ServletContext servletContext)
    {
        // TODO is it possible to make a real cleanup?
    }

    /**
     * Configures this JSF application. It's required that every
     * FacesInitializer (i.e. every subclass) calls this method during
     * initialization.
     * 
     * @param servletContext
     *            the current ServletContext
     * @param externalContext
     *            the current ExternalContext
     * @param expressionFactory
     *            the ExpressionFactory to use
     * 
     * @return the current runtime configuration
     */
    protected RuntimeConfig buildConfiguration(ServletContext servletContext,
            ExternalContext externalContext, ExpressionFactory expressionFactory)
    {
        RuntimeConfig runtimeConfig = RuntimeConfig.getCurrentInstance(externalContext);
        runtimeConfig.setExpressionFactory(expressionFactory);
        
        //ApplicationImpl.setInitializingRuntimeConfig(runtimeConfig);
        
        // And configure everything
        new FacesConfigurator(externalContext).configure();
        
        validateFacesConfig(servletContext, externalContext);
        
        return runtimeConfig;
    }
    
    protected void validateFacesConfig(ServletContext servletContext, ExternalContext externalContext)
    {
        String validate = servletContext.getInitParameter(FacesConfigValidator.VALIDATE_CONTEXT_PARAM);
        if ("true".equals(validate) && log.isWarnEnabled()) { // the default value is false
            List<String> warnings = FacesConfigValidator.validate(
                    externalContext, servletContext.getRealPath("/"));
            
            for (String warning : warnings) {
                log.warn(warning);
            }
        }
    }
    
    /**
     * Try to load user-definied ExpressionFactory. Returns <code>null</code>,
     * if no custom ExpressionFactory was specified. 
     * 
     * @param externalContext the current ExternalContext
     * 
     * @return User-specified ExpressionFactory, or 
     *          <code>null</code>, if no no custom implementation was specified
     * 
     */
    protected static ExpressionFactory getUserDefinedExpressionFactory(ExternalContext externalContext)
    {
        String expressionFactoryClassName = externalContext.getInitParameter(EXPRESSION_FACTORY);
        if (expressionFactoryClassName != null
                && expressionFactoryClassName.trim().length() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("Attempting to load the ExpressionFactory implementation " 
                        + "you've specified: '" + expressionFactoryClassName + "'.");
            }
            
            return loadExpressionFactory(expressionFactoryClassName);
        }
        
        return null;
    }
    
    /**
     * Loads and instantiates the given ExpressionFactory implementation.
     * 
     * @param expressionFactoryClassName
     *            the class name of the ExpressionFactory implementation
     * 
     * @return the newly created ExpressionFactory implementation, or
     *         <code>null</code>, if an error occurred
     */
    protected static ExpressionFactory loadExpressionFactory(String expressionFactoryClassName) 
    {
       try {
           Class<?> expressionFactoryClass = Class.forName(expressionFactoryClassName);
           return (ExpressionFactory) expressionFactoryClass.newInstance();
       } catch (Exception ex) {
           if (log.isDebugEnabled()) {
               log.debug("An error occured while instantiating a new ExpressionFactory. " 
                   + "Attempted to load class '" + expressionFactoryClassName + "'.", ex);
           }
       }
       
       return null;
    }
    
    public FacesContext initStartupFacesContext(ServletContext servletContext)
    {
        // We cannot use FacesContextFactory, because it is necessary to initialize 
        // before Application and RenderKit factories, so we should use different object. 
        return _createFacesContext(servletContext, true);
    }
        
    public void destroyStartupFacesContext(FacesContext facesContext)
    {
        _releaseFacesContext(facesContext);
    }
    
    public FacesContext initShutdownFacesContext(ServletContext servletContext)
    {
        return _createFacesContext(servletContext, false);
    }
        
    public void destroyShutdownFacesContext(FacesContext facesContext)
    {
        _releaseFacesContext(facesContext);
    }
    
    private FacesContext _createFacesContext(ServletContext servletContext, boolean startup)
    {
        ExternalContext externalContext = new StartupServletExternalContextImpl(servletContext, startup);
        FacesContext facesContext = new StartupFacesContextImpl(externalContext, 
                (ReleaseableExternalContext) externalContext, startup);
        
        // If getViewRoot() is called during application startup or shutdown, 
        // it should return a new UIViewRoot with its locale set to Locale.getDefault().
        UIViewRoot startupViewRoot = new UIViewRoot();
        startupViewRoot.setLocale(Locale.getDefault());
        facesContext.setViewRoot(startupViewRoot);
        
        return facesContext;
    }
    
    private void _releaseFacesContext(FacesContext facesContext)
    {        
        // make sure that the facesContext gets released.
        // This is important in an OSGi environment 
        if (facesContext != null)
        {
            facesContext.release();
        }        
    }
    
    /**
     * Performs initialization tasks depending on the current environment.
     * 
     * @param servletContext
     *            the current ServletContext
     * @param externalContext
     *            the current ExternalContext
     */
    protected abstract void initContainerIntegration(
            ServletContext servletContext, ExternalContext externalContext);

}
