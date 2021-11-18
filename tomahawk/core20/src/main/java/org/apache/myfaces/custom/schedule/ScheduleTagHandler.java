/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.schedule;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRuleset;

import org.apache.myfaces.custom.facelets.tag.MethodRule;

/**
 * 
 * @since 1.1.7
 */
public class ScheduleTagHandler extends ComponentHandler {
 
    private static final String MOUSE_LISTENER = "mouseListener";
    
    private static final Class [] mouseListenerParamList = new Class[]{ScheduleMouseEvent.class}; 

    public ScheduleTagHandler(ComponentConfig tagConfig) {
        super(tagConfig);
    }

    protected MetaRuleset createMetaRuleset(Class type)
    {       
        return super.createMetaRuleset(type).alias("class", "styleClass")
            .addRule(
                new MethodRule(MOUSE_LISTENER, 
                        String.class, mouseListenerParamList));
    }

 }