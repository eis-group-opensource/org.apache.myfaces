/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class FacesConfig
{

    private List<Application> applications = new ArrayList<Application>();
    private List<Factory> factories = new ArrayList<Factory>();
    private Map<String, String> components = new HashMap<String, String>();
    private List<Converter> converters = new ArrayList<Converter>();
    private List<ManagedBean> managedBeans = new ArrayList<ManagedBean>();
    private List<NavigationRule> navigationRules = new ArrayList<NavigationRule>();
    private List<RenderKit> renderKits = new ArrayList<RenderKit>();
    private List<String> lifecyclePhaseListener = new ArrayList<String>();
    private Map<String, String> validators = new HashMap<String, String>();

    public void addApplication(Application application)
    {
        applications.add(application);
    }

    public void addFactory(Factory factory)
    {
        factories.add(factory);
    }

    public void addComponent(String componentType, String componentClass)
    {
        components.put(componentType, componentClass);
    }

    public void addConverter(Converter converter)
    {
        converters.add(converter);
    }

    public void addManagedBean(ManagedBean bean)
    {
        managedBeans.add(bean);
    }

    public void addNavigationRule(NavigationRule rule)
    {
        navigationRules.add(rule);
    }

    public void addRenderKit(RenderKit renderKit)
    {
        renderKits.add(renderKit);
    }

    public void addLifecyclePhaseListener(String value)
    {
        lifecyclePhaseListener.add(value);
    }

    public void addValidator(String id, String validatorClass)
    {
        validators.put(id, validatorClass);
    }

    public List<Application> getApplications()
    {
        return applications;
    }

    public List<Factory> getFactories()
    {
        return factories;
    }

    public Map<String, String> getComponents()
    {
        return components;
    }

    public List<Converter> getConverters()
    {
        return converters;
    }

    public List<ManagedBean> getManagedBeans()
    {
        return managedBeans;
    }

    public List<NavigationRule> getNavigationRules()
    {
        return navigationRules;
    }

    public List<RenderKit> getRenderKits()
    {
        return renderKits;
    }

    public List<String> getLifecyclePhaseListener()
    {
        return lifecyclePhaseListener;
    }

    public Map<String, String> getValidators()
    {
        return validators;
    }
}
