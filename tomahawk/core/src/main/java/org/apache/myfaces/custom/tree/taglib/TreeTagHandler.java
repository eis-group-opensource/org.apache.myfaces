/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.model.DefaultTreeModel;
import org.apache.myfaces.custom.tree.model.TreeModel;
import org.apache.myfaces.custom.tree.model.TreePath;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.html.HtmlComponentHandler;

/**
 * 
 * @since 1.1.7
 */
public class TreeTagHandler extends HtmlComponentHandler
{

    private TagAttribute valueAttr;
    private TagAttribute expandRootAttr;
    
    private boolean expandRoot;
    
    public TreeTagHandler(ComponentConfig config)
    {
        super(config);
        valueAttr = getRequiredAttribute("value");
        expandRootAttr = getRequiredAttribute("expandRoot");
        expandRoot = false;
    }
    
    protected void setAttributes(FaceletContext ctx, Object instance)
    {
        super.setAttributes(ctx, instance);
        
        Application app = ctx.getFacesContext().getApplication();
        
        HtmlTree tree = (HtmlTree) instance;
        
        if(valueAttr != null)
        {
            String value = valueAttr.getValue();
            if (value != null && UIComponentTag.isValueReference(value))
            {
                tree.setValueBinding("model", app.createValueBinding(valueAttr
                        .getValue()));
            }
        }
        else
        {
            ValueBinding binding = tree.getValueBinding("model");
            if (binding == null) {
                binding = app.createValueBinding("#{sessionScope.tree}");
            }
            tree.setValueBinding("model", binding);            
        }
        
        if (expandRootAttr != null)
        {
            expandRoot = expandRootAttr.getBoolean(ctx);
        }
    }
    
    protected void onComponentCreated(FaceletContext ctx,
            UIComponent component, UIComponent parent)
    {
        FacesContext context = ctx.getFacesContext();
        Application app = ctx.getFacesContext().getApplication();
        
        if (valueAttr != null) {
            String value = valueAttr.getValue();
            if (value != null){
                ValueBinding valueBinding = app.createValueBinding(value);
                TreeModel treeModel = (TreeModel) (valueBinding.getValue(context));
        
                if (treeModel == null) {
                    // create default model
                    treeModel = new DefaultTreeModel();
                    valueBinding.setValue(context, treeModel);
                }
            }
        }
        
        HtmlTree tree = (HtmlTree) component;
        
        if (expandRoot)
        {
            // component was created, so expand the root node
            TreeModel model = tree.getModel(context);
    
            if (model != null) {
                tree.expandPath(new TreePath(new Object[] { model.getRoot() }),
                        context);
            }
        }
        tree.addToModelListeners();
    }
    
}
