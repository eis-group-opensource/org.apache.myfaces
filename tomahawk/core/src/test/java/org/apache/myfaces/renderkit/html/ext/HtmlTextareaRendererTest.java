/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.StringWriter;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlInputTextarea;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlTextareaRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlInputTextarea inputTextarea;

    public HtmlTextareaRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() 
    {
        return new TestSuite(HtmlTextareaRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();
        inputTextarea = new HtmlInputTextarea();
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        inputTextarea = null;
    }

    public void testRenderDefault() throws Exception
    {
        inputTextarea.encodeEnd(facesContext);
        facesContext.renderResponse();

        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        String output = writer.getWriter().toString();
        assertEquals("<textarea name=\"_id0\"></textarea>", output);
    }

    public void testRenderColsRows() throws Exception
    {
        inputTextarea.setCols(5);
        inputTextarea.setRows(10);
        inputTextarea.encodeEnd(facesContext);
        facesContext.renderResponse();

        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        String output = writer.getWriter().toString();
        assertEquals("<textarea name=\"_id0\" cols=\"5\" rows=\"10\"></textarea>", output);
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                inputTextarea, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
