/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectOneRow;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class SelectOneRowRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private SelectOneRow selectOneRow;
    
    public SelectOneRowRendererTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(SelectOneRowRendererTest.class);
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        selectOneRow = new SelectOneRow();   
        selectOneRow.setGroupName("groupName");
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        selectOneRow = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                selectOneRow, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
