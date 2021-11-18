/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/


package org.apache.myfaces.shared.util;

import junit.framework.Test;
import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * @author Dennis C. Byrne
 */

public class SecretKeyConfigurationTest extends AbstractJsfTestCase
{

    public SecretKeyConfigurationTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return null; // keep this method or maven won't run it
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        servletContext.addInitParameter(StateUtils.INIT_SECRET, "shouldn't matter");
        
    }

    public void testMissingSecretKeyEncrypt(){
        
        try{
            StateUtils.encrypt("serialized objects".getBytes(), externalContext);
            fail("An exception should be thrown if there" +
                    " is no SecretKey in application scope and cacheing is enabled ");
        }catch(NullPointerException e){
        }
        
    }
    
    public void testNonSecretKeyEncrypt(){
        
        servletContext.setAttribute(StateUtils.INIT_SECRET_KEY_CACHE, new Integer(8));
        
        try{
            
            StateUtils.encrypt("serialized objects".getBytes(), externalContext);
            fail("An exception should be thrown if there" +
                    " is no SecretKey in application scope and cacheing is enabled ");
        }catch(ClassCastException cce){
        }
        
    }
    
    public void testMissingSecretKeyDecrypt(){
        
        boolean npeThrown = false;
        
        try{
            StateUtils.decrypt("serialized objects".getBytes(), externalContext);
        }catch(NullPointerException e){
            npeThrown = true;
        }
        
        assertTrue("An exception should be thrown if there" +
                " is no SecretKey in application scope and cacheing is enabled ", npeThrown);
    }
    
    public void testNonSecretKeyDecrypt(){
        
        servletContext.setAttribute(StateUtils.INIT_SECRET_KEY_CACHE, new Integer(8));
        
        try{
            
            StateUtils.decrypt("serialized objects".getBytes(), externalContext);
            fail("An exception should be thrown if there" +
                    " is no SecretKey in application scope and cacheing is enabled ");
        }catch(ClassCastException cce){
        }
        
    }

}
