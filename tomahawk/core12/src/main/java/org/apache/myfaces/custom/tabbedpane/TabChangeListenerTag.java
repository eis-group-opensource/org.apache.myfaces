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
 * Tag to add a tab change listeners to a {@link org.apache.myfaces.custom.tabbedpane.HtmlPanelTabbedPane}
 *
 * @JSFJspTag
 *   name="t:tabChangeListener"
 *   bodyContent="empty"
 *   tagHandler = "org.apache.myfaces.custom.tabbedpane.TabChangeListenerTagHandler"
 *   
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 692184 $ $Date: 2008-09-04 21:21:52 +0300 (Thu, 04 Sep 2008) $
 */
public class TabChangeListenerTag extends TagSupport
{
    private static final long serialVersionUID = -6903749011638483023L;
    private String type = null;


    public TabChangeListenerTag()
    {
    }

    /**
     * @JSFJspAttribute
     *   required = "true"
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
        UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (componentTag == null)
        {
            throw new JspException("TabChangeListenerTag has no UIComponentTag ancestor");
        }

        if (componentTag.getCreated())
        {
            //Component was just created, so we add the Listener
            UIComponent component = componentTag.getComponentInstance();
            if (component instanceof HtmlPanelTabbedPane)
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
                    ((HtmlPanelTabbedPane) component).addTabChangeListener(listener);
                }
                else if(listenerRef instanceof TabChangeListener)
                {
                    TabChangeListener listener = (TabChangeListener) listenerRef;
                    ((HtmlPanelTabbedPane) component).addTabChangeListener(listener);

                }
                else
                    throw new JspException("type is neither a 'String' nor a value-binding to a 'String' or a 'TabChangeListener' instance.");
            }
            else
            {
                throw new JspException("Component " + component.getId() + " is no HtmlPanelTabbedPane");
            }
        }

        return Tag.SKIP_BODY;
    }
}
