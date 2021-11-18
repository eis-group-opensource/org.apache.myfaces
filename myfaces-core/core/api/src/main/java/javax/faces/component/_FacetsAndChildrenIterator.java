/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
class _FacetsAndChildrenIterator<E>
        implements Iterator
{
    private Iterator<UIComponent> _facetsIterator;
    private Iterator<UIComponent> _childrenIterator;

    _FacetsAndChildrenIterator(Map facetMap, List childrenList)
    {
        _facetsIterator   = facetMap != null ? facetMap.values().iterator() : null;
        _childrenIterator = childrenList != null ? childrenList.iterator() : null;
    }

    public boolean hasNext()
    {
        boolean hasNext = (_facetsIterator != null && _facetsIterator.hasNext()) ||
               (_childrenIterator != null && _childrenIterator.hasNext());
        if (!hasNext)
        {
            _facetsIterator = null;
            _childrenIterator = null;
        }
        return hasNext;
    }

    public Object next()
    {
        if (_facetsIterator != null && _facetsIterator.hasNext())
        {
            return _facetsIterator.next();
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
