/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import java.util.Collections;

/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 472630 $ $Date: 2006-11-08 22:40:03 +0200 (Wed, 08 Nov 2006) $
 */
class _SerializableScalarDataModel
        extends _SerializableDataModel
{
    private static final long serialVersionUID = 8344668178297539400L;
    //private static final Log log = LogFactory.getLog(_SerializableDataModel.class);

    public _SerializableScalarDataModel(int first, int rows, Object scalar)
    {
        _first = first;
        _rows = rows;
        _rowCount = 1;
        if (_rows <= 0)
        {
            _rows = _rowCount - first;
        }
        _list = Collections.singletonList(scalar);
    }
}
