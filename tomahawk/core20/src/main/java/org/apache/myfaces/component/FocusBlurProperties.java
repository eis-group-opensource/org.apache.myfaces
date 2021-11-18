/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

public interface FocusBlurProperties
{

    /**
     * HTML: Specifies a script to be invoked when the element loses focus.
     * 
     */
    @JSFProperty(clientEvent="blur")
    public String getOnblur();
    
    /**
     * HTML: Specifies a script to be invoked when the element receives focus.
     * 
     */
    @JSFProperty(clientEvent="focus")
    public String getOnfocus();
    
}
