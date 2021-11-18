/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces;

/**
 * Provides various assert calls which can be used for tests.
 * 
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 514642 $ $Date: 2007-03-05 12:49:41 +0200 (Mon, 05 Mar 2007) $
 */
public class Assert
{
    protected Assert()
    {
    }

    /**
     * Asserts that the execution of the {@link TestRunner#run()} method will throw the <code>expected</code>
     * exception
     * 
     * @param expected
     *            the expected Exception
     * @param testCase
     *            the testcase to run
     */
    public static void assertException(Class<? extends Throwable> expected, TestRunner testCase)
    {
        junit.framework.Assert.assertNotNull(expected);
        junit.framework.Assert.assertNotNull(testCase);
        try
        {
            testCase.run();
        }
        catch (Throwable e)
        {
            if (!expected.isAssignableFrom(e.getClass()))
            {
                AssertionError assertionError = new AssertionError("caught exception <" + e.getClass()
                        + "> does not match expected <" + expected + ">: " + e.getMessage());
                assertionError.initCause(e);
                throw assertionError;
            }
            return;
        }
        junit.framework.Assert.fail(expected.getName() + " expected");
    }

}