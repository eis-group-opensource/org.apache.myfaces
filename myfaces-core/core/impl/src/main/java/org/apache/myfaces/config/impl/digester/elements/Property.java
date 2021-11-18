/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

/**
 * @author Martin Marinschek
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 *
     The "property" element represents a JavaBean property of the Java class
     represented by our parent element.

     Property names must be unique within the scope of the Java class
     that is represented by the parent element, and must correspond to
     property names that will be recognized when performing introspection
     against that class via java.beans.Introspector.

    <!ELEMENT property        (description*, display-name*, icon*, property-name, property-class, default-value?, suggested-value?, property-extension*)>

 *          <p/>
 */
public class Property
{
    private List<String> _description;
    private List<String> _displayName;
    private List<String> _icon;
    private String _propertyName;
    private String _propertyClass;
    private String _defaultValue;
    private String _suggestedValue;
    private List<String> _propertyExtension;


    public void addDescription(String value)
    {
        if(_description == null)
            _description = new ArrayList<String>();

        _description.add(value);
    }

    public Iterator<String> getDescriptions()
    {
        if(_description==null)
            return Collections.EMPTY_LIST.iterator();

        return _description.iterator();
    }

    public void addDisplayName(String value)
    {
        if(_displayName == null)
            _displayName = new ArrayList<String>();

        _displayName.add(value);
    }

    public Iterator<String> getDisplayNames()
    {
        if(_displayName==null)
            return Collections.EMPTY_LIST.iterator();

        return _displayName.iterator();
    }

    public void addIcon(String value)
    {
        if(_icon == null)
            _icon = new ArrayList<String>();

        _icon.add(value);
    }

    public Iterator<String> getIcons()
    {
        if(_icon==null)
            return Collections.EMPTY_LIST.iterator();

        return _icon.iterator();
    }

    public void setPropertyName(String propertyName)
    {
        _propertyName = propertyName;
    }

    public String getPropertyName()
    {
        return _propertyName;
    }

    public void setPropertyClass(String propertyClass)
    {
        _propertyClass = propertyClass;
    }

    public String getPropertyClass()
    {
        return _propertyClass;
    }

    public void setDefaultValue(String defaultValue)
    {
        _defaultValue = defaultValue;
    }

    public String getDefaultValue()
    {
        return _defaultValue;
    }

    public void setSuggestedValue(String suggestedValue)
    {
        _suggestedValue = suggestedValue;
    }

    public String getSuggestedValue()
    {
        return _suggestedValue;
    }

    public void addPropertyExtension(String propertyExtension)
    {
        if(_propertyExtension == null)
            _propertyExtension = new ArrayList<String>();

        _propertyExtension.add(propertyExtension);
    }

    public Iterator<String> getPropertyExtensions()
    {
        if(_propertyExtension==null)
            return Collections.EMPTY_LIST.iterator();

        return _propertyExtension.iterator();
    }

}
