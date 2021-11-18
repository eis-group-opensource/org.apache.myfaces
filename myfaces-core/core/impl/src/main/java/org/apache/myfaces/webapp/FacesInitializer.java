/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 1067544 $ $Date: 2011-02-06 00:33:47 +0200 (Sun, 06 Feb 2011) $
 */
public interface FacesInitializer
{
    void initFaces(ServletContext servletContext);
    
    void destroyFaces(ServletContext servletContext);

    /**
     * @since 1.2.10
     * @param servletContext
     */
    FacesContext initStartupFacesContext(ServletContext servletContext);
    
    /**
     * @since 1.2.10
     * @param facesContext
     */
    void destroyStartupFacesContext(FacesContext facesContext);
        
    /**
     * @since 1.2.10
     * @param servletContext
     */
    FacesContext initShutdownFacesContext(ServletContext servletContext);    

    /**
     * @since 1.2.10
     * @param facesContext
     */
    void destroyShutdownFacesContext(FacesContext facesContext);
}
