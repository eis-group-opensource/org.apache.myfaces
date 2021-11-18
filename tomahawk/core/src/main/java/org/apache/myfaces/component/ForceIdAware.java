/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

/**
 * Behavioral interface.
 * Components that support forceId generation should implement 
 * this interface to optimize property access.
 *
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public interface ForceIdAware
{

    /**
     * If true, this component will force the use of the specified id when rendering.
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "false"
     *   
     * @return
     */
    public boolean isForceId();
    
    public void setForceId(boolean forceId);
    
    /**
     *  If false, this component will not append a '[n]' suffix 
     *  (where 'n' is the row index) to components that are 
     *  contained within a "list." This value will be true by 
     *  default and the value will be ignored if the value of 
     *  forceId is false (or not specified.)
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "true"
     *   
     * @return
     */
    public boolean isForceIdIndex();
    
    public void setForceIdIndex(boolean forceIdIndex);
    
}
