/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

/**
 * A class which provide the resource using the standard <code>class.getResource</code> lookup
 * stuff.
 *
 * @author imario (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class DefaultResourceProvider implements ResourceProvider
{
    private final Class clazz;

    public DefaultResourceProvider(Class clazz)
    {
        this.clazz = clazz;
    }

    protected URL getResource(String resource)
    {
        resource = "resource/" + resource;
        return clazz.getResource(resource);
    }

    public boolean exists(ServletContext context, String resource)
    {
        return getResource(resource) != null;
    }

    public long getLastModified(ServletContext context, String resource) throws IOException
    {
        return getResource(resource).openConnection().getLastModified();
    }

    public int getContentLength(ServletContext context, String resource) throws IOException
    {
        return getResource(resource).openConnection().getContentLength();
    }

    public InputStream getInputStream(ServletContext context, String resource) throws IOException
    {
        return getResource(resource).openConnection().getInputStream();
    }

    public String getEncoding(ServletContext context, String resource) throws IOException
    {
        return null; //Tomahawk-877, this has to be null for now to avoid encoding issues
    }
}
