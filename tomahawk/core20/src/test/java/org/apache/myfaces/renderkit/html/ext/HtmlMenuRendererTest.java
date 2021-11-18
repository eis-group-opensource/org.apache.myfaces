/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlSelectManyMenu;
import org.apache.myfaces.component.html.ext.HtmlSelectOneMenu;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;

public class HtmlMenuRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlSelectOneMenu selectOneMenu;
    private HtmlSelectManyMenu selectManyMenu;
    private MockResponseWriter writer;

    public HtmlMenuRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlMenuRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();
        writer = (MockResponseWriter)facesContext.getResponseWriter();
        selectOneMenu = new HtmlSelectOneMenu();
        selectManyMenu = new HtmlSelectManyMenu();
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        selectOneMenu = null;
        selectManyMenu = null;
        writer = null;
    }

    public void testSelectOneHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        List items = new ArrayList();
        items.add(new SelectItem("mars"));

        UISelectItems selectItems = new UISelectItems();
        selectItems.setValue(items);

        selectOneMenu.getChildren().add(selectItems);
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                selectOneMenu, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testSelectManyHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        List items = new ArrayList();
        items.add(new SelectItem("mars"));

        UISelectItems selectItems = new UISelectItems();
        selectItems.setValue(items);

        selectManyMenu.getChildren().add(selectItems);
       
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                selectManyMenu, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
