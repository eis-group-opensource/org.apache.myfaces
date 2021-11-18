/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tabbedpane;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

/**
 * Custom Facelets taghandler for the TabChangeListener class.
 *  
 * @since 1.1.7
 */
public class TabChangeListenerTagHandler extends TagHandler
{
    private final TagAttribute typeAttr;

    public TabChangeListenerTagHandler(TagConfig config)
    {
        super(config);
        typeAttr = getRequiredAttribute("type");
    }

    public void apply(FaceletContext faceletContext, UIComponent parent)
            throws IOException, FacesException, FaceletException, ELException
    {
        // only process this tag if the parent has just been created..
        if (parent.getParent() == null)
        {
            if (parent instanceof HtmlPanelTabbedPane)
            {
                Object listenerRef;
                if (typeAttr.isLiteral())
                {
                    listenerRef = typeAttr.getValue();
                }
                else
                {
                    listenerRef = typeAttr.getObject(faceletContext);
                }
                
                if (listenerRef instanceof String)
                {
                    String className = (String) listenerRef;
                    TabChangeListener listener = (TabChangeListener) ClassUtils.newInstance(className);
                    ((HtmlPanelTabbedPane) parent).addTabChangeListener(listener);
                }
                else if (listenerRef instanceof TabChangeListener)
                {
                    TabChangeListener listener = (TabChangeListener) listenerRef;
                    ((HtmlPanelTabbedPane) parent).addTabChangeListener(listener);
                }
                else if (listenerRef == null)
                {
                    throw new FaceletException("Property 'type' must not be null.");
                }
                else
                {
                    throw new FaceletException(
                       "Property 'type' must be either a string (containing a class name) " +
                       "or a TabChangeListener instance.");
                }
            }
            else
            {
                throw new FaceletException(
                        "Component " + parent.getId() + " is not of type HtmlPanelTabbedPane");
            }
        }
    }
}
