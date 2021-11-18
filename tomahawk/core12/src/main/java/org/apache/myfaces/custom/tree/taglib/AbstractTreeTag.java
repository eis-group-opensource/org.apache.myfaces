/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
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
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller </a>
 * @version $Revision: 664402 $ $Date: 2004/10/13 11:50:58
 */
public class AbstractTreeTag extends HtmlPanelGroupTag {

    private ValueExpression value;

    private boolean expandRoot;

    public String getComponentType() {
        return "org.apache.myfaces.HtmlTree";
    }

    public String getRendererType() {
        return "org.apache.myfaces.HtmlTree";
    }

    public ValueExpression getValue() {
        return value;
    }

    public void setValue(ValueExpression newValue) {
        value = newValue;
    }

    public boolean isExpandRoot() {
        return expandRoot;
    }

    public void setExpandRoot(boolean expandRoot) {
        this.expandRoot = expandRoot;
    }

    /**
     * Obtain tree model or create a default model.
     */
    public int doStartTag() throws JspException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (value != null) {
            TreeModel treeModel = (TreeModel) (value.getValue(context.getELContext()));

            if (treeModel == null) {
                // create default model
                treeModel = new DefaultTreeModel();
                value.setValue(context.getELContext(), treeModel);
            }
        }
        int answer = super.doStartTag();
        HtmlTree tree = (HtmlTree) getComponentInstance();

        if (getCreated() && expandRoot) {
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

    public void release() {
        super.release();
        value = null;
        expandRoot = false;
    }

    /**
     * Applies attributes to the tree component
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        
        if (value != null) {
            if (!value.isLiteralText()) {
                component.setValueExpression("model", value);
            }
        } else {
            ValueExpression binding = component.getValueExpression("model");
            if (binding == null) {
                binding = context.getApplication().
                    getExpressionFactory().createValueExpression(
                    context.getELContext(),"#{sessionScope.tree}", Object.class);
            }
            component.setValueExpression("model", binding);
        }
    }
}
