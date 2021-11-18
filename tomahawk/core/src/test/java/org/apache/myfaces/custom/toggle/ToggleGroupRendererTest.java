/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.toggle;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class ToggleGroupRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private MockResponseWriter writer;
    private ToggleGroup toggleGroup;
    
    public ToggleGroupRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(ToggleGroupRendererTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();
        writer = (MockResponseWriter)facesContext.getResponseWriter();
        toggleGroup = new ToggleGroup();
    }
    
    public void tearDown() {
        writer = null;
        toggleGroup = null;
    }    
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = {
            //_UniversalProperties
            new HtmlRenderedAttr("dir"), 
            new HtmlRenderedAttr("lang"), 
            new HtmlRenderedAttr("title"),
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
            new HtmlRenderedAttr("style", "style", "style=\"style\""), 
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\""),
        };

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                toggleGroup, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }    
    
    public void testHtmlPropertyPassTruNotRendered() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                toggleGroup, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }    
}
