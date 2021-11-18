/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.aliasbean;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * Tag handler used in facelets
 * 
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 *
 */
public class AliasBeanTagHandler extends ComponentHandler
{
    private TagAttribute valueAttr;
    private TagAttribute aliasAttr;

    public AliasBeanTagHandler(ComponentConfig tagConfig)
    {
        super(tagConfig);

        valueAttr = getRequiredAttribute("value");
        aliasAttr = getRequiredAttribute("alias");
    }

    protected void setAttributes(FaceletContext ctx, Object instance)
    {
        super.setAttributes(ctx, instance);
        
        Application app = ctx.getFacesContext().getApplication();
        
        AliasBean aliasBean = (AliasBean) instance;

        String value = valueAttr.getValue();
        if (UIComponentTag.isValueReference(value))
        {
            aliasBean.setValueBinding("value", app.createValueBinding(valueAttr
                    .getValue()));
        }
        else
        {
            aliasBean.setValue(value);
        }

        String alias = aliasAttr.getValue();
        if (UIComponentTag.isValueReference(alias))
        {
            aliasBean.setValueBinding("alias", app.createValueBinding(aliasAttr
                    .getValue()));
        }
        else
        {
            aliasBean.setAlias(alias);
        }
    }
    
    protected void applyNextHandler(FaceletContext ctx, UIComponent component)
            throws IOException, FacesException, ELException
    {
        AliasBean aliasBean = (AliasBean) component;
        aliasBean.makeAlias(ctx.getFacesContext());
        super.applyNextHandler(ctx, component);
        aliasBean.removeAlias(ctx.getFacesContext());
    }
    
    /**
     * We have to add the children to the parent, for ensure proper
     * behavior between aliasBean and aliasBeansScope.
     * 
     */
    protected void onComponentCreated(FaceletContext faceletcontext,
            UIComponent component, UIComponent parent)    
    {
        parent.getChildren().add(component);
    }

}
