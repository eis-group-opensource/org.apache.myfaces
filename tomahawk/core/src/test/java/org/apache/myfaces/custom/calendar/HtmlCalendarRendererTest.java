/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.calendar;

import java.io.IOException;

import javax.faces.FacesException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlCalendarRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlInputCalendar inputCalendar;
    
    public HtmlCalendarRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlCalendarRendererTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();
        inputCalendar = new HtmlInputCalendar();
        facesContext.getApplication().addComponent("org.apache.myfaces.HtmlInputTextHelp", "org.apache.myfaces.custom.inputTextHelp.HtmlInputTextHelp");
    }
    
    public void tearDown() throws Exception {
        super.tearDown();
        inputCalendar = null;
    }
    
    public void testRenderedAttributes() throws IOException {
        System.out.println("Testing rendered attributes");
        inputCalendar.setOnfocus("onfocusTest()");
        inputCalendar.setOnmouseover("onMouseOverTest()");
        inputCalendar.setRenderAsPopup(true);
        
        try {
        inputCalendar.encodeEnd(facesContext);
        } catch(FacesException fe) {
            fe.printStackTrace();
        }
        facesContext.renderResponse();
        System.out.println("---------------------------------------");
        MockResponseWriter writer = (MockResponseWriter) facesContext.getResponseWriter();
        System.out.println(writer.getWriter().toString());
        System.out.println("\n---------------------------------------");
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        attrs[6] = new HtmlRenderedAttr("onchange", "onchange", "onchange=\"this.value = formatValue(this.value);onchange");
        
        inputCalendar.setRenderAsPopup(true);
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                inputCalendar, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
