/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext.behavior;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import org.apache.myfaces.component.behavior.AbstractClientBehaviorTestCase;
import org.apache.myfaces.component.behavior.HtmlClientEventAttributesUtil;
import org.apache.myfaces.component.behavior.HtmlRenderedClientEventAttr;
import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.apache.myfaces.component.html.ext.HtmlMessage;
import org.apache.myfaces.component.html.ext.HtmlPanelGroup;
import org.junit.Ignore;
import org.junit.Test;


public class HtmlMessageClientBehaviorTest extends AbstractClientBehaviorTestCase
{
    private HtmlRenderedClientEventAttr[] attrs = null;
    
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        attrs = HtmlClientEventAttributesUtil.generateClientBehaviorEventAttrs();
    }

    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
        attrs = null;
    }

    @Override
    protected UIComponent createComponentToTest()
    {
        HtmlMessage component = new HtmlMessage();
        HtmlInputText input = new HtmlInputText();
        String inputId = "input"+facesContext.getViewRoot().createUniqueId(); 
        input.setId(inputId);
        component.setFor(inputId);
        HtmlPanelGroup group = new HtmlPanelGroup();
        group.getChildren().add(input);
        group.getChildren().add(component);
        facesContext.getViewRoot().getChildren().add(group);
        facesContext.addMessage(inputId, new FacesMessage("message1"));
        return component;
    }

    @Override
    protected HtmlRenderedClientEventAttr[] getClientBehaviorHtmlRenderedAttributes()
    {
        return attrs;
    }
    
    /**
     * span tag does not have name attribute
     */
    @Test
    @Ignore
    @Override
    public void testClientBehaviorHolderRendersName()
    {
        super.testClientBehaviorHolderRendersName();
    }
}
