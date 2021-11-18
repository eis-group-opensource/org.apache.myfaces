/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import junit.framework.Test;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.TestUtils;

public class HtmlMessageTest extends AbstractTomahawkViewControllerTestCase
{

    public HtmlMessageTest(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /*
    public static Test suite()
    {
        return null; // keep this method or maven won't run it
    }*/

    /**
     * Verify component renders with the default renderer.
     */
    public void testDefaultRenderer()
    {
        // Define required panel group
        HtmlPanelGroup panelGroup = new HtmlPanelGroup();

        // Define the referenced component
        UIComponent referencedComponent = new HtmlInputText();
        referencedComponent.setId("referencedComponent");
        //referencedComponent.setParent(panelGroup);
        panelGroup.getChildren().add(referencedComponent);
        facesContext.addMessage(referencedComponent.getId(), new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "summary", "detail"));

        // Define the component
        HtmlMessage component = new HtmlMessage();
        component.setId("TestComponent");

        referencedComponent.setParent(panelGroup);
        panelGroup.getChildren().add(component);
        component.setFor(referencedComponent.getId());

        // Render the component
        try
        {
            TestUtils.renderComponent(facesContext, panelGroup);
        }
        catch (IOException e)
        {
            fail(e.getMessage());
        }

        // Verify component was rendered
        assertIdExists(component.getId());
    }
}
