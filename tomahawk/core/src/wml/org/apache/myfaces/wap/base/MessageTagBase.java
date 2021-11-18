/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.base;

import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Implements attributes:
 * <ol>
 * <li>for
 * <li>showDetail
 * <li>showSummary
 * </ol>
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */

public abstract class MessageTagBase extends ComponentTagBase {

    /* properties */
    private String forComponent = null;
    private String showDetail = null;
    private String showSummary = null;

    /** Creates a new instance of UIComponentTagBase */
    public MessageTagBase() {
        super();
    }

    public abstract String getRendererType();

    public void release() {
        super.release();
        this.forComponent = null;
        this.showDetail = null;
        this.showSummary= null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        if (getRendererType() != null) {
            component.setRendererType(getRendererType());
        }

        UIMessage comp = (UIMessage)component;

        if (forComponent != null) {
            if (isValueReference(forComponent)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(forComponent);
                component.setValueBinding("for", vb);
            } else {
                comp.setFor(forComponent);
            }
        }

        if (showDetail != null) {
            if (isValueReference(showDetail)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(showDetail);
                component.setValueBinding("showDetail", vb);
            } else {
                boolean bool = Boolean.valueOf(showDetail).booleanValue();
                comp.setShowDetail(bool);
            }
        }

        if (showSummary != null) {
            if (isValueReference(showSummary)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(showSummary);
                component.setValueBinding("showSummary", vb);
            } else {
                boolean bool = Boolean.valueOf(showSummary).booleanValue();
                comp.setShowSummary(bool);
            }
        }
    }

    // ----------------------------------------------------- Getters and Setters
     public String getFor() {
        return forComponent;
    }

    public void setFor(String forComponent) {
        this.forComponent = forComponent;
    }

    public String getShowDetail() {
        return showDetail;
    }

    public void setShowDetail(String showDetail) {
        this.showDetail = showDetail;
    }

    public String getShowSummary() {
        return showSummary;
    }

    public void setShowSummary(String showSummary) {
        this.showSummary = showSummary;
    }

}
