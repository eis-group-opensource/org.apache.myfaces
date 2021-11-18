/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import org.apache.myfaces.config.impl.digester.elements.NavigationCase;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class NavigationRule implements org.apache.myfaces.config.element.NavigationRule
{

    private String fromViewId;
    private List<NavigationCase> navigationCases = new ArrayList<NavigationCase>();


    public String getFromViewId()
    {
        return fromViewId;
    }


    public void setFromViewId(String fromViewId)
    {
        this.fromViewId = fromViewId;
    }


    public void addNavigationCase(NavigationCase value)
    {
        navigationCases.add(value);
    }


    public Collection<NavigationCase> getNavigationCases()
    {
        return navigationCases;
    }

}
