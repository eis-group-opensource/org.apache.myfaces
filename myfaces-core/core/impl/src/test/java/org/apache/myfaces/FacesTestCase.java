/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.apache.shale.test.mock.MockFacesContext12;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

/**
 * @author Mathias Broekelmann (latest modification by $Author: matzew $)
 * @version $Revision: 521179 $ $Date: 2007-03-22 11:57:24 +0200 (Thu, 22 Mar 2007) $
 */
public abstract class FacesTestCase extends TestCase
{
    protected FacesContext _facesContext;
    protected IMocksControl _mocksControl;
    protected ExternalContext _externalContext;
    protected Application _application;
    protected ELContext _elContext;

    protected void setUp() throws Exception
    {
        _mocksControl = EasyMock.createControl();
        _externalContext = _mocksControl.createMock(ExternalContext.class);
        _facesContext = _mocksControl.createMock(FacesContext.class);
        MockFacesContext12.setCurrentInstance(_facesContext);
        _application = _mocksControl.createMock(Application.class);
        _elContext = _mocksControl.createMock(ELContext.class);        
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        MockFacesContext12.setCurrentInstance(null);
    }
}
