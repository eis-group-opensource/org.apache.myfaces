/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.schedule;

import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.MethodRule;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.html.HtmlComponentHandler;

/**
 * 
 * @since 1.1.7
 */
public class ScheduleTagHandler extends HtmlComponentHandler {
 
    private static final String MOUSE_LISTENER = "mouseListener";
    
    private static final Class [] mouseListenerParamList = new Class[]{ScheduleMouseEvent.class}; 

    public ScheduleTagHandler(ComponentConfig tagConfig) {
        super(tagConfig);
    }

    protected MetaRuleset createMetaRuleset(Class type)
    {       
        return super.createMetaRuleset(type).addRule(
                new MethodRule(MOUSE_LISTENER, 
                        String.class, mouseListenerParamList));
    }

 }