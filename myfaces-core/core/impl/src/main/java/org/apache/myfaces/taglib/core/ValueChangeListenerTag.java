/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeListener;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspAttribute;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspTag;

/**
 * Adds the specified ValueChangeListener to the nearest parent UIComponent 
 * (which is expected to be a UIInput component).
 * <p>
 * Whenever the form containing the parent UIComponent is submitted,
 * an instance of the specified type is created. If the submitted
 * value from the component is different from the component's current
 * value then a ValueChangeEvent is queued. When the ValueChangeEvent
 * is processed (at end of the validate phase for non-immediate components,
 * or at end of the apply-request-values phase for immediate components)
 * the object's processValueChange method is invoked.
 * </p>
 * <p>
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * </p>
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFJspTag(name="f:valueChangeListener",bodyContent="empty")
public class ValueChangeListenerTag
        extends GenericListenerTag<EditableValueHolder, ValueChangeListener>
{
    private static final long serialVersionUID = 2155190261951046892L;

    public ValueChangeListenerTag()
    {
        super(EditableValueHolder.class);
    }

    protected void addListener(EditableValueHolder editableValueHolder, ValueChangeListener valueChangeListener)
    {
        editableValueHolder.addValueChangeListener(valueChangeListener);
    }
    
    protected ValueChangeListener createDelegateListener(ValueExpression type,
            ValueExpression binding)
    {
        return new DelegateValueChangeListener(type,binding);
    }

    /**
     * The name of a Java class that implements ValueChangeListener.
     */
    @JSFJspAttribute(
            className="java.lang.String",
            rtexprvalue=true)
    public void setType(ValueExpression type)
    {
        super.setType(type);
    }

    /**
     * Value binding expression that evaluates to an implementation of
     * the javax.faces.event.ValueChangeListener interface.
     */
    @JSFJspAttribute(
            className="javax.faces.event.ValueChangeListener",
            rtexprvalue=true)
    public void setBinding(ValueExpression binding)
    {
        super.setBinding(binding);
    }    
}
