/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.stylesheet;

import java.io.IOException;
import java.io.StringWriter;

import javax.faces.FacesException;

import junit.framework.Test;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.shale.test.mock.MockResponseWriter;

public class StylesheetRendererTest extends AbstractTomahawkViewControllerTestCase
{

    private MockResponseWriter writer ;
    private Stylesheet stylesheet ;
    
    public StylesheetRendererTest(String name)
    {
        super(name);
    }
    
    protected void setUp() throws Exception
    {
        super.setUp();
        stylesheet = new Stylesheet();
        stylesheet.setPath("test.css");
        stylesheet.setMedia("printer");
        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
//        facesContext.getRenderKit().addRenderer(
//                stylesheet.getFamily(), 
//                stylesheet.getRendererType(), 
//                new StylesheetRenderer());
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
        writer = null;
        stylesheet = null;
    }

    /*
    public static Test suite()
    {
        return null; // keep this method or maven won't run it
    }*/

    /*
     * Test method for 'org.apache.myfaces.custom.stylesheet.StylesheetRenderer.encodeEnd(FacesContext, UIComponent)'
     */
    public void testInline() throws IOException
    {
        stylesheet.setInline(true);
        stylesheet.setPath("/styles/test.css");
        stylesheet.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        
        assertTrue("looking for a <style>", output.trim().startsWith("<style"));
    }

    /*
     * Test method for 'org.apache.myfaces.custom.stylesheet.StylesheetRenderer.encodeEnd(FacesContext, UIComponent)'
     */
    public void testInlineInvalid() throws IOException
    {
        stylesheet.setInline(true);
        try
        {
            stylesheet.encodeEnd(facesContext);
            fail("No exception was thrown for an invalid stylesheet path");
        }
        catch(FacesException e)
        {
            // expected
        }
    }

    public void testLink() throws IOException
    {

        stylesheet.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        
        assertTrue("looking for a <link>", output.trim().startsWith("<link"));
    }

}
