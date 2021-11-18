/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.taglib;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.IconProvider;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

/**
 * 
 * @since 1.1.7
 */
public class IconProviderTagHandler extends TagHandler
{

    private final TagAttribute typeAttr;
    
    public IconProviderTagHandler(TagConfig config)
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
                IconProvider provider = (IconProvider) ClassUtils.newInstance(className);
                ((HtmlTree) parent).setIconProvider(provider);                
            }
            else
            {
                throw new FacesException(
                        "Component is not HtmlTree children");
            }            
        }
    }
}
