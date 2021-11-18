/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class Component
{

    private String componentType;
    private String componentClass;


    public void setComponentType(String componentType)
    {
        this.componentType = componentType;
    }


    public void setComponentClass(String componentClass)
    {
        this.componentClass = componentClass;
    }


    public String getComponentType()
    {
        return componentType;
    }


    public String getComponentClass()
    {
        return componentClass;
    }
}
