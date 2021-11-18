/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.transform;

import java.io.InputStream;

/**
 * Simple class to be used as a ManagedBean in testing.
 *
 * @author Sean Schofield
 */
public class ManagedFoo
{
    private String content;
    private String stylesheet;
    private String contentLocation;
    private String stylesheetLocation;
    private InputStream contentStream;
    private InputStream styleStream;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getStylesheet()
    {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet)
    {
        this.stylesheet = stylesheet;
    }

    public String getContentLocation()
    {
        return contentLocation;
    }

    public void setContentLocation(String contentLocation)
    {
        this.contentLocation = contentLocation;
    }

    public String getStylesheetLocation()
    {
        return stylesheetLocation;
    }

    public void setStylesheetLocation(String stylesheetLocation)
    {
        this.stylesheetLocation = stylesheetLocation;
    }

    public InputStream getContentStream()
    {
        return contentStream;
    }

    public void setContentStream(InputStream contentStream)
    {
        this.contentStream = contentStream;
    }

    public InputStream getStyleStream()
    {
        return styleStream;
    }

    public void setStyleStream(InputStream styleStream)
    {
        this.styleStream = styleStream;
    }
}
