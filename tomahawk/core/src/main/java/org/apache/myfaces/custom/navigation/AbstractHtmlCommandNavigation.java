/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.navigation;

import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlCommandLink;

/**
 * Command, that represents a navigation item.
 *
 * Must be nested inside a panel_navigation action and renders a 
 * clickable navigation item. This action is derived from the 
 * standard command_link action and has equal attributes. 
 * (Replaces former "navigation_item" tag.) 
 * 
 * Unless otherwise specified, all attributes accept static values 
 * or EL expressions.
 *
 * @JSFComponent
 *   name = "t:commandNavigation"
 *   class = "org.apache.myfaces.custom.navigation.HtmlCommandNavigation"
 *   tagClass = "org.apache.myfaces.custom.navigation.HtmlCommandNavigationTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 1001029 $ $Date: 2010-09-24 21:57:00 +0300 (Fri, 24 Sep 2010) $
 */
public abstract class AbstractHtmlCommandNavigation
        extends HtmlCommandLink
{
    private static final Log log = LogFactory.getLog(AbstractHtmlCommandNavigation.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandNavigation";
    public static final String COMPONENT_FAMILY = "javax.faces.Command";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Navigation";
    
    public boolean isImmediate()
    {
        //always immediate
        return true;
    }

    public void setImmediate(boolean immediate)
    {
        if (log.isWarnEnabled()) log.warn("Immediate property of HtmlCommandNavigation cannot be set --> ignored.");
    }

    public Boolean getOpenDirectly()
    {
        return Boolean.valueOf(isOpen());            
    }
    
    public Boolean getActiveDirectly()
    {
        return Boolean.valueOf(isActive());            
    }
    
    /**
     * @return false, if this item is child of another HtmlCommandNavigation, which is closed
     */
    public boolean isRendered()
    {
        if (! super.isRendered()) {
            return false;
        }
        UIComponent parent = getParent();
        while (parent != null)
        {
            if (parent instanceof HtmlCommandNavigation)
            {
                if (!((HtmlCommandNavigation)parent).isOpen())
                {
                    return false;
                }
            }

            if (parent instanceof HtmlPanelNavigation)
            {
                break;
            }
            else
            {
                parent = parent.getParent();
            }
        }

        return true;
    }

    /**
     * @JSFProperty
     *   tagExcluded="true"
     */
    public abstract boolean isOpen();
    
    public abstract void setOpen(boolean open);

    /**
     * @JSFProperty
     *   tagExcluded="true"
     */
    public abstract boolean isActive();
    
    public abstract void setActive(boolean active);
        
    public void toggleOpen()
    {
        if (isOpen())
        {
            if (getChildCount() > 0)
            {
                //item is a menu group --> close item
                setOpen(false);
            }
        }
        else
        {
            UIComponent parent = getParent();

            //close all siblings
            closeAllChildren(parent.getChildren().iterator());

            //open all parents (to be sure) and search HtmlPanelNavigation
            UIComponent p = parent;
            while (p != null && !(p instanceof HtmlPanelNavigation))
            {
                if (p instanceof HtmlCommandNavigation)
                {
                    ((HtmlCommandNavigation)p).setOpen(true);
                }
                p = p.getParent();
            }
            // p is now the HtmlPanelNavigation

            if (!hasCommandNavigationChildren())
            {
                //item is an end node --> deactivate all other nodes, and then...
                if (!(p instanceof HtmlPanelNavigation))
                {
                    log.error("HtmlCommandNavigation without parent HtmlPanelNavigation ?!");
                }
                else
                {
                    //deactivate all other items
                    deactivateAllChildren(p.getChildren().iterator());
                }
                //...activate this item
                setActive(true);
            }
            else
            {
                //open item
                setOpen(true);
            }
        }
    }

    private boolean hasCommandNavigationChildren()
    {
        if (getChildCount() == 0)
        {
            return false;
        }
        List list = getChildren();
        for (int i = 0, sizei = list.size(); i < sizei; i++)
        {
            if (list.get(i) instanceof HtmlCommandNavigation)
            {
                return true;
            }
        }
        return false;
    }


    private static void deactivateAllChildren(Iterator children)
    {
        while (children.hasNext())
        {
            UIComponent ni = (UIComponent)children.next();
            if (ni instanceof HtmlCommandNavigation)
            {
                ((HtmlCommandNavigation)ni).setActive(false);
                if (ni.getChildCount() > 0)
                {
                    deactivateAllChildren(ni.getChildren().iterator());
                }
            }
        }
    }

    private static void closeAllChildren(Iterator children)
    {
        while (children.hasNext())
        {
            UIComponent ni = (UIComponent)children.next();
            if (ni instanceof HtmlCommandNavigation)
            {
                ((HtmlCommandNavigation)ni).setOpen(false);
                if (ni.getChildCount() > 0)
                {
                    closeAllChildren(ni.getChildren().iterator());
                }
            }
        }
    }


    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        if (event instanceof ActionEvent)
        {
            ActionEvent actionEvent = (ActionEvent)event;
            if (actionEvent.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES)
            {
                HtmlCommandNavigation navItem = (HtmlCommandNavigation)actionEvent.getComponent();
                navItem.toggleOpen();
                FacesContext.getCurrentInstance().renderResponse();
            }
        }
        super.broadcast(event);
    }
    
}
