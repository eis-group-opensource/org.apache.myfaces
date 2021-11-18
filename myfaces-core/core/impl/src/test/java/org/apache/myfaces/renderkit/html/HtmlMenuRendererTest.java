/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlSelectManyMenu;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.model.SelectItem;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

/**
 * @author Bruno Aranda (latest modification by $Author: lu4242 $)
 * @version $Revision: 700596 $ $Date: 2008-10-01 01:07:55 +0300 (Wed, 01 Oct 2008) $
 */
public class HtmlMenuRendererTest extends AbstractJsfTestCase
{
    private MockResponseWriter writer ;
    private HtmlSelectOneMenu selectOneMenu;
    private HtmlSelectManyMenu selectManyMenu;

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

        selectOneMenu = new HtmlSelectOneMenu();
        selectManyMenu = new HtmlSelectManyMenu();

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                selectOneMenu.getFamily(),
                selectOneMenu.getRendererType(),
                new HtmlMenuRenderer());
        facesContext.getRenderKit().addRenderer(
                selectManyMenu.getFamily(),
                selectManyMenu.getRendererType(),
                new HtmlMenuRenderer());

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
