/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.captcha;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import org.apache.myfaces.shared_tomahawk.resource.ResourceHandlerSupport;


public class CAPTCHAResource extends Resource
{

    private String _captchaSessionKeyName;
    private ResourceHandlerSupport _resourceHandlerSupport;
    
    public CAPTCHAResource(ResourceHandlerSupport support, String captchaSessionKeyName, String resourceName)
    {
        super();
        _resourceHandlerSupport = support;
        _captchaSessionKeyName = captchaSessionKeyName;
        setLibraryName(CAPTCHAResourceHandlerWrapper.CAPTCHA_LIBRARY);
        setResourceName(resourceName);
        setContentType("image/png");
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        // Return an InputStream forces buffer the image, and really it is not necessary.
        // so, we just use CAPTCHAResourceHandlerWrapper to intercept requests and render
        // the image there directly.
        return null;
    }
    
    static class WrappedByteArrayOutputStream extends ByteArrayOutputStream{
        
        public WrappedByteArrayOutputStream(){
            super();
        }
        
        public WrappedByteArrayOutputStream(int size){
            super(size);                
        }
        
        private byte[] getInnerArray(){
            return buf; 
        }
        
        private int getInnerCount(){
            return count;
        }
    }

    @Override
    public String getRequestPath()
    {
        String path;
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (_resourceHandlerSupport.isExtensionMapping())
        {
            path = ResourceHandler.RESOURCE_IDENTIFIER + '/' + 
                getResourceName() + _resourceHandlerSupport.getMapping();
        }
        else
        {
            String mapping = _resourceHandlerSupport.getMapping(); 
            path = ResourceHandler.RESOURCE_IDENTIFIER + '/' + getResourceName();
            path = (mapping == null) ? path : mapping + path;
        }
        path = path + "?ln=" + getLibraryName() + "&captchaSessionKeyName=" + _captchaSessionKeyName + "&dummyParameter=" + System.currentTimeMillis();

        return facesContext.getApplication().getViewHandler().getResourceURL(facesContext, path);
    }

    @Override
    public Map<String, String> getResponseHeaders()
    {
        return Collections.emptyMap();
    }

    @Override
    public URL getURL()
    {
        // This is a generated resource, so it does not have an inner url, return null
        return null;
    }

    @Override
    public boolean userAgentNeedsUpdate(FacesContext context)
    {
        return true;
    }
}
