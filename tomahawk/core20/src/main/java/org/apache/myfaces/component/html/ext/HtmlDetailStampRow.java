/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.component.UIPanel;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

@JSFComponent
public class HtmlDetailStampRow extends UIPanel
{
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.HtmlDetailStampRow"; 
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlDetailStampRow";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlDetailStampRow";

    public HtmlDetailStampRow()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }
}
