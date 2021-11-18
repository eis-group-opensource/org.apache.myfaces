/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.webapp.webxml;

/**
 * @author Manfred Geiler (latest modification by $Author: matzew $)
 * @version $Revision: 557350 $ $Date: 2007-07-18 21:19:50 +0300 (Tr, 18 Lie 2007) $
 */
public class ServletMapping
{
    private final String _servletName;
    private final Class _servletClass;
    private final String _urlPattern;
    private final String _extension;
    private final String _prefix;

    public ServletMapping(String servletName, Class servletClass, String urlPattern)
    {
        _servletName = servletName;
        _servletClass = servletClass;
        _urlPattern = urlPattern;
        _extension = _urlPattern != null && _urlPattern.startsWith("*.") ? _urlPattern.substring(_urlPattern
                .indexOf('.')) : null;
        if (_extension == null)
        {
            int index = _urlPattern.indexOf("/*");
            if (index != -1)
            {
                _prefix = _urlPattern.substring(0, _urlPattern.indexOf("/*"));
            }
            else
            {
                _prefix = _urlPattern;
            }
        }
        else
        {
            _prefix = null;
        }
    }

    public boolean isExtensionMapping()
    {
        return _extension != null;
    }

    public String getExtension()
    {
        return _extension;
    }

    public String getPrefix()
    {
        return _prefix;
    }

    public String getServletName()
    {
        return _servletName;
    }

    public Class getServletClass()
    {
        return _servletClass;
    }

    public String getUrlPattern()
    {
        return _urlPattern;
    }
}
