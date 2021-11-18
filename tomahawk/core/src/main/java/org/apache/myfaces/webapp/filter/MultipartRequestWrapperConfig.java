/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;

import org.apache.myfaces.tomahawk.util.ExternalContextUtils;

/**
 * This class is used to retrieve the context paramaters used to initialize 
 * of the MultipartRequestWrapper.
 * 
 * @author Hazem Saleh
 * 
 */
class MultipartRequestWrapperConfig
{
    
    private int _uploadMaxSize = 100 * 1024 * 1024; // 10 MB
    private int _uploadMaxFileSize = 100 * 1024 * 1024; // 10 MB
    private int _uploadThresholdSize = 1 * 1024 * 1024; // 1 MB
    private String _uploadRepositoryPath = null; //standard temp directory 
    private boolean _cacheFileSizeErrors = false;
    
    private static final String UPLOAD_MAX_SIZE = "org.apache.myfaces.UPLOAD_MAX_SIZE";
    private static final String UPLOAD_MAX_FILE_SIZE = "org.apache.myfaces.UPLOAD_MAX_FILE_SIZE";
    private static final String UPLOAD_THRESHOLD_SIZE = "org.apache.myfaces.UPLOAD_THRESHOLD_SIZE"; 
    private static final String UPLOAD_MAX_REPOSITORY_PATH = "org.apache.myfaces.UPLOAD_MAX_REPOSITORY_PATH";  
    private static final String UPLOAD_CACHE_FILE_SIZE_ERRORS = "org.apache.myfaces.UPLOAD_CACHE_FILE_SIZE_ERRORS";
    private static final String MULTIPART_REQUEST_WRAPPER_CONFIG = MultipartRequestWrapperConfig.class.getName();
    
    private MultipartRequestWrapperConfig() {}
        
    private static int resolveSize(String param, int defaultValue)
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
            }
            else if (param.endsWith("m"))
            {
                factor = 1024 * 1024;
                number = param.substring(0, param.length() - 1);
            }
            else if (param.endsWith("k"))
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
    
    public int getUploadMaxSize()
    {
        return _uploadMaxSize;
    }

    public void setUploadMaxSize(int uploadMaxSize)
    {
        this._uploadMaxSize = uploadMaxSize;
    }

    public int getUploadMaxFileSize()
    {
        return _uploadMaxFileSize;
    }

    public void setUploadMaxFileSize(int uploadMaxFileSize)
    {
        this._uploadMaxFileSize = uploadMaxFileSize;
    }

    public int getUploadThresholdSize()
    {
        return _uploadThresholdSize;
    }

    public void setUploadThresholdSize(int uploadThresholdSize)
    {
        this._uploadThresholdSize = uploadThresholdSize;
    }

    public String getUploadRepositoryPath()
    {
        return _uploadRepositoryPath;
    }

    public void setUploadRepositoryPath(String uploadRepositoryPath)
    {
        this._uploadRepositoryPath = uploadRepositoryPath;
    }
    
    public boolean isCacheFileSizeErrors()
    {
        return _cacheFileSizeErrors;
    }
    
    public void setCacheFileSizeErrors(boolean cacheFileSizeErrors)
    {
        this._cacheFileSizeErrors = cacheFileSizeErrors;
    }

    public static MultipartRequestWrapperConfig getMultipartRequestWrapperConfig(
            ExternalContext context)
    {

        MultipartRequestWrapperConfig config = (MultipartRequestWrapperConfig) context
                .getApplicationMap().get(MULTIPART_REQUEST_WRAPPER_CONFIG);

        if (config == null)
        {
            config = new MultipartRequestWrapperConfig();

            if(!ExternalContextUtils.getRequestType(context).isPortlet())
            {
                ServletContext servletContext = (ServletContext) context
                        .getContext();
    
                String param = servletContext
                        .getInitParameter(UPLOAD_MAX_FILE_SIZE);
    
                config._uploadMaxFileSize = resolveSize(param,
                        config._uploadMaxFileSize);
                
                param = servletContext
                    .getInitParameter(UPLOAD_MAX_SIZE);

                if (param != null)
                {
                    config._uploadMaxSize = resolveSize(param,
                            config._uploadMaxSize);
                }
                else
                {
                    //If not set, default to uploadMaxFileSize
                    config._uploadMaxSize = resolveSize(param,
                            config._uploadMaxFileSize);
                }
    
                param = servletContext.getInitParameter(UPLOAD_THRESHOLD_SIZE);
    
                config._uploadThresholdSize = resolveSize(param,
                        config._uploadThresholdSize);
    
                config._uploadRepositoryPath = servletContext
                        .getInitParameter(UPLOAD_MAX_REPOSITORY_PATH);

                config._cacheFileSizeErrors = getBooleanValue(servletContext
                        .getInitParameter(UPLOAD_CACHE_FILE_SIZE_ERRORS), false);
                
                context.getApplicationMap().put(MULTIPART_REQUEST_WRAPPER_CONFIG,
                        config);
            }
            else
            {
                Object portletContext = context.getContext();

                String param = PortletUtils.getContextInitParameter(
                        portletContext, UPLOAD_MAX_FILE_SIZE);
        
                config._uploadMaxFileSize = resolveSize(param,
                        config._uploadMaxFileSize);
                
                param = PortletUtils.getContextInitParameter(
                        portletContext, UPLOAD_MAX_SIZE);

                if (param != null)
                {
                    config._uploadMaxSize = resolveSize(param,
                            config._uploadMaxSize);
                }
                else
                {
                    //If not set, default to uploadMaxFileSize
                    config._uploadMaxSize = resolveSize(param,
                            config._uploadMaxFileSize);
                }
        
                param = PortletUtils.getContextInitParameter(
                        portletContext, UPLOAD_THRESHOLD_SIZE);
        
                config._uploadThresholdSize = resolveSize(param,
                        config._uploadThresholdSize);
        
                config._uploadRepositoryPath = PortletUtils.getContextInitParameter(
                        portletContext,UPLOAD_MAX_REPOSITORY_PATH);
                
                config._cacheFileSizeErrors = getBooleanValue(PortletUtils
                        .getContextInitParameter(portletContext, 
                                UPLOAD_CACHE_FILE_SIZE_ERRORS), false);
        
                context.getApplicationMap().put(MULTIPART_REQUEST_WRAPPER_CONFIG,
                        config);
            }
        }

        return config;
    }
}
