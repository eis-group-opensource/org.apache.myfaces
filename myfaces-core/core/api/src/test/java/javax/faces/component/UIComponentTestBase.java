/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import javax.faces.context.FacesContext;

import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.testng.annotations.BeforeMethod;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 520085 $ $Date: 2007-03-19 22:20:52 +0200 (Mon, 19 Mar 2007) $
 */
public abstract class UIComponentTestBase
{
    protected IMocksControl _mocksControl;
    protected FacesContext _facesContext;

    @BeforeMethod(alwaysRun = true)
    protected void setUp() throws Exception
    {
        _mocksControl = EasyMock.createNiceControl();
        _facesContext = _mocksControl.createMock(FacesContext.class);
    }
}