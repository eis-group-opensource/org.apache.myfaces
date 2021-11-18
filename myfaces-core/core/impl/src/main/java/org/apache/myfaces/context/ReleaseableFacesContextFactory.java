/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context;

/**
 *
 * @author  Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 799765 $ $Date: 2009-07-31 17:55:49 -0500 (vie, 31 jul 2009) $
 */
public interface ReleaseableFacesContextFactory
{
    /**
     * Release resources that the ExternalContext is holding onto.
     */
    public void release();
}
