/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter.portlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

/**
 * <p>
 * NOTE: This class should be used(instantiated) only by 
 * TomahawkFacesContextWrapper. By that reason, it could change
 * in the future.
 * </p>
 * 
 * @since 1.1.8
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 782179 $ $Date: 2009-06-06 04:35:54 +0300 (Sat, 06 Jun 2009) $
 */
public class ActionRequestWrapper extends PortletRequestWrapper
    implements ActionRequest
{

    public ActionRequestWrapper(PortletRequest request)
    {
        super(request);
    }
    
    private ActionRequest _getActionRequest()
    {
        return (ActionRequest)super.getRequest();
    }

    public String getCharacterEncoding()
    {
        return _getActionRequest().getCharacterEncoding();
    }

    public int getContentLength()
    {
        return _getActionRequest().getContentLength();
    }

    public String getContentType()
    {
        return _getActionRequest().getContentType();
    }

    public InputStream getPortletInputStream() throws IOException
    {
        return _getActionRequest().getPortletInputStream();
    }

    public BufferedReader getReader() throws UnsupportedEncodingException,
            IOException
    {
        return _getActionRequest().getReader();
    }

    public void setCharacterEncoding(String s)
            throws UnsupportedEncodingException
    {
        _getActionRequest().setCharacterEncoding(s);
    }    

}
