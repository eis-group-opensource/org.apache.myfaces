/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

import javax.faces.context.FacesContext;


/**
 * Extended version of {@link javax.faces.component.html.HtmlInputHidden} 
 * that provides additional MyFaces functionality.
 *
 * @JSFComponent
 *   name = "t:inputHidden"
 *   class = "org.apache.myfaces.component.html.ext.HtmlInputHidden"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlInputHiddenTag"
 * @since 1.1.7
 * @author Sean Schofield
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlInputHidden
    extends javax.faces.component.html.HtmlInputHidden
    implements ForceIdAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlInputHidden";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Hidden";

    public AbstractHtmlInputHidden()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }
    
    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }
}

