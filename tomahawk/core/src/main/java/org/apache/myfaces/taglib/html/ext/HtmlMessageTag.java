/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.html.ext;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlMessage;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlMessageTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author: mkienenb $)
 * @version $Revision: 523868 $ $Date: 2007-03-30 02:02:29 +0300 (Fri, 30 Mar 2007) $
 */
public class HtmlMessageTag
        extends HtmlMessageTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlOutputFormatTag.class);

    public String getComponentType()
    {
        return HtmlMessage.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlMessage.DEFAULT_RENDERER_TYPE;
    }

    private String _summaryFormat;
    private String _detailFormat;
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _replaceIdWithLabel;
    private String _forceSpan;

    public void release() {
        super.release();

        _summaryFormat = null;
        _detailFormat = null;
        _enabledOnUserRole = null;
        _visibleOnUserRole = null;
        _replaceIdWithLabel = null;
        _forceSpan = null;
    }
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "summaryFormat", _summaryFormat);
        setStringProperty(component, "detailFormat", _detailFormat);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setBooleanProperty(component, "replaceIdWithLabel",_replaceIdWithLabel==null?Boolean.TRUE.toString():_replaceIdWithLabel);
        setBooleanProperty(component, "forceSpan",_forceSpan ==null?Boolean.FALSE.toString():_forceSpan);
    }

    public void setSummaryFormat(String summaryFormat)
    {
        _summaryFormat = summaryFormat;
    }

    public void setDetailFormat(String detailFormat)
    {
        _detailFormat = detailFormat;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setReplaceIdWithLabel(String replaceIdWithLabel)
    {
        _replaceIdWithLabel = replaceIdWithLabel;
    }

    public void setForceSpan(String forceSpan)
    {
        _forceSpan = forceSpan;
    }
}
