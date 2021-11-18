/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.tomahawk.resource;

import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;

import org.apache.myfaces.shared_tomahawk.resource.BaseResourceHandlerSupport;
import org.apache.myfaces.shared_tomahawk.resource.ResourceLoader;

/**
 * A ResourceHandlerSupport implementation for use with standard Java Servlet engines,
 * ie an engine that supports javax.servlet, and uses a standard web.xml file.
 * 
 * @author Leonardo Uribe (latest modification by $Author: bommel $)
 * @version $Revision: 915763 $ $Date: 2010-02-24 07:24:11 -0500 (24 Feb 2010) $
 */
public class UncompressedResourceHandlerSupport extends BaseResourceHandlerSupport
{
    private ResourceLoader[] _resourceLoaders;

    public UncompressedResourceHandlerSupport()
    {
    }

    @Override
    public ResourceLoader[] getResourceLoaders()
    {
        if (_resourceLoaders == null)
        {
            if (FacesContext.getCurrentInstance().isProjectStage(ProjectStage.Development))
            {
                _resourceLoaders = new ResourceLoader[] {
                        new UncompressedClassLoaderResourceLoader("META-INF/uncompressed-js-resources")
                        };
            }
            else
            {
                _resourceLoaders = new ResourceLoader[] {};
            }
        }
        return _resourceLoaders;
    }
}
