/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.StringWriter;

import javax.faces.component.UIColumn;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;

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
public class HtmlGridRendererTest extends AbstractJsfTestCase
{
    private static final String LINE_SEPARATOR = System.getProperty(
            "line.separator", "\r\n");

    private MockResponseWriter writer ;
    private HtmlPanelGrid panelGrid;
    private HtmlOutputText colText;

    public HtmlGridRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlGridRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        panelGrid = new HtmlPanelGrid();
        colText = new HtmlOutputText();

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                panelGrid.getFamily(),
                panelGrid.getRendererType(),
                new HtmlGridRenderer());
        facesContext.getRenderKit().addRenderer(
                colText.getFamily(),
                colText.getRendererType(),
                new HtmlTextRenderer());

    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        panelGrid = null;
        writer = null;
    }

    public void testRenderTable() throws Exception
    {
        UIColumn col1 = new UIColumn();
        HtmlOutputText col1Text = new HtmlOutputText();
        col1Text.setValue("col1Text");

        UIColumn col2 = new UIColumn();
        HtmlOutputText col2Text = new HtmlOutputText();
        col2Text.setValue("col2Text");

        col1.getChildren().add(col1Text);
        col2.getChildren().add(col2Text);
        panelGrid.getChildren().add(col1);
        panelGrid.getChildren().add(col2);

        panelGrid.encodeBegin(facesContext);
        panelGrid.encodeChildren(facesContext);
        panelGrid.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        assertEquals("<table><tbody><tr><td>col1Text</td></tr>" + LINE_SEPARATOR +
                "<tr><td>col2Text</td></tr>" + LINE_SEPARATOR +
                "</tbody></table>", output);
    }

    public void testHtmlPropertyPassTru() throws Exception 
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicReadOnlyAttrs();

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                panelGrid, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testHtmlPropertyPassTruNotRendered() throws Exception 
    { 
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                panelGrid, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
