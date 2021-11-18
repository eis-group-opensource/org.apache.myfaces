/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.model;

import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

//import javax.servlet.jsp.
/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @version $Revision: 1154078 $ $Date: 2011-08-05 06:03:06 +0300 (Fri, 05 Aug 2011) $
 */
public class ResultDataModel extends DataModel
{
    // FIELDS
    private int _rowIndex = -1;
    private Result _data;

    // CONSTRUCTORS
    public ResultDataModel()
    {
        super();
    }

    public ResultDataModel(Result result)
    {
        if (result == null) throw new NullPointerException("result");
        setWrappedData(result);
    }

    // METHODS
    public int getRowCount()
    {
        if (getRows() == null)
        {
            return -1;
        }
        return getRows().length;
    }

    public Object getRowData()
    {
        if (getRows() == null)
        {
            return null;
        }
        if (!isRowAvailable())
        {
            throw new IllegalArgumentException("row is unavailable");
        }
        return getRows()[_rowIndex];
    }

    public int getRowIndex()
    {
        return _rowIndex;
    }

    public Object getWrappedData()
    {
        return _data;
    }

    public boolean isRowAvailable()
    {
        if (getRows() == null)
        {
            return false;
        }
        return _rowIndex >= 0 && _rowIndex < getRows().length;
    }

    public void setRowIndex(int rowIndex)
    {
        if (rowIndex < -1)
        {
            throw new IllegalArgumentException("illegal rowIndex " + rowIndex);
        }
        int oldRowIndex = _rowIndex;
        _rowIndex = rowIndex;
        if (getRows() != null && oldRowIndex != _rowIndex)
        {
            Object data = isRowAvailable() ? getRowData() : null;
            DataModelEvent event = new DataModelEvent(this, _rowIndex, data);
            DataModelListener[] listeners = getDataModelListeners();
            for (int i = 0; i < listeners.length; i++)
            {
                listeners[i].rowSelected(event);
            }
        }
    }

    private SortedMap[] getRows()
    {
        if(_data==null)
            return null;

        return _data.getRows();
    }

    public void setWrappedData(Object data)
    {
        if (data == null)
        {
            setRowIndex(-1);
            _data = null;
        }
        else
        {
            _data = ((Result)data);
            _rowIndex = -1;
            setRowIndex(0);
        }
    }

}
