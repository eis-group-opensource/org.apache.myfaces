/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.html.ext;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlCommandLink;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlCommandLinkTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 564616 $ $Date: 2007-08-10 18:22:19 +0300 (Fri, 10 Aug 2007) $
 */
public class HtmlCommandLinkTag
        extends HtmlCommandLinkTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlCommandLinkTag.class);

    public String getComponentType()
    {
        return HtmlCommandLink.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlCommandLink.DEFAULT_RENDERER_TYPE;
    }


    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    private String _actionFor;
    private String _disabled;
    private String _disabledStyle;
    private String _disabledStyleClass;

    public void release() {
        super.release();

        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
        _actionFor = null;
        _disabled = null;
        _disabledStyle = null;
        _disabledStyleClass = null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setStringProperty(component, "actionFor", _actionFor);
        setBooleanProperty(component, "disabled", _disabled);
        setStringProperty(component, "disabledStyle",_disabledStyle);
        setStringProperty(component, "disabledStyleClass",_disabledStyleClass);
    }


    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setActionFor(String actionFor)
    {
        _actionFor = actionFor;
    }

    public void setDisabled(String disabled)
    {
        _disabled = disabled;
    }

    public void setDisabledStyle(String disabledStyle)
    {
        _disabledStyle = disabledStyle;
    }

    public void setDisabledStyleClass(String disabledStyleClass)
    {
        _disabledStyleClass = disabledStyleClass;
    }
}
