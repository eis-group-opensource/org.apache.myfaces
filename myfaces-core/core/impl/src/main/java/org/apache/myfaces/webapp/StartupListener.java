/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import javax.servlet.ServletContextEvent;


/**
 * Startup Listener for the myfaces init process
 * This interface allows to implement
 * Plugins which then can be hooked into the various stages
 * of our initialisation process to add various plugins
 * which depend on the various phases of the init
 *
 * @author Werner Punz
 */
public interface StartupListener {
    /**
     * This method is called before myfaces initializes
     *
     * @param evt the corresponding servlet context event keeping all the servlet context data and references
     */
    public void preInit(ServletContextEvent evt);

    /**
     * This method is called after myfaces has initialized
     *
     * @param evt the corresponding servlet context event keeping all the servlet context data and references
     */
    public void postInit(ServletContextEvent evt);

    /**
     * This method is called before myfaces is destroyed
     *
     * @param evt the corresponding servlet context event keeping all the servlet context data and references
     */
    public void preDestroy(ServletContextEvent evt);

    /**
     * This method is called after myfaces is destroyed
     *
     * @param evt the corresponding servlet context event keeping all the servlet context data and references
     */
    public void postDestroy(ServletContextEvent evt);

}

