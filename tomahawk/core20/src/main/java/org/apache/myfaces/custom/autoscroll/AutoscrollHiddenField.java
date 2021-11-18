/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.autoscroll;

import javax.faces.component.UIOutput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.tomahawk.util.TomahawkResourceUtils;

/**
 * This component is used internally to render an autoscroll hidden field at the end
 * of the form.
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
@JSFComponent
public class AutoscrollHiddenField extends UIOutput
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.custom.autoscroll.AutoscrollHiddenField";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.custom.autoscroll.AutoscrollHiddenField";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.custom.autoscroll.AutoscrollHiddenField";
    
    public AutoscrollHiddenField()
    {
        this.getAttributes().put(JSFAttr.TARGET_ATTR, TomahawkResourceUtils.FORM_LOCATION);
    }
    
    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }
}
