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
     The "attribute" element represents a named, typed, value associated with
     the parent UIComponent via the generic attributes mechanism.

     Attribute names must be unique within the scope of the parent (or related)
     component.

     <!ELEMENT attribute       (description*, display-name*, icon*, attribute-name, attribute-class, default-value?, suggested-value?, attribute-extension*)>

 *          <p/>
 */
public class Attribute
{
    private List<String> _description;
    private List<String> _displayName;
    private List<String> _icon;
    private String _attributeName;
    private String _attributeClass;
    private String _defaultValue;
    private String _suggestedValue;
    private List<String> _attributeExtension;


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

    public void setAttributeName(String attributeName)
    {
        _attributeName = attributeName;
    }

    public String getAttributeName()
    {
        return _attributeName;
    }

    public void setAttributeClass(String attributeClass)
    {
        _attributeClass = attributeClass;
    }

    public String getAttributeClass()
    {
        return _attributeClass;
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

    public void addAttributeExtension(String attributeExtension)
    {
        if(_attributeExtension == null)
            _attributeExtension = new ArrayList<String>();

        _attributeExtension.add(attributeExtension);
    }

    public Iterator<String> getAttributeExtensions()
    {
        if(_attributeExtension==null)
            return Collections.EMPTY_LIST.iterator();

        return _attributeExtension.iterator();
    }
}
