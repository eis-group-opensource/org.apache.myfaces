/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import java.sql.ResultSet;

/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 472630 $ $Date: 2006-11-08 22:40:03 +0200 (Wed, 08 Nov 2006) $
 */
class _SerializableResultSetDataModel
        extends _SerializableDataModel
{
    private static final long serialVersionUID = -917870953917004531L;
    //private static final Log log = LogFactory.getLog(_SerializableDataModel.class);

    public _SerializableResultSetDataModel(int first, int rows, ResultSet resultSet)
    {
        throw new UnsupportedOperationException("not yet supported"); //TODO
    }
}
