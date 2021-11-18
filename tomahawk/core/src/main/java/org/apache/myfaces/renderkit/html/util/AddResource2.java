/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

/**
 * This call work as a base class to implement instances of AddResource.
 * It includes new methods that could be used, to prevent modifications
 * on AddResource interface and break existing applications.
 * 
 * @author Leonardo Uribe (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-03 16:58:05 -0500 (jue, 03 jul 2008) $
 */
public abstract class AddResource2 implements AddResource
{

    public void responseStarted(Object context, Object request)
    {
        //Use the old method by default
        responseStarted();
    }
}
