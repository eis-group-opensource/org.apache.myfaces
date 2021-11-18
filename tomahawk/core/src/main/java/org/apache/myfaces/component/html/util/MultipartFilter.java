/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.myfaces.webapp.filter.MultipartRequestWrapper;


/**
 * This filters is mandatory for the use of many components.
 * It handles the Multipart requests (for file upload)
 * It's used by the components that need javascript libraries
 *
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller </a>
 * @version $Revision: 782515 $ $Date: 2009-06-08 06:28:42 +0300 (Mon, 08 Jun 2009) $
 */
public class MultipartFilter implements Filter
{

    private int uploadMaxSize = 100 * 1024 * 1024; // 100 MB
    
    private int uploadMaxFileSize = 100 * 1024 * 1024; // 10 MB

    private int uploadThresholdSize = 1 * 1024 * 1024; // 1 MB

    private String uploadRepositoryPath = null; //standard temp directory
    
    private boolean cacheFileSizeErrors = false;

    public void init(FilterConfig filterConfig)
    {
        uploadMaxFileSize = resolveSize(filterConfig.getInitParameter("uploadMaxFileSize"), uploadMaxFileSize);
        String param = filterConfig.getInitParameter("uploadMaxSize");
        if (param != null)
        {
            uploadMaxSize = resolveSize(param, uploadMaxSize);
        }
        else
        {
            //If not set, default to uploadMaxFileSize
            uploadMaxSize = resolveSize(param, uploadMaxFileSize);
        }
        uploadThresholdSize = resolveSize(filterConfig.getInitParameter("uploadThresholdSize"), uploadThresholdSize);
        uploadRepositoryPath = filterConfig.getInitParameter("uploadRepositoryPath");
        cacheFileSizeErrors = getBooleanValue(filterConfig.getInitParameter("cacheFileSizeErrors"), false);
    }


    private int resolveSize(String param, int defaultValue)
    {
        int numberParam = defaultValue;

        if (param != null)
        {
            param = param.toLowerCase();
            int factor = 1;
            String number = param;

            if (param.endsWith("g"))
            {
                factor = 1024 * 1024 * 1024;
                number = param.substring(0, param.length() - 1);
            } else if (param.endsWith("m"))
            {
                factor = 1024 * 1024;
                number = param.substring(0, param.length() - 1);
            } else if (param.endsWith("k"))
            {
                factor = 1024;
                number = param.substring(0, param.length() - 1);
            }

            numberParam = Integer.parseInt(number) * factor;
        }
        return numberParam;
    }
    
    private static boolean getBooleanValue(String initParameter, boolean defaultVal)
    {
        if(initParameter == null || initParameter.trim().length()==0)
            return defaultVal;

        return (initParameter.equalsIgnoreCase("on") || initParameter.equals("1") || initParameter.equalsIgnoreCase("true"));
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        if (!(response instanceof HttpServletResponse))
        {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // For multipart/form-data requests
        if (ServletFileUpload.isMultipartContent(httpRequest))
        {
            chain.doFilter(new MultipartRequestWrapper(httpRequest, uploadMaxFileSize, 
                    uploadThresholdSize, uploadRepositoryPath, uploadMaxSize , cacheFileSizeErrors), response);
        } else
        {
            chain.doFilter(request, response);
        }
    }


    public void destroy()
    {
        // NoOp
    }
}
