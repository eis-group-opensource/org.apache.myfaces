/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.myfaces.config.ManagedBeanBuilder;

/**
 * @author Dennis Byrne
 */

public class MyFacesHttpSessionAttributeListener extends AbstractMyFacesListener
        implements HttpSessionAttributeListener {

    public void attributeAdded(HttpSessionBindingEvent event) { // noop
    }

    public void attributeRemoved(HttpSessionBindingEvent event) {
        doPreDestroy(event, ManagedBeanBuilder.SESSION);
    }

    public void attributeReplaced(HttpSessionBindingEvent event) {
        doPreDestroy(event, ManagedBeanBuilder.SESSION);
    }

}
