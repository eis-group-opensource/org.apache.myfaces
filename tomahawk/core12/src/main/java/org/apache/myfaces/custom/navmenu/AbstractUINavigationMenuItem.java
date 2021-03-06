/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.navmenu;

import java.util.Iterator;
import java.util.StringTokenizer;

import javax.el.MethodExpression;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.component.MethodBindingToMethodExpression;
import org.apache.myfaces.component.MethodExpressionToMethodBinding;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlCommandNavigationItem;
import org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlPanelNavigationMenu;

/**
 * A menu item. Used by navigationMenu, jscookMenu. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @since 1.1.7
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @version $Revision: 719425 $ $Date: 2008-11-21 01:41:15 +0200 (Fri, 21 Nov 2008) $
 */
@JSFComponent(
        name = "t:navigationMenuItem",
        bodyContent = "JSP",
        clazz = "org.apache.myfaces.custom.navmenu.UINavigationMenuItem",
        tagClass = "org.apache.myfaces.custom.navmenu.HtmlNavigationMenuItemTag")
public abstract class AbstractUINavigationMenuItem extends UISelectItem implements
    UserRoleAware, ActionSource {
    private static final boolean DEFAULT_IMMEDIATE = true;

    public static final String COMPONENT_TYPE = "org.apache.myfaces.NavigationMenuItem";
    public static final String COMPONENT_FAMILY = "javax.faces.SelectItem";

    public AbstractUINavigationMenuItem() {
        super();
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    /**
     *  
     */
    @JSFProperty
    public abstract String getIcon();

    /**
     * 
     */
    @JSFProperty(defaultValue="false")
    public abstract boolean isSplit();

    /**
     * 
     */
    @JSFProperty(defaultValue="false",tagExcluded=true)
    public abstract boolean isOpen();
    
    public abstract void setOpen(boolean open);

    public abstract void setActive(boolean active);

    /**
     * 
     */
    @JSFProperty(defaultValue="false",tagExcluded=true)
    public abstract boolean isActive();

    /**
     * 
     */
    @JSFProperty(defaultValue="true",tagExcluded=true)
    public abstract boolean isImmediate();

    /**
     * 
     */
    @JSFProperty(tagExcluded=true)
    public abstract String getExternalLink();

    // Action Source    
    /**
     * Specifies the action to take when this command is invoked.
     *
     * If the value is an expression, it is expected to be a method 
     * binding EL expression that identifies an action method. An action method
     * accepts no parameters and has a String return value, called the action
     * outcome, that identifies the next view displayed. The phase that this
     * event is fired in can be controlled via the immediate attribute.
     *
     * If the value is a string literal, it is treated as a navigation outcome
     * for the current view.  This is functionally equivalent to a reference to
     * an action method that returns the string literal.
     * 
     */
    @JSFProperty(
            stateHolder = true,
            literalOnly = true,
            returnSignature = "java.lang.Object",
            jspName = "action")
    public abstract MethodExpression getActionExpression();
    
    /**
     * @deprecated Use getActionExpression() instead.
     */
    public MethodBinding getAction()
    {
        MethodExpression actionExpression = getActionExpression();
        if (actionExpression instanceof MethodBindingToMethodExpression) {
            return ((MethodBindingToMethodExpression)actionExpression).getMethodBinding();
        }
        if(actionExpression != null)
        {
            return new MethodExpressionToMethodBinding(actionExpression);
        }
        return null;
    }
    
    public abstract void setActionExpression(MethodExpression actionExpression);
    
    /**
     * @deprecated Use setActionExpression instead.
     */
    public void setAction(MethodBinding action)
    {
        if(action != null)
        {
            setActionExpression(new MethodBindingToMethodExpression(action));
        } 
        else
        {
            setActionExpression(null);
        }
    }
    
    
    public abstract void setActionListener(MethodBinding actionListener);

    /**
     * A method binding EL expression that identifies an action listener method
     * to be invoked if this component is activated by the user. An action
     * listener method accepts a parameter of type javax.faces.event.ActionEvent
     * and returns void. The phase that this event is fired in can be controlled
     * via the immediate attribute.
     *  
     */
    @JSFProperty(
         stateHolder = true,
         literalOnly = true,
         returnSignature="void",
         methodSignature="javax.faces.event.ActionEvent")
    public abstract MethodBinding getActionListener();

    public void addActionListener(ActionListener listener) {
        addFacesListener(listener);
    }

    public ActionListener[] getActionListeners() {
        return (ActionListener[]) getFacesListeners(ActionListener.class);
    }

    public void removeActionListener(ActionListener listener) {
        removeFacesListener(listener);
    }

    // Action Source

    /**
     * 
     */
    @JSFProperty
    public abstract String getTarget();

    /**
     * When set instead of a Hyperlink a span tag is rendered in 
     * the corresponding Component
     *
     */
    @JSFProperty(defaultValue="false") 
    public abstract boolean isDisabled();

    /**
     * CSS-Style Attribute to render when disabled is true
     * 
     */
    @JSFProperty
    public abstract String getDisabledStyle();

    /**
     * @see javax.faces.component.UIComponent#broadcast(javax.faces.event.FacesEvent)
     */
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        super.broadcast(event);

        if (event instanceof ActionEvent) {
            FacesContext context = getFacesContext();

            MethodBinding actionListenerBinding = getActionListener();
            if (actionListenerBinding != null) {
                try {
                    actionListenerBinding.invoke(context,
                                                 new Object[]{event});
                }
                catch (EvaluationException e) {
                    Throwable cause = e.getCause();
                    if (cause != null
                        && cause instanceof AbortProcessingException) {
                        throw (AbortProcessingException) cause;
                    }
                    else {
                        throw e;
                    }
                }
            }

            ActionListener defaultActionListener = context.getApplication()
                .getActionListener();
            if (defaultActionListener != null) {
                defaultActionListener.processAction((ActionEvent) event);
            }
        }
    }

    /**
     * CSS-Style Class to use when disabled is true
     * 
     */
    @JSFProperty
    public abstract String getDisabledStyleClass();

    /**
     * 
     */
    @JSFProperty(
        localMethod= true,
        tagExcluded = true) 
    public abstract String getActiveOnViewIds();
    
    protected abstract String getLocalActiveOnViewIds();
    
    public String getActiveOnViewIdsDirectly() {
        return getLocalActiveOnViewIds();
    }

    /**
     * A boolean value that indicates whether this component should be rendered.
     * Default value: true.
     */
    @JSFProperty(tagExcluded=false)
    public boolean isRendered() {
        if (!UserRoleUtils.isVisibleOnUserRole(this))
            return false;
        return super.isRendered();
    }

    public void toggleActive(FacesContext context) {
        StringTokenizer tokenizer = new StringTokenizer(this.getActiveOnViewIdsDirectly(), ";");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.trim().equals(context.getViewRoot().getViewId())) {
                this.deactivateAll();
                this.setActive(true);
                openParents();
            }
            else {
                this.setActive(false);
            }
        }
    }

    private void openParents() {
        UIComponent comp = this;

        while ((comp = comp.getParent()) instanceof AbstractUINavigationMenuItem) {
            AbstractUINavigationMenuItem parent = (AbstractUINavigationMenuItem) comp;
            if (!parent.isOpen())
                parent.setOpen(true);
            else
                return;
        }
    }

    public void deactivateAll() {
        UIComponent parent = this.getParent();
        while (!(parent instanceof HtmlPanelNavigationMenu) && parent != null) {
            parent = parent.getParent();
        }
        if (parent == null) {
            throw new IllegalStateException("no PanelNavigationMenu!");
        }

        HtmlPanelNavigationMenu root = (HtmlPanelNavigationMenu) parent;
        for (Iterator<UIComponent> it = root.getChildren().iterator(); it.hasNext();) {
            Object o = it.next();
            if (o instanceof AbstractUINavigationMenuItem) {
                AbstractUINavigationMenuItem navItem = (AbstractUINavigationMenuItem) o;
                navItem.setActive(false);
                if (navItem.getChildCount() > 0) {
                    navItem.deactivateChildren();
                }
            }
            if (o instanceof HtmlCommandNavigationItem) {
                HtmlCommandNavigationItem current = (HtmlCommandNavigationItem) o;
                current.setActive(false);
                if (current.getChildCount() > 0) {
                    current.deactivateChildren();
                }
            }
        }
    }

    public void deactivateChildren() {
        for (Iterator<UIComponent> it = this.getChildren().iterator(); it.hasNext();) {
            Object o = it.next();
            if (o instanceof AbstractUINavigationMenuItem) {
                AbstractUINavigationMenuItem current = (AbstractUINavigationMenuItem) o;
                current.setActive(false);
                if (current.getChildCount() > 0) {
                    current.deactivateChildren();
                }
            }
        }
    }

    public Boolean getActiveDirectly() {
        return Boolean.valueOf(isActive());
    }
}
