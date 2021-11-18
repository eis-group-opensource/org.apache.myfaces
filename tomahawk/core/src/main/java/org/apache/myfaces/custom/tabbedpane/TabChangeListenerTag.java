/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tabbedpane;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.myfaces.shared_tomahawk.util.ClassUtils;


/**
 * Adds a tab-change-listener to the enclosing t:panelTabbedPane component.
 * <p>
 * When the panelTabbedPane changes the displayed tab, the listener is invoked.
 * </p>
 *
 * @JSFJspTag
 *   name="t:tabChangeListener"
 *   bodyContent="empty"
 *   tagHandler = "org.apache.myfaces.custom.tabbedpane.TabChangeListenerTagHandler"
 *   
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 698966 $ $Date: 2008-09-25 16:41:35 +0300 (Thu, 25 Sep 2008) $
 */
public class TabChangeListenerTag extends TagSupport
{
    private static final long serialVersionUID = -6903749011638483023L;
    private String type = null;


    public TabChangeListenerTag()
    {
    }

    /**
     * Define a listener to be attached to the parent HtmlPanelTabbedPane instance.
     * <p>
     * This attribute may be a literal string containing a fully-qualified class name. The
     * specified class must implement the TabChangeListener interface and have a no-arguments
     * constructor. A new instance will be created when the view is created.
     * </p>
     * <p>
     * This attribute may also be an EL expression that returns type String. The EL expression will be
     * evaluated when the view is built, and the returned value must be a fully-qualified class name. The
     * specified class must implement the TabChangeListener interface and have a no-arguments constructor.
     * A new instance will be created when the view is created.
     * </p>
     * <p>
     * This attribute may also be an EL expression that returns a TabChangeListener instance.
     * </p>
     * <p>
     * It is an error if an EL expression returns an object of any type other than String or TabChangeListener.
     * </p>
     * 
     * @JSFJspAttribute required = "true"
     */
    public void setType(String type)
    {
        this.type = type;
    }


    public int doStartTag() throws JspException
    {
        if (type == null)
        {
            throw new JspException("type attribute not set");
        }

        //Find parent UIComponentTag
        UIComponentTag parentComponentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (parentComponentTag == null)
        {
            throw new JspException("TabChangeListenerTag has no UIComponentTag ancestor");
        }

        if (parentComponentTag.getCreated())
        {
            //Component was just created, so we add the Listener
            UIComponent parent = parentComponentTag.getComponentInstance();
            if (parent instanceof HtmlPanelTabbedPane)
            {
                Object listenerRef = type;
                if (UIComponentTag.isValueReference(type))
                {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    ValueBinding valueBinding = facesContext.getApplication().createValueBinding(type);
                    listenerRef = valueBinding.getValue(facesContext);
                }

                if(listenerRef instanceof String)
                {
                    String className = (String) listenerRef;
                    TabChangeListener listener = (TabChangeListener) ClassUtils.newInstance(className);
                    ((HtmlPanelTabbedPane) parent).addTabChangeListener(listener);
                }
                else if(listenerRef instanceof TabChangeListener)
                {
                    TabChangeListener listener = (TabChangeListener) listenerRef;
                    ((HtmlPanelTabbedPane) parent).addTabChangeListener(listener);
                }
                else if (listenerRef == null)
                {
                    throw new JspException("Property 'type' must not be null.");
                }
                else
                {
                    throw new JspException(
                       "Property 'type' must be either a string (containing a class name) " +
                       "or a TabChangeListener instance.");
                }
            }
            else
            {
                throw new JspException(
                    "Component " + parent.getId() + " is not of type HtmlPanelTabbedPane");
            }
        }

        return Tag.SKIP_BODY;
    }
}
