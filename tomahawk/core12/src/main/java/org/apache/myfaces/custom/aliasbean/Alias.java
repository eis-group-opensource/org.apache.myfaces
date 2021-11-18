/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.aliasbean;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A helper bean used by both AliasBean and AliasBeansScope components.
 * <p>
 * An Alias instance represents a single mapping from a "temporary" bean name
 * to the real bean that temporary name should reference. When this alias
 * is "activated" the temporary name is registered and when the alias is
 * "deactivated" the temporary name is removed.
 *
 * @author Sylvain Vieujot (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */

class Alias {
    static final Log log = LogFactory.getLog(Alias.class);
    
    private transient UIComponent _aliasComponent;
    private String _aliasBeanExpression;
    private String _valueExpression;
    private transient boolean _active = false;
    
    private transient Object evaluatedExpression = null;

    Alias(AliasBean aliasComponent){
        this._aliasComponent = aliasComponent;
    }

    /**
     * Define the temporary/transient name that will exist while this alias
     * is "active" (in scope). This is usually a constant string.
     */
    void setAliasBeanExpression(String aliasBeanExpression){
        this._aliasBeanExpression = aliasBeanExpression;
    }
    
    /**
     * Define the object that will be referenced by the temporary/transient
     * name while it exists.
     * <p>
     * This can be a constant, but is more usually an EL expression. The value is
     * recalculated each time this alias is "activated".
     */
    void setValueExpression(String valueExpression){
        this._valueExpression = valueExpression;
    }
    
    String getValueExpression(){
        return _valueExpression;
    }
    
    boolean isActive(){
        return _active;
    }
    
    String[] saveState(){
        return new String[]{_aliasBeanExpression, _valueExpression};
    }
    
    void restoreState(Object state){
        String[] values = (String[]) state;
        _aliasBeanExpression = values[0];
        _valueExpression = values[1];
    }
    
    private void computeEvaluatedExpression(FacesContext facesContext){
        if( evaluatedExpression != null )
            return;
        
        ValueExpression valueVB = null;
        if (_valueExpression == null) {
            valueVB = _aliasComponent.getValueExpression("value");
            _valueExpression = valueVB.getExpressionString();
        }

        if( valueVB == null ){
            if( _valueExpression.startsWith("#{") ){
                valueVB = facesContext.getApplication().getExpressionFactory()
                    .createValueExpression(facesContext.getELContext(),                            
                            _valueExpression,
                            Object.class);
                evaluatedExpression = valueVB.getValue(facesContext.getELContext());
            }else{
                evaluatedExpression = _valueExpression;
            }
        }else{
            evaluatedExpression = valueVB.getValue(facesContext.getELContext());
        }
    }

    /**
     * Activate this alias (ie create the temporary name).
     */
    void make(FacesContext facesContext){
        if( _active )
            return;

        ValueExpression aliasVB;
        if (_aliasBeanExpression == null) {
            aliasVB = _aliasComponent.getValueExpression("alias");
            if( aliasVB == null )
                return;
            _aliasBeanExpression = aliasVB.getExpressionString();
            if( _aliasBeanExpression == null )
                return;
        } else {
            aliasVB = facesContext.getApplication().getExpressionFactory().
                createValueExpression(facesContext.getELContext(),_aliasBeanExpression,Object.class);
        }

        computeEvaluatedExpression( facesContext );
        
        aliasVB.setValue(facesContext.getELContext(), evaluatedExpression);
        _active = true;

        log.debug("makeAlias: " + _valueExpression + " = " + _aliasBeanExpression);
    }
    
    /**
     * Deactivate this alias (ie remove the temporary name).
     */
    void remove(FacesContext facesContext){
        _active = false;
        if( evaluatedExpression == null )
            return;
        
        evaluatedExpression = null;

        log.debug("removeAlias: " + _valueExpression + " != " + _aliasBeanExpression);
        ValueExpression aliasVB = _aliasComponent.getValueExpression("alias");
        if( aliasVB != null )
            aliasVB.setValue(facesContext.getELContext(), null);
    }
}
