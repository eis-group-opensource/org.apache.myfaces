/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tabbedpane;

import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.myfaces.test.utils.MockTestViewHandler;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlTabbedPaneRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlPanelTabbedPane tabbedPane;
    
    public HtmlTabbedPaneRendererTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(HtmlTabbedPaneRendererTest.class);
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        application.setViewHandler(new MockTestViewHandler());
        tabbedPane = new HtmlPanelTabbedPane();
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        tabbedPane = null;
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
            new HtmlRenderedAttr("style"), 
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"myFaces_panelTabbedPane styleClass\""),
        };
        
        tabbedPane.setServerSideTabSwitch(false);
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        try {
            HtmlCheckAttributesUtil.checkRenderedAttributes(
                    tabbedPane, facesContext, writer, attrs);
        } catch(Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            fail(sw.toString() + "\nHTML.TABLE_PASSTHROUGH_ATTRIBUTES: " + printHTMLAttrs(HTML.TABLE_PASSTHROUGH_ATTRIBUTES));
        }
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testHtmlPropertyPassTruNotRendered() throws Exception 
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();

        tabbedPane.setServerSideTabSwitch(false);
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                tabbedPane, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
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
