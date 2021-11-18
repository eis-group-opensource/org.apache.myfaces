/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.context.FacesContext;

import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.DisplayValueOnlyAware;
import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;

/**
 * Extends standard inputText by user role support. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:inputText"
 *   class = "org.apache.myfaces.component.html.ext.HtmlInputText"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlInputTextTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlInputText
        extends javax.faces.component.html.HtmlInputText
        implements UserRoleAware, DisplayValueOnlyCapable,
        ForceIdAware, DisplayValueOnlyAware, DataProperties, AlignProperty
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlInputText";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Text";

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
     *  If true the input is rendered disabled on the client side and 
     *  a hidden input is used to actualy submit his value back to 
     *  the server.
     *  
     * @JSFProperty
     */
    public abstract boolean isDisabledOnClientSide();
    
    /**
     * Non HTML standard attribute to disable browser's autocomplete function.
     * 
     * @JSFProperty
     */
    public abstract String getAutocomplete();
    
}
