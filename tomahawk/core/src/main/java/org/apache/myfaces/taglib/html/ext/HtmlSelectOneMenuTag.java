/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.html.ext;

import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlSelectOneMenu;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlSelectMenuTagBase;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class HtmlSelectOneMenuTag
        extends HtmlSelectMenuTagBase
{
    public String getComponentType()
    {
        return HtmlSelectOneMenu.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlSelectOneMenu.DEFAULT_RENDERER_TYPE;
    }

    private String _escape;
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    private String _displayValueOnly;
    private String _displayValueOnlyStyle;
    private String _displayValueOnlyStyleClass;

    public void release() {
        super.release();
        _escape=null;
        _enabledOnUserRole=null;
        _visibleOnUserRole=null;

        _displayValueOnly=null;
        _displayValueOnlyStyle=null;
        _displayValueOnlyStyleClass=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setBooleanProperty(component, JSFAttr.ESCAPE_ATTR, _escape);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);

        setBooleanProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_ATTR, _displayValueOnly);
        setStringProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_STYLE_ATTR, _displayValueOnlyStyle);
        setStringProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_STYLE_CLASS_ATTR, _displayValueOnlyStyleClass);
    }

    public void setEscape(String escape)
    {
        _escape = escape;
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
}
