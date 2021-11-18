/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;

import org.apache.myfaces.shared.renderkit.RendererUtils;

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectItemsIterator implements Iterator
{
    private final Iterator _childs;
    private Iterator _nestedItems;
    private Object _nextItem;
    private String _collectionLabel;
    private UISelectItems _currentUISelectItems;

    public SelectItemsIterator(UIComponent selectItemsParent)
    {
        _childs = selectItemsParent.getChildren().iterator();
    }

    public boolean hasNext()
    {
        if(_nextItem != null)
        {
            return true;
        }
        if(_nestedItems != null)
        {
            if(_nestedItems.hasNext())
            {
                return true;
            }
            _nestedItems = null;
        }            
        if (_childs.hasNext())
        {
            UIComponent child = (UIComponent) _childs.next();
            // When there is other components nested that does
            // not extends from UISelectItem or UISelectItems
            // the behavior for this iterator is just skip this
            // element(s) until an element that extends from these
            // classes are found. If there is no more elements
            // that conform this condition, just return false.
            while (!(child instanceof UISelectItem)
                    && !(child instanceof UISelectItems))
            {
                //Try to skip it
                if (_childs.hasNext())
                {
                    //Skip and do the same check
                    child = (UIComponent) _childs.next();
                }
                else
                {
                    //End loop, so the final result is return false,
                    //since there are no more components to iterate.
                    return false;
                }
            }
            if (child instanceof UISelectItem)
            {
                UISelectItem uiSelectItem = (UISelectItem) child;
                Object item = uiSelectItem.getValue();
                if (item == null)
                {
                    Object itemValue = ((UISelectItem) child).getItemValue();
                    String label = ((UISelectItem) child).getItemLabel();
                    String description = ((UISelectItem) child)
                                    .getItemDescription();
                    boolean disabled = ((UISelectItem) child).isItemDisabled();
                    boolean escaped = ((UISelectItem) child).isItemEscaped();
                    if (label == null)
                    {
                        label = itemValue.toString();
                    }
                    item = new SelectItem(itemValue, label, description,
                                    disabled, escaped);
                }
                else if (!(item instanceof SelectItem))
                {
                    ValueBinding binding = ((UISelectItem) child)
                                    .getValueBinding("value");
                    throw new IllegalArgumentException(
                                    "Value binding '"
                                    + (binding == null ? null : binding.getExpressionString())
                                    + "' of UISelectItem : "
                                    + RendererUtils.getPathToComponent(child)
                                    + " does not reference an Object of type SelectItem");
                }
                _nextItem = item;
                return true;
            }
            else if (child instanceof UISelectItems)
            {
                _currentUISelectItems = ((UISelectItems) child);
                Object value = _currentUISelectItems.getValue();

                if (value instanceof SelectItem)
                {
                    _nextItem = value;
                    return true;
                }
                else if (value instanceof SelectItem[])
                {
                    _nestedItems = Arrays.asList((SelectItem[]) value)
                                    .iterator();
                    _collectionLabel = "Array";
                    return hasNext();
                }
                else if (value instanceof Collection)
                {
                    _nestedItems = ((Collection)value).iterator();
                    _collectionLabel = "Collection";
                    return hasNext();
                }
                else if (value instanceof Map)
                {
                    Map map = ((Map) value);
                    Collection items = new ArrayList(map.size()); 
                    for (Iterator it = map.entrySet().iterator(); it
                                    .hasNext();)
                    {
                        Map.Entry entry = (Map.Entry) it.next();
                        items.add(new SelectItem(entry.getValue(), entry
                                        .getKey().toString()));
                    }
                    _nestedItems = items.iterator();
                    _collectionLabel = "Map";
                    return hasNext();
                }
                else
                {
                    ValueBinding binding = _currentUISelectItems.getValueBinding("value");

                    throw new IllegalArgumentException(
                        "Value binding '"
                        + (binding == null ? null : binding
                                        .getExpressionString())
                        + "'of UISelectItems with component-path "
                        + RendererUtils.getPathToComponent(child)
                        + " does not reference an Object of type SelectItem, SelectItem[], Collection or Map but of type : "
                        + ((value == null) ? null : value
                                        .getClass()
                                        .getName()));
                }
            }
        }
        return false;
    }

    public Object next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException();
        }
        if(_nextItem != null)
        {
            Object value = _nextItem;
            _nextItem = null;
            return value;
        }        
        if (_nestedItems != null)
        {
            Object item = _nestedItems.next();
            if (!(item instanceof SelectItem))
            {
                ValueBinding binding = _currentUISelectItems
                                .getValueBinding("value");
                throw new IllegalArgumentException(
                _collectionLabel + " referenced by UISelectItems with binding '"
                + binding.getExpressionString()
                + "' and Component-Path : " + RendererUtils.getPathToComponent(_currentUISelectItems)
                + " does not contain Objects of type SelectItem");
            }
            return item;
        }
        throw new NoSuchElementException();
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
