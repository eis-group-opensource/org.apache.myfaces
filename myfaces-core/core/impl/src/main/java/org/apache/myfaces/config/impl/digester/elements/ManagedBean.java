/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

import org.apache.myfaces.shared_impl.util.ClassUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class ManagedBean implements org.apache.myfaces.config.element.ManagedBean
{

    private String description;
    private String name;
    private String beanClassName;
    private Class beanClass;
    private String scope;
    private List<ManagedProperty> property = new ArrayList<ManagedProperty>();
    private MapEntries mapEntries;
    private ListEntries listEntries;


    public int getInitMode()
    {
        if (mapEntries != null) {
            return INIT_MODE_MAP;
        }
        if (listEntries != null) {
            return INIT_MODE_LIST;
        }
        if (! property.isEmpty()) {
            return INIT_MODE_PROPERTIES;
        }
        return INIT_MODE_NO_INIT;
    }



    public org.apache.myfaces.config.element.MapEntries getMapEntries()
    {
        return mapEntries;
    }


    public void setMapEntries(MapEntries mapEntries)
    {
        this.mapEntries = mapEntries;
    }


    public org.apache.myfaces.config.element.ListEntries getListEntries()
    {
        return listEntries;
    }


    public void setListEntries(ListEntries listEntries)
    {
        this.listEntries = listEntries;
    }


    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getManagedBeanName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getManagedBeanClassName()
    {
        return beanClassName;
    }


    public Class getManagedBeanClass()
    {
        if (beanClassName == null)
        {
            return null;
        }
        if (beanClass == null)
        {
            beanClass = ClassUtils.simpleClassForName(beanClassName);
        }
        return beanClass;
    }


    public void setBeanClass(String beanClass)
    {
        this.beanClassName = beanClass;
    }


    public String getManagedBeanScope()
    {
        return scope;
    }


    public void setScope(String scope)
    {
        this.scope = scope;
    }


    public void addProperty(ManagedProperty value)
    {
        property.add(value);
    }


    public Iterator<ManagedProperty> getManagedProperties()
    {
        return property.iterator();
    }
}
