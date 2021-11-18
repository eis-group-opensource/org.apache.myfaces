/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.io.Serializable;
import java.util.List;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
class _AttachedListStateWrapper
        implements Serializable
{
    private static final long serialVersionUID = -3958718149793179776L;
    private List<Object> _wrappedStateList;

    public _AttachedListStateWrapper(List<Object> wrappedStateList)
    {
        _wrappedStateList = wrappedStateList;
    }

    public List<Object> getWrappedStateList()
    {
        return _wrappedStateList;
    }
}
