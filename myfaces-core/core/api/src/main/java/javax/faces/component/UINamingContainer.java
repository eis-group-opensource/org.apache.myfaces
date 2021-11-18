/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspProperty;

/**
 * Base class for components that provide a new "namespace" for the ids of their
 * child components.
 * <p>
 * See the javadocs for interface NamingContainer for further details.
 * </p>
 */
@JSFComponent(
        name="f:subview",
        bodyContent="JSP",
        tagClass="org.apache.myfaces.taglib.core.SubviewTag")
@JSFJspProperty(name="id",required=true)
public class UINamingContainer extends UIComponentBase implements NamingContainer
{
    public static final String COMPONENT_TYPE = "javax.faces.NamingContainer";
    public static final String COMPONENT_FAMILY = "javax.faces.NamingContainer";

    /**
     * Construct an instance of the UINamingContainer.
     */
    public UINamingContainer()
    {
        setRendererType(null);
    }

    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }
}
