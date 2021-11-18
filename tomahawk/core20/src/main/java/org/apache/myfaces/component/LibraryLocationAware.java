/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public interface LibraryLocationAware
{
    public static final String STYLE_LIBRARY_ATTR = "styleLibrary";
    public static final String JAVASCRIPT_LIBRARY_ATTR = "javascriptLibrary";
    public static final String IMAGE_LIBRARY_ATTR = "imageLibrary";
    
    /**
     *  
     */
    @JSFProperty
    public String getJavascriptLibrary();
    
    /**
     *  
     */
    @JSFProperty
    public String getImageLibrary();
    
    /**
     * 
     */
    @JSFProperty
    public String getStyleLibrary();

}
