/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.calendar;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;

import org.apache.myfaces.component.behavior.AbstractClientBehaviorTestCase;
import org.apache.myfaces.component.behavior.HtmlClientEventAttributesUtil;
import org.apache.myfaces.component.behavior.HtmlRenderedClientEventAttr;
import org.apache.myfaces.custom.calendar.HtmlInputCalendar;
import org.apache.myfaces.shared_impl.renderkit.ClientBehaviorEvents;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Leonardo Uribe (latest modification by $Author: jankeesvanandel $)
 * @version $Revision: 799929 $ $Date: 2009-08-01 16:29:33 -0500 (01 ago 2009) $
 */
public class HtmlCalendarPopupClientBehaviorRendererTest extends AbstractClientBehaviorTestCase
{
    private HtmlRenderedClientEventAttr[] attrs = null;
    
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        attrs = HtmlClientEventAttributesUtil.generateClientBehaviorInputEventAttrs();
    }

    @Override
    protected void setUpServletObjects() throws Exception
    {
        // TODO Auto-generated method stub
        super.setUpServletObjects();
        request.setAttribute("org.apache.MyFaces.FIRST_SUBMIT_SCRIPT_ON_PAGE", Boolean.FALSE);
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
        HtmlInputCalendar calendar = new HtmlInputCalendar();
        calendar.setRenderAsPopup(true);
        UIComponent form = new HtmlForm();
        form.getChildren().add(calendar);
        facesContext.getViewRoot().getChildren().add(form);
        return calendar;
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
