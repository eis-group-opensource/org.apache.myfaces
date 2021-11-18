﻿/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.autoscroll;

import java.io.IOException;
import java.util.List;

import javax.faces.component.StateHolder;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PostRestoreStateEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletAttribute;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;

/**
 * Adds to UICommand components that implements ClientBehaviorHolder interface an
 * AutoscrollBehavior.
 * <p>
 * Use it in this way to ensure proper operation:
 * </p>
 * <code>
 * &lt;f:metadata&gt;
 *   &lt;t:autoScrollPage/&gt;
 * &lt;/f:metadata&gt;
 * </code>
 * <p>
 * Or in this way:
 * </p>
 * <code>
 * &lt;h:body&gt;
 *   &lt;t:autoScrollPage/&gt;
 * </code>
 * <p>
 * It is possible to use it at start of the page too, but note this tag attach a listener to
 * PostAddToViewEvent, so previous components added to the view before apply this TagHandler
 * will not be taken into account.
 * </p>
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
@JSFFaceletTag(name="t:autoScrollPage")
public class AutoscrollPageTagHandler extends TagHandler
{

    /**
     * 
     */
    @JSFFaceletAttribute(name="event",
            className="javax.el.ValueExpression",
            deferredValueType="java.lang.String")
    private TagAttribute _event;
    
    public AutoscrollPageTagHandler(TagConfig config)
    {
        super(config);
        _event = this.getAttribute("event");
    }

    public void apply(FaceletContext ctx, UIComponent parent)
            throws IOException
    {
        UIViewRoot root = getViewRoot(ctx, parent);
        
        if (!root.getAttributes().containsKey(AutoscrollBehaviorTagHandler.AUTOSCROLL_TAG_ON_PAGE))
        {
            root.getAttributes().put(AutoscrollBehaviorTagHandler.AUTOSCROLL_TAG_ON_PAGE, Boolean.TRUE);
        }
        
        String eventName = (_event == null) ? null : _event.getValue(ctx);
        
        root.subscribeToViewEvent(PostAddToViewEvent.class, 
                new RegisterAutoscrollBehaviorOnCommandListener(eventName));
        root.subscribeToEvent(PostRestoreStateEvent.class, new RegisterAutoscrollListener(eventName));
    }
    
    private UIViewRoot getViewRoot(FaceletContext ctx, UIComponent parent)
    {
        UIComponent c = parent;
        do
        {
            if (c instanceof UIViewRoot)
            {
                return (UIViewRoot) c;
            }
            else
            {
                c = c.getParent();
            }
        } while (c != null);

        return ctx.getFacesContext().getViewRoot();
    }

    public static class RegisterAutoscrollListener implements ComponentSystemEventListener, StateHolder
    {
        private String eventName;
        
        public RegisterAutoscrollListener()
        {
            super();
        }

        public RegisterAutoscrollListener(String event)
        {
            super();
            eventName = event;
        }

        public void processEvent(ComponentSystemEvent event)
        {
            FacesContext.getCurrentInstance().getViewRoot().subscribeToViewEvent(PostAddToViewEvent.class, 
                    new RegisterAutoscrollBehaviorOnCommandListener(eventName));
        }

        public boolean isTransient()
        {
            return false;
        }
        
        public void setTransient(boolean newTransientValue)
        {
        }

        public void restoreState(FacesContext context, Object state)
        {
            eventName = (String)state;
        }

        public Object saveState(FacesContext context)
        {
            return eventName;
        }
    }
    
    public static class RegisterAutoscrollBehaviorOnCommandListener implements SystemEventListener
    {
        private String _eventName;

        public RegisterAutoscrollBehaviorOnCommandListener(String eventName)
        {
            super();
            _eventName = eventName;
        }

        public boolean isListenerForSource(Object source)
        {
            return (source instanceof UICommand && source instanceof ClientBehaviorHolder);
        }        
        
        public void processEvent(SystemEvent event)
        {
            ClientBehaviorHolder component = (ClientBehaviorHolder) event.getSource();
            
            String eventName = (_eventName == null) ? component.getDefaultEventName() : _eventName;
            
            boolean found = false;
            List<ClientBehavior> list = component.getClientBehaviors().get(eventName);
            if (list != null)
            {
                for (ClientBehavior cb : list)
                {
                    if (cb instanceof AutoscrollBehavior)
                    {
                        found = true;
                        break;
                    }
                }
            }
            
            if (!found)
            {
                ClientBehavior behavior = (ClientBehavior) FacesContext.getCurrentInstance().getApplication().createBehavior(AutoscrollBehavior.BEHAVIOR_ID);
                component.addClientBehavior(eventName, behavior);
            }
        }
    }
}
