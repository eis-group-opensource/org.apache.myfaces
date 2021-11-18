/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.captcha;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.custom.captcha.util.CAPTCHAImageGenerator;
import org.apache.myfaces.custom.captcha.util.CAPTCHATextGenerator;
import org.apache.myfaces.custom.captcha.util.ColorGenerator;
import org.apache.myfaces.shared_tomahawk.resource.ResourceHandlerSupport;
import org.apache.myfaces.tomahawk.application.DefaultResourceHandlerSupport;

public class CAPTCHAResourceHandlerWrapper extends javax.faces.application.ResourceHandlerWrapper
{
    public final static String CAPTCHA_LIBRARY = "oam.custom.captcha";
    
    private ResourceHandler _delegate;
    
    private ResourceHandlerSupport _resourceHandlerSupport;

    public CAPTCHAResourceHandlerWrapper(ResourceHandler delegate)
    {
        super();
        _delegate = delegate;
    }
    
    @Override
    public ResourceHandler getWrapped()
    {
        return _delegate;
    }

    @Override
    public Resource createResource(String resourceName, String libraryName)
    {
        if (libraryName != null && CAPTCHA_LIBRARY.equals(libraryName))
        {
            return internalCreateResource(resourceName, libraryName);
        }
        else
        {
            return super.createResource(resourceName, libraryName);
        }
    }

    @Override
    public Resource createResource(String resourceName, String libraryName, String contentType)
    {
        if (libraryName != null && CAPTCHA_LIBRARY.equals(libraryName))
        {
            return internalCreateResource(resourceName, libraryName);
        }
        else
        {
            return super.createResource(resourceName, libraryName, contentType);
        }
    }
    
    private Resource internalCreateResource(String resourceName, String libraryName)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (isResourceRequest(facesContext))
        {
            Map<String, String> requestMap = facesContext.getExternalContext().getRequestParameterMap();
            String captchaSessionKeyName = requestMap.get(CAPTCHAComponent.ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME).toString();
            return new CAPTCHAResource(getResourceHandlerSupport(), captchaSessionKeyName, resourceName);
        }
        else
        {
            String captchaSessionKeyName = (String) facesContext.getAttributes().get(CAPTCHAComponent.ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME);
            return new CAPTCHAResource(getResourceHandlerSupport(), captchaSessionKeyName, resourceName);
        }
    }
    
    @Override
    public void handleResourceRequest(FacesContext facesContext) throws IOException
    {
        final Map<String, String> requestMap = facesContext.getExternalContext().getRequestParameterMap();
        final String libraryName = requestMap.get("ln");
        
        if (libraryName != null && CAPTCHA_LIBRARY.equals(libraryName))
        {
            final String captchaSessionKeyName = requestMap.get(CAPTCHAComponent.ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME).toString();
            String resourceBasePath = getResourceHandlerSupport().calculateResourceBasePath(facesContext);
            
            Object response = facesContext.getExternalContext().getResponse();
            HttpServletResponse httpServletResponse = getHttpServletResponse(response);
            
            String resourceName = null;
            if (resourceBasePath.startsWith(ResourceHandler.RESOURCE_IDENTIFIER))
            {
                resourceName = resourceBasePath
                        .substring(ResourceHandler.RESOURCE_IDENTIFIER.length() + 1);
            }
            else
            {
                //Does not have the conditions for be a resource call
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            if (captchaSessionKeyName == null)
            {
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Resource resource = null;
            if (libraryName != null)
            {
                //log.info("libraryName=" + libraryName);
                resource = facesContext.getApplication().getResourceHandler().createResource(resourceName, libraryName);
            }
            else
            {
                resource = facesContext.getApplication().getResourceHandler().createResource(resourceName);
            }
            
            if (resource == null)
            {
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
    
            if (!resource.userAgentNeedsUpdate(facesContext))
            {
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
            
            httpServletResponse.setContentType(resource.getContentType());
            
            Map<String, String> headers = resource.getResponseHeaders();
    
            for (Map.Entry<String, String> entry : headers.entrySet())
            {
                httpServletResponse.setHeader(entry.getKey(), entry.getValue());
            }

            //serve up the bytes
            try
            {
                // construct the CAPTCHA image generator object.
                CAPTCHAImageGenerator captchaImageGenerator = new CAPTCHAImageGenerator();
                OutputStream out = httpServletResponse.getOutputStream();
                try
                {
                    String captchaText;
                    Color endingColor = ColorGenerator.generateRandomColor(null);
                    Color startingColor = ColorGenerator
                            .generateRandomColor(endingColor);
                
                    // Generate random CAPTCHA text.
                    captchaText = CAPTCHATextGenerator.generateRandomText();

                    // Set the generated text in the user session.
                    facesContext.getExternalContext().getSessionMap().put(
                            captchaSessionKeyName, captchaText);

                    // Generate the image, the BG color is randomized from starting to ending colors.
                    captchaImageGenerator.generateImage(out, captchaText,
                            startingColor, endingColor);
                }
                finally
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                Logger log = Logger.getLogger(CAPTCHAResourceHandlerWrapper.class.getName());
                if (log.isLoggable(Level.SEVERE))
                    log.severe("Error trying to load resource " + resourceName
                            + " with library " + libraryName + " :"
                            + e.getMessage());
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else
        {
            super.handleResourceRequest(facesContext);
        }
    }
    
    @Override
    public boolean libraryExists(String libraryName)
    {
        if (libraryName != null && CAPTCHA_LIBRARY.equals(libraryName))
        {
            return true;
        }
        else
        {
            return super.libraryExists(libraryName);
        }
    }

    /**
     * @param resourceHandlerSupport
     *            the resourceHandlerSupport to set
     */
    public void setResourceHandlerSupport(
            ResourceHandlerSupport resourceHandlerSupport)
    {
        _resourceHandlerSupport = resourceHandlerSupport;
    }

    /**
     * @return the resourceHandlerSupport
     */
    protected ResourceHandlerSupport getResourceHandlerSupport()
    {
        if (_resourceHandlerSupport == null)
        {
            _resourceHandlerSupport = new DefaultResourceHandlerSupport();
        }
        return _resourceHandlerSupport;
    }
    
    private static HttpServletResponse getHttpServletResponse(Object response)
    {
        // unwrap the response until we find a HttpServletResponse
        while (response != null)
        {
            if (response instanceof HttpServletResponse)
            {
                // found
                return (HttpServletResponse) response;
            }
            if (response instanceof ServletResponseWrapper)
            {
                // unwrap
                response = ((ServletResponseWrapper) response).getResponse();
            }
            // no more possibilities to find a HttpServletResponse
            break; 
        }
        return null; // not found
    }
}
