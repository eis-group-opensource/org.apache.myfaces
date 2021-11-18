/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class RenderKit
{

    private String id;
    private String renderKitClass;
    private List<org.apache.myfaces.config.element.Renderer> renderer = new ArrayList<org.apache.myfaces.config.element.Renderer>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRenderKitClass()
    {
        return renderKitClass;
    }

    public void setRenderKitClass(String renderKitClass)
    {
        this.renderKitClass = renderKitClass;
    }

    public List<org.apache.myfaces.config.element.Renderer> getRenderer()
    {
        return renderer;
    }

    public void addRenderer(org.apache.myfaces.config.element.Renderer value)
    {
        renderer.add(value);
    }

    public void merge(RenderKit renderKit)
    {
        renderer.addAll(renderKit.getRenderer());
    }

}
