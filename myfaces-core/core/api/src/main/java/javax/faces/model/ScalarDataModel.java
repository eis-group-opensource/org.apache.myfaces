/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.model;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @version $Revision: 1154078 $ $Date: 2011-08-05 06:03:06 +0300 (Fri, 05 Aug 2011) $
 */
public class ScalarDataModel extends DataModel
{
    // FIELDS
    private int _rowIndex = -1;
    private Object _data;

    // CONSTRUCTORS
    public ScalarDataModel()
    {
        super();
    }

    public ScalarDataModel(Object scalar)
    {
        setWrappedData(scalar);
    }

    // METHODS
    public int getRowCount()
    {
        return _data != null ? 1 : -1;
    }

    public Object getRowData()
    {
        if (_data == null)
        {
            return null;
        }
        if (!isRowAvailable())
        {
            throw new IllegalArgumentException("row is unavailable");
        }
        return _data;
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
        if (_data == null)
        {
            return false;
        }
        return _rowIndex == 0;
    }

    public void setRowIndex(int rowIndex)
    {
        if (rowIndex < -1)
        {
            throw new IllegalArgumentException("illegal rowIndex " + rowIndex);
        }
        int oldRowIndex = _rowIndex;
        _rowIndex = rowIndex;
        if (_data != null && oldRowIndex != _rowIndex)
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
    
    public void setWrappedData(Object data)
    {
        if (data == null)
        {
            setRowIndex(-1);
            _data = null;
        }
        else
        {
            _data = data;
            _rowIndex = -1;
            setRowIndex(0);
        }
    }

}
