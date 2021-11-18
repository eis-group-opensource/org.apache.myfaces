/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.component.AltProperty;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.DisplayValueOnlyAware;
import org.apache.myfaces.component.EscapeAware;
import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.custom.ExtendedComponentBase;
import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.shared_tomahawk.component.EscapeCapable;

/**
 * Extends standard selectManyCheckbox with user role support and
 * a valueType attribute. 
 * 
 * Additionally this extended selectManyCheckbox accepts a layout 
 * attribute of value "spread" (see custom checkbox tag).
 *  
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: jakobk $)
 * @version $Revision: 955214 $ $Date: 2010-06-16 15:16:02 +0300 (Wed, 16 Jun 2010) $
 */
@JSFComponent(
    name = "t:selectManyCheckbox",
    clazz = "org.apache.myfaces.component.html.ext.HtmlSelectManyCheckbox",
    tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlSelectManyCheckboxTag")
public abstract class AbstractHtmlSelectManyCheckbox
        extends javax.faces.component.html.HtmlSelectManyCheckbox
        implements UserRoleAware, DisplayValueOnlyCapable,  
        EscapeCapable, DisplayValueOnlyAware, EscapeAware,
        ForceIdAware, DataProperties, AltProperty,
        ExtendedComponentBase
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSelectManyCheckbox";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Checkbox";

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
     * A integer representing the number of checkbox rows if the 
     * layout is lineDirection and checkbox columns if the layout 
     * is pageDirection.
     */
    @JSFProperty
    public abstract String getLayoutWidth();
    
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
    
    private static boolean booleanFromObject(Object obj, boolean defaultValue)
    {
        if(obj instanceof Boolean)
        {
            return ((Boolean) obj).booleanValue();
        }
        else if(obj instanceof String)
        {
            return Boolean.valueOf(((String) obj)).booleanValue();
        }

        return defaultValue;
    }
}
