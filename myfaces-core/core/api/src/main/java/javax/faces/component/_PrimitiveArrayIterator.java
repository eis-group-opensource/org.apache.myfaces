/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * This utility iterator uses reflection to iterate over primitive arrays.
 */
class _PrimitiveArrayIterator implements Iterator
{
    public _PrimitiveArrayIterator(Object primitiveArray)
    {
        if (primitiveArray == null ||
            !primitiveArray.getClass().isArray())
        {
            throw new IllegalArgumentException("Requires a primitive array");
        }

        _primitiveArray = primitiveArray;
        _size = Array.getLength(primitiveArray);
    }

    public boolean hasNext()
    {
        return (_position < _size);
    }

    public Object next()
    {
        return Array.get(_primitiveArray, _position++);
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    private Object _primitiveArray;
    private int    _size;
    private int    _position;
}
