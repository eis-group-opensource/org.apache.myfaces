/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import javax.faces.component.html.HtmlOutputText;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlPanelGroup;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;


public class HtmlGroupRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private static String PANEL_CHILD_TEXT = "PANEL";
    private static String STYLE_CLASS = "myStyleClass";

    private MockResponseWriter writer ;
    private HtmlPanelGroup panelGroup;

    public HtmlGroupRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlGroupRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();
        panelGroup = new HtmlPanelGroup();

        HtmlOutputText panelChildOutputText = new HtmlOutputText();
        panelChildOutputText.setValue(PANEL_CHILD_TEXT);
        panelGroup.getChildren().add(panelChildOutputText);

        writer = (MockResponseWriter)facesContext.getResponseWriter();
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        writer = null;
    }
    
    public void testHtmlPropertyPassTruNotRendered() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                panelGroup, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicReadOnlyAttrs();

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                panelGroup, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
