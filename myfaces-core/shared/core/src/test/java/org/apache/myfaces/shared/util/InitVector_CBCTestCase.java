/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/


package org.apache.myfaces.shared.util;

import junit.framework.Test;
import org.apache.shale.test.base.AbstractJsfTestCase;

import javax.faces.FacesException;

/**
 * @author Dennis C. Byrne
 */

public class InitVector_CBCTestCase extends AbstractJsfTestCase {

    public InitVector_CBCTestCase(String name) {
        super(name);
    }
    
    public static Test suite() {
        return null; // keep this method or maven won't run it
    }    

    public void setUp() throws Exception{
    
        super.setUp();
        
        servletContext.addInitParameter(StateUtils.INIT_SECRET, "shouldn't matter");
        servletContext.addInitParameter(StateUtils.INIT_ALGORITHM, "shouldn't matter either");
        servletContext.addInitParameter(StateUtils.INIT_ALGORITHM_PARAM, "CBC/PKCS5Padding");
        servletContext.addInitParameter(StateUtils.INIT_SECRET_KEY_CACHE, "false");
        // DO NOT UNCOMMENT THIS ! we are simulating a bad conf
        //servletContext.addInitParameter(org.apache.myfaces.shared.util.StateUtils.INIT_ALGORITHM_IV, BASE64_KEY_SIZE_16);        
        
    }

    public void testDecryption() {
        
        byte[] sensitiveBytes = "bound to fail".getBytes();
        
        try{
            
            StateUtils.decrypt(sensitiveBytes, externalContext);
            
            fail("MyFaces should throw a meaningful " +
                    "exception when users opt for CBC mode " +
                    "encryption w/out an initialization vector.");
            
        }catch(FacesException fe){
        }
        
    }
    
    public void testEncryption() {
        
        byte[] sensitiveBytes = "bound to fail".getBytes();
        
        try{
            
            StateUtils.encrypt(sensitiveBytes, externalContext);
            
            fail("MyFaces should throw a meaningful " +
                    "exception when users opt for CBC mode " +
                    "encryption w/out an initialization vector.");
            
        }catch(FacesException fe){
        }
        
    }
    
}
