/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.el.unified.resolver;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;

import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.config.impl.digester.elements.ManagedBean;
import org.apache.myfaces.el.unified.resolver.GuiceResolver;
import org.apache.shale.test.base.AbstractJsfTestCase;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceResolverTestCase extends AbstractJsfTestCase {

    public GuiceResolverTestCase(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        
        super.setUp();
        
        // simulate a ServletContextListener
        Injector injector = Guice.createInjector(new ShoppingModule());
        servletContext.setAttribute(GuiceResolver.KEY, injector);
        
        // simulate Myfaces starting up
        RuntimeConfig rc = RuntimeConfig.getCurrentInstance(externalContext);
        ManagedBean bean = new ManagedBean();
        bean.setBeanClass(ShoppingCart.class.getName());
        bean.setScope("request");
        rc.addManagedBean("shoppingCart", bean);
        
    }

    public void testResolve() {
        
        ELResolver resolver = new GuiceResolver();
        
        ShoppingCart cart = (ShoppingCart) resolver.getValue(facesContext.getELContext(), ((Object)null), ((Object)"shoppingCart"));
        
        assertNotNull(cart);
        
        assertEquals(new BulkOrder().toString(), cart.getOrder().toString());
        
        cart = (ShoppingCart) resolver.getValue(facesContext.getELContext(), ((Object)null), ((Object)"XXXshoppingCart"));
        
        assertNull(cart);
    }
    
}
