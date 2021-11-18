/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class Application
{

    private final List<String> actionListener = new ArrayList<String>();
    private final List<String> defaultRenderkitId = new ArrayList<String>();
    private final List<String> messageBundle = new ArrayList<String>();
    private final List<String> navigationHandler = new ArrayList<String>();
    private final List<String> viewHandler = new ArrayList<String>();
    private final List<String> stateManager = new ArrayList<String>();
    private final List<String> propertyResolver = new ArrayList<String>();
    private final List<String> variableResolver = new ArrayList<String>();
    private final List<LocaleConfig> localeConfig = new ArrayList<LocaleConfig>();
    private final List<String> elResolver = new ArrayList<String>();
    private final List<ResourceBundle> resourceBundle = new ArrayList<ResourceBundle>();

    public void addActionListener(String listener)
    {
        actionListener.add(listener);
    }

    public void addDefaultRenderkitId(String id)
    {
        defaultRenderkitId.add(id);
    }

    public void addMessageBundle(String bundle)
    {
        messageBundle.add(bundle);
    }

    public void addNavigationHandler(String handler)
    {
        navigationHandler.add(handler);
    }

    public void addStateManager(String manager)
    {
        stateManager.add(manager);
    }

    public void addPropertyResolver(String resolver)
    {
        propertyResolver.add(resolver);
    }

    public void addVariableResolver(String handler)
    {
        variableResolver.add(handler);
    }

    public void addLocaleConfig(LocaleConfig config)
    {
        localeConfig.add(config);
    }

    public void addViewHandler(String handler)
    {
        viewHandler.add(handler);
    }

    public void addElResolver(String handler)
    {
        elResolver.add(handler);
    }

    public void addResourceBundle(ResourceBundle bundle)
    {
        resourceBundle.add(bundle);
    }

    public List<String> getActionListener()
    {
        return actionListener;
    }

    public List<String> getDefaultRenderkitId()
    {
        return defaultRenderkitId;
    }

    public List<String> getMessageBundle()
    {
        return messageBundle;
    }

    public List<String> getNavigationHandler()
    {
        return navigationHandler;
    }

    public List<String> getViewHandler()
    {
        return viewHandler;
    }

    public List<String> getStateManager()
    {
        return stateManager;
    }

    public List<String> getPropertyResolver()
    {
        return propertyResolver;
    }

    public List<String> getVariableResolver()
    {
        return variableResolver;
    }

    public List<LocaleConfig> getLocaleConfig()
    {
        return localeConfig;
    }

    public List<String> getElResolver()
    {
        return elResolver;
    }

    public List<ResourceBundle> getResourceBundle()
    {
        return resourceBundle;
    }
}
