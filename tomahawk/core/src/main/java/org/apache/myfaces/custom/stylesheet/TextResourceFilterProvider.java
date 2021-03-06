/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.stylesheet;

import org.apache.myfaces.renderkit.html.util.ResourceProvider;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Allow URLs to return data that is cached by the TextResourceFilter class.
 * <p>
 * This implements the Tomahawk ResourceProvider interface which works together
 * with the Tomahawk AddResourceFactory, ExtensionsFilter and AddResource classes.
 * The AddResourceFactory can generate a URL which can be embedded in an HTML page.
 * When invoked, that URL triggers the ExtensionsFilter which invokes AddResource
 * which then calls back into this class. And this class then retrieves the
 * requested data from the TextResourceFilter.  
 */
public class TextResourceFilterProvider implements ResourceProvider
{
    // Hack note: a slash has to be prefixed to the resource value here because
    // we are abusing the AddResource API in StylesheetRenderer; see comments
    // in StylesheetRenderer.encodeEnd for details.

    public boolean exists(ServletContext context, String resource)
    {
        resource = "/" + resource; // hack
        return TextResourceFilter.getInstance(context).getFilteredResource(resource) != null;
    }

    public int getContentLength(ServletContext context, String resource) throws IOException
    {
        resource = "/" + resource; // hack
        return TextResourceFilter.getInstance(context).getFilteredResource(resource).getSize();
    }

    public long getLastModified(ServletContext context, String resource) throws IOException
    {
        resource = "/" + resource; // hack
        return TextResourceFilter.getInstance(context).getFilteredResource(resource).getLastModified();
    }

    public InputStream getInputStream(ServletContext context, String resource) throws IOException
    {
        resource = "/" + resource; // hack
        return new ByteArrayInputStream(
            TextResourceFilter.getInstance(context).getFilteredResource(resource).getText().getBytes(
                getEncoding(context, resource)
            ));
    }

    public String getEncoding(ServletContext context, String resource) throws IOException
    {
        return "UTF-8";
    }
}
