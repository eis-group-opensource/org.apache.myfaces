﻿/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext.behavior;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;

import org.apache.myfaces.application.NavigationHandlerImpl;
import org.apache.myfaces.component.behavior.AbstractClientBehaviorTestCase;
import org.apache.myfaces.component.behavior.HtmlClientEventAttributesUtil;
import org.apache.myfaces.component.behavior.HtmlRenderedClientEventAttr;
import org.apache.myfaces.component.html.ext.HtmlCommandLink;
import org.apache.myfaces.shared_impl.renderkit.ClientBehaviorEvents;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.apache.myfaces.shared_impl.util.ArrayUtils;

/**
 * @author Leonardo Uribe (latest modification by $Author: jankeesvanandel $)
 * @version $Revision: 799929 $ $Date: 2009-08-01 16:29:33 -0500 (01 ago 2009) $
 */
public class HtmlCommandLinkClientBehaviorRendererTest extends AbstractClientBehaviorTestCase
{
    private HtmlRenderedClientEventAttr[] attrs = null;
    
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        attrs =  (HtmlRenderedClientEventAttr[]) 
                ArrayUtils.concat(HtmlClientEventAttributesUtil.generateClientBehaviorEventAttrs(),
                new HtmlRenderedClientEventAttr[]{
                    new HtmlRenderedClientEventAttr(HTML.ONFOCUS_ATTR, ClientBehaviorEvents.FOCUS),
                    new HtmlRenderedClientEventAttr(HTML.ONBLUR_ATTR, ClientBehaviorEvents.BLUR),
                    new HtmlRenderedClientEventAttr(HTML.ONCLICK_ATTR, ClientBehaviorEvents.ACTION)
                });
    }
    
    

    @Override
    protected void setUpApplication() throws Exception
    {
        super.setUpApplication();
        application.setNavigationHandler(new NavigationHandlerImpl());
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
        UIComponent component = new HtmlCommandLink();
        UIComponent form = new HtmlForm();
        form.getChildren().add(component);
        facesContext.getViewRoot().getChildren().add(form);
        return component;
    }

    @Override
    protected HtmlRenderedClientEventAttr[] getClientBehaviorHtmlRenderedAttributes()
    {
        return attrs;
    }
}