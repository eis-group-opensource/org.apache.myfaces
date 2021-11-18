/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Ernst Fastl
 */
public class RowInfo
{
    private List _columnInfos;

    public List getColumnInfos()
    {
        if ( _columnInfos == null )
        {
            _columnInfos = new ArrayList();
        }
        return _columnInfos;
    }
}
