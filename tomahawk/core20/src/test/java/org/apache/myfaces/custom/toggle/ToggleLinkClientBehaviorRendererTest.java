/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.toggle;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;

import org.apache.myfaces.application.NavigationHandlerImpl;
import org.apache.myfaces.component.behavior.AbstractClientBehaviorTestCase;
import org.apache.myfaces.component.behavior.HtmlClientEventAttributesUtil;
import org.apache.myfaces.component.behavior.HtmlRenderedClientEventAttr;
import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.apache.myfaces.shared_impl.renderkit.ClientBehaviorEvents;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.apache.myfaces.shared_impl.util.ArrayUtils;

/**
 * @author Leonardo Uribe (latest modification by $Author: jankeesvanandel $)
 * @version $Revision: 799929 $ $Date: 2009-08-01 16:29:33 -0500 (01 ago 2009) $
 */
public class ToggleLinkClientBehaviorRendererTest extends AbstractClientBehaviorTestCase
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
                    new HtmlRenderedClientEventAttr(HTML.ONBLUR_ATTR, ClientBehaviorEvents.BLUR)
                });
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
        ToggleLink component = new ToggleLink();
        TogglePanel panel = new TogglePanel();
        UIComponent form = new HtmlForm();
        HtmlInputText inputText = new HtmlInputText();
        inputText.setId("comp1");
        component.setFor("comp1");
        form.getChildren().add(panel);
        panel.getChildren().add(component);
        panel.getChildren().add(inputText);
        facesContext.getViewRoot().getChildren().add(form);
        return component;
    }

    @Override
    protected HtmlRenderedClientEventAttr[] getClientBehaviorHtmlRenderedAttributes()
    {
        return attrs;
    }
}
