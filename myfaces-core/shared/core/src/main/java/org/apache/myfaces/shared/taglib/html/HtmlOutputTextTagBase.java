/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.JSFAttr;

import javax.faces.component.UIComponent;


/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlOutputTextELTagBase} instead
 */
public abstract class HtmlOutputTextTagBase
    extends org.apache.myfaces.shared.taglib.html.HtmlComponentTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // UIOutput attributes
    // value and converterId --> already implemented in UIComponentTagBase

    // HtmlOutputText attributes
    private String _escape;

    public void release() {
        super.release();
        _escape=null;
    }


    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setBooleanProperty(component, JSFAttr.ESCAPE_ATTR, _escape);

        //TODO: idea: set transient and override setValue method in UIOutput so that
        //transient is set to false when a new value is set
    }

    public void setEscape(String escape)
    {
        _escape = escape;
    }
}
