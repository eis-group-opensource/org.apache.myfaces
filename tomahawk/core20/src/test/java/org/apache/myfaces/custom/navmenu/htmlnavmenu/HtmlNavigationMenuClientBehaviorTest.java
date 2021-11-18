/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.navmenu.htmlnavmenu;

import javax.faces.component.UIComponent;

import org.apache.myfaces.component.behavior.AbstractClientBehaviorTestCase;
import org.apache.myfaces.component.behavior.HtmlClientEventAttributesUtil;
import org.apache.myfaces.component.behavior.HtmlRenderedClientEventAttr;
import org.junit.Ignore;
import org.junit.Test;


public class HtmlNavigationMenuClientBehaviorTest extends AbstractClientBehaviorTestCase
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
        HtmlPanelNavigationMenu navigation = new HtmlPanelNavigationMenu();
        HtmlCommandNavigationItem menuItem = new HtmlCommandNavigationItem();
        navigation.getChildren().add(menuItem);
        return navigation;
    }

    @Override
    protected HtmlRenderedClientEventAttr[] getClientBehaviorHtmlRenderedAttributes()
    {
        return attrs;
    }
    
    /**
     * div tag does not have name attribute
     */
    @Test
    @Ignore
    @Override
    public void testClientBehaviorHolderRendersName()
    {
        super.testClientBehaviorHolderRendersName();
    }
}
