/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.base;

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implements attributes:
 * <ol>
 * <li>action
 * <li>actionListener
 * </ol>
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public abstract class ActionSourceTagBase extends ComponentTagBase {
    private static Log log = LogFactory.getLog(ActionSourceTagBase.class);

    /* properties */
    private String action = null;
    private String actionListener = null;
    private String immediate = null;

    /** Creates a new instance of CommandTag */
    public ActionSourceTagBase() {
        super();
    }

    public abstract String getRendererType();

    public void release() {
        super.release();
        this.action = null;
        this.actionListener = null;
        this.immediate = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        Class[] mbParams = {ActionEvent.class};

        if (action != null) {
            if (!(component instanceof ActionSource)) {
                throw new IllegalArgumentException("Component " + component.getId() + " is no ActionSource, cannot set 'action' attribute.");
            }
            MethodBinding mb;
            if (isValueReference(action))
                mb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(action, null);
            else
                mb = new ConstantMethodBinding(action);

            ((ActionSource)component).setAction(mb);
        }

        if (actionListener != null) {
            if (!(component instanceof ActionSource)) {
                throw new IllegalArgumentException("Component " + component.getId() + " is no ActionSource, cannot set 'actionListener' attribute.");
            }
            if (isValueReference(actionListener)) {
                MethodBinding mb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(actionListener, mbParams);
                ((ActionSource)component).setActionListener(mb);
            }
            else {
                log.error("Invalid expression " + actionListener);
            }
        }

        if (immediate != null) {
            if (component instanceof ActionSource) {
                if (isValueReference(immediate)) {
                    ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(immediate);
                    component.setValueBinding("immediate", vb);
                }
                else {
                    Boolean imm = Boolean.valueOf(immediate);
                    ((ActionSource)component).setImmediate(imm.booleanValue());
                }

            }
            else log.error("Component " + component.getClass().getName() + " is no ActionSource, cannot set 'immediate' attribute.");
        }
    }

    // ----------------------------------------------------- Getters and Setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionListener() {
        return actionListener;
    }

    public void setActionListener(String actionListener) {
        this.actionListener = actionListener;
    }

    /**
     * Getter for property immediate.
     * @return value of property immediate.
     */
    public String getImmediate() {
        return immediate;
    }

    /**
     * Setter for property immediate.
     * @param converter new value of property immediate.
     */
    public void setImmediate(String immediate) {
        this.immediate = immediate;
    }
}
