/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.DisplayValueOnlyAware;
import org.apache.myfaces.component.EscapeAware;
import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.shared_tomahawk.component.EscapeCapable;

/**
 * Extends standard selectManyMenu with user role support and
 * a valueType attribute.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @since 1.1.7
 * @author Martin Marinschek (latest modification by $Author: jakobk $)
 * @version $Revision: 955214 $ $Date: 2010-06-16 15:16:02 +0300 (Wed, 16 Jun 2010) $
 */
@JSFComponent(
    name = "t:selectManyMenu",
    clazz = "org.apache.myfaces.component.html.ext.HtmlSelectManyMenu",
    tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlSelectManyMenuTag")
public abstract class AbstractHtmlSelectManyMenu
        extends javax.faces.component.html.HtmlSelectManyMenu
        implements UserRoleAware, DisplayValueOnlyCapable,
        EscapeCapable, DisplayValueOnlyAware, EscapeAware,
        ForceIdAware, DataProperties
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSelectManyMenu";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Menu";
    
    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
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

    public boolean isSetDisplayValueOnly(){
        return getDisplayValueOnly() != null ? true : false;  
    }
    
    public boolean isDisplayValueOnly(){
        return getDisplayValueOnly() != null ? getDisplayValueOnly().booleanValue() : false;
    }
    
    public void setDisplayValueOnly(boolean displayValueOnly){
        this.setDisplayValueOnly((Boolean) Boolean.valueOf(displayValueOnly));
    }
    
    /**
     * Specifies the value type of the selectable items. This attribute is
     * similar to the collectionType attribute introduced in JSF 2.0. 
     * It can be used to declare the type of the selectable items when using
     * a Collection to store the values in the managed bean, because it is 
     * not possible in Java to get the value type of a type-safe Collection
     * (in contrast to arrays where this is possible). 
     * 
     * @since 2.0
     */
    @JSFProperty
    public abstract String getValueType(); 

}
