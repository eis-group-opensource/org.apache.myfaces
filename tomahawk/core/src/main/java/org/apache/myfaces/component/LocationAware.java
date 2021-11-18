/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

/**
 * 
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public interface LocationAware
{

    /**
     *  An alternate location to find javascript resources. 
     *  If no values is specified, javascript will be loaded 
     *  from the resources directory using AddResource and 
     *  ExtensionsFilter.
     * 
     * @JSFProperty 
     */
    public String getJavascriptLocation();
    
    /**
     * An alternate location to find image resources. If no 
     * values is specified, images will be loaded from the 
     * resources directory using AddResource and ExtensionsFilter.
     * 
     * @JSFProperty 
     */
    public String getImageLocation();
    
    /**
     * An alternate location to find stylesheet resources. If no 
     * values is specified, stylesheets will be loaded from the 
     * resources directory using AddResource and ExtensionsFilter.
     * 
     * @JSFProperty 
     */
    public String getStyleLocation();
    
}
