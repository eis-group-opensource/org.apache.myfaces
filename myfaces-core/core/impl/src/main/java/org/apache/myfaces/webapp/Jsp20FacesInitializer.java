/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Initializes MyFaces in a JSP 2.0 (or less) environment.
 *
 */
public class Jsp20FacesInitializer extends AbstractFacesInitializer
{
    /**
     * The logger instance for this class.
     */
    private static final Log log = LogFactory.getLog(Jsp20FacesInitializer.class);
    
    /**
     * The ExpressionFactory implementation of the EL-RI.
     */
    private static final String EL_RI_EXPRESSION_FACTORY_IMPL = "com.sun.el.ExpressionFactoryImpl";
    
    /**
     * Jasper's ExpressionFactory implementation.
     */
    private static final String JASPER_EL_EXPRESSION_FACTORY_IMPL = "org.apache.el.ExpressionFactoryImpl";
    
    /**
     * All known ExpressionFactory implementations.
     */
    private static final String[] KNOWN_EXPRESSION_FACTORIES = 
        new String[] { EL_RI_EXPRESSION_FACTORY_IMPL, JASPER_EL_EXPRESSION_FACTORY_IMPL };

    
    @Override
    protected void initContainerIntegration(
            ServletContext servletContext, ExternalContext externalContext)
    {
        if (log.isInfoEnabled()) {
            log.info("This application isn't running in a JSP 2.1 container."); 
        }
        
        // It's possible to run MyFaces in a JSP 2.0 Container, but the user has to provide
        // the ExpressionFactory implementation to use as there is no JspApplicationContext
        // we could ask for. Having said that, though, the user only has to provide it, if
        // there is no known ExpressionFactory available (i.e. if neither 
        // "com.sun.el.ExpressionFactoryImpl" nor "org.apache.el.ExpressionFactoryImpl"
        // are available).
        ExpressionFactory expressionFactory = getUserDefinedExpressionFactory(externalContext);

        if (expressionFactory == null) {
            if (log.isInfoEnabled()) {
                log.info("Either you haven't specified the ExpressionFactory implementation, or an " 
                        + "error occured while instantiating the implementation you've specified. "
                        + "However, attempting to load a known implementation.");
            }
            
            expressionFactory = findExpressionFactory(KNOWN_EXPRESSION_FACTORIES);
            if (expressionFactory == null) { // if we still haven't got a valid implementation
                if (log.isErrorEnabled()) {
                    log.error("No valid ExpressionFactory implementation is available "
                            + "but that's required as this application isn't running in a JSP 2.1 container.");
                }
                
                // use a dummy implementation that reports the error again
                expressionFactory = new ErrorExpressionFactory();
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug("The following ExpressionFactory implementation will " 
                    + "be used: '" + expressionFactory + "'.");
        }
        
        buildConfiguration(servletContext, externalContext, expressionFactory);
    }
    
    /**
     * Attempts to find a valid ExpressionFactory implementation. Each of the
     * given "ExpressionFactory implementation candidates" will be tried to
     * instantiate. If an attempt succeeded, the ExpressionFactory implementation
     * will be returned (i.e. the first valid ExpressionFactory implementation
     * will be returned) and if no attempt succeeds, <code>null</code> will be
     * returned.
     * 
     * @param expressionFactoryClassNames
     *            "ExpresionFactory implementation candidates"
     * 
     * @return the newly created ExpressionFactory implementation, or
     *         <code>null</code>, if there is no valid implementation
     */
    private static ExpressionFactory findExpressionFactory(String[] expressionFactoryClassNames) 
    {
        for (String expressionFactoryClassName : expressionFactoryClassNames) {
            ExpressionFactory expressionFactory = 
                loadExpressionFactory(expressionFactoryClassName);
            if (expressionFactory != null) {
                return expressionFactory;
            }
        }
        
        return null;
    }
    
    /**
     * Dummy implementation informing the user that there is no valid
     * ExpressionFactory implementation available. This class makes it easier
     * for the user to understand why the application crashes. Otherwise he
     * would have to deal with NullPointerExceptions.
     * 
     */
    private class ErrorExpressionFactory extends ExpressionFactory
    {

        public Object coerceToType(Object obj, Class<?> targetType)
        {
            throw new FacesException("No valid ExpressionFactory implementation is available "
                            + "but that's required as this application isn't running in a JSP 2.1 container.");
        }

        public MethodExpression createMethodExpression(ELContext context,
                String expression, Class<?> expectedReturnType, Class<?>[] expectedParamTypes)
        {
            throw new FacesException("No valid ExpressionFactory implementation is available "
                            + "but that's required as this application isn't running in a JSP 2.1 container.");
        }

        public ValueExpression createValueExpression(Object instance, Class<?> expectedType)
        {
            throw new FacesException("No valid ExpressionFactory implementation is available "
                            + "but that's required as this application isn't running in a JSP 2.1 container.");
        }

        public ValueExpression createValueExpression(
                ELContext context, String expression, Class<?> expectedType)
        {
            throw new FacesException("No valid ExpressionFactory implementation is available "
                            + "but that's required as this application isn't running in a JSP 2.1 container.");
        }

    }

}
