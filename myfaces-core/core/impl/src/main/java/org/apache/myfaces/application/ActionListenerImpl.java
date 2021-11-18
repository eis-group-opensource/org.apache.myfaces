/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.application;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.ActionSource;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;


/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @author Anton Koinov
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public class ActionListenerImpl
    implements ActionListener
{

    public void processAction(ActionEvent actionEvent) throws AbortProcessingException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();

        ActionSource actionSource = (ActionSource)actionEvent.getComponent();
        MethodBinding methodBinding = actionSource.getAction();

        String fromAction = null;
        String outcome = null;
        if (methodBinding != null)
        {
            fromAction = methodBinding.getExpressionString();
            try
            {
                Object objOutcome = methodBinding.invoke(facesContext, null);

                if (objOutcome != null)
                {
                    outcome = objOutcome.toString();
                }
            }
            catch (EvaluationException e)
            {
                Throwable cause = e.getCause();
                if (cause != null && cause instanceof AbortProcessingException)
                {
                    throw (AbortProcessingException)cause;
                }
   
                throw new FacesException("Error calling action method of component with id " + actionEvent.getComponent().getClientId(facesContext), e);
                
            }
            catch (RuntimeException e)
            {
                throw new FacesException("Error calling action method of component with id " + actionEvent.getComponent().getClientId(facesContext), e);
            }
        }

        NavigationHandler navigationHandler = application.getNavigationHandler();
        navigationHandler.handleNavigation(facesContext,
                                           fromAction,
                                           outcome);
        //Render Response if needed
        facesContext.renderResponse();

    }
}
