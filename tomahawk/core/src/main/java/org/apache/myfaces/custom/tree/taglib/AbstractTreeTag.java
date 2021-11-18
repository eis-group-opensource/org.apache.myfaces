/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.JspException;

import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.model.DefaultTreeModel;
import org.apache.myfaces.custom.tree.model.TreeModel;
import org.apache.myfaces.custom.tree.model.TreePath;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlPanelGroupTag;

/**
 * <p>
 * HtmlTree tag.
 * </p>
 * @since 1.1.7
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller </a>
 * @version $Revision: 888604 $ $Date: 2004/10/13 11:50:58
 */
public class AbstractTreeTag extends HtmlPanelGroupTag {

    private String value;

    private String expandRoot;

    public String getComponentType() {
        return "org.apache.myfaces.HtmlTree";
    }

    public String getRendererType() {
        return "org.apache.myfaces.HtmlTree";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        value = newValue;
    }

    public String isExpandRoot() {
        return expandRoot;
    }

    public void setExpandRoot(String expandRoot) {
        this.expandRoot = expandRoot;
    }

    /**
     * Obtain tree model or create a default model.
     */
    public int doStartTag() throws JspException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (value != null) {
            ValueBinding valueBinding = context.getApplication()
                    .createValueBinding(value);
            TreeModel treeModel = (TreeModel) (valueBinding.getValue(context));

            if (treeModel == null) {
                // create default model
                treeModel = new DefaultTreeModel();
                valueBinding.setValue(context, treeModel);
            }
        }
        int answer = super.doStartTag();
        HtmlTree tree = (HtmlTree) getComponentInstance();

        if (getCreated() && parseBoolean(expandRoot)) {
            // component was created, so expand the root node
            TreeModel model = tree.getModel(context);

            if (model != null) {
                tree.expandPath(new TreePath(new Object[] { model.getRoot() }),
                        context);
            }
        }

        tree.addToModelListeners();
        return answer;
    }
    
    private boolean parseBoolean(String s)
    {
        return ((s != null) && s.equalsIgnoreCase("true"));
    }

    public void release() {
        super.release();
        value = null;
        expandRoot = null;
    }

    /**
     * Applies attributes to the tree component
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        if (value != null) {
            if (isValueReference(value)) {
                ValueBinding binding = context.getApplication()
                        .createValueBinding(value);
                component.setValueBinding("model", binding);
            }
        } else {
            ValueBinding binding = component.getValueBinding("model");
            if (binding == null) {
                binding = context.getApplication().createValueBinding(
                        "#{sessionScope.tree}");
            }
            component.setValueBinding("model", binding);
        }
    }
}
