/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Ernst Fastl
 */
public class TableContext
{
    private List _rowInfos;

    public List getRowInfos()
    {
        if ( _rowInfos == null )
        {
            _rowInfos = new ArrayList();
        }
        return _rowInfos;
    }
}
