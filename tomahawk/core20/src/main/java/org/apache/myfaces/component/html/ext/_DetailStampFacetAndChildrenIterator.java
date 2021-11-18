/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;

/**
 * @author Leonardo Uribe
 * @since 1.1.10
 */
class _DetailStampFacetAndChildrenIterator implements Iterator<UIComponent>
{
    private UIComponent _detailStamp;
    private Iterator<UIComponent> _childrenIterator;

    _DetailStampFacetAndChildrenIterator(UIComponent detailStamp, List<UIComponent> childrenList)
    {
        _detailStamp = detailStamp;
        _childrenIterator = childrenList != null ? childrenList.iterator() : null;
    }

    public boolean hasNext()
    {
        boolean hasNext = (_detailStamp != null)
                || (_childrenIterator != null && _childrenIterator.hasNext());
        if (!hasNext)
        {
            _detailStamp = null;
            _childrenIterator = null;
        }
        
        return hasNext;
    }

    public UIComponent next()
    {
        if (_detailStamp != null)
        {
            UIComponent detailStamp = _detailStamp;
            _detailStamp = null;
            return detailStamp;
        }
        else if (_childrenIterator != null && _childrenIterator.hasNext())
        {
            return _childrenIterator.next();
        }
        else
        {
            throw new NoSuchElementException();
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
    }
}
