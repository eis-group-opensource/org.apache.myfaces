/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.StringWriter;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlInputSecret;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;


public class HtmlSecretRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private MockResponseWriter writer ;
    private HtmlInputSecret inputText;

    public HtmlSecretRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlSecretRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        inputText = new HtmlInputSecret();

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                inputText.getFamily(),
                inputText.getRendererType(),
                new HtmlSecretRenderer());

    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        inputText = null;
        writer = null;
    }

    public void testInputTextDefault() throws Exception
    {
        inputText.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        assertEquals("<input type=\"password\" name=\"j_id0\"/>", output);
    }

    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                inputText, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
