/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.context.FacesContext;

import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * Extends standard graphicImage. Unless otherwise specified, 
 * all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:graphicImage"
 *   class = "org.apache.myfaces.component.html.ext.HtmlGraphicImage"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlGraphicImageTag"
 * @since 1.1.7
 * @author Bruno Aranda
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlGraphicImage
        extends javax.faces.component.html.HtmlGraphicImage
        implements UserRoleAware, ForceIdAware, AlignProperty
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlGraphicImage";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Image";
        
    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }
    
    /**
     *  HTML: Specifies the width of the border of this element, in pixels. Deprecated in HTML 4.01.
     * 
     * @JSFProperty 
     */
    public abstract String getBorder();
    
    /**
     * HTML: The amount of white space to be inserted to the left and right of this element, in undefined units. Deprecated in HTML 4.01.
     * 
     * @JSFProperty 
     */
    public abstract String getHspace();
    
    /**
     * HTML: The amount of white space to be inserted above and below this element, in undefined units. Deprecated in HTML 4.01.
     * 
     * @JSFProperty 
     */
    public abstract String getVspace();

}
