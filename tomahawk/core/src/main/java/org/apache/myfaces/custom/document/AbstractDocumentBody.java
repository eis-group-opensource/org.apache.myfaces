/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

import org.apache.myfaces.component.StyleAware;

/**
 * Document to enclose the document body. If not otherwise possible you can use
 * state="start|end" to demarkate the document boundaries
 * 
 * @JSFComponent
 *   name = "t:documentBody"
 *   class = "org.apache.myfaces.custom.document.DocumentBody"
 *   tagClass = "org.apache.myfaces.custom.document.DocumentBodyTag"
 * @since 1.1.7
 * @author Mario Ivankovits (latest modification by $Author: lu4242 $)
 * @version $Revision: 691871 $ $Date: 2008-09-04 07:32:08 +0300 (Thu, 04 Sep 2008) $
 */
abstract class AbstractDocumentBody extends AbstractDocument implements StyleAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.DocumentBody";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.DocumentBody";

    public AbstractDocumentBody()
    {
        super(DEFAULT_RENDERER_TYPE);
    }
    
    /**
     * @JSFProperty
     */
    public abstract String getOnload();

    /**
     * @JSFProperty
     */
    public abstract String getOnunload();

    /**
     * @JSFProperty
     */
    public abstract String getOnresize();

    /**
     * @JSFProperty
     */
    public abstract String getOnkeypress();
    
}