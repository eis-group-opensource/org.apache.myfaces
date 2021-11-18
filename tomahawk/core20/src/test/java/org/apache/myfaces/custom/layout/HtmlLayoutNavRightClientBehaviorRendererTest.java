/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.layout;

import javax.faces.component.UIComponent;

import org.apache.myfaces.component.behavior.AbstractClientBehaviorTestCase;
import org.apache.myfaces.component.behavior.HtmlClientEventAttributesUtil;
import org.apache.myfaces.component.behavior.HtmlRenderedClientEventAttr;
import org.apache.myfaces.component.html.ext.HtmlPanelGrid;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Leonardo Uribe (latest modification by $Author: jankeesvanandel $)
 * @version $Revision: 799929 $ $Date: 2009-08-01 16:29:33 -0500 (01 ago 2009) $
 */
public class HtmlLayoutNavRightClientBehaviorRendererTest extends AbstractClientBehaviorTestCase
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
        HtmlPanelLayout panelLayout = new HtmlPanelLayout();
        panelLayout.setLayout("navigationRight");
        return panelLayout;
    }

    @Override
    protected HtmlRenderedClientEventAttr[] getClientBehaviorHtmlRenderedAttributes()
    {
        return attrs;
    }
    
    @Ignore
    @Test
    @Override
    public void testClientBehaviorHolderRendersName() 
    {
        super.testClientBehaviorHolderRendersName();
    }
}
