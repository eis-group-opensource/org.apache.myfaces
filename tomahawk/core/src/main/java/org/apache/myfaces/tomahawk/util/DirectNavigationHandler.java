/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.tomahawk.util;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * An alternative implementation of the standard JSF NavigationHandler API which
 * directly using the outcome of an action as the name of the page to navigate to.
 * <p>
 * This bypasses the "logical navigation" approach of standard JSF, so that pages
 * can be wired together by using actual urls in command actions and action-method
 * return values.
 * <p>
 * When this navigation-handler is configured, all navigation-rule entries in
 * faces-config.xml files are ignored completely.
 * <p>
 * This navigation handler does not work well in combination with other custom
 * navigation handlers, as it never passes any calls down to underlying
 * implementations. Having another NavigationHandler decorate this one will
 * work; having this navigation handler decorate another will not as the
 * decorated handler will never be invoked. Therefore if multiple navigation
 * handlers are to be used (eg the Tomahawk RedirectTrackerNavigationHandler
 * in combination with this one), then this should be listed first in the
 * faces-config.xml file so that the other handlers decorate this one. 
 */
public class DirectNavigationHandler extends NavigationHandler
{
    /**
     * Gives the handleNavigation() method an alternative behaviour. Linking
     * is now processed directly to the given url (e.g. action="/pages/site.jsp").
     *
     * There is no check if the outcome value really points to a valid page.
     */
    public void handleNavigation(FacesContext context, String fromAction, String outcome)
    {
        if(outcome == null || outcome.length()==0)
        {
            return;
        }

        ViewHandler viewHandler=context.getApplication().getViewHandler();
        UIViewRoot viewRoot=viewHandler.createView(context,outcome);
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }
}
