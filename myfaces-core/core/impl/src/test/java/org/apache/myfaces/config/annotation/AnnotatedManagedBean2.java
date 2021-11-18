/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.config.annotation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Leonardo Uribe
 */

public class AnnotatedManagedBean2 {

    private boolean postConstructCalled = false; // using a stub for a mock

    private boolean preDestroyCalled = false; // using a stob for a mock here

    boolean throwExcetion;

    private String managedProperty;

    public AnnotatedManagedBean2()
    {
    }

    public AnnotatedManagedBean2(boolean throwExcetion) {
        this.throwExcetion = throwExcetion;
    }

    @PostConstruct
    public void postConstruct()  {
        
        if (managedProperty == null)
        {
            throw new RuntimeException("managedProperty must be initialized before call of postConstruct() method");
        }
        
        postConstructCalled = true;

        if (throwExcetion) {
            throw new RuntimeException();
        }
    }

    @PreDestroy
    public void preDestroy() {
        preDestroyCalled = true;

        if (throwExcetion) {
            throw new RuntimeException();
        }
    }

    boolean isPostConstructCalled() {
        return postConstructCalled;
    }

    boolean isPreDestroyCalled() {
        return preDestroyCalled;
    }
    
    public String getManagedProperty() {
        return managedProperty;
    }

    public void setManagedProperty(String managedProperty) {
        //Set throught injection
        if (postConstructCalled)
        {
            throw new RuntimeException();
        }
            
        this.managedProperty = managedProperty;
    }

}
