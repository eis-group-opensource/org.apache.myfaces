/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.html.ext;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlGraphicImageTagBase;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlGraphicImage;

/**
 * @author Bruno Aranda
 * @version $Revision$ $Date: 2005-05-11 18:45:06 +0200 (Wed, 11 May 2005) $
 */
public class HtmlGraphicImageTag
        extends HtmlGraphicImageTagBase
{
    public String getComponentType()
    {
        return HtmlGraphicImage.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlGraphicImage.DEFAULT_RENDERER_TYPE;
    }

    private String _align;
    private String _border;
    private String _enabledOnUserRole;
    private String _hspace;
    private String _visibleOnUserRole;
    private String _vspace;

    public void release() {
        super.release();

        _align=null;
        _border=null;
        _enabledOnUserRole=null;
        _hspace=null;
        _visibleOnUserRole=null;
        _vspace=null;
   }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HTML.ALIGN_ATTR, _align);
        setStringProperty(component, HTML.BORDER_ATTR, _border);
        setStringProperty(component, HTML.HSPACE_ATTR, _hspace);
        setStringProperty(component, HTML.VSPACE_ATTR, _vspace);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
    }

    public void setAlign(String align)
    {
        _align = align;
    }

    public void setBorder(String border)
    {
        _border = border;
    }

    public void setHspace(String hspace)
    {
        _hspace = hspace;
    }

    public void setVspace(String vspace)
    {
        _vspace = vspace;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

}
