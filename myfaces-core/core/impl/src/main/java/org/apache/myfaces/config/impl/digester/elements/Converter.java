/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class Converter
{

    private String converterId;
    private String forClass;
    private String converterClass;
    private List<Property> _properties = null;
    private List<Attribute> _attributes = null;


    public String getConverterId()
    {
        return converterId;
    }


    public void setConverterId(String converterId)
    {
        this.converterId = converterId;
    }


    public String getForClass()
    {
        return forClass;
    }


    public void setForClass(String forClass)
    {
        this.forClass = forClass;
    }


    public String getConverterClass()
    {
        return converterClass;
    }


    public void setConverterClass(String converterClass)
    {
        this.converterClass = converterClass;
    }

    public void addProperty(Property value)
    {
        if(_properties==null)
            _properties = new ArrayList<Property>();

        _properties.add(value);
    }

    public Iterator<Property> getProperties()
    {
        if(_properties==null)
            return Collections.EMPTY_LIST.iterator();

        return _properties.iterator();
    }
    
    public void addAttribute(Attribute value)
    {
        if(_attributes == null)
            _attributes = new ArrayList<Attribute>();

        _attributes.add(value);
    }

    public Iterator<Attribute> getAttributes()
    {
        if(_attributes==null)
            return Collections.EMPTY_LIST.iterator();

        return _attributes.iterator();
    }
}
