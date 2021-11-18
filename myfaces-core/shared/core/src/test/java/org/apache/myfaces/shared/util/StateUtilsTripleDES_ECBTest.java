/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import junit.framework.Test;

/**
 * This TestCase uses the 3DES algorithm in Electronic CodeBook mode
 * with PKCS5 padding.
 * <p/>
 * <p/>
 * If you are getting a SecurityException complaining about keysize,
 * you most likely need to get the unlimited strength jurisdiction
 * policy files from a place like http://java.sun.com/j2se/1.4.2/download.html .
 * </p>
 *
 * @author Dennis C. Byrne
 */

public class StateUtilsTripleDES_ECBTest extends AbstractStateUtilsTest
{

    public StateUtilsTripleDES_ECBTest(String name) {
        super(name);
    }

    public static Test suite() {
        return null; // keep this method or maven won't run it
    }

    public void setUp() throws Exception
    {
        super.setUp();

        servletContext.addInitParameter(StateUtils.INIT_SECRET, BASE64_KEY_SIZE_24);
        servletContext.addInitParameter(StateUtils.INIT_ALGORITHM, "DESede");
        servletContext.addInitParameter(StateUtils.INIT_ALGORITHM_PARAM, "ECB/PKCS5Padding");
        StateUtils.initSecret(servletContext); // should do nothing

    }

}
