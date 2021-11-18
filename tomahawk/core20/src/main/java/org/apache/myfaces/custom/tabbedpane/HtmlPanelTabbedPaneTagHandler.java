/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tabbedpane;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRuleset;

import org.apache.myfaces.custom.facelets.tag.MethodRule;

/**
 * 
 * @since 1.1.7
 *
 */
public class HtmlPanelTabbedPaneTagHandler extends ComponentHandler
{
    static final String METHOD_BINDING_ATTR_NAME = "tabChangeListener";
    static final Class[] METHOD_BINDING_SIGNATURE = { TabChangeEvent.class };

    protected final static MethodRule actionListenerTagRule = new MethodRule(
            METHOD_BINDING_ATTR_NAME, void.class, METHOD_BINDING_SIGNATURE);

    public HtmlPanelTabbedPaneTagHandler(ComponentConfig config)
    {
        super(config);
    }

    protected MetaRuleset createMetaRuleset(Class type)
    {
        return super.createMetaRuleset(type).alias("class", "styleClass")
            .addRule(actionListenerTagRule);
    }

}