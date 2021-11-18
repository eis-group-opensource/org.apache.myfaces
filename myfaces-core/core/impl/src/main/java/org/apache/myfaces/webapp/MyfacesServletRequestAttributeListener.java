/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

import org.apache.myfaces.config.ManagedBeanBuilder;

/**
 * @author Dennis Byrne
 */

public class MyfacesServletRequestAttributeListener extends AbstractMyFacesListener
        implements ServletRequestAttributeListener {
    
    public void attributeAdded(ServletRequestAttributeEvent event) { // noop
    }

    public void attributeRemoved(ServletRequestAttributeEvent event) { 
        doPreDestroy(event, ManagedBeanBuilder.REQUEST);
    }

    public void attributeReplaced(ServletRequestAttributeEvent event) {
        doPreDestroy(event, ManagedBeanBuilder.REQUEST);        
    }

}
