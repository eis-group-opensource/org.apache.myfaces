/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

import java.io.Serializable;

/**
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 788877 $ $Date: 2009-06-26 16:30:38 -0500 (vie, 26 jun 2009) $
 */
public class AttachedDeltaWrapper implements Serializable
{
    private static final long serialVersionUID = 4732389964367986402L;

    private Object _wrappedStateObject;

    /**
     * @param clazz
     *            null means wrappedStateObject is a List of state objects
     * @param wrappedStateObject
     */
    public AttachedDeltaWrapper(Class<?> clazz, Object wrappedStateObject)
    {
        if (wrappedStateObject != null && !(wrappedStateObject instanceof Serializable))
        {
            throw new IllegalArgumentException("Attached state for Object of type " + clazz + " (Class "
                    + wrappedStateObject.getClass().getName() + ") is not serializable");
        }
        _wrappedStateObject = wrappedStateObject;
    }

    public Object getWrappedStateObject()
    {
        return _wrappedStateObject;
    }
}
