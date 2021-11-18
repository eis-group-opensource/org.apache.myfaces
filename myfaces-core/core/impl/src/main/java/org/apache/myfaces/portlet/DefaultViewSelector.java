/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.portlet;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Imlementations of this interface allow a JSF application to specify which
 * JSF view will be selected when the incoming request does not provide a View
 * Id.  The implementation can optionally return <code>null</code> to revert to
 * the default View Id specified in portlet.xml.
 *
 * @author  Stan Silvert (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public interface DefaultViewSelector {

    /**
     * This method will be called by the MyFacesGenericPortlet in order to
     * give the selector an opportunity to store a reference to the
     * PortletContext.
     */
    public void setPortletContext(PortletContext portletContext);

    /**
     * This method allows a JSF application to specify which JSF view will be
     * when the incoming request does not provide a view id.
     *
     * @param request The RenderRequest
     * @param response The RenderResponse
     * @return a JSF View Id, or <code>null</code> if the selector wishes to
     *         revert to the default View Id specified in portlet.xml.
     * @throws PortletException if a View Id can not be determined because of
     *                          some underlying error.
     */
    public String selectViewId(RenderRequest request, RenderResponse response) throws PortletException;
}
