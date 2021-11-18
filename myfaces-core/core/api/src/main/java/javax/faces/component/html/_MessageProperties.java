/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

interface _MessageProperties
{
    /**
     * CSS class to be used for messages with severity "ERROR".
     * 
     */
    @JSFProperty
    public abstract String getErrorClass();

    /**
     * CSS style to be used for messages with severity "ERROR".
     * 
     */
    @JSFProperty
    public abstract String getErrorStyle();

    /**
     * CSS class to be used for messages with severity "FATAL".
     * 
     */
    @JSFProperty
    public abstract String getFatalClass();

    /**
     * CSS style to be used for messages with severity "FATAL".
     * 
     */
    @JSFProperty
    public abstract String getFatalStyle();

    /**
     * CSS class to be used for messages with severity "INFO".
     * 
     */
    @JSFProperty
    public abstract String getInfoClass();

    /**
     * CSS style to be used for messages with severity "INFO".
     * 
     */
    @JSFProperty
    public abstract String getInfoStyle();

    /**
     * If true, the message summary will be rendered as a tooltip (i.e. HTML title attribute).
     * 
     */
    @JSFProperty(defaultValue="false")
    public abstract boolean isTooltip();
    
    /**
     * CSS class to be used for messages with severity "WARN".
     * 
     */
    @JSFProperty
    public abstract String getWarnClass();

    /**
     * CSS style to be used for messages with severity "WARN".
     * 
     */
    @JSFProperty
    public abstract String getWarnStyle();

}
