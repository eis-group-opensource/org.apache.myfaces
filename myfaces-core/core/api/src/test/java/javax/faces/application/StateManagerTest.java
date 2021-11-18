/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.application;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.apache.shale.test.mock.MockStateManager;
import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;

public class StateManagerTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(StateManagerTest.class);
    }

    public StateManagerTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
      * Test method for 'javax.faces.application.StateManager.isSavingStateInClient(FacesContext)'
      */
    public void testNullThrowsIsSavingStateInClient() {
        MockStateManager subject = new MockStateManager();
        try {
            subject.isSavingStateInClient(null);
            fail("should have thrown an exception");
        } catch (RuntimeException e) {
        }
    }

    /*
      * Test method for 'javax.faces.application.StateManager.isSavingStateInClient(FacesContext)'
      */
    public void testIsSavingStateInClientTrue() {
        MockControl contextControl = MockClassControl.createControl(FacesContext.class);
        MockControl externalControl = MockClassControl.createControl(ExternalContext.class);
        FacesContext context = (FacesContext)contextControl.getMock();
        ExternalContext external = (ExternalContext)externalControl.getMock();
        context.getExternalContext();
        contextControl.setReturnValue(external);
        contextControl.replay();
        external.getInitParameter(StateManager.STATE_SAVING_METHOD_PARAM_NAME);
        externalControl.setReturnValue("client");
        externalControl.replay();

        MockStateManager subject = new MockStateManager();
        assertEquals(true, subject.isSavingStateInClient(context));
    }

    /*
      * Test method for 'javax.faces.application.StateManager.isSavingStateInClient(FacesContext)'
      */
    public void testIsSavingStateInClientFalse() {
        MockControl contextControl = MockClassControl.createControl(FacesContext.class);
        MockControl externalControl = MockClassControl.createControl(ExternalContext.class);
        FacesContext context = (FacesContext)contextControl.getMock();
        ExternalContext external = (ExternalContext)externalControl.getMock();
        context.getExternalContext();
        contextControl.setReturnValue(external);
        contextControl.replay();
        external.getInitParameter(StateManager.STATE_SAVING_METHOD_PARAM_NAME);
        externalControl.setReturnValue("server");
        externalControl.replay();

        MockStateManager subject = new MockStateManager();
        assertEquals(false, subject.isSavingStateInClient(context));
        // calling a second time asserts that the code is caching the value correctly
        assertEquals(false, subject.isSavingStateInClient(context));
    }

    /*
      * Test method for 'javax.faces.application.StateManager.isSavingStateInClient(FacesContext)'
      */
    public void testIsSavingStateInClientBogus() {
        MockControl contextControl = MockClassControl.createControl(FacesContext.class);
        MockControl externalControl = MockClassControl.createControl(ExternalContext.class);
        FacesContext context = (FacesContext)contextControl.getMock();
        ExternalContext external = (ExternalContext)externalControl.getMock();
        context.getExternalContext();
        contextControl.setReturnValue(external);
        context.getExternalContext();
        contextControl.setReturnValue(external);
        contextControl.replay();
        external.getInitParameter(StateManager.STATE_SAVING_METHOD_PARAM_NAME);
        externalControl.setReturnValue("blorf");
        external.log("Illegal state saving method 'blorf', default server state saving will be used");
        externalControl.setVoidCallable();
        externalControl.replay();

        MockStateManager subject = new MockStateManager();
        assertEquals(false, subject.isSavingStateInClient(context));
    }

    /*
      * Test method for 'javax.faces.application.StateManager.isSavingStateInClient(FacesContext)'
      */
    public void testIsSavingStateInClientNull() {
        MockControl contextControl = MockClassControl.createControl(FacesContext.class);
        MockControl externalControl = MockClassControl.createControl(ExternalContext.class);
        FacesContext context = (FacesContext)contextControl.getMock();
        ExternalContext external = (ExternalContext)externalControl.getMock();
        context.getExternalContext();
        contextControl.setReturnValue(external);
        context.getExternalContext();
        contextControl.setReturnValue(external);
        contextControl.replay();
        external.getInitParameter(StateManager.STATE_SAVING_METHOD_PARAM_NAME);
        externalControl.setReturnValue(null);
        external.log("No state saving method defined, assuming default server state saving");
        externalControl.setVoidCallable();
        externalControl.replay();

        MockStateManager subject = new MockStateManager();
        assertEquals(false, subject.isSavingStateInClient(context));
    }

}
