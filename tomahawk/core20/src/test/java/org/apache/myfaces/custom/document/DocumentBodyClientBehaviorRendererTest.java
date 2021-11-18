/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

import javax.faces.component.UIComponent;

import org.apache.myfaces.component.behavior.AbstractClientBehaviorTestCase;
import org.apache.myfaces.component.behavior.HtmlClientEventAttributesUtil;
import org.apache.myfaces.component.behavior.HtmlRenderedClientEventAttr;
import org.apache.myfaces.shared_impl.renderkit.ClientBehaviorEvents;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Leonardo Uribe (latest modification by $Author: jankeesvanandel $)
 * @version $Revision: 799929 $ $Date: 2009-08-01 16:29:33 -0500 (01 ago 2009) $
 */
public class DocumentBodyClientBehaviorRendererTest extends AbstractClientBehaviorTestCase
{
    private HtmlRenderedClientEventAttr[] attrs = null;
    
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        attrs = (HtmlRenderedClientEventAttr[]) 
            org.apache.myfaces.shared_impl.util.ArrayUtils.concat( 
                    HtmlClientEventAttributesUtil.generateClientBehaviorEventAttrs(),
                new HtmlRenderedClientEventAttr[]{
                    new HtmlRenderedClientEventAttr(HTML.ONLOAD_ATTR, ClientBehaviorEvents.LOAD),
                    new HtmlRenderedClientEventAttr(HTML.ONUNLOAD_ATTR, ClientBehaviorEvents.UNLOAD)
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
        return new DocumentBody();
    }

    @Override
    protected HtmlRenderedClientEventAttr[] getClientBehaviorHtmlRenderedAttributes()
    {
        return attrs;
    }
    
    /**
     * Components that render client behaviors should always render "id" and "name" attribute
     * <body> does not have "name", so we just need to check "id"
     */
    @Test
    @Ignore
    @Override
    public void testClientBehaviorHolderRendersName() 
    {
        super.testClientBehaviorHolderRendersName();
    }

}
