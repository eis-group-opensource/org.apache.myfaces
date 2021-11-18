/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.column;

import javax.faces.component.UIColumn;
import javax.faces.component.behavior.ClientBehaviorHolder;

/**
 * A tag that extend h:column to provide HTML passthrough attributes. 
 * Tag t:column can be used instead of h:column in a t:datatable. 
 * It provides HTML passthrough attributes for header (th), footer 
 * (td) and row cells (td). Unless otherwise specified, all 
 * attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:column"
 *   class = "org.apache.myfaces.custom.column.HtmlSimpleColumn"
 *   tagClass = "org.apache.myfaces.custom.column.HtmlColumnTag"
 * @since 1.1.7
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public abstract class AbstractHtmlSimpleColumn extends UIColumn implements HtmlColumn,
    ClientBehaviorHolder
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlColumn";

    
    public boolean isGroupByValueSet()
    {
        return getGroupByValue() != null;
    }
    
    /**
     * This attribute tells the datatable to group by data in this column
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isGroupBy();


    /**
     *  Optional - Allows you configure where to get the value to 
     *  check for the group change condition. Default: all children 
     *  of the column cell will be checked
     * 
     * @JSFProperty
     */
    public abstract Object getGroupByValue();

    /**
     * This attribute tells the datatable to make this column the 
     * default sorted, when sortable=true
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isDefaultSorted();

    /**
     * This attribute makes this column automaticaly sortable 
     * by a row object's property
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isSortable();

    /**
     *  This attribute tells row object's property by which 
     *  sorting will be performed on this column
     * 
     * @JSFProperty
     */    
    public abstract String getSortPropertyName();

}
