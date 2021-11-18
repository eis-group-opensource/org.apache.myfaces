/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.html.HTML;

import javax.faces.component.UIComponent;


/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @author Martin Marinschek
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlOutputLabelELTagBase} instead
 */
public abstract class HtmlOutputLabelTagBase
    extends org.apache.myfaces.shared.taglib.html.HtmlComponentTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML label attributes
    private String _accesskey;
    private String _onblur;
    private String _onfocus;

    // UIOutput attributes
    // value and converterId --> already implemented in UIComponentTagBase

    //HTMLOutputLabel attributes
    private String _for;

    public void release() {
        super.release();
        _accesskey=null;
        _onblur=null;
        _onfocus=null;
        _for=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HTML.ACCESSKEY_ATTR, _accesskey);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, HTML.ONFOCUS_ATTR, _onfocus);

        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.FOR_ATTR, _for);
   }

    public void setAccesskey(String accesskey)
    {
        _accesskey = accesskey;
    }

    public void setOnblur(String onblur)
    {
        _onblur = onblur;
    }

    public void setOnfocus(String onfocus)
    {
        _onfocus = onfocus;
    }

    public void setFor(String aFor)
    {
        _for = aFor;
    }
}
