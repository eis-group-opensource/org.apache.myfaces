/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.test;

import java.io.File;
import java.io.StringWriter;

import org.apache.myfaces.shared_tomahawk.config.MyfacesConfig;
import org.apache.myfaces.test.base.AbstractViewControllerTestCase;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.apache.myfaces.test.mock.resource.MockResourceHandler;
import org.apache.myfaces.test.utils.TestUtils;

/**
 * Abstract Shale Test base class, which sets up the JSF environment.  If the test
 * overrides <code>setUp()</code> and/or <code>tearDown()</code>, then those
 * methods but call the overwitten method to insure a valid test environment.
 */
public class AbstractTomahawkViewControllerTestCase extends AbstractViewControllerTestCase
{
    /** Response Writer */
    private MockResponseWriter writer;
    
    //private MockResourceHandler resourceHandler;

    /**
     * Construct a new instance of the test.
     * 
     * @param name Name of the test.
     */
    public AbstractTomahawkViewControllerTestCase(String name)
    {
        super(name);
    }

    /**
     *  Setup the test environment, including the following:
     *  <ul>
     *  <li>Set the Application Map.</li>
     *  <li>Set a response writer</li>
     *  <li>Add Tomahawk's renderers to the faces context.</li>
     *  </ul> 
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        // additional setup not provided automatically by the shale mock stuff
        facesContext.getExternalContext().getApplicationMap().put(
                MyfacesConfig.class.getName(), new MyfacesConfig());
        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
        
        //resourceHandler = new MockResourceHandler(new File(this.getClass().getName().replace('.', '/')));
        //facesContext.getApplication().setResourceHandler(resourceHandler);
        
        TestUtils.addDefaultRenderers(facesContext);
    }

    /**
     * Tear down the test environment.
     */
    protected void tearDown() throws Exception
    {
        //resourceHandler = null;
        writer = null;
        super.tearDown();
    }

    /**
     * Verify the following:
     * <ul>
     * <li>id is not null</li>
     * <li>Response is complete</li>
     * <li>Responce contains the id</li>
     * </ul>
     * 
     * @param id ID to verify
     */
    protected void assertIdExists(String id)
    {
        assertNotNull("ID is not null", id);
        assertTrue("Response Complete", facesContext.getResponseComplete());
        String output = writer.getWriter().toString();
//        System.out.println("Output = '" + output + "'");
        assertNotNull("Has output", output);
        assertTrue("Contains id '" + id + "'", output.indexOf(id) != -1);
    }

}
