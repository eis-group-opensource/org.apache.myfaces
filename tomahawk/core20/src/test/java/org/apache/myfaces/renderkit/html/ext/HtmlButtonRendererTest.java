/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import javax.faces.component.html.HtmlForm;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlCommandButton;
import org.apache.myfaces.shared_impl.config.MyfacesConfig;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.mock.MockExternalContext;
import org.apache.myfaces.test.mock.MockHttpServletRequest;
import org.apache.myfaces.test.mock.MockHttpServletResponse;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.apache.myfaces.test.mock.MockServletContext;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;

public class HtmlButtonRendererTest extends AbstractTomahawkViewControllerTestCase {

    private MockResponseWriter writer;
    private HtmlCommandButton commandButton;
    private HtmlForm form;
    
    public HtmlButtonRendererTest(String name) {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlButtonRendererTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();
        writer = (MockResponseWriter)facesContext.getResponseWriter();
        commandButton = new HtmlCommandButton();
        form = new HtmlForm();
        commandButton.setParent(form);
    }
    
    public void tearDown() throws Exception {
        super.tearDown();
        writer = null;
        form = null;
        commandButton = null;
    }

    public void testJSAllowedHtmlPropertyPassTru() throws Exception 
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
                //_ChangeSelectProperties
                new HtmlRenderedAttr("onchange"), 
                new HtmlRenderedAttr("onselect"),
                //_EventProperties
                new HtmlRenderedAttr("onclick", "onclick", 
                        "onclick=\""), 
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
        
        MockServletContext servletContext = new MockServletContext();
        servletContext.addInitParameter("org.apache.myfaces.ALLOW_JAVASCRIPT", "true");
        MockExternalContext mockExtCtx = new MockExternalContext(servletContext, 
                new MockHttpServletRequest(), new MockHttpServletResponse());
        MyfacesConfig config = MyfacesConfig.getCurrentInstance(mockExtCtx);
        facesContext.setExternalContext(mockExtCtx);
    
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                commandButton, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testJSNotAllowedHtmlPropertyPassTru() throws Exception 
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
            //_ChangeSelectProperties
            new HtmlRenderedAttr("onchange"), 
            new HtmlRenderedAttr("onselect"),
            //_EventProperties
            new HtmlRenderedAttr("onclick","onclick","var cf = function(){onclick};var oamSF = function(){};return (cf.apply(this, [])==false)? false : oamSF.apply(this, []); "), 
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
                commandButton, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }

    }
}
