/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import junit.framework.Test;

/**
 * This TestCase uses the the default algorithm/mode/padding of
 * StateUtils.
 *
 * @author Dennis C. Byrne
 */

public class StateUtilsDefaultTest extends AbstractStateUtilsTest
{

    public StateUtilsDefaultTest(String name) {
        super(name);
    }

    public static Test suite() {
        return null; // keep this method or maven won't run it
    }

    public void setUp() throws Exception
    {
        super.setUp();

        servletContext.addInitParameter(StateUtils.INIT_SECRET, BASE64_KEY_SIZE_8);
        servletContext.addInitParameter(StateUtils.INIT_ALGORITHM, StateUtils.DEFAULT_ALGORITHM);
        servletContext.addInitParameter(StateUtils.INIT_ALGORITHM_PARAM, StateUtils.DEFAULT_ALGORITHM_PARAMS);
        StateUtils.initSecret(servletContext);// should do nothing

    }
}
