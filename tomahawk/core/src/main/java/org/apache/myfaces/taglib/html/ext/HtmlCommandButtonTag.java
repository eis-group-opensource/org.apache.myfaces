/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.html.ext;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlCommandButton;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlCommandButtonTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author: mkienenb $)
 * @version $Revision: 523868 $ $Date: 2007-03-30 02:02:29 +0300 (Fri, 30 Mar 2007) $
 */
public class HtmlCommandButtonTag
        extends HtmlCommandButtonTagBase
{
    public String getComponentType()
    {
        return HtmlCommandButton.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlCommandButton.DEFAULT_RENDERER_TYPE;
    }

    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _actionFor;

    public void release() {
        super.release();

        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
        _actionFor=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setStringProperty(component, "actionFor", _actionFor);
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

}
