/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context;

/**
 * Allows the FacesContextImpl to refer to the external context
 * polymorphically.
 *
 * @author  Stan Silvert (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public interface ReleaseableExternalContext
{
    /**
     * Release resources that the ExternalContext is holding onto.
     */
    public void release();
}
