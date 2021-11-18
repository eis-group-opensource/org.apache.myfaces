/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

public class ResourcePosition
{
    
    private final int _pos;

    protected ResourcePosition(int pos)
    {
        _pos = pos;
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (obj instanceof ResourcePosition)
        {
            return ((ResourcePosition) obj)._pos == _pos;
        }
        return false;
    }

    public int hashCode()
    {
        return _pos;
    }
}