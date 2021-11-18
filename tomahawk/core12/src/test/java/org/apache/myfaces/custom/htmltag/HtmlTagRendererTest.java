/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.htmltag;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.custom.htmlTag.HtmlTag;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlTagRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlTag tag;

    public HtmlTagRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() 
    {
        return new TestSuite(HtmlTagRendererTest.class);
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        tag = new HtmlTag();        
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        tag = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = {
            //_StyleProperties
            new HtmlRenderedAttr("style"), 
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\""),
        };
        
        tag.setValue("div");
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                tag, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
