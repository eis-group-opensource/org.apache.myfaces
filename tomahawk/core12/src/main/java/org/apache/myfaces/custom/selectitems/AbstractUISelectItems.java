/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectitems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 * An extended version of the standard UISelectItems. Populates the 
 * SelectItem collection from the given value automatically using 
 * the itemLabel and itemValue attributes. By using the component 
 * there is no need to manually create a SelectItem collection 
 * because component automatically populates SelectItem objects 
 * from types like Collection, Map and etc..
 * 
 * @JSFComponent
 *   name = "t:selectItems"
 *   class = "org.apache.myfaces.custom.selectitems.UISelectItems"
 *   tagClass = "org.apache.myfaces.custom.selectitems.SelectItemsTag"
 * @since 1.1.7
 * @author cagatay (latest modification by $Author: lu4242 $)
 * @version $Revision: 891039 $ $Date: 2009-12-16 00:29:01 +0200 (Wed, 16 Dec 2009) $
 */
public abstract class AbstractUISelectItems extends javax.faces.component.UISelectItems {
    
    public static final String COMPONENT_TYPE = "org.apache.myfaces.UISelectItems";
    
    /**
     * name of the iterator
     * 
     * @JSFProperty
     */
    public abstract String getVar();
    
    /**
     * name of the selectitem
     * 
     * @JSFProperty
     */
    public abstract Object getItemLabel();

    /**
     * value of the selectitem
     * 
     * @JSFProperty
     */
    public abstract Object getItemValue();
    
    /**
     * indicate if the label should be escaped of the selectitem
     * 
     * @since 1.1.9
     * @JSFProperty
     */
    public abstract Object getItemLabelEscaped();
    
    /**
     * name of the selectitem
     * 
     * @since 1.1.9
     * @JSFProperty
     */
    public abstract Object getItemDescription();
    
    /**
     * disabled state of the selectitem
     * 
     * @since 1.1.9
     * @JSFProperty
     */
    public abstract Object getItemDisabled();
    
    /**
     * Only applies when value points to a map. Use the Entry instance instead
     * the value for resolve EL Expressions
     * 
     * @since 1.1.10
     * @JSFProperty
     *    defaultValue = "false"
     */
    public abstract boolean isUseEntryAsItem();
    
    public Object getValue() {
        Object value = super.getValue();
        String var = getVar(); 
        if (var != null && var.length() > 0)
        {
            return createSelectItems(value);
        }
        else
        {
            return value;
        }
    }

    private SelectItem[] createSelectItems(Object value) {
        List items = new ArrayList();
        
        if (value instanceof SelectItem[]) {
            return (SelectItem[]) value;
        }
        else if (value instanceof Collection) {
            Collection collection = (Collection) value;
            for (Iterator iter = collection.iterator(); iter.hasNext();) {
                Object currentItem = (Object) iter.next();
                if (currentItem instanceof SelectItemGroup) {
                    SelectItemGroup itemGroup = (SelectItemGroup) currentItem;        
                    SelectItem[] itemsFromGroup = itemGroup.getSelectItems();
                    for (int i = 0; i < itemsFromGroup.length; i++) {
                        items.add(itemsFromGroup[i]);
                    }
                }
                else {
                    putIteratorToRequestParam(currentItem);
                    SelectItem selectItem = createSelectItem();
                    removeIteratorFromRequestParam();
                    items.add(selectItem);
                }
            }
        }
        else if (value instanceof Map) {
            Map map = (Map) value;
            if (isUseEntryAsItem())
            {
                for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
                    Entry currentItem = (Entry) iter.next();
                    putIteratorToRequestParam(currentItem);
                    SelectItem selectItem = createSelectItem();
                    removeIteratorFromRequestParam();
                    items.add(selectItem);
                }
            }
            else
            {
                for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
                    Entry currentItem = (Entry) iter.next();
                    putIteratorToRequestParam(currentItem.getValue());
                    SelectItem selectItem = createSelectItem();
                    removeIteratorFromRequestParam();
                    items.add(selectItem);
                }
            }
        }
        
        return (SelectItem[]) items.toArray(new SelectItem[0]);
    }

    private SelectItem createSelectItem() {
        SelectItem item = null;
        Object value = getItemValue();
        String label = getItemLabel() != null ? getItemLabel().toString() : null;
        String description = getItemDescription() != null ? getItemDescription().toString() : null;
        Boolean disabled = (Boolean) (getItemDisabled() != null ? getItemDisabled() : Boolean.FALSE);
        Boolean escaped = (Boolean) (getItemLabelEscaped() != null ? getItemLabelEscaped() : Boolean.TRUE);
        
        if(label != null)
            item = new SelectItem(value, label, description, disabled, escaped);
        else
            item = new SelectItem(value, value == null ? null : value.toString(), description, disabled, escaped);
        
        return item;
    }
    
    private void putIteratorToRequestParam(Object object) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(getVar(), object);
    }
    
    private void removeIteratorFromRequestParam() {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove(getVar());
    }
    
}

