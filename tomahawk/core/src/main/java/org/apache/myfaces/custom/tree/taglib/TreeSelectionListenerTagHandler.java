/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.taglib;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.event.TreeSelectionListener;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

/**
 * 
 * @since 1.1.7
 */
public class TreeSelectionListenerTagHandler extends TagHandler
{

    private final TagAttribute typeAttr;
    
    public TreeSelectionListenerTagHandler(TagConfig config)
    {
        super(config);
        typeAttr = getRequiredAttribute("type");
    }

    public void apply(FaceletContext faceletContext, UIComponent parent)
            throws IOException, FacesException, FaceletException, ELException
    {
        if (parent.getParent() == null)
        {
            if (parent instanceof HtmlTree)
            {
                String className;
                if (!typeAttr.isLiteral())
                {
                    className = typeAttr.getValue();
                }
                else
                {
                    className = typeAttr.getValue(faceletContext);
                }
                TreeSelectionListener listener = (TreeSelectionListener) ClassUtils.newInstance(className);
                ((HtmlTree) parent).addTreeSelectionListener(listener);
            }
            else
            {
                throw new FacesException(
                        "Component is not HtmlTree children");
            }            
        }
    }
}
