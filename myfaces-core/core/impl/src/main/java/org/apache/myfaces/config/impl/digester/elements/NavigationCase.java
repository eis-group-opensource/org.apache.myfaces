/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;




/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class NavigationCase implements org.apache.myfaces.config.element.NavigationCase
{

    private String fromAction;
    private String fromOutcome;
    private String toViewId;
    private boolean redirect;


    public String getFromAction()
    {
        return fromAction;
    }


    public void setFromAction(String fromAction)
    {
        this.fromAction = fromAction;
    }


    public String getFromOutcome()
    {
        return fromOutcome;
    }


    public void setFromOutcome(String fromOutcome)
    {
        this.fromOutcome = fromOutcome;
    }


    public String getToViewId()
    {
        return toViewId;
    }


    public void setToViewId(String toViewId)
    {
        this.toViewId = toViewId;
    }


    public void setRedirect()
    {
        this.redirect = true;
    }


    public boolean isRedirect()
    {
        return redirect;
    }
}
