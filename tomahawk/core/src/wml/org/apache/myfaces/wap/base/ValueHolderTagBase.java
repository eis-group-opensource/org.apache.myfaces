/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.base;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implements attributes:
 * <ol>
 * <li>converter
 * <li>value
 * </ol>
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public abstract class ValueHolderTagBase extends ComponentTagBase {
    private static Log log = LogFactory.getLog(ValueHolderTagBase.class);

    /* properties */
    private String converter = null;
    private String value = null;

    /** Creates a new instance of ValueTag */
    public ValueHolderTagBase() {
        super();
    }

    public abstract String getRendererType();

    public void release() {
        super.release();
        this.converter = null;
        this.value = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        if (converter != null) {
            if (component instanceof ValueHolder) {
                if (isValueReference(converter)) {
                    ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(converter);
                    component.setValueBinding("converter", vb);
                }
                else {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    Converter conv = facesContext.getApplication().createConverter(converter);
                    ((ValueHolder)component).setConverter(conv);
                }
            }
            else {
                log.error("Component " + component.getClass().getName() + " is no ValueHolder, cannot set 'converter' attribute.");
            }
        }


        if (value != null) {
            if (component instanceof ValueHolder) {
                if (isValueReference(value)) {
                    ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(value);
                    component.setValueBinding("value", vb);
                } else
                    ((ValueHolder)component).setValue(value);

            }
            else log.error("Component " + component.getClass().getName() + " is no ValueHolder, cannot set 'value' attribute.");
        }

    }
    // ----------------------------------------------------- Getters and Setters

    /**
     * Getter for property converter.
     * @return value of property converter.
     */
    public String getConverter() {
        return converter;
    }

    /**
     * Setter for property converter.
     * @param converter new value of property converter.
     */
    public void setConverter(String converter) {
        this.converter = converter;
    }

    /**
     * Getter for property value.
     * @return value of property value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter for property value.
     * @param value new value of property value.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
