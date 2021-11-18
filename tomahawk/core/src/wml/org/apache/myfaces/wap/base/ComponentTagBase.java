/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.base;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 * Implements attributes:
 * <ol>
 * <li>id
 * <li>renderer
 * <li>binding
 * </ol>
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */

public abstract class ComponentTagBase extends UIComponentTag {

    /* properties */
    private String id = null;
    private String rendered = null;
    private String binding = null;

    /** Creates a new instance of UIComponentTagBase */
    public ComponentTagBase() {
        super();
    }

    public abstract String getRendererType();

    public void release() {
        super.release();
        this.id = null;
        this.rendered = null;
        this.binding = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        if (getRendererType() != null) {
            component.setRendererType(getRendererType());
        }

        if (id != null) {
            if (isValueReference(id)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(id);
                component.setValueBinding("id", vb);
            } else {
                component.setId(id);
            }
        }

        if (rendered != null) {
            if (isValueReference(rendered)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(rendered);
                component.setValueBinding("rendered", vb);
            } else {
                boolean bool = Boolean.valueOf(rendered).booleanValue();
                component.setRendered(bool);
            }
        }

        if (binding != null) {
            if (isValueReference(binding)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(binding);
                component.setValueBinding("binding", vb);
            } else {
                throw new IllegalArgumentException("Not a valid binding: " + binding);
            }
        }
    }
    // ----------------------------------------------------- Getters and Setters

    /**
     * Getter for property id.
     * @return value of property id.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for property id.
     * @param id new value of property id.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Getter for property rendered.
     * @return value of property rendered.
     */
    public String getRendered() {
        return rendered;
    }

    /**
     * Setter for property rendered.
     * @param rendered new value of property rendered.
     */
    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    /**
     * Setter for property binding.
     * @param binding new value of property binding.
     */
    public void setBinding(String binding) {
        if (!isValueReference(binding)) {
            throw new IllegalArgumentException("Not a valid binding: " + binding);
        }
        this.binding = binding;
    }
}
