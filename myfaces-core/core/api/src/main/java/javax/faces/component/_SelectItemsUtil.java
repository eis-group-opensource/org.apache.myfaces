/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.util.Arrays;
import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 * @author Mathias Broekelmann (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
class _SelectItemsUtil
{
    public static interface _ValueConverter
    {
        Object getConvertedValue(FacesContext context, String value);
    }
    
    /**
     * @param context the faces context
     * @param value the value to check
     * @param converter 
     * @param iterator contains instances of SelectItem
     * @return if the value of a selectitem is equal to the given value
     */
    public static boolean matchValue(FacesContext context, Object value,
                    Iterator<SelectItem> selectItemsIter, _ValueConverter converter)
    {
        while (selectItemsIter.hasNext())
        {
            SelectItem item = selectItemsIter.next();
            if (item instanceof SelectItemGroup)
            {
                SelectItemGroup itemgroup = (SelectItemGroup) item;
                SelectItem[] selectItems = itemgroup.getSelectItems();
                if (selectItems != null
                                && selectItems.length > 0
                                && matchValue(context, value, Arrays.asList(
                                                selectItems).iterator(), converter))
                {
                    return true;
                }
            }
            else
            {
                Object itemValue = item.getValue();
                if(converter != null && itemValue instanceof String)
                {
                    itemValue = converter.getConvertedValue(context, (String)itemValue);
                }
                if (value==itemValue || value.equals(itemValue))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
