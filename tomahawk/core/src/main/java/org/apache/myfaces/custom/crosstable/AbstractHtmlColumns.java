/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.crosstable;

import org.apache.myfaces.custom.column.HtmlColumn;


/**
 * The tag allows dynamic columns in a datatable. 
 * 
 * The UIColumns component is used below a t:datatable to create a 
 * dynamic count of columns. It is used like a UIData component 
 * which iterates through a datamodel to create the columns. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:columns" 
 *   class = "org.apache.myfaces.custom.crosstable.HtmlColumns"
 *   tagClass = "org.apache.myfaces.custom.crosstable.HtmlColumnsTag"
 *   implements = "org.apache.myfaces.custom.column.HtmlColumn"
 *   defaultRendererType = ""
 * @since 1.1.7
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractHtmlColumns extends UIColumns implements HtmlColumn {
    
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlColumns";

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

    // the following are not implemented, but are in the HtmlColumn interface
    public String getColspan() {return null;}
    public void setColspan(String colspan) {}
    public String getHeadercolspan() {return null;}
    public void setHeadercolspan(String headercolspan) {}
    public String getFootercolspan() {return null;}
    public void setFootercolspan(String footercolspan) {}

    public String getColumnId() {
        return null;
    }

    public void setColumnId(String columnId) {
    }

}
