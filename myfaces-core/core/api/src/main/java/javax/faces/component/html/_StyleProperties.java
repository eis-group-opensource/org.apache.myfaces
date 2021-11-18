/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

interface _StyleProperties
{
    /**
     * HTML: CSS styling instructions.
     * 
     */
    @JSFProperty
    public abstract String getStyle();

    /**
     * The CSS class for this element.  Corresponds to the HTML 'class' attribute.
     * 
     */
    @JSFProperty
    public abstract String getStyleClass();

}
