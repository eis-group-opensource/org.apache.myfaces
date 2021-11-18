/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.renderkit.html;

import javax.faces.component.UISelectItem;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.component.html.ext.HtmlSelectManyCheckbox;
import org.apache.myfaces.custom.tree.HtmlTreeCheckbox;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.mock.MockResponseWriter;

public class HtmlTreeCheckboxRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlTreeCheckbox treeCheckbox;
    
    public HtmlTreeCheckboxRendererTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(HtmlTreeCheckboxRendererTest.class);
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        treeCheckbox = new HtmlTreeCheckbox();   
        
        HtmlSelectManyCheckbox selectMany = new HtmlSelectManyCheckbox();
        UISelectItem item = new UISelectItem();
        item.setItemLabel("mars");
        item.setItemValue("mars");
        selectMany.getChildren().add(item);
        treeCheckbox.setFor(selectMany.getClientId(facesContext));
        treeCheckbox.getChildren().add(selectMany);
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        treeCheckbox = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                treeCheckbox, facesContext, writer, attrs);
        //treeCheckbox does not have attributes defined in tld that are rendered in html
        if(!HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
