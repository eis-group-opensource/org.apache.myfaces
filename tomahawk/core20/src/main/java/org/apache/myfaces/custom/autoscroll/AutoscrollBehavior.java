/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.autoscroll;

import javax.faces.application.ResourceDependency;
import javax.faces.component.behavior.ClientBehaviorBase;
import javax.faces.component.behavior.ClientBehaviorContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFBehavior;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.FormInfo;
/**
 * Adds to a command link or button a javascript that enable automatic scrolling 
 * behavior after the event is invoked.
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
@JSFBehavior(name="t:autoScroll",bodyContent="empty")
@ResourceDependency(library="org.apache.myfaces", name="oamSubmit.js")
public class AutoscrollBehavior extends ClientBehaviorBase
{

    public static final String BEHAVIOR_ID = "org.apache.myfaces.custom.autoscroll.AutoscrollBehavior";
    
    @Override
    public String getScript(ClientBehaviorContext behaviorContext)
    {
        FormInfo nestedFormInfo = RendererUtils.findNestingForm(behaviorContext.getComponent(), behaviorContext.getFacesContext());
        StringBuffer script = new StringBuffer();
        HtmlRendererUtils.appendAutoScrollAssignment(behaviorContext.getFacesContext(), script, nestedFormInfo.getFormName());
        return script.toString();
    }
    
    /**
     * The event that this client behavior should be attached.
     * 
     * @return
     */
    @JSFProperty
    private String getEvent()
    {
        return null;
    }
}
