/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import java.util.ArrayList;
import java.util.SortedMap;

/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 472630 $ $Date: 2006-11-08 22:40:03 +0200 (Wed, 08 Nov 2006) $
 */
class _SerializableResultDataModel
        extends _SerializableDataModel
{
    private static final long serialVersionUID = -1935350044609953509L;
    //private static final Log log = LogFactory.getLog(_SerializableDataModel.class);

    public _SerializableResultDataModel(int first, int rows, javax.servlet.jsp.jstl.sql.Result result)
    {
        _first = first;
        _rows = rows;
        _rowCount = result.getRowCount();
        if (_rows <= 0)
        {
            _rows = _rowCount - first;
        }
        _list = new ArrayList(_rows);
        SortedMap[] resultRows = result.getRows();
        for (int i = 0; i < _rowCount; i++)
        {
            _list.add(resultRows[_first + i]);
        }
    }

}
