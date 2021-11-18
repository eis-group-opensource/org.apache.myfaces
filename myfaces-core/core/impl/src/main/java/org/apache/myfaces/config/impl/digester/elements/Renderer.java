/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class Renderer implements org.apache.myfaces.config.element.Renderer
{

    private String componentFamily;
    private String rendererType;
    private String rendererClass;


    public String getComponentFamily()
    {
        return componentFamily;
    }


    public void setComponentFamily(String componentFamily)
    {
        this.componentFamily = componentFamily;
    }


    public String getRendererType()
    {
        return rendererType;
    }


    public void setRendererType(String rendererType)
    {
        this.rendererType = rendererType;
    }


    public String getRendererClass()
    {
        return rendererClass;
    }


    public void setRendererClass(String rendererClass)
    {
        this.rendererClass = rendererClass;
    }


}
