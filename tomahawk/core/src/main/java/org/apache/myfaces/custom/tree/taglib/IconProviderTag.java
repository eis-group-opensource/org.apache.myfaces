/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.taglib;

import org.apache.myfaces.shared_tomahawk.util.ClassUtils;
import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.IconProvider;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * @JSFJspTag
 *   name="t:iconProvider"
 *   bodyContent="empty"
 *   tagHandler="org.apache.myfaces.custom.tree.taglib.IconProviderTagHandler"
 *   
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 666398 $ $Date: 2008-06-11 03:13:17 +0300 (Wed, 11 Jun 2008) $
 */
public class IconProviderTag
    extends TagSupport
{
    private static final long serialVersionUID = -8851450834386187922L;
    private String type = null;


    public IconProviderTag()
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
            throw new JspException("IconProviderTag has no UIComponentTag ancestor");
        }

        UIComponent component = componentTag.getComponentInstance();
        if (component instanceof HtmlTree)
        {
            String className;
            if (UIComponentTag.isValueReference(type))
            {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ValueBinding vb = facesContext.getApplication().createValueBinding(type);
                className = (String) vb.getValue(facesContext);
            } else
            {
                className = type;
            }
            IconProvider provider = (IconProvider) ClassUtils.newInstance(className);
            ((HtmlTree) component).setIconProvider(provider);
        } else
        {
            throw new JspException("Component " + component.getId() + " is no HtmlTree");
        }

        return Tag.SKIP_BODY;
    }
}
