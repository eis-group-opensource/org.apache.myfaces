/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Deprecated
public final class ServletFacesContextImpl extends FacesContextImpl
{
    public ServletFacesContextImpl(ServletContext servletContext, ServletRequest servletRequest, ServletResponse servletResponse)
    {
        super(servletContext, servletRequest, servletResponse);
    }
}
