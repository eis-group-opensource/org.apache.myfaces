/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.Renderer;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * <p>Utility class for providing basic functionality to the HTML faces 
 * extended components.<p>
 * 
 * @author Sean Schofield
 * @version $Revision: 664580 $ $Date: 2008-06-09 03:21:01 +0300 (Mon, 09 Jun 2008) $
 */
public final class HtmlComponentUtils
{
    /**
     * Constructor (Private)
     */
    private HtmlComponentUtils()
    {}

    /**
     * Gets the client id associated with the component.  Checks the forceId 
     * attribute of the component (if present) and uses the orginally supplied 
     * id value if that attribute is true.  Also performs the required call 
     * to <code>convertClientId</code> on the {@link Renderer} argument.
     * 
     * @param component The component for which the client id is needed.
     * @param renderer The renderer associated with the component.
     * @param context Additional context information to help in the request.
     * @return The clientId to use with the specified component.
     */
    public static String getClientId(UIComponent component,
                                     Renderer renderer,
                                     FacesContext context)
    {

        //forceId enabled?
        boolean forceId = RendererUtils.getBooleanValue(JSFAttr.FORCE_ID_ATTR,
                component.getAttributes().get(JSFAttr.FORCE_ID_ATTR),false);

        if (forceId && component.getId() != null)
        {
            String clientId = component.getId();

            /**
             * See if there is a parent naming container.  If there is ...
             */
            UIComponent parentContainer = HtmlComponentUtils.findParentNamingContainer(component, false);
            if (parentContainer != null)
            {
                if (parentContainer instanceof UIData)
                {
                    // see if the originally supplied id should be used
                    boolean forceIdIndex = RendererUtils.getBooleanValue(JSFAttr.FORCE_ID_ATTR,
                            component.getAttributes().get(JSFAttr.FORCE_ID_INDEX_ATTR),true);

                    // note: user may have specifically requested that we do not add the special forceId [index] suffix
                    if (forceIdIndex)
                    {
                        int rowIndex = ( (UIData) parentContainer).getRowIndex();
                        if (rowIndex != -1) {
                            clientId = clientId + "[" + rowIndex + "]";
                        }
                    }
                }
            }

            // JSF spec requires that renderer get a chance to convert the id
            if (renderer != null)
            {
                clientId = renderer.convertClientId(context, clientId);
            }

            return clientId;
        }
        else
        {
            return null;
        }
    }

    /**
     * Locates the {@link NamingContainer} associated with the givem 
     * {@link UIComponent}.
     * 
     * @param component The component whose naming locator needs to be found.
     * @param returnRootIfNotFound Whether or not the root should be returned 
     *    if no naming container is found.
     * @return The parent naming container (or root if applicable).
     */
    public static UIComponent findParentNamingContainer(UIComponent component,
                                                        boolean returnRootIfNotFound)
    {
        UIComponent parent = component.getParent();
        if (returnRootIfNotFound && parent == null)
        {
            return component;
        }
        while (parent != null)
        {
            if (parent instanceof NamingContainer) return parent;
            if (returnRootIfNotFound)
            {
                UIComponent nextParent = parent.getParent();
                if (nextParent == null)
                {
                    return parent;  //Root
                }
                parent = nextParent;
            }
            else
            {
                parent = parent.getParent();
            }
        }
        return null;
    }
    
    
    /**
     * The getParameterMap() is used for getting the parameters
     * of a specific component.
     * @param component
     * @return the Map of the component.
     */
    public static Map getParameterMap(UIComponent component) {
        Map result = new HashMap();
        for (Iterator iter = component.getChildren().iterator(); iter.hasNext();) {
            UIComponent child = (UIComponent) iter.next();
            if (child instanceof UIParameter) {
                UIParameter uiparam = (UIParameter) child;
                Object value = uiparam.getValue();
                if (value != null) {
                    result.put(uiparam.getName(), value);
                }
            }
        }
        return result;
    }   
    
    /**
     * The getLifecycleId() is used for getting the id of 
     * the Lifecycle from the ServletContext.
     * @param context
     * @return the id of the life cycle.
     */ 
    public static String getLifecycleId(ServletContext context) {
        String lifecycleId = context
                .getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);
        return lifecycleId != null ? lifecycleId
                : LifecycleFactory.DEFAULT_LIFECYCLE;
    }       

}
