/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.html.ext;

import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlPanelGridTagBase;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlPanelGrid;

import javax.faces.component.UIComponent;

/**
 * @author Martin Marinschek (latest modification by $Author: mmarinschek $)
 * @version $Revision: 170213 $ $Date: 2005-05-15 13:22:56 +0200 (Sun, 15 May 2005) $
 */
public class HtmlPanelGridTag
        extends HtmlPanelGridTagBase
{
    public String getComponentType()
    {
        return HtmlPanelGrid.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlPanelGrid.DEFAULT_RENDERER_TYPE;
    }

    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    private String _displayValueOnly;
    private String _displayValueOnlyStyle;
    private String _displayValueOnlyStyleClass;

    public void release() {
        super.release();
        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
        _displayValueOnly = null;
        _displayValueOnlyStyle = null;
        _displayValueOnlyStyleClass = null;
   }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);

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

}
