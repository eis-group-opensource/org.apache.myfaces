/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree;

import javax.faces.component.html.HtmlCommandLink;


/**
 * HTML image link.
 *
 * @JSFComponent
 *   class = "org.apache.myfaces.custom.tree.HtmlTreeImageCommandLink"
 * @since 1.1.7
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlTreeImageCommandLink
        extends HtmlCommandLink
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTreeImageCommandLink";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.HtmlTree";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlTreeImageCommandLink";

    /**
     * @JSFProperty
     */
    public abstract String getImage();
    
}
