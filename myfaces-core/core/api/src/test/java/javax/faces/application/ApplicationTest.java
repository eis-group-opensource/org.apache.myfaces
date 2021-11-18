/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.application;

import static org.apache.myfaces.Assert.assertException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import org.apache.myfaces.TestRunner;

import javax.el.ValueExpression;

import junit.framework.TestCase;

/**
 * Tests for {@link Application}
 * 
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 775008 $ $Date: 2009-05-15 08:23:20 +0300 (Fri, 15 May 2009) $
 */
public class ApplicationTest extends TestCase
{
    private Application app;

    protected void setUp() throws Exception
    {
        app = (Application) Enhancer.create(Application.class, NoOp.INSTANCE);
    }

    /**
     * Test method for {@link javax.faces.application.Application#addELResolver(javax.el.ELResolver)}.
     */
    /*
    public void testAddELResolver()
    {
        assertException(UnsupportedOperationException.class, new TestRunner()
        {
            public void run()
            {
                app.addELResolver(null);
            }
        });
    }*/

    /**
     * Test method for {@link javax.faces.application.Application#getELResolver()}.
     */
    /*
    public void testGetELResolver()
    {
        assertException(UnsupportedOperationException.class, new TestRunner()
        {
            public void run()
            {
                app.getELResolver();
            }
        });
    }*/

    /**
     * Test method for
     * {@link javax.faces.application.Application#getResourceBundle(javax.faces.context.FacesContext, java.lang.String)}.
     */
    /*
    public void testGetResourceBundle()
    {
        assertException(UnsupportedOperationException.class, new TestRunner()
        {
            public void run()
            {
                app.getResourceBundle(null, null);
            }
        });
    }*/

    /**
     * Test method for
     * {@link javax.faces.application.Application#createComponent(javax.el.ValueExpression, javax.faces.context.FacesContext, java.lang.String)}.
     */
    /*
    public void testCreateComponentValueExpressionFacesContextString()
    {
        assertException(UnsupportedOperationException.class, new TestRunner()
        {
            public void run()
            {
                app.createComponent((ValueExpression) null, null, null);
            }
        });
    }*/

    /**
     * Test method for {@link javax.faces.application.Application#getExpressionFactory()}.
     */
    /*
    public void testGetExpressionFactory()
    {
        assertException(UnsupportedOperationException.class, new TestRunner()
        {
            public void run()
            {
                app.getExpressionFactory();
            }
        });
    }*/

    /**
     * Test method for {@link javax.faces.application.Application#addELContextListener(javax.el.ELContextListener)}.
     */
    /*
    public void testAddELContextListener()
    {
        assertException(UnsupportedOperationException.class, new TestRunner()
        {
            public void run()
            {
                app.addELContextListener(null);
            }
        });
    }*/

    /**
     * Test method for {@link javax.faces.application.Application#removeELContextListener(javax.el.ELContextListener)}.
     */
    /*
    public void testRemoveELContextListener()
    {
        assertException(UnsupportedOperationException.class, new TestRunner()
        {
            public void run()
            {
                app.removeELContextListener(null);
            }
        });
    }*/

    /**
     * Test method for {@link javax.faces.application.Application#getELContextListeners()}.
     */
    /*
    public void testGetELContextListeners()
    {
        assertException(UnsupportedOperationException.class, new TestRunner()
        {
            public void run()
            {
                app.getELContextListeners();
            }
        });
    }*/

    /**
     * Test method for
     * {@link javax.faces.application.Application#evaluateExpressionGet(javax.faces.context.FacesContext, java.lang.String, java.lang.Class)}.
     */
    /*
    public void testEvaluateExpressionGet()
    {
        assertException(UnsupportedOperationException.class, new TestRunner()
        {
            public void run()
            {
                app.evaluateExpressionGet(null, null, null);
            }
        });
    }*/
}
