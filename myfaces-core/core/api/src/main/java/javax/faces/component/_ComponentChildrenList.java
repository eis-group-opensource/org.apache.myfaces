/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
class _ComponentChildrenList
        extends AbstractList
        implements Serializable
{
    private static final long serialVersionUID = -6775078929331154224L;
    private UIComponent _component;
    private List<Object> _list = new ArrayList<Object>();

    _ComponentChildrenList(UIComponent component)
    {
        _component = component;
    }

    public Object get(int index)
    {
        return _list.get(index);
    }

    public int size()
    {
        return _list.size();
    }

    public Object set(int index, Object value)
    {
        checkValue(value);
        setNewParent((UIComponent)value);
        UIComponent child = (UIComponent) _list.set(index, value);
        if (child != null) child.setParent(null);
        return child;
    }

    public boolean add(Object value)
    {
        checkValue(value);
        setNewParent((UIComponent)value);
        return _list.add(value);
    }

    public void add(int index, Object value)
    {
        checkValue(value);
        setNewParent((UIComponent)value);
        _list.add(index, value);
    }

    public Object remove(int index)
    {
        UIComponent child = (UIComponent) _list.remove(index);
        if (child != null) child.setParent(null);
        return child;
    }


    private void setNewParent(UIComponent child)
    {
        UIComponent oldParent = child.getParent();
        if (oldParent != null)
        {
            oldParent.getChildren().remove(child);
        }
        child.setParent(_component);
    }

    private void checkValue(Object value)
    {
        if (value == null) throw new NullPointerException("value");
        if (!(value instanceof UIComponent)) throw new ClassCastException("value is not a UIComponent");
    }

}
