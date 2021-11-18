/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.apache.myfaces.config.ManagedBeanBuilder;

/**
 * @author Dennis Byrne
 */

public class MyFacesServletContextListener extends AbstractMyFacesListener implements ServletContextAttributeListener {

    public void attributeAdded(ServletContextAttributeEvent event) { // noop
    }

    public void attributeRemoved(ServletContextAttributeEvent event) {
        doPreDestroy(event, ManagedBeanBuilder.APPLICATION);
    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
        doPreDestroy(event, ManagedBeanBuilder.APPLICATION);
    }

}
