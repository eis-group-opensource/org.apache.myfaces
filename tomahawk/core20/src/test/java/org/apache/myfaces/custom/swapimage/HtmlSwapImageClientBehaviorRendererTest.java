/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.swapimage;

import javax.faces.component.UIComponent;

import org.apache.myfaces.component.behavior.AbstractClientBehaviorTestCase;
import org.apache.myfaces.component.behavior.HtmlClientEventAttributesUtil;
import org.apache.myfaces.component.behavior.HtmlRenderedClientEventAttr;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Leonardo Uribe (latest modification by $Author: jankeesvanandel $)
 * @version $Revision: 799929 $ $Date: 2009-08-01 16:29:33 -0500 (01 ago 2009) $
 */
public class HtmlSwapImageClientBehaviorRendererTest extends AbstractClientBehaviorTestCase
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
        HtmlSwapImage swapImage = new HtmlSwapImage();
        swapImage.setActiveImageUrl("http://testurl/swapimage/activeImg.jpeg");
        swapImage.setSwapImageUrl("http://testurl/swapimage/swapImg.jpeg");
        swapImage.setUrl("http://testurl.com");
        return swapImage;
    }

    @Override
    protected HtmlRenderedClientEventAttr[] getClientBehaviorHtmlRenderedAttributes()
    {
        return attrs;
    }

    @Test
    @Ignore
    @Override
    public void testClientBehaviorHolderRendersName()
    {
        super.testClientBehaviorHolderRendersName();
    }
}
