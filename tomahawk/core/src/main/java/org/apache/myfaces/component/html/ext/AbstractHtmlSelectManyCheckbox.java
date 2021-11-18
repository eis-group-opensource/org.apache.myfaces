/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.context.FacesContext;

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
 * Extends standard selectManyCheckbox with user role support. 
 * 
 * Additionally this extended selectManyCheckbox accepts a layout 
 * attribute of value "spread" (see custom checkbox tag).
 *  
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:selectManyCheckbox"
 *   class = "org.apache.myfaces.component.html.ext.HtmlSelectManyCheckbox"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlSelectManyCheckboxTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
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
     * 
     * @JSFProperty
     */
    public abstract String getLayoutWidth();

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
