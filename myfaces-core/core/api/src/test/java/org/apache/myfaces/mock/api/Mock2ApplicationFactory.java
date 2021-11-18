/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.mock.api;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

/**
 * Necessary for testing application-factory-setup with to application-factories;
 */
public class Mock2ApplicationFactory extends ApplicationFactory {
    private Application app = null;
    private ApplicationFactory factory = null;

    public Mock2ApplicationFactory() {
    }

    public Mock2ApplicationFactory(ApplicationFactory factory) {
        this.factory = factory;
    }

    public Application getApplication() {
        return app;
    }

    public void setApplication(Application application) {
        this.app = application;
    }

}
