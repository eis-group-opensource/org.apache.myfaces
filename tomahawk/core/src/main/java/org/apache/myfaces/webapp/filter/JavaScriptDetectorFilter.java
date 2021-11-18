/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.JavascriptUtils;


/**
 *
 * Filter to handle javascript detection redirect. This is an EXPERIMENTAL feature.
 *
 * @author Oliver Rossmueller (latest modification by $Author: grantsmith $)
 *
 */
public class JavaScriptDetectorFilter implements Filter
{
    private static final Log log = LogFactory.getLog(JavaScriptDetectorFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        JavascriptUtils.setJavascriptDetected(request.getSession(true), true); // mark the session to use javascript

        String redirectURL = response.encodeRedirectURL(request.getParameter("goto"));
        log.info("Enabled JavaScript for session - redirect to " + redirectURL);
        response.sendRedirect(redirectURL);
    }

    public void destroy()
    {
    }
}