/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.StringWriter;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlMessages;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

/**
 * @author Bruno Aranda (latest modification by $Author: lu4242 $)
 * @version $Revision: 779712 $ $Date: 2009-05-28 21:30:00 +0300 (Thu, 28 May 2009) $
 */
public class HtmlMessagesRendererTest extends AbstractJsfTestCase
{
    private static final String ERROR_CLASS = "errorClass";
    private static final String WARN_CLASS = "warnClass";
    private static final String INFO_CLASS = "infoClass";

    private HtmlMessages messages;
    private MockResponseWriter writer;

    public HtmlMessagesRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlMessagesRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();
        messages = new HtmlMessages();

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                messages.getFamily(),
                messages.getRendererType(),
                new HtmlMessagesRenderer());
        
        messages.setErrorClass(ERROR_CLASS);
        messages.setWarnClass(WARN_CLASS);
        messages.setInfoClass(INFO_CLASS);
        messages.setWarnStyle("warnStyle");
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        messages = null;
        writer = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = {
            //_EventProperties
            new HtmlRenderedAttr("onclick",2), 
            new HtmlRenderedAttr("ondblclick",2), 
            new HtmlRenderedAttr("onkeydown",2), 
            new HtmlRenderedAttr("onkeypress",2),
            new HtmlRenderedAttr("onkeyup",2), 
            new HtmlRenderedAttr("onmousedown",2), 
            new HtmlRenderedAttr("onmousemove",2), 
            new HtmlRenderedAttr("onmouseout",2),
            new HtmlRenderedAttr("onmouseover",2), 
            new HtmlRenderedAttr("onmouseup",2),
            //_StyleProperties
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\""),
            new HtmlRenderedAttr("style"),
            new HtmlRenderedAttr("warnClass", "warnClass", "class=\"warnClass\"",2),
            new HtmlRenderedAttr("warnStyle", "warnStyle", "style=\"warnStyle\"",2)
        };
        
        facesContext.addMessage("test1", new FacesMessage(FacesMessage.SEVERITY_WARN, "warnSumary", "detailWarnSummary"));
        facesContext.addMessage("test2", new FacesMessage(FacesMessage.SEVERITY_WARN, "warnSumary2", "detailWarnSummary2"));        

        messages.setLayout("table");
        //messages.setStyle("left: 48px; top: 432px; position: absolute");
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                messages, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testHtmlPropertyPassTruNotRendered() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();
        
        facesContext.addMessage("test1", new FacesMessage(FacesMessage.SEVERITY_WARN, "warnSumary", "detailWarnSummary"));

        messages.setLayout("table");
        messages.setStyle("left: 48px; top: 432px; position: absolute");
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                messages, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
