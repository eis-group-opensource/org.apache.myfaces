/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;

import junit.framework.Test;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.TestUtils;

public class HtmlSelectBooleanCheckboxTest extends AbstractTomahawkViewControllerTestCase
{

    public HtmlSelectBooleanCheckboxTest(String name)
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
        // Define the component
        UIComponent component = new HtmlSelectBooleanCheckbox();
        component.setId("TestComponent");
        component.setParent(new HtmlForm());

        // Render the component
        try
        {
            TestUtils.renderComponent(facesContext, component);
        }
        catch (IOException e)
        {
            fail(e.getMessage());
        }

        // Verify component was rendered
        assertIdExists(component.getId());
    }
}
