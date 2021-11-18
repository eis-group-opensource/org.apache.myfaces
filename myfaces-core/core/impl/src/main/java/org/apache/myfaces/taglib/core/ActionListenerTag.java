/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.el.ValueExpression;
import javax.faces.component.ActionSource;
import javax.faces.event.ActionListener;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspAttribute;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspTag;

/**
 * This tag creates an instance of the specified ActionListener, and
 * associates it with the nearest parent UIComponent.
 * <p>
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * </p>
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFJspTag(
        name="f:actionListener",
        bodyContent="empty")
public class ActionListenerTag
        extends GenericListenerTag<ActionSource, ActionListener>
{
    private static final long serialVersionUID = -2021978765020549175L;

    public ActionListenerTag()
    {
        super(ActionSource.class);
    }

    protected void addListener(ActionSource actionSource, ActionListener actionListener)
    {
        actionSource.addActionListener(actionListener);
    }

    protected ActionListener createDelegateListener(ValueExpression type,
            ValueExpression binding)
    {
        return new DelegateActionListener(type,binding);
    }
    
    /**
     * The fully qualified class name of the ActionListener class.
     */
    @JSFJspAttribute(
            className="java.lang.String",
            rtexprvalue=true)
    public void setType(ValueExpression type)
    {
        super.setType(type);
    }
    
    /**
     * Value binding expression that evaluates to an object that
     * implements javax.faces.event.ActionListener.
     */
    @JSFJspAttribute(
            className="javax.faces.event.ActionListener",
            rtexprvalue=true)
    public void setBinding(ValueExpression binding)
    {
        super.setBinding(binding);
    }
}


