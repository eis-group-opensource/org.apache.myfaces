/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import static org.easymock.EasyMock.*;

import javax.el.ELContext;
import javax.el.ELContextEvent;
import javax.el.ELContextListener;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.apache.shale.test.mock.MockFacesContext12;
import org.easymock.IMocksControl;
import org.easymock.classextension.EasyMock;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 514285 $ $Date: 2007-03-04 02:15:00 +0200 (Sun, 04 Mar 2007) $
 */
public class FacesELContextListenerTest extends TestCase
{

    /**
     * Test method for {@link org.apache.myfaces.webapp.FacesELContextListener#contextCreated(javax.el.ELContextEvent)}.
     */
    public void testContextCreated()
    {
        FacesELContextListener listener = new FacesELContextListener();
        IMocksControl mockControl = EasyMock.createControl();
        ELContext elctx = mockControl.createMock(ELContext.class);
        MockFacesContext12 facesctx = new MockFacesContext12();
        Application app = mockControl.createMock(Application.class);
        facesctx.setApplication(app);
        ELContextEvent event = mockControl.createMock(ELContextEvent.class);
        expect(event.getELContext()).andReturn(elctx);
        elctx.putContext(eq(FacesContext.class), same(facesctx));
        ELContextListener elctxListener = mockControl.createMock(ELContextListener.class);
        expect(app.getELContextListeners()).andReturn(new ELContextListener[] { elctxListener });
        elctxListener.contextCreated(same(event));
        mockControl.replay();
        listener.contextCreated(event);
        mockControl.verify();
    }

}
