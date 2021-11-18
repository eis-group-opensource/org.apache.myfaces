/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import junit.framework.Test;

/**
 * <p>This TestCase uses the Advanced Encryption Standard with
 * Cipher Block Chaining mode and PKCS5 padding.</p>
 * <p/>
 * <p/>
 * If you are getting a SecurityException complaining about keysize,
 * you most likely need to get the unlimited strength jurisdiction
 * policy files from a place like http://java.sun.com/j2se/1.4.2/download.html .
 * </p>
 *
 * @see pom.xml <excludes>
 * @author Dennis C. Byrne
 */

public class StateUtilsAES_CBCTest extends AbstractStateUtilsTest
{

    public StateUtilsAES_CBCTest(String name) {
        super(name);
    }

    public static Test suite() {
        return null; // keep this method or maven won't run it
    }

    public void setUp() throws Exception
    {
        super.setUp();

        servletContext.addInitParameter(StateUtils.INIT_SECRET, BASE64_KEY_SIZE_24);
        servletContext.addInitParameter(StateUtils.INIT_ALGORITHM, "AES");
        servletContext.addInitParameter(StateUtils.INIT_ALGORITHM_PARAM, "CBC/PKCS5Padding");
        servletContext.addInitParameter(StateUtils.INIT_ALGORITHM_IV, BASE64_KEY_SIZE_16);
        StateUtils.initSecret(servletContext);// should do nothing

    }

}
