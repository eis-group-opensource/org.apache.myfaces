/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/


package org.apache.myfaces.shared.util;

import junit.framework.Test;
import org.apache.shale.test.base.AbstractJsfTestCase;

import javax.crypto.SecretKey;

/**
 * @author Dennis C. Byrne
 */

public class SecretKeyCacheTest extends AbstractJsfTestCase
{

    public SecretKeyCacheTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return null; // keep this method or maven won't run it
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        
        servletContext.addInitParameter(StateUtils.INIT_SECRET, 
                AbstractStateUtilsTest.BASE64_KEY_SIZE_8);
        
    }

    public void testDefaultAlgorithmUse(){
        
        StateUtils.initSecret(servletContext);
        
        SecretKey secretKey = (SecretKey) servletContext.getAttribute(StateUtils.INIT_SECRET_KEY_CACHE);
        
        assertTrue("Making sure MyFaces uses the " +
                "default algorithm when one is not specified",
                StateUtils.DEFAULT_ALGORITHM.equals(secretKey.getAlgorithm()));
        
    }
    
    public void testInitFacesWithCache(){
        
        StateUtils.initSecret(servletContext);
        
        Object object = servletContext.getAttribute(StateUtils.INIT_SECRET_KEY_CACHE);
        
        assertFalse("Making sure StateUtils.initSecret() puts an object in application scope", 
                object == null);
        
        assertTrue("Making sure StateUtils.initSecret() is creating a SecretKey", 
                object instanceof SecretKey);
        
    }
    
}
