/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

interface _EscapeProperty
{
    /**
     * Indicates whether rendered markup should be escaped.
     * Default: true
     * 
     */
    @JSFProperty(defaultValue="true")    
    public boolean isEscape();
}
