/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class Factory
{

    private List<String> applicationFactory = new ArrayList<String>();
    private List<String> facesContextFactory = new ArrayList<String>();
    private List<String> lifecycleFactory = new ArrayList<String>();
    private List<String> renderkitFactory = new ArrayList<String>();

    public void addApplicationFactory(String factory)
    {
        applicationFactory.add(factory);
    }

    public void addFacesContextFactory(String factory)
    {
        facesContextFactory.add(factory);
    }

    public void addLifecycleFactory(String factory)
    {
        lifecycleFactory.add(factory);
    }

    public void addRenderkitFactory(String factory)
    {
        renderkitFactory.add(factory);
    }

    public List<String> getApplicationFactory()
    {
        return applicationFactory;
    }

    public List<String> getFacesContextFactory()
    {
        return facesContextFactory;
    }

    public List<String> getLifecycleFactory()
    {
        return lifecycleFactory;
    }

    public List<String> getRenderkitFactory()
    {
        return renderkitFactory;
    }
}
