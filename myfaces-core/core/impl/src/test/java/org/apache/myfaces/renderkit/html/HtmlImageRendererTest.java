/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.StringWriter;

import javax.faces.component.html.HtmlGraphicImage;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

/**
 * @author Bruno Aranda (latest modification by $Author: baranda $)
 * @version $Revision: 451814 $ $Date: 2006-10-01 22:28:42 +0100 (dom, 01 oct 2006) $
 */
public class HtmlImageRendererTest extends AbstractJsfTestCase
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

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                graphicImage.getFamily(),
                graphicImage.getRendererType(),
                new HtmlImageRenderer());

    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        graphicImage = null;
        writer = null;
    }

    public void testRenderDefault() throws Exception
    {
        String imgUrl = "http://www.apache.org/images/feather.gif";
        String src = facesContext.getApplication().getViewHandler().getResourceURL(facesContext, imgUrl);
        
        graphicImage.setId("img1");
        graphicImage.setValue(imgUrl);
        graphicImage.encodeBegin(facesContext);
        graphicImage.encodeChildren(facesContext);
        graphicImage.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        assertEquals("<img id=\"img1\" src=\"" + src + "\"/>", output);
    }

    public void testHtmlPropertyPassTru() throws Exception
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicReadOnlyAttrs();
        
        graphicImage.setId("img1");
        graphicImage.setValue("http://www.apache.org/images/feather.gif");
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                graphicImage, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testHtmlPropertyPassTruNotRendered() throws Exception
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();
        
        graphicImage.setId("img1");
        graphicImage.setValue("http://www.apache.org/images/feather.gif");
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                graphicImage, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
