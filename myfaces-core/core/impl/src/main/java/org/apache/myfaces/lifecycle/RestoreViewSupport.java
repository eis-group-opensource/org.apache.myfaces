/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.lifecycle;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Support class for restore view phase
 * 
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 517403 $ $Date: 2007-03-12 23:17:00 +0200 (Mon, 12 Mar 2007) $
 */
public interface RestoreViewSupport
{
    /**
     * <p>
     * Calculates the view id from the given faces context by the following algorithm
     * </p>
     * <ul>
     * <li>lookup the viewid from the request attribute "javax.servlet.include.path_info"
     * <li>if null lookup the value for viewid by {@link ExternalContext#getRequestPathInfo()}
     * <li>if null lookup the value for viewid from the request attribute "javax.servlet.include.servlet_path"
     * <li>if null lookup the value for viewid by {@link ExternalContext#getRequestServletPath()}
     * <li>if null throw a {@link FacesException}
     * </ul>
     */
    String calculateViewId(FacesContext facesContext);

    /**
     * Processes the component tree. For each component (including the given one) in the tree determine if a value
     * expression for the attribute "binding" is defined. If the expression is not null set the component instance to
     * the value of this expression
     * 
     * @param facesContext
     * @param component
     *            the root component
     */
    void processComponentBinding(FacesContext facesContext, UIComponent component);

    /**
     * <p>
     * Determine if the current request is a post back by the following algorithm.
     * </p>
     * <p>
     * Find the render-kit-id for the current request by calling calculateRenderKitId() on the Applications
     * ViewHandler. Get that RenderKits ResponseStateManager and call its isPostback() method, passing the given
     * FacesContext.
     * </p>
     * 
     * @param facesContext
     * @return
     */
    boolean isPostback(FacesContext facesContext);
}
