/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.htmlTag;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspProperty;
import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.StyleAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * Creates an arbitrary HTML tag which encloses any child components. 
 * The value attribute specifies the name of the generated tag.
 * <br/>
 * If value is an empty string then no tag will be generated, but 
 * the child components will be rendered. This differs from setting 
 * rendered=false, which prevents child components from being 
 * rendered at all.
 * <br/>
 * You can specify some attribute to be added to the component 
 * using f:param like this:
 * <br/>
 * <t:htmlTag value="span">
 *       <f:param name="title" value="Hello world!"/>
 * </t:htmlTag>
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @since 1.1.7
 * @author bdudney (latest modification by $Author: lu4242 $)
 * @version $Revision: 691871 $ $Date: 2008-09-04 07:32:08 +0300 (Thu, 04 Sep 2008) $
 */
@JSFComponent(
        name = "t:htmlTag",
        clazz = "org.apache.myfaces.custom.htmlTag.HtmlTag",
        tagClass = "org.apache.myfaces.custom.htmlTag.HtmlTagTag")
@JSFJspProperty(
        name = "converter",
        returnType = "javax.faces.convert.Converter",
        tagExcluded = true)
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

}