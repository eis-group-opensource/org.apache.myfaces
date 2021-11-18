/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.collapsiblepanel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlCommandLink;

/**
 * Link used to collapse or expand a t:collapsiblePanel. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:headerLink"
 *   class = "org.apache.myfaces.custom.collapsiblepanel.HtmlHeaderLink"
 *   tagClass = "org.apache.myfaces.custom.collapsiblepanel.HtmlHeaderLinkTag"
 *
 * @since 1.1.7
 * @author Martin Marinschek (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 *
 */
public abstract class AbstractHtmlHeaderLink extends HtmlCommandLink
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlHeaderLink";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HeaderLink";
    
    private static final String LINK_ID = "ToggleCollapsed".intern();
    
    public String getClientId(FacesContext context)
    {
        if (context == null) throw new NullPointerException("context");

        String clientId;
        
        //Try to find its nearest parent that extends form HtmlCollapsiblePanel
        //to calculate its id.
        UIComponent collapsiblePanel = findParentCollapsiblePanel(this);
        
        if (collapsiblePanel == null)
        {
            //Calculate its id normally
            clientId = super.getClientId(context);
        }
        else
        {
            //Create its id based on collapsiblePanel id.
            //There only could exists one headerLink per collapsiblePanel.
            String calculatedId = collapsiblePanel.getClientId(context) + LINK_ID;
            
            //Get the original
            clientId = super.getClientId(context);
            
            //just change the final id with the calculated id.
            int lastDoublePointLocation = clientId.lastIndexOf(':');
            clientId = clientId.substring(0,lastDoublePointLocation) +
                calculatedId.substring(lastDoublePointLocation);
        }
        
        return clientId;
    }
    
    protected static UIComponent findParentCollapsiblePanel(UIComponent component)
    {
        UIComponent currentComponent = component;

        // Search for an ancestor that is a instance of HtmlCollapsiblePanel
        while (null != (currentComponent = currentComponent.getParent()))
        {
            if (currentComponent instanceof HtmlCollapsiblePanel)
            {
                break;
            }
        }
        return currentComponent;
    }
    
    /**
     * This property is no longer available since getClientId()
     * is overridden to proper working of collapsiblePanel
     * 
     * @JSFProperty
     *   tagExcluded = "true"
     *   
     * @return
     */
    public Boolean getForceId()
    {
        return Boolean.FALSE;
    }
    
    public void setForceId(Boolean forceId){
        throw new UnsupportedOperationException(); 
    }
    
    /**
     * This property is no longer available since getClientId()
     * is overridden to proper working of collapsiblePanel
     * 
     * @JSFProperty
     *   tagExcluded = "true"
     *   
     * @return
     */
    public Boolean getForceIdIndex()
    {
        return Boolean.TRUE;
    }
    
    public void setForceIdIndex(Boolean forceIdIndex)
    {
        throw new UnsupportedOperationException();
    }

}
