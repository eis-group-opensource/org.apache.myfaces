/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectOneLanguage;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class SelectOneLanguageRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private SelectOneLanguage selectLanguage;
    
    public SelectOneLanguageRendererTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(SelectOneLanguageRendererTest.class);
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        selectLanguage = new SelectOneLanguage();
        facesContext.getApplication().addComponent("org.apache.myfaces.CAPTCHA", "org.apache.myfaces.custom.captcha.CAPTCHAComponent");        
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        selectLanguage = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                selectLanguage, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
