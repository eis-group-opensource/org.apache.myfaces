/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.myfaces.config.ManagedBeanBuilder;

/**
 * @author Dennis Byrne
 */

public class MyFacesServletRequestListener extends AbstractMyFacesListener implements ServletRequestListener {

    public void requestDestroyed(ServletRequestEvent event) {

        ServletRequest request = event.getServletRequest();
        Enumeration<String> attributes = request.getAttributeNames();
        
        while(attributes.hasMoreElements()) 
        {
            String name = attributes.nextElement();
            Object attribute = request.getAttribute(name);
            doPreDestroy(attribute, name, ManagedBeanBuilder.REQUEST);
        }
        
    }
        
    public void requestInitialized(ServletRequestEvent event) { // noop
    }

}
