/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

/**
 * @author Ernst Fastl
 */
public class ColumnInfo
{
    private boolean _rendered = true;
    private int _rowSpan=0;
    private String _style;
    private String _styleClass;

    public boolean isRendered()
    {
        return _rendered;
    }

    public void setRendered(boolean rendered)
    {
        this._rendered = rendered;
    }

    public int getRowSpan()
    {
        return _rowSpan;
    }

    public void setRowSpan(int rowSpan)
    {
        this._rowSpan = rowSpan;
    }

    public String getStyle()
    {
        return _style;
    }

    public void setStyle(String style)
    {
        this._style = style;
    }

    public String getStyleClass()
    {
        return _styleClass;
    }

    public void setStyleClass(String styleClass)
    {
        this._styleClass = styleClass;
    }
}
