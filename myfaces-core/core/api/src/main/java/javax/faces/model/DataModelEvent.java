/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.model;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class DataModelEvent extends java.util.EventObject
{
    private static final long serialVersionUID = 1823115573192262656L;
    // FIELDS
    private int _index;
    private Object _data;


    public DataModelEvent(DataModel _model, int _index, Object _data)
    {
        super(_model);
        this._index = _index;
        this._data = _data;
    }


    // METHODS
    public DataModel getDataModel()
    {
        return (DataModel) getSource();
    }

    public Object getRowData()
    {
        return _data;
    }

    public int getRowIndex()
    {
        return _index;
    }

}