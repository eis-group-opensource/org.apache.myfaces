/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.picklist;

import javax.faces.component.UISelectItem;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;

public class HtmlPicklistRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlSelectManyPicklist picklist;
    
    public HtmlPicklistRendererTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(HtmlPicklistRendererTest.class);
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        picklist = new HtmlSelectManyPicklist();        
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        picklist = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = {
            //_AccesskeyProperty
            new HtmlRenderedAttr("accesskey", 2),
            //_UniversalProperties
            new HtmlRenderedAttr("dir", 2), 
            new HtmlRenderedAttr("lang", 2), 
            new HtmlRenderedAttr("title", 2),
            //_FocusBlurProperties
            new HtmlRenderedAttr("onfocus", 2), 
            new HtmlRenderedAttr("onblur", 2),
            //_ChangeSelectProperties
            new HtmlRenderedAttr("onchange", 2), 
            new HtmlRenderedAttr("onselect", 2),
            //_EventProperties
            new HtmlRenderedAttr("onclick", 2), 
            new HtmlRenderedAttr("ondblclick", 2), 
            new HtmlRenderedAttr("onkeydown", 2), 
            new HtmlRenderedAttr("onkeypress", 2),
            new HtmlRenderedAttr("onkeyup", 2), 
            new HtmlRenderedAttr("onmousedown", 2), 
            new HtmlRenderedAttr("onmousemove", 2), 
            new HtmlRenderedAttr("onmouseout", 2),
            new HtmlRenderedAttr("onmouseover", 2), 
            new HtmlRenderedAttr("onmouseup", 2),
            //_StyleProperties
            new HtmlRenderedAttr("style", 2), 
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\"", 2),
            //_TabindexProperty
            new HtmlRenderedAttr("tabindex", 2)
        };
        
        UISelectItem item = new UISelectItem();
        item.setItemLabel("mars");
        item.setItemValue("mars");
        picklist.getChildren().add(item);
        picklist.setAddButtonStyle("btnStyle");
        picklist.setAddButtonStyleClass("btnStyleClass");
        picklist.setAddAllButtonStyle("btnStyle");
        picklist.setAddAllButtonStyleClass("btnStyleClass");
        picklist.setRemoveButtonStyle("btnStyle");
        picklist.setRemoveButtonStyleClass("btnStyleClass");
        picklist.setRemoveAllButtonStyle("btnStyle");
        picklist.setRemoveAllButtonStyleClass("btnStyleClass");
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                picklist, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
