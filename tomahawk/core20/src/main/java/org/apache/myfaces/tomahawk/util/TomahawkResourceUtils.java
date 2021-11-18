/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.tomahawk.util;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.ResourceUtils;

/**
 * Utility class for add resources using JSF 2.0 Resource API. It is expected
 * this methods be called from a listener for PreRenderViewAddResourceEvent.
 * 
 * @author Leonardo Uribe
 * @since 1.1.10
 *
 */
public class TomahawkResourceUtils
{
    public static final String HEAD_LOCATION = "head";
    public static final String BODY_LOCATION = HTML.BODY_ELEM;
    public static final String FORM_LOCATION = HTML.FORM_ELEM;
    public static final String ADDED_RESOURCES_SET = "org.apache.myfaces.ADDED_RESOURCES_SET";
    
    /**
     * Return a set of already rendered resources by this renderer on the current
     * request. 
     * 
     * @param facesContext
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Boolean> getAddedResources(FacesContext facesContext)
    {
        Map<String, Boolean> map = (Map<String, Boolean>) facesContext.getAttributes().get(ADDED_RESOURCES_SET);
        if (map == null)
        {
            map = new HashMap<String, Boolean>();
            facesContext.getAttributes().put(ADDED_RESOURCES_SET,map);
        }
        return map;
    }
    
    public static void resetAddedResources(FacesContext facesContext)
    {
        facesContext.getAttributes().remove(ADDED_RESOURCES_SET);
    }
    
    public static void markResourceAsAdded(FacesContext facesContext, String libraryName, String resourceName)
    {
        getAddedResources(facesContext).put(libraryName != null ? libraryName+'/'+resourceName : resourceName, Boolean.TRUE);
    }
    
    public static boolean isAddedResource(FacesContext facesContext, String libraryName, String resourceName)
    {
        return getAddedResources(facesContext).containsKey(libraryName != null ? libraryName+'/'+resourceName : resourceName);
    }

    /**
     * Add the script on PreRenderViewAddResourceEvent. The expected component resource instance
     * has "transient" property as true.
     * 
     * @param facesContext
     * @param libraryName
     * @param resourceName
     */
    public static void addOutputScriptResource(final FacesContext facesContext, final String libraryName, final String resourceName)
    {
        if (isAddedResource(facesContext, libraryName, resourceName))
        {
            return;
        }
        
        UIOutput outputScript = (UIOutput) facesContext.getApplication().
            createComponent(facesContext, ResourceUtils.JAVAX_FACES_OUTPUT_COMPONENT_TYPE, ResourceUtils.DEFAULT_SCRIPT_RENDERER_TYPE);
        outputScript.getAttributes().put(JSFAttr.LIBRARY_ATTR, libraryName);
        outputScript.getAttributes().put(JSFAttr.NAME_ATTR, resourceName);
        outputScript.setTransient(true);
        outputScript.setId(facesContext.getViewRoot().createUniqueId());
        facesContext.getViewRoot().addComponentResource(facesContext, outputScript);
        
        markResourceAsAdded(facesContext, libraryName, resourceName);
    }
    
    public static void addOutputStylesheetResource(final FacesContext facesContext, final String libraryName, final String resourceName)
    {
        if (isAddedResource(facesContext, libraryName, resourceName))
        {
            return;
        }

        UIOutput outputStylesheet = (UIOutput) facesContext.getApplication().
            createComponent(facesContext, ResourceUtils.JAVAX_FACES_OUTPUT_COMPONENT_TYPE, ResourceUtils.DEFAULT_STYLESHEET_RENDERER_TYPE);
        outputStylesheet.getAttributes().put(JSFAttr.LIBRARY_ATTR, libraryName);
        outputStylesheet.getAttributes().put(JSFAttr.NAME_ATTR, resourceName);
        outputStylesheet.setTransient(true);
        outputStylesheet.setId(facesContext.getViewRoot().createUniqueId());
        facesContext.getViewRoot().addComponentResource(facesContext, outputStylesheet);
        
        markResourceAsAdded(facesContext, libraryName, resourceName);
    }
    
    public static void addInlineOutputStylesheetResource(final FacesContext facesContext, Object value)
    {
        UIOutput stylesheet = (UIOutput) facesContext.getApplication().createComponent(facesContext, 
                ResourceUtils.JAVAX_FACES_OUTPUT_COMPONENT_TYPE, ResourceUtils.JAVAX_FACES_TEXT_RENDERER_TYPE);
        UIOutput outputStylesheet = (UIOutput) facesContext.getApplication().
            createComponent(facesContext, ResourceUtils.JAVAX_FACES_OUTPUT_COMPONENT_TYPE, ResourceUtils.DEFAULT_STYLESHEET_RENDERER_TYPE);
        stylesheet.setValue( value);
        stylesheet.setTransient(true);
        stylesheet.setId(facesContext.getViewRoot().createUniqueId());
        outputStylesheet.getChildren().add(stylesheet);
        outputStylesheet.setTransient(true);
        outputStylesheet.setId(facesContext.getViewRoot().createUniqueId());
        facesContext.getViewRoot().addComponentResource(facesContext, outputStylesheet);
    }
    
    public static void addInlineOutputScriptResource(final FacesContext facesContext, String target, Object value)
    {
        UIOutput script = (UIOutput) facesContext.getApplication().createComponent(facesContext, 
                ResourceUtils.JAVAX_FACES_OUTPUT_COMPONENT_TYPE, ResourceUtils.JAVAX_FACES_TEXT_RENDERER_TYPE);
        UIOutput outputScript = (UIOutput) facesContext.getApplication().
            createComponent(facesContext, ResourceUtils.JAVAX_FACES_OUTPUT_COMPONENT_TYPE, ResourceUtils.DEFAULT_SCRIPT_RENDERER_TYPE);
        if (target != null)
        {
            script.getAttributes().put(JSFAttr.TARGET_ATTR, target);
        }
        script.setValue( value);
        script.setTransient(true);
        script.setId(facesContext.getViewRoot().createUniqueId());
        outputScript.getChildren().add(script);
        outputScript.setTransient(true);
        outputScript.setId(facesContext.getViewRoot().createUniqueId());
        facesContext.getViewRoot().addComponentResource(facesContext, outputScript);
    }
    
    public static String getIconSrc(final FacesContext facesContext, final String libraryName, final String resourceName)
    {
        final ResourceHandler resourceHandler = facesContext.getApplication().getResourceHandler();
        final Resource resource;
        
        if ((libraryName != null) && (libraryName.length() > 0))
        {
            resource = resourceHandler.createResource(resourceName, libraryName);
        }
        else
        {
            resource = resourceHandler.createResource(resourceName);    
        }
        
        if (resource == null)
        {
            return "RES_NOT_FOUND";
        }
        else
        {
            return resource.getRequestPath();
        }
    }
}
