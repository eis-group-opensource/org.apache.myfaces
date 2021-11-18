/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.StringWriter;

import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputLink;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.shared_impl.config.MyfacesConfig;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockExternalContext;
import org.apache.shale.test.mock.MockHttpServletRequest;
import org.apache.shale.test.mock.MockHttpServletResponse;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;
import org.apache.shale.test.mock.MockServletContext;

/**
 * @author Bruno Aranda (latest modification by $Author: lu4242 $)
 * @version $Revision: 700596 $ $Date: 2008-10-01 01:07:55 +0300 (Wed, 01 Oct 2008) $
 */
public class HtmlLinkRendererTest extends AbstractJsfTestCase
{

    private MockResponseWriter writer;
    private HtmlCommandLink commandLink;
    private HtmlOutputLink outputLink;

    public HtmlLinkRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlLinkRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        UIForm form = new UIForm();

        commandLink = new HtmlCommandLink();
        outputLink = new HtmlOutputLink();
        outputLink.setValue("http://someurl");

        form.getChildren().add(commandLink);

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                commandLink.getFamily(),
                commandLink.getRendererType(),
                new HtmlLinkRenderer());
        facesContext.getRenderKit().addRenderer(
                form.getFamily(),
                form.getRendererType(),
                new HtmlFormRenderer());
        facesContext.getRenderKit().addRenderer(
                outputLink.getFamily(),
                outputLink.getRendererType(),
                new HtmlLinkRenderer());
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        writer = null;
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
            new HtmlRenderedAttr("onclick", "onclick", 
                    "onclick=\"var cf = function(){onclick};var oamSF = function(){return oamSubmitForm(&apos;j_id1&apos;,&apos;j_id1:j_id0&apos;);};return (cf()==false)? false : oamSF();\""), 
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
        
        commandLink.setValue("outputdata");
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                commandLink, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
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
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\""),
            //_TabindexProperty
            new HtmlRenderedAttr("tabindex")
        };

        
        commandLink.setValue("outputdata");
        
        MockServletContext servletContext = new MockServletContext();
        servletContext.addInitParameter("org.apache.myfaces.ALLOW_JAVASCRIPT", "false");
        MockExternalContext mockExtCtx = new MockExternalContext(servletContext, 
                new MockHttpServletRequest(), new MockHttpServletResponse());
        MyfacesConfig config = MyfacesConfig.getCurrentInstance(mockExtCtx);
        facesContext.setExternalContext(mockExtCtx);
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                commandLink, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testOutputLink() throws Exception 
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
            new HtmlRenderedAttr("onclick"), 
            new HtmlRenderedAttr("ondblclick"), 
            new HtmlRenderedAttr("onkeydown"), 
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
                outputLink, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
