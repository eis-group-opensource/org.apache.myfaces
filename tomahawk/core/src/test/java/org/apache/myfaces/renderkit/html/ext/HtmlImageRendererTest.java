/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlGraphicImage;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlImageRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private MockResponseWriter writer ;
    private HtmlGraphicImage graphicImage;
    
    public HtmlImageRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlImageRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();
        graphicImage = new HtmlGraphicImage();
        writer = (MockResponseWriter)facesContext.getResponseWriter();

    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        graphicImage = null;
        writer = null;
    }

    public void testRenderDefault() throws Exception
    {
        graphicImage.setAlt("foo");
        graphicImage.setId("img1");
        graphicImage.setValue("http://myfaces.apache.org");
        graphicImage.encodeBegin(facesContext);
        graphicImage.encodeChildren(facesContext);
        graphicImage.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        assertEquals("<img id=\"img1\" src=\"nullhttp://myfaces.apache.org\" alt=\"foo\"/>", output);
    }

    public void testHtmlPropertyPassTruNotRendered() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();
        
        graphicImage.setId("img1");
        graphicImage.setValue("http://myfaces.apache.org");
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                graphicImage, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicReadOnlyAttrs();
        
        graphicImage.setId("img1");
        graphicImage.setValue("http://myfaces.apache.org");
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                graphicImage, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
