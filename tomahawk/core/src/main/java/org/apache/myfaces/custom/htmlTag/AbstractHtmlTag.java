/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.htmlTag;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.StyleAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * Creates an arbitrary HTML tag which encloses any child components. 
 * The value attribute specifies the name of the generated tag.
 * 
 * If value is an empty string then no tag will be generated, but 
 * the child components will be rendered. This differs from setting 
 * rendered=false, which prevents child components from being 
 * rendered at all.
 * 
 * There is currently no facility for adding attributes to the 
 * generated html tag other than those explicitly supported by 
 * the attributes on this JSF component.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:htmlTag"
 *   class = "org.apache.myfaces.custom.htmlTag.HtmlTag"
 *   tagClass = "org.apache.myfaces.custom.htmlTag.HtmlTagTag"
 * @since 1.1.7
 * @author bdudney (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlTag extends UIOutput 
    implements UserRoleAware, StyleAware, ForceIdAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTag";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlTagRenderer";

    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this,
                getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }
    
    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }
    
    /**
     * This component converts submitted values to String, so
     * converter is not needed, not custom conversion necessary.
     * 
     * @JSFProperty
     *   tagExcluded = "true"
     */
    public Converter getConverter()
    {
        return null;
    }
    
    public void setConverter(Converter converter)
    {
        throw new UnsupportedOperationException();
    }
    
}