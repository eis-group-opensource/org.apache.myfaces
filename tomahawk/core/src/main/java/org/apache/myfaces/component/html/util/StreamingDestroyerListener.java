/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.myfaces.shared_tomahawk.config.MyfacesConfig;

public class StreamingDestroyerListener implements ServletContextListener 
{

    public void contextInitialized(ServletContextEvent event)
    {
        //Only initialize a StreamingThreadManager if StreamingAddResource is used
        String addResourceClass = MyfacesConfig.getAddResourceClassFromServletContext(event.getServletContext());
        
        if (addResourceClass != null && addResourceClass.equals(StreamingAddResource.class.getName()))
        {
            StreamingThreadManager manager = new StreamingThreadManager();
            event.getServletContext().setAttribute(StreamingThreadManager.KEY,
                    manager);
            manager.init();
        }
    }

    public void contextDestroyed(ServletContextEvent event)
    {
        StreamingThreadManager manager = (StreamingThreadManager) event.getServletContext().getAttribute(StreamingThreadManager.KEY);
        if (manager != null)
        {
            manager.destroy();
        }
    }
}
