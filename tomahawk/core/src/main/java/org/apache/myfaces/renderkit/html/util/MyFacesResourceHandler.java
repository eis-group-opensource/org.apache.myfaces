/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

import javax.faces.context.FacesContext;

/**
 * A ResourceHandler which always generates URLs that trigger the
 * MyfacesResourceLoader to load resources from the classpath
 * relative to some Tomahawk class.
 * <p>
 * This is intended to support loading of Tomahawk resources only;
 * applications which wish to use the Tomahawk AddResources framework
 * for loading resources from elsewhere should implement their own
 * ResourceHandler class.
 * 
 * @author Mathias Broekelmann
 */
public class MyFacesResourceHandler implements ResourceHandler
{
    private final Class _myfacesCustomComponent;
    private final String _resource;

    /**
     * Constructor.
     * 
     * @param myfacesCustomComponent is a class that must be in package
     *   org.apache.myfaces.custom. The resource to be served will be
     *   located relative to this class in the classpath. Note that code
     *   wishing to serve resources from other locations in the classpath
     *   must write a custom ResourceHandler implementation.
     *   
     * @param resourceName is the name of a file that can be found in dir
     *  "resource/{resourceName} relative to the location of the specified
     *  component class in the classpath. Because the resource is always
     *  relative to a class file, it must never begin with a slash. 
     */
    public MyFacesResourceHandler(Class myfacesCustomComponent, String resourceName)
    {
        validateCustomComponent(myfacesCustomComponent);
        _myfacesCustomComponent = myfacesCustomComponent;
        _resource = resourceName;
    }
    
    /**
     * Return a Class object which can decode the url generated by this
     * class in the getResourceUri method and use that info to locate
     * the resource data represented by this object.
     * 
     * @see ResourceHandler#getResourceLoaderClass()
     */
    public Class getResourceLoaderClass()
    {
        return MyFacesResourceLoader.class;
    }

    /**
     * Verify that the base class for the resource lookup is in the 
     * org.apache.myfaces.custom package.
     * 
     * @param myfacesCustomComponent is the base component for the lookup.
     * @throws IllegalArgumentException if the class is not in the expected package. 
     */
    protected void validateCustomComponent(Class myfacesCustomComponent)
    {
        if (!myfacesCustomComponent.getName().startsWith(
                MyFacesResourceLoader.ORG_APACHE_MYFACES_CUSTOM + "."))
        {
            throw new IllegalArgumentException(
                    "expected a myfaces custom component class in package "
                            + MyFacesResourceLoader.ORG_APACHE_MYFACES_CUSTOM);
        }
    }

    /**
     * Return a URL that the browser can later submit to retrieve the resource
     * handled by this instance.
     * <p>
     * The emitted URL is of form:
     * <pre>
     *   {partial.class.name}/{resourceName}
     * </pre>
     * where partial.class.name is the name of the base class specified in the
     * constructor, and resourceName is the resource specified in the constructor.
     * 
     * @see org.apache.myfaces.shared.renderkit.html.util.ResourceHandler#getResourceUri(javax.faces.context.FacesContext)
     */
    public String getResourceUri(FacesContext context)
    {
        String className = _myfacesCustomComponent.getName();
        StringBuffer sb = new StringBuffer();
        sb.append(className.substring(
            MyFacesResourceLoader.ORG_APACHE_MYFACES_CUSTOM.length() + 1));
        sb.append("/");
        if (_resource != null)
        {
            if (_resource.startsWith("/"))
            {
                throw new IllegalArgumentException(
                    "Tomahawk resources are always relative to the associated class." +
                    " Absolute resource paths are not allowed: " + _resource);
            }
            sb.append(_resource);
        }
        return sb.toString();
    }
}
