/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.application.jsp;

//import org.apache.myfaces.shared_impl.config.MyfacesConfig;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;

import javax.faces.application.StateManager;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.render.RenderKitFactory;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockResponseWriter;

public class JspStateManagerImplTest extends AbstractJsfTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(JspStateManagerImplTest.class);
    }

    public JspStateManagerImplTest(String name) {
        super(name);
    }

    /**
     * New test to address an issue uncovered through TCK testing.
     */
    public void testWriteAndRestoreState() throws Exception
    {
        // additional setup not provided automatically by the shale mock stuff
        facesContext.setResponseWriter(new MockResponseWriter(new BufferedWriter(new CharArrayWriter()), null, null));

        UIViewRoot viewRoot = facesContext.getViewRoot();
        viewRoot.setViewId("/root");
        StateManager stateManager = new JspStateManagerImpl();

        UIOutput output = new UIOutput();
        output.setValue("foo");
        output.setId("foo");

        stateManager.writeState(facesContext, stateManager.saveSerializedView(facesContext));

        UIViewRoot restoredViewRoot = stateManager.restoreView(facesContext, "/root", RenderKitFactory.HTML_BASIC_RENDER_KIT);
        assertNotNull("restored view root should not be null", restoredViewRoot);
    }

    public void testSaveInSessionWithoutSerialize() throws Exception
    {
        // create a fake viewRoot
        UIViewRoot root = new UIViewRoot();
        root.getAttributes().put("key", "value");

        // simulate server-side-state-saving without serialization
        Object state = root.saveState(facesContext);

        // restore view
        UIViewRoot root1 = new UIViewRoot();
        root1.restoreState(facesContext, state);

        // restore view .. next request
        UIViewRoot root2 = new UIViewRoot();
        root2.restoreState(facesContext, state);

        // chaange attribute in root1
        root1.getAttributes().put("key", "borken");

        // other values should not have been changed
        assertEquals("value", root2.getAttributes().get("key"));
        assertEquals("value", root.getAttributes().get("key"));
    }
}
