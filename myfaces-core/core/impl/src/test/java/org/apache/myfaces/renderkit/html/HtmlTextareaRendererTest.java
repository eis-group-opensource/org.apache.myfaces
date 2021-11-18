/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.StringWriter;

import javax.faces.component.html.HtmlInputTextarea;

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
public class HtmlTextareaRendererTest extends AbstractJsfTestCase
{
    private MockResponseWriter writer ;
    private HtmlInputTextarea inputTextarea;

    public HtmlTextareaRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlTextareaRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        inputTextarea = new HtmlInputTextarea();

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                inputTextarea.getFamily(),
                inputTextarea.getRendererType(),
                new HtmlTextareaRenderer());

    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        inputTextarea = null;
        writer = null;
    }

    public void testRenderDefault() throws Exception
    {
        inputTextarea.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        assertEquals("<textarea name=\"j_id0\"></textarea>", output);
    }

    public void testRenderColsRows() throws Exception
    {
        inputTextarea.setCols(5);
        inputTextarea.setRows(10);
        inputTextarea.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        assertEquals("<textarea name=\"j_id0\" cols=\"5\" rows=\"10\"></textarea>", output);
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                inputTextarea, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
