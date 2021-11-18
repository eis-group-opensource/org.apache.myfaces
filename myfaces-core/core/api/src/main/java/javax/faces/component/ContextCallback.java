/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.component;

import javax.faces.context.FacesContext;

/**
 * See Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
public interface ContextCallback
{
    
    /**
     * 
     * @param context <code>FacesContext</code> for the current request
     * @param target <code>UIComponent</code> on which the <code>UIComponent.invokeOnComponent()</code> will be called
     */
    void invokeContextCallback(FacesContext context, UIComponent target);
}
