/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.toggle;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class ToggleLinkRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private MockResponseWriter writer;
    private ToggleLink link;
    private TogglePanel panel;
    
    public ToggleLinkRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(ToggleLinkRendererTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();
        writer = (MockResponseWriter)facesContext.getResponseWriter();
        link = new ToggleLink();
        panel = new TogglePanel();
        
        HtmlInputText inputText = new HtmlInputText();
        inputText.setId("comp1");
        
        panel.getChildren().add(link);
        panel.getChildren().add(inputText);
        link.setFor("comp1");
    }
    
    public void tearDown() {
        writer = null;
        link = null;
    }    
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = {
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
            new HtmlRenderedAttr("onclick", "onclick", "onclick=\""), 
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
            //_TabindexProperty
            new HtmlRenderedAttr("tabindex")
        };

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                link, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }    
}
