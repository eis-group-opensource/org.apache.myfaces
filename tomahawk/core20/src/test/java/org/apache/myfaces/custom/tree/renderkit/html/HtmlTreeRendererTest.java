/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.renderkit.html;

import javax.el.ValueExpression;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.model.DefaultTreeModel;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;

public class HtmlTreeRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlTree tree;
    
    public HtmlTreeRendererTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(HtmlTreeRendererTest.class);
    }
    
    public void setUp() throws Exception
    {
        super.setUp();
        tree = new HtmlTree();
        facesContext.getApplication().addComponent("org.apache.myfaces.HtmlTreeNode", "org.apache.myfaces.custom.tree.HtmlTreeNode");
        facesContext.getApplication().addComponent("org.apache.myfaces.HtmlTreeImageCommandLink", "org.apache.myfaces.custom.tree.HtmlTreeImageCommandLink");
        
        DefaultTreeModel treeModel = new DefaultTreeModel();
        ValueExpression v = facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), "#{model}", treeModel.getClass());
        facesContext.getExternalContext().getRequestMap().put("model", treeModel);
        tree.setValueExpression("model", v);
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        tree = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicReadOnlyAttrs();
            
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                tree, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testHtmlPropertyPassTruNotRendered() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateAttrsNotRenderedForReadOnly();
             
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                tree, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) 
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
