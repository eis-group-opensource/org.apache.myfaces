/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.IOException;
import java.io.StringWriter;

import javax.faces.component.html.HtmlOutputLabel;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlLabelRendererTest extends AbstractJsfTestCase
{
    private MockResponseWriter writer;
    private HtmlOutputLabel label;
    
    public HtmlLabelRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() 
    {
        return new TestSuite(HtmlLabelRendererTest.class);
    }
    
    public void setUp() throws Exception 
    {
        super.setUp();
        label = new HtmlOutputLabel();
        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
        
        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                label.getFamily(),
                label.getRendererType(),
                new HtmlLabelRenderer());
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        writer = null;
        label = null;
    }

    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = 
        {
            //_AccesskeyProperty
            new HtmlRenderedAttr("accesskey"),
            //_UniversalProperties
            new HtmlRenderedAttr("dir"), 
            new HtmlRenderedAttr("lang"), 
            new HtmlRenderedAttr("title"),
            //_FocusBlurProperties
            new HtmlRenderedAttr("onfocus"), 
            new HtmlRenderedAttr("onblur"),
            //_EventProperties
            new HtmlRenderedAttr("onclick"), 
            new HtmlRenderedAttr("ondblclick"), 
            new HtmlRenderedAttr("onkeydown"), 
            new HtmlRenderedAttr("onkeypress"),
            new HtmlRenderedAttr("onkeyup"), 
            new HtmlRenderedAttr("onmousedown"), 
            new HtmlRenderedAttr("onmousemove"), 
            new HtmlRenderedAttr("onmouseout"),
            new HtmlRenderedAttr("onmouseover"), 
            new HtmlRenderedAttr("onmouseup"),
            //_StyleProperties
            new HtmlRenderedAttr("style"), 
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\""),
        };
        
        label.setValue("outputdata");
        label.setFor("compId");
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                label, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    /**
     * Gets the page contents.
     * @return the page contents
     */
    protected String getPageContents()
    {
        return ((StringWriter) writer.getWriter()).toString();
    }
    
    public void testEscapeUntouched() throws IOException
    {
        label.setId("labelId");
        label.setValue("<span class=\"required\">field label</span>");

        // render label
        label.encodeAll(facesContext);

        String page = getPageContents();
        assertEquals("<label id=\"labelId\">&lt;span class=&quot;required&quot;&gt;field label&lt;/span&gt;</label>", page);
    }

    public void testEscapeSetToFalse() throws IOException
    {
        label.setId("labelId");
        label.setValue("<span class=\"required\">field label</span>");
        label.setEscape(false);

        // render label
        label.encodeAll(facesContext);

        String page = getPageContents();
        assertEquals("<label id=\"labelId\"><span class=\"required\">field label</span></label>", page);
    }
    
}
