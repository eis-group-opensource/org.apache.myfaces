/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlSelectManyListbox;
import org.apache.myfaces.component.html.ext.HtmlSelectOneListbox;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlListboxRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private MockResponseWriter writer ;
    private HtmlSelectOneListbox selectOneListbox;
    private HtmlSelectManyListbox selectManyListbox;

    public HtmlListboxRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlListboxRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        selectOneListbox = new HtmlSelectOneListbox();
        selectManyListbox = new HtmlSelectManyListbox();

        writer = (MockResponseWriter) facesContext.getResponseWriter();
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        selectOneListbox = null;
        writer = null;
    }

    public void testSelectOneHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        List items = new ArrayList();
        items.add(new SelectItem("mars"));

        UISelectItems selectItems = new UISelectItems();
        selectItems.setValue(items);

        selectOneListbox.getChildren().add(selectItems);

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                selectOneListbox, facesContext, writer, attrs);
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

        selectManyListbox.getChildren().add(selectItems);
        
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                selectManyListbox, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
