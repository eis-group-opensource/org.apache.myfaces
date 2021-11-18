/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.io.Serializable;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
class _AttachedStateWrapper
        implements Serializable
{
    private static final long serialVersionUID = 4948301780259917764L;
    private Class _class;
    private Object _wrappedStateObject;

    /**
     * @param clazz null means wrappedStateObject is a List of state objects
     * @param wrappedStateObject
     */
    public _AttachedStateWrapper(Class clazz, Object wrappedStateObject)
    {
        if (wrappedStateObject != null && !(wrappedStateObject instanceof Serializable))
        {
            throw new IllegalArgumentException("Attached state for Object of type " + clazz + " (Class " + wrappedStateObject.getClass().getName() + ") is not serializable");
        }
        _class = clazz;
        _wrappedStateObject = wrappedStateObject;
    }

    public Class getClazz()
    {
        return _class;
    }

    public Object getWrappedStateObject()
    {
        return _wrappedStateObject;
    }
}
