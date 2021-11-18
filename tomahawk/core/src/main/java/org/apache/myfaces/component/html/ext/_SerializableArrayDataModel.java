/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import java.util.ArrayList;

/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 472630 $ $Date: 2006-11-08 22:40:03 +0200 (Wed, 08 Nov 2006) $
 */
class _SerializableArrayDataModel
        extends _SerializableDataModel
{
    private static final long serialVersionUID = -4785289115095508976L;
    //private static final Log log = LogFactory.getLog(_SerializableDataModel.class);

    public _SerializableArrayDataModel(int first, int rows, Object[] array)
    {
        _first = first;
        _rows = rows;
        _rowCount = array.length;
        if (_rows <= 0)
        {
            _rows = _rowCount - first;
        }
        _list = new ArrayList(_rows);
        for (int i = 0; i < _rows && _first + i < _rowCount; i++)
        {
            _list.add(array[_first + i]);
        }
    }
}
