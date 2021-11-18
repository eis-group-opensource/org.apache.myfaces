/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

import javax.faces.component.behavior.ClientBehaviorHolder;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.StyleAware;
import org.apache.myfaces.component.UniversalProperties;

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
 * @version $Revision: 691871 $ $Date: 2008-09-03 23:32:08 -0500 (03 sep 2008) $
 */
abstract class AbstractDocumentBody extends AbstractDocument 
    implements StyleAware, EventAware, UniversalProperties, ClientBehaviorHolder
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.DocumentBody";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.DocumentBody";

    public AbstractDocumentBody()
    {
        super(DEFAULT_RENDERER_TYPE);
    }
    
    /**
     * HTML: Script to be invoked when the page is loaded
     * 
     */
    @JSFProperty(clientEvent="load")
    public abstract String getOnload();
    
    /**
     * HTML: Script to be invoked when the page is unloaded
     * 
     */
    @JSFProperty(clientEvent="unload")
    public abstract String getOnunload();

    /**
     * 
     */
    @JSFProperty(clientEvent="resize")
    public abstract String getOnresize();
}