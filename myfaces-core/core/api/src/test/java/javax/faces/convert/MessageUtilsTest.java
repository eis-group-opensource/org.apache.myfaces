/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.convert;

import javax.el.ValueExpression;
import javax.faces.component.html.HtmlInputText;

import junit.framework.Test;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.el.MockValueExpression;

public class MessageUtilsTest extends AbstractJsfTestCase {
    
    public MessageUtilsTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetLabelFromAttributesMap() {
        HtmlInputText inputText = new HtmlInputText();
        inputText.getAttributes().put("label", "testLabel");
        Object label = _MessageUtils.getLabel(facesContext, inputText);
        assertEquals("testLabel", label);
    }
    
    public void testGetLabelFromValueExpression() {
        facesContext.getExternalContext().getRequestMap().put("lbl", "testLabel");
        HtmlInputText inputText = new HtmlInputText();
        ValueExpression expression = new MockValueExpression("#{requestScope.lbl}",String.class);
        inputText.setValueExpression("label", expression);
        
        Object label = _MessageUtils.getLabel(facesContext, inputText);
        assertEquals("testLabel", label);
    }
    
    public void testGetLabelReturnsClientIdWhenLabelIsNotSpecified() {
        HtmlInputText inputText = new HtmlInputText();
        inputText.setId("testId");
        Object label = _MessageUtils.getLabel(facesContext, inputText);
        assertEquals("testId", label);
    }
}