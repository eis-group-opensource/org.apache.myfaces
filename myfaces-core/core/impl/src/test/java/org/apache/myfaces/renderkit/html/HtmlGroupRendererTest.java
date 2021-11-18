/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.StringWriter;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

/**
 * @author Bruno Aranda (latest modification by $Author: lu4242 $)
 * @version $Revision: 700596 $ $Date: 2008-10-01 01:07:55 +0300 (Wed, 01 Oct 2008) $
 */
public class HtmlGroupRendererTest extends AbstractJsfTestCase
{
    private static String PANEL_CHILD_TEXT = "PANEL";

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

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                panelGroup.getFamily(),
                panelGroup.getRendererType(),
                new HtmlGroupRenderer());
        facesContext.getRenderKit().addRenderer(
                panelChildOutputText.getFamily(),
                panelChildOutputText.getRendererType(),
                new HtmlTextRenderer());

    }

    public void tearDown()throws Exception
    {
        super.tearDown();
        writer = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicReadOnlyAttrs();        

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                panelGroup, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testHtmlPropertyPassTruNotRendered() throws Exception
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();        

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                panelGroup, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
