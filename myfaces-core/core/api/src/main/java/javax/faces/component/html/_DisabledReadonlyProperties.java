/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

interface _DisabledReadonlyProperties
{
    /**
     * HTML: When true, this element cannot receive focus.
     * 
     */
    @JSFProperty(defaultValue="false")
    public abstract boolean isDisabled();

    /**
     * HTML: When true, indicates that this component cannot be modified by the user.
     * The element may receive focus unless it has also been disabled.
     * 
     */
    @JSFProperty(defaultValue="false")
    public abstract boolean isReadonly();
}
