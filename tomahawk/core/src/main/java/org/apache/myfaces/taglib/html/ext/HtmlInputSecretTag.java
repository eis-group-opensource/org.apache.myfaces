/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.html.ext;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlInputSecret;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlInputSecretTagBase;

/**
 * @author Bruno Aranda 
 * @version $Revision$ $Date: 2005-06-05 13:08:33 +0200 (Sun, 5 Jun 2005) $
 */
public class HtmlInputSecretTag
        extends HtmlInputSecretTagBase
{
    public String getComponentType()
    {
        return HtmlInputSecret.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlInputSecret.DEFAULT_RENDERER_TYPE;
    }

    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _datafld;
    private String _datasrc;
    private String _dataformatas;

    private String _displayValueOnly;
    private String _displayValueOnlyStyle;
    private String _displayValueOnlyStyleClass;

    public void release() {
        super.release();

        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
        _datafld=null;
        _datasrc=null;
        _dataformatas=null;
        _displayValueOnly=null;
        _displayValueOnlyStyle=null;
        _displayValueOnlyStyleClass=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setStringProperty(component, HTML.DATAFLD_ATTR, _datafld);
        setStringProperty(component, HTML.DATASRC_ATTR, _datasrc);
        setStringProperty(component, HTML.DATAFORMATAS_ATTR, _dataformatas);
        setBooleanProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_ATTR, _displayValueOnly);
        setStringProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_STYLE_ATTR, _displayValueOnlyStyle);
        setStringProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_STYLE_CLASS_ATTR, _displayValueOnlyStyleClass);
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setDisplayValueOnly(String displayValueOnly)
    {
        _displayValueOnly = displayValueOnly;
    }

    public void setDisplayValueOnlyStyle(String displayValueOnlyStyle)
    {
        _displayValueOnlyStyle = displayValueOnlyStyle;
    }

    public void setDisplayValueOnlyStyleClass(String displayValueOnlyStyleClass)
    {
        _displayValueOnlyStyleClass = displayValueOnlyStyleClass;
    }

    public void setDatafld(String datafld)
    {
        _datafld = datafld;
    }

    public void setDatasrc(String datasrc)
    {
        _datasrc = datasrc;
    }

    public void setDataformatas(String dataformatas)
    {
        _dataformatas = dataformatas;
    }
}
