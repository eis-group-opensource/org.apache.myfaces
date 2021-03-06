/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.updateactionlistener;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.event.ActionListener;
import javax.faces.webapp.UIComponentTag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentConfig;

/**
 * Tag handler for the Tomahawk UpdateActionListener tag
 * 
 * @since 1.1.7
 * @author arobinson74
 */
public class UpdateActionListenerTagHandler extends TagHandler
{
    private static Log logger = LogFactory
            .getLog(UpdateActionListenerTagHandler.class);

    private TagAttribute converterAttr;
    private TagAttribute propertyAttr;
    private TagAttribute valueAttr;

    /**
     * @param config
     */
    public UpdateActionListenerTagHandler(ComponentConfig config)
    {
        this((TagConfig) config);
    }

    /**
     * @param config
     */
    public UpdateActionListenerTagHandler(TagConfig config)
    {
        super(config);
        valueAttr = getRequiredAttribute("value");
        propertyAttr = getRequiredAttribute("property");
        converterAttr = getAttribute("converter");
    }

    /**
     * @see com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext, javax.faces.component.UIComponent)
     */
    public void apply(FaceletContext ctx, UIComponent parent)
            throws IOException, FacesException, FaceletException, ELException
    {
        logger.debug("Apply called. Component: " + parent);
        ActionSource actionSource = (ActionSource) parent;

        if (sourceHasProperty(actionSource))
            return;

        UpdateActionListener al = new UpdateActionListener();

        Application app = ctx.getFacesContext().getApplication();
        if (converterAttr != null)
        {
            Converter converter = app.createConverter(converterAttr
                    .getValue(ctx));
            al.setConverter(converter);
        }

        String value = valueAttr.getValue();
        if (UIComponentTag.isValueReference(value))
            al.setValueBinding(app.createValueBinding(valueAttr.getValue()));
        else
            al.setValue(value);

        al.setPropertyBinding(app.createValueBinding(propertyAttr.getValue()));

        actionSource.addActionListener(al);
    }

    private boolean sourceHasProperty(ActionSource source)
    {
        ActionListener[] listeners = source.getActionListeners();
        for (int i = 0; i < listeners.length; i++)
        {
            ActionListener listener = listeners[i];
            if (listener instanceof UpdateActionListener == false)
                continue;
            UpdateActionListener al = (UpdateActionListener) listener;
            if (al.getPropertyBinding().getExpressionString().equals(
                    this.propertyAttr.getValue()))
            {
                logger.debug("Action listener already has a listener for "
                        + this.propertyAttr.getValue());
                return true;
            }
        }
        logger.debug("Action listener for property is not present. Property: "
                + this.propertyAttr.getValue());
        return false;
    }
}
