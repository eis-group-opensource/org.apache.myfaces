/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.test;

import static junit.framework.Assert.fail;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 511496 $ $Date: 2007-02-25 15:02:32 +0200 (Sun, 25 Feb 2007) $
 */
public class AssertThrowables
{
    public static void assertThrowable(Class<? extends Throwable> expected,
            TestRunnable test)
    {
        try
        {
            test.run();
            fail("expected exception: " + expected);
        }
        catch (Throwable e)
        {
            if (!expected.isAssignableFrom(e.getClass()))
            {
                fail("expected exception: " + expected + " but got "
                        + e.getClass());
            }
        }
    }

    public static void assertThrowable(String message,
            Class<? extends Throwable> expected, TestRunnable test)
    {
        try
        {
            test.run();
            fail(message);
        }
        catch (Throwable e)
        {
            if (!expected.isAssignableFrom(e.getClass()))
            {
                fail(message);
            }
        }
    }
}
