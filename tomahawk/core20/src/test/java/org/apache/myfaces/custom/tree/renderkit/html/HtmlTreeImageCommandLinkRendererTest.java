/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.renderkit.html;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.custom.tree.HtmlTreeImageCommandLink;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;

public class HtmlTreeImageCommandLinkRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlTreeImageCommandLink treeCommandLink;
    
    public HtmlTreeImageCommandLinkRendererTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(HtmlTreeImageCommandLinkRendererTest.class);
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        treeCommandLink = new HtmlTreeImageCommandLink();
        facesContext.getApplication().addComponent("org.apache.myfaces.HtmlTreeImageCommandLink", "org.apache.myfaces.custom.tree.HtmlTreeImageCommandLink");        
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        treeCommandLink = null;
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
            new HtmlRenderedAttr("onclick", "onclick", "return jsf.util.chain(document.getElementById(&apos;j_id0&apos;), event,&apos;onclick&apos;, &apos;return myfaces.oam.submitForm(\\&apos;linkDummyForm\\&apos;,\\&apos;j_id0\\&apos;);&apos;);"), 
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
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                treeCommandLink, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
