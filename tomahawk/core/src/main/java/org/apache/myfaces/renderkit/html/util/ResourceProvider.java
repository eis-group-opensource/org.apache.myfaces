/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * A class which can provide the resource itself
 *
 * @author imario (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public interface ResourceProvider
{
    /**
     * check if the resource exists
     */
    public boolean exists(ServletContext context, String resource);

    /**
     * get the content length of the resource
     */
    public int getContentLength(ServletContext context, String resource) throws IOException;

    /**
     * get the last modified time of the resource
     */
    public long getLastModified(ServletContext context, String resource) throws IOException;

    /**
     * get the input stream of the resource
     */
    public InputStream getInputStream(ServletContext context, String resource) throws IOException;

    /**
     * get resource encoding
     */
    public String getEncoding(ServletContext context, String resource) throws IOException;
}
