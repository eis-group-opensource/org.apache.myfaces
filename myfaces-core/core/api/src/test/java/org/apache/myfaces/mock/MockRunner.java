/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.mock;

import org.apache.myfaces.TestRunner;
import org.easymock.classextension.IMocksControl;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 518517 $ $Date: 2007-03-15 10:47:31 +0200 (Thu, 15 Mar 2007) $
 */
public abstract class MockRunner implements TestRunner
{
    private final IMocksControl _mockControl;

    public MockRunner(IMocksControl mockControl)
    {
        _mockControl = mockControl;
    }

    public final void run() throws Throwable
    {
        setUp(_mockControl);
        _mockControl.replay();
        run(_mockControl);
        _mockControl.verify();
    }

    protected abstract void run(IMocksControl mockControl) throws Exception;

    protected abstract void setUp(IMocksControl mockControl);

}
