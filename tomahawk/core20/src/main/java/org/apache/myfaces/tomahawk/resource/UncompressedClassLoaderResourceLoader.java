/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.tomahawk.resource;

import org.apache.myfaces.shared_tomahawk.resource.ClassLoaderResourceLoader;
import org.apache.myfaces.shared_tomahawk.resource.ResourceMeta;
import org.apache.myfaces.shared_tomahawk.resource.ResourceMetaImpl;

/**
 * A resource loader implementation which loads resources from the thread ClassLoader.
 * 
 */
public class UncompressedClassLoaderResourceLoader extends ClassLoaderResourceLoader
{
    
    public UncompressedClassLoaderResourceLoader(String prefix)
    {
        super(prefix);
    }

    @Override
    public ResourceMeta createResourceMeta(String prefix, String libraryName, String libraryVersion,
                                           String resourceName, String resourceVersion)
    {
        return new ResourceMetaImpl(prefix, libraryName, libraryVersion, resourceName, resourceVersion);
    }
}
