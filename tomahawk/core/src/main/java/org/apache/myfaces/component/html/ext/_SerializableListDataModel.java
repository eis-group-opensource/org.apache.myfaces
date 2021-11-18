/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.component.StateHolder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 472630 $ $Date: 2006-11-08 22:40:03 +0200 (Wed, 08 Nov 2006) $
 * %Log$
 */
class _SerializableListDataModel
        extends _SerializableDataModel
{
    private static final long serialVersionUID = 2579712878688635627L;
    //private static final Log log = LogFactory.getLog(_SerializableDataModel.class);

    public _SerializableListDataModel(int first, int rows, List list)
    {
        _first = first;
        _rows = rows;
        _rowCount = list.size();
        if (_rows <= 0)
        {
            _rows = _rowCount - first;
        }

        if (_rows == _rowCount)
        {
            //whole list must be saved
            if (list instanceof Serializable || list instanceof StateHolder)
            {
                _list = list;
            }
            else
            {
                //copy list
                _list = new ArrayList(list);
            }
        }
        else
        {
            int size = _rows > 0 && _rows < _rowCount ? _rows : _rowCount;
            _list = new ArrayList(size);
            if (size > _rowCount - _first)
            {
                size = _rowCount - _first;
            }
            for (int i = 0; i < size; i++)
            {
                _list.add(list.get(_first + i));
            }
        }
    }
}
