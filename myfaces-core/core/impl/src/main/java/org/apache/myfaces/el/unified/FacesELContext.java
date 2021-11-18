/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;
import javax.faces.context.FacesContext;

/**
 * ELContext used for JSF.
 * 
 * @author Stan Silvert
 */
public class FacesELContext extends ELContext
{

    private ELResolver _elResolver;
    private FunctionMapper _functionMapper;
    private VariableMapper _variableMapper;

    public FacesELContext(ELResolver elResolver, FacesContext facesContext)
    {
        this._elResolver = elResolver;
        putContext(FacesContext.class, facesContext);

        // TODO: decide if we need to implement our own FunctionMapperImpl and
        // VariableMapperImpl instead of relying on Tomcat's version.
        // this.functionMapper = new FunctionMapperImpl();
        // this.variableMapper = new VariableMapperImpl();
    }

    @Override
    public VariableMapper getVariableMapper()
    {
        return _variableMapper;
    }

    public void setVariableMapper(VariableMapper varMapper)
    {
        _variableMapper = varMapper;
    }

    @Override
    public FunctionMapper getFunctionMapper()
    {
        return _functionMapper;
    }

    public void setFunctionMapper(FunctionMapper functionMapper)
    {
        _functionMapper = functionMapper;
    }

    @Override
    public ELResolver getELResolver()
    {
        return _elResolver;
    }

}
