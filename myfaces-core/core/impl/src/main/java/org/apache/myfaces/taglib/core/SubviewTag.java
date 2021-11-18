/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.faces.webapp.UIComponentELTag;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.myfaces.application.jsp.ViewResponseWrapper;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspTag;

/**
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
public class SubviewTag extends UIComponentELTag
{
    public SubviewTag() {
        super();
    }
    
    public String getComponentType()
    {
        return UINamingContainer.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return null;
    }
    
    /**
     * Creates a UIComponent from the BodyContent
     * If a Subview is included via the <jsp:include> tag
     * the corresponding jsp is rendered with
     * getServletContext().getRequestDispatcher("includedSite").include(request,response)
     * and it is possible that something was written to the Response direct.
     * So is is necessary that the content of the wrapped response is added to the componenttree.
     * @return UIComponent or null
     */
    protected UIComponent createVerbatimComponentFromBodyContent() {
        UIOutput component = (UIOutput) super.createVerbatimComponentFromBodyContent();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Object response = facesContext.getExternalContext().getResponse();
        String wrappedOutput;

        if (response instanceof ViewResponseWrapper)
        {
            ViewResponseWrapper wrappedResponse = (ViewResponseWrapper) response;
            wrappedOutput = wrappedResponse.toString();
            if (wrappedOutput != null && wrappedOutput.length() > 0)
            {
                String componentvalue = null;
                if (component != null)
                {
                    //save the Value of the Bodycontent
                  componentvalue = (String) component.getValue();
                }
                component = super.createVerbatimComponent();
                if (componentvalue != null)
                {
                    component.setValue(wrappedOutput + componentvalue);
                }
                else
                {
                    component.setValue(wrappedOutput);
                }
                wrappedResponse.reset();
            }
        }
        return component;
    }    
    
}
