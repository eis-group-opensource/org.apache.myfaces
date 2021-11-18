/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.event.TreeSelectionListener;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;


/**
 * Tag to add a tree selection listeners to a {@link HtmlTree}
 *
 * @JSFJspTag
 *   name="t:treeSelectionListener"
 *   bodyContent="empty"
 *   tagHandler="org.apache.myfaces.custom.tree.taglib.TreeSelectionListenerTagHandler"
 *   
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 666398 $ $Date: 2008-06-11 03:13:17 +0300 (Wed, 11 Jun 2008) $
 */
public class TreeSelectionListenerTag extends TagSupport
{
    private static final long serialVersionUID = 7322612767746076403L;
    private String type = null;


    public TreeSelectionListenerTag()
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
            throw new JspException("TreeSelectionListenerTag has no UIComponentTag ancestor");
        }

        if (componentTag.getCreated())
        {
            //Component was just created, so we add the Listener
            UIComponent component = componentTag.getComponentInstance();
            if (component instanceof HtmlTree)
            {
                String className;
                if (UIComponentTag.isValueReference(type))
                {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    ValueBinding valueBinding = facesContext.getApplication().createValueBinding(type);
                    className = (String) valueBinding.getValue(facesContext);
                } else
                {
                    className = type;
                }
                TreeSelectionListener listener = (TreeSelectionListener) ClassUtils.newInstance(className);
                ((HtmlTree) component).addTreeSelectionListener(listener);
            } else
            {
                throw new JspException("Component " + component.getId() + " is no HtmlTree");
            }
        }

        return Tag.SKIP_BODY;
    }
}
