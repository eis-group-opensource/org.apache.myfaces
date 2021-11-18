/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import static org.testng.Assert.*;

import javax.el.ValueExpression;
import javax.faces.el.ValueBinding;

import org.easymock.classextension.EasyMock;
import org.testng.annotations.Test;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 526438 $ $Date: 2007-04-07 17:16:53 +0300 (Sat, 07 Apr 2007) $
 */
@SuppressWarnings("deprecation")
public class UIGraphicTest
{

    @Test
    public void testUrlValue()
    {
        UIGraphic graphic = new UIGraphic();
        graphic.setValue("xxx");
        assertEquals(graphic.getUrl(), "xxx");
        graphic.setUrl("xyz");
        assertEquals(graphic.getValue(), "xyz");
    }

    @Test
    public void testUrlValueExpression()
    {
        UIGraphic graphic = new UIGraphic();
        ValueExpression expression = EasyMock.createMock(ValueExpression.class);
        graphic.setValueExpression("url", expression);
        assertSame(graphic.getValueExpression("value"), expression);

        expression = EasyMock.createMock(ValueExpression.class);
        graphic.setValueExpression("value", expression);
        assertSame(graphic.getValueExpression("url"), expression);
    }

    @Test
    public void testUrlValueBinding()
    {
        UIGraphic graphic = new UIGraphic();
        ValueBinding binding = EasyMock.createMock(ValueBinding.class);
        graphic.setValueBinding("url", binding);
        assertSame(graphic.getValueBinding("value"), binding);

        binding = EasyMock.createMock(ValueBinding.class);
        graphic.setValueBinding("value", binding);
        assertSame(graphic.getValueBinding("url"), binding);
    }
}
