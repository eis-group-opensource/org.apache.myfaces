/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.apache.myfaces.component.html.ext.HtmlOutputText;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlTextRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlOutputText outputText;
    private HtmlInputText inputText;

    public static Test suite()
    {
        return new TestSuite(HtmlTextRendererTest.class); 
    }

    public HtmlTextRendererTest(String name)
    {
        super(name);
    }

    public void setUp() throws Exception
    {
        try {     
        super.setUp();
        } catch(Exception e) {
            e.printStackTrace();
        }

        outputText = new HtmlOutputText();
        inputText = new HtmlInputText();
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        outputText = null;
        inputText = null;
    }

    public void testStyleClassAttr() throws IOException
    {
        outputText.setValue("Output");
        outputText.setStyleClass("myStyleClass");

        outputText.encodeEnd(facesContext);
        facesContext.renderResponse();

        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        String output = writer.getWriter().toString();

        assertEquals("<span class=\"myStyleClass\">Output</span>", output);
        assertNotSame("Output", output);
    }

    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                inputText, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
