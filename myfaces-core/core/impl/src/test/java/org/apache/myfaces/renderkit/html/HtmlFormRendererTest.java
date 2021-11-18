/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.faces.component.html.HtmlForm;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.myfaces.test.utils.MockTestViewHandler;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlFormRendererTest extends AbstractJsfTestCase
{
    private MockResponseWriter writer ;
    private HtmlForm form;

    public HtmlFormRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlFormRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        application.setViewHandler(new MockTestViewHandler());
        form = new HtmlForm();

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                form.getFamily(),
                form.getRendererType(),
                new HtmlFormRenderer());

    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        form = null;
        writer = null;
    }

    public void testHtmlPropertyPassTru() throws Exception 
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicReadOnlyAttrs();

        try {
            HtmlCheckAttributesUtil.checkRenderedAttributes(
                    form, facesContext, writer, attrs);
        } catch(Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            fail(sw.toString() + "\nHTML.FORM_PASSTHROUGH_ATTRIBUTES: " + printHTMLAttrs(HTML.FORM_PASSTHROUGH_ATTRIBUTES));
        }
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testHtmlPropertyPassTruNotRendered() throws Exception 
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();

        try {
            HtmlCheckAttributesUtil.checkRenderedAttributes(
                    form, facesContext, writer, attrs);
        } catch(Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            fail(sw.toString() + "\nHTML.FORM_PASSTHROUGH_ATTRIBUTES: " + printHTMLAttrs(HTML.FORM_PASSTHROUGH_ATTRIBUTES));
        }
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    private String printHTMLAttrs(String[] attrs) {
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < attrs.length; i++) {
            buffer.append(attrs[i]);
            if(i+1 < attrs.length) {
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }
}
