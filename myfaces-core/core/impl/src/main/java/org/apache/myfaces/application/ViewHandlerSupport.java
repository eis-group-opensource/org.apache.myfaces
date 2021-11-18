/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.application;

import javax.faces.context.FacesContext;

/**
 * A utility class to isolate a ViewHandler implementation from the underlying 
 * request/response framework.
 * <p>
 * For example, an implementation of this interface might support javax.servlet,
 * javax.portlet, or some other mechanism.
 *    
 * @author Mathias Broekelmann (latest modification by $Author: skitching $)
 * @version $Revision: 589835 $ $Date: 2007-10-29 22:10:36 +0200 (Mon, 29 Oct 2007) $
 */
public interface ViewHandlerSupport
{
    String calculateViewId(FacesContext context, String viewId);

    /**
     * Return a string containing a webapp-relative URL that the user can invoke
     * to render the specified view.
     * <p>
     * URLs and ViewIds are not quite the same; for example a url of "/foo.jsf"
     * or "/faces/foo.jsp" may be needed to access the view "/foo.jsp". 
     */
    String calculateActionURL(FacesContext facesContext, String viewId); 
}
