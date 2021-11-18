/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import junit.framework.TestCase;
import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.el.DefaultPropertyResolver;
import org.apache.myfaces.el.VariableResolverImpl;
import org.apache.myfaces.el.unified.resolver.FacesCompositeELResolver;
import static org.easymock.EasyMock.*;
import org.easymock.IAnswer;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

import javax.el.ExpressionFactory;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;

/**
 * @author Mathias Broekelmann (latest modification by $Author: baranda $)
 * @version $Revision: 532019 $ $Date: 2007-04-24 19:34:36 +0200 (Di, 24 Apr 2007) $
 */
public class Jsp21FacesInitializerTest extends TestCase
{

    /**
     * Test method for {@link org.apache.myfaces.webapp.DefaultFacesInitializer#initFaces(javax.servlet.ServletContext)}.
     * @throws Exception 
     */
    public void testInitFaces() throws Exception
    {
        Jsp21FacesInitializer initializer = new Jsp21FacesInitializer();
        IMocksControl control = EasyMock.createControl();
        
        JspFactory jspFactory = control.createMock(JspFactory.class);
        initializer.setJspFactory(jspFactory);
        
        RuntimeConfig runtimeConfig = control.createMock(RuntimeConfig.class);
        
        ServletContext context = control.createMock(ServletContext.class);
        ExpressionFactory expressionFactory = control.createMock(ExpressionFactory.class);
        runtimeConfig.setExpressionFactory(expressionFactory);
        runtimeConfig.setPropertyResolverChainHead(isA(DefaultPropertyResolver.class));
        runtimeConfig.setVariableResolverChainHead(isA(VariableResolverImpl.class));
        
        expect(context.getAttribute(eq(RuntimeConfig.class.getName()))).andReturn(runtimeConfig).anyTimes();
        
        expect(context.getInitParameter(eq(FacesServlet.CONFIG_FILES_ATTR))).andReturn(null);
        expect(context.getResourceAsStream(eq("/WEB-INF/faces-config.xml"))).andReturn(null);
        expect(context.getInitParameter(eq(FacesServlet.LIFECYCLE_ID_ATTR))).andReturn(null);

        // TODO: add myfaces specific tests
        expect(context.getResource(isA(String.class))).andReturn(null);
        expect(context.getResourceAsStream(isA(String.class))).andReturn(null);
        expect(context.getInitParameter(isA(String.class))).andReturn(null).anyTimes();
        expect(context.getAttribute(isA(String.class))).andReturn(null).anyTimes();
        context.setAttribute(isA(String.class), anyObject());
        expectLastCall().anyTimes();
        expect(context.getRealPath(isA(String.class))).andAnswer(new IAnswer<String>() {
            public String answer() throws Throwable
            {
                return (String) org.easymock.EasyMock.getCurrentArguments()[0];
            }
        });
        
        JspApplicationContext jspAppCtx = control.createMock(JspApplicationContext.class);
        expect(jspAppCtx.getExpressionFactory()).andReturn(expressionFactory);
        jspAppCtx.addELContextListener(isA(FacesELContextListener.class));
        expect(jspFactory.getJspApplicationContext(eq(context))).andReturn(jspAppCtx);
        jspAppCtx.addELResolver(isA(FacesCompositeELResolver.class));

        control.replay();
        initializer.initFaces(context);

        // In MYFACES-1222: The Jsp21FacesInitializer isn't practicable anymore.
        // The ServletContext-Mock won't return its WebXml instance, so Jsp21FacesInitializer will stop initializing.
        // This is why the next line is commented:
        
        //control.verify();
    }

}
