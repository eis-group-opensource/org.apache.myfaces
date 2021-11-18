/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.mock;

import org.apache.myfaces.TestRunner;
import org.easymock.classextension.IMocksControl;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 518517 $ $Date: 2007-03-15 10:47:31 +0200 (Thu, 15 Mar 2007) $
 */
public abstract class ExceptionMockRunner extends MockRunner implements TestRunner
{

    private final Throwable _exception;

    public ExceptionMockRunner(IMocksControl mockControl, Throwable exception)
    {
        super(mockControl);
        _exception = exception;
    }

    @Override
    protected final void setUp(IMocksControl mockControl)
    {
        setUp(mockControl, _exception);
    }

    protected abstract void setUp(IMocksControl mockControl, Throwable exceptionToThrow);

}
