/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.base;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
/**
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public class ConstantMethodBinding extends MethodBinding implements StateHolder {
    private String outCome;
    private boolean _transient;

    /** Creates a new instance of ConstantMethodBinding */
    public ConstantMethodBinding() {
    }

    public ConstantMethodBinding(String outCome) {
        this.outCome = outCome;
    }

    public Class getType(FacesContext facesContext) throws MethodNotFoundException {
        return(String.class);
    }

    public Object invoke(FacesContext facesContext, Object[] obj) throws EvaluationException, MethodNotFoundException {
        return(outCome);
    }


    public void restoreState(FacesContext facesContext, Object obj) {
        this.outCome = (String)obj;
    }

    public Object saveState(FacesContext facesContext) {
        return(outCome);
    }

    public boolean isTransient() {
        return _transient;
    }

    public void setTransient(boolean _transient) {
        this._transient = _transient;
    }
}
