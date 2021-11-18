/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.webapp.webxml;

/**
 * @author Mario Ivankovits (latest modification by $Author: matzew $)
 * @version $Revision: 557350 $ $Date: 2007-07-18 21:19:50 +0300 (Tr, 18 Lie 2007) $
 */
public class FilterMapping
{
    private String _filterName;
    private Class _filterClass;
    private String _urlPattern;
    private boolean _isExtensionMapping = false;

    public FilterMapping(String filterName,
                          Class filterClass,
                          String urlPattern)
    {
        _filterName = filterName;
        _filterClass = filterClass;
        _urlPattern = urlPattern;
        if (_urlPattern != null)
        {
            if (_urlPattern.startsWith("*."))
            {
                _isExtensionMapping = true;
            }
        }
    }

    public boolean isExtensionMapping()
    {
        return _isExtensionMapping;
    }

    public String getFilterName()
    {
        return _filterName;
    }

    public Class getFilterClass()
    {
        return _filterClass;
    }

    public String getUrlPattern()
    {
        return _urlPattern;
    }
}
