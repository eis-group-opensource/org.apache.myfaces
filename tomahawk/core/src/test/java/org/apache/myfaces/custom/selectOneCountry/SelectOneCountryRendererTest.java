/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectOneCountry;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class SelectOneCountryRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private SelectOneCountry selectCountry;
    
    public SelectOneCountryRendererTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(SelectOneCountryRendererTest.class);
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        selectCountry = new SelectOneCountry();
        facesContext.getApplication().addComponent("org.apache.myfaces.CAPTCHA", "org.apache.myfaces.custom.captcha.CAPTCHAComponent");        
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        selectCountry = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                selectCountry, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
