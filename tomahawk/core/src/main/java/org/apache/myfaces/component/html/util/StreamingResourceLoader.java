/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.component.html.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.ResourceLoader;

/**
 * Serve component-specific resources that MUST be embedded in the HEAD
 * of an html page.
 * <p>
 * Currently, there is only one case where resources <i>must</i> be in the
 * document head: inline CSS or links to CSS stylesheets.
 * <p>
 * When using the StreamingAddResource class, a single link is output in the
 * document HEAD for each page which embeds the name of this class in the url.
 * This causes the browser to make a GET request to that link url when rendering
 * the page; the tomahawk extensions filter sees the embedded ResourceLoader
 * class name and creates an instance of this class to handle the request.
 * <p>
 * Note that for other resources the StreamingAddResources class generates urls
 * that embed the standard MyFacesResourceLoader url, ie this class does not
 * handle serving of resources other than the ones that MUST be in the head 
 * section.
 * <p>
 * The url also embeds a "request id" which is unique for each page served. This
 * id is then used as a key into a global-scoped cache. The data there was inserted
 * during the previous request, and is deleted as soon as it is served up by this 
 * class.
 */
public class StreamingResourceLoader implements ResourceLoader
{
    private final static Log log = LogFactory.getLog(StreamingResourceLoader.class);
    
    public StreamingResourceLoader()
    {
    }

    public void serveResource(ServletContext context, HttpServletRequest request, HttpServletResponse response, String resourceUri) throws IOException
    {
        // Technically here we should check for "/header.css" on the end of the url. But right now,
        // this ResourceLoader only ever serves that one "virtual" css resource, so this request
        // cannot be for anything else...

        int pos = resourceUri.indexOf("/");
        Long requestId = new Long(Long.parseLong(resourceUri.substring(0, pos), 10));
        
        StreamingThreadManager manager = (StreamingThreadManager) context.getAttribute(StreamingThreadManager.KEY);
        
        StreamingThreadManager.HeaderInfoEntry headerInfoEntry = manager.getHeaderInfo(requestId);
        if (headerInfoEntry == null)
        {
            log.warn("No streamable resources found for request: " + requestId + " resourceUri: " + resourceUri);
            return;
        }

        /*
         * Ensure the browser doesn't cache this response. We never generate the same url twice
         * (the requestId value embedded in the url changes for each request) so storing the
         * response in a browser cache is just a waste; the cached data would never be used again.
         */
        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-control", "no-cache, must-revalidate");
        
        try
        {
            PrintWriter pw = response.getWriter();
            
            StreamingAddResource.StreamablePositionedInfo positionedInfo;
            try
            {
                while ((positionedInfo = headerInfoEntry.fetchInfo()) != null)
                {
                    positionedInfo.writePositionedInfo(response, pw);
                    pw.flush();
                }
                pw.close();
            }
            catch (InterruptedException e)
            {
                throw (IOException) new IOException().initCause(e);
            }
        }
        finally
        {
            manager.removeHeaderInfo(requestId);
        }
    }

}
