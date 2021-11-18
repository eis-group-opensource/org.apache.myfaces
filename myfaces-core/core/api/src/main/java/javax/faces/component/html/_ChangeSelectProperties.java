/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

interface _ChangeSelectProperties
{
    /**
     * HTML: Specifies a script to be invoked when the element is modified.
     * 
     */
    @JSFProperty
    public abstract String getOnchange();


    /**
     * HTML: Specifies a script to be invoked when the element is selected.
     * 
     */
    @JSFProperty
    public abstract String getOnselect();

}
