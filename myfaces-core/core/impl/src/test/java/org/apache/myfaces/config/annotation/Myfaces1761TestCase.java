/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.annotation;

import org.apache.myfaces.config.ManagedBeanBuilder;
import org.apache.myfaces.config.impl.digester.elements.ManagedBean;
import org.apache.myfaces.config.impl.digester.elements.ManagedProperty;
import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * Test MYFACES-1761 Handling PostConstruct annotations - wrong order 
 * 
 * @author Leonardo Uribe
 *
 */
public class Myfaces1761TestCase extends AbstractJsfTestCase
{

    protected ManagedBeanBuilder managedBeanBuilder;
    protected LifecycleProvider2 lifecycleProvider;
    protected ManagedBean beanConfiguration;
    
    private static final String TEST_LIFECYCLE_PROVIDER = TestLifecycleProvider2.class.getName();
    
    protected static final String INJECTED_VALUE = "tatiana";
    
    public Myfaces1761TestCase(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        managedBeanBuilder  = new ManagedBeanBuilder();
        
        beanConfiguration = new ManagedBean();        
        beanConfiguration.setBeanClass(AnnotatedManagedBean2.class.getName());
        beanConfiguration.setName("managed");
        beanConfiguration.setScope("request");
        
        ManagedProperty managedProperty = new ManagedProperty();
        managedProperty.setPropertyName("managedProperty");
        managedProperty.setValue(INJECTED_VALUE);
        beanConfiguration.addProperty(managedProperty);
        
        LifecycleProviderFactory.getLifecycleProviderFactory(externalContext).release();
        servletContext.addInitParameter(DefaultLifecycleProviderFactory.LIFECYCLE_PROVIDER, TEST_LIFECYCLE_PROVIDER);
    }

    public void tearDown() throws Exception
    {
        LifecycleProviderFactory.getLifecycleProviderFactory(externalContext).release();
        super.tearDown();
        managedBeanBuilder = null;
    }
    
    public void testPostConstruct() throws Exception
    {
        AnnotatedManagedBean2 bean = (AnnotatedManagedBean2) managedBeanBuilder.buildManagedBean(facesContext, beanConfiguration);
        assertEquals(INJECTED_VALUE, bean.getManagedProperty());
        assertTrue(bean.isPostConstructCalled());
    }
}
