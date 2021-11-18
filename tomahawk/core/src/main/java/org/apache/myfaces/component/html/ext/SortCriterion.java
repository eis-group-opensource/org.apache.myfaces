/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import java.io.Serializable;

/**
 *
 */
public final class SortCriterion implements Serializable {
    
    private final String _property;
    private final boolean _sortOrder;
    
    public SortCriterion(String property, boolean isAscending) {
        if (property == null)
            throw new NullPointerException("property is null");
        
        _property = property;
        _sortOrder = isAscending;
    }
    
    /**
     * Gets the direction in which the property of this class is sorted.
     * @return true if the property identified by this class is sorted in
     * ascending order.
     */
    public boolean isAscending() {
        return _sortOrder;
    }
    
    /**
     * Gets the property that is identified by this class. This is the property
     * that must be sorted by. If a collection of beans is being sorted, bean rules
     * will be used to find a suitable getter method that matches this property.
     * The value returned by the getter method will be sorted on.
     * If a collection of Maps is being sorted, this property will be used
     * as the key into each Map to get at the value being sorted.
     */
    public String getProperty() {
        return _property;
    }
    
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (obj instanceof SortCriterion) {
            SortCriterion that = (SortCriterion) obj;
            return (this.getProperty().equals(that.getProperty())) &&
                    (this.isAscending() == that.isAscending());
        }
        
        return false;
    }
    
    public int hashCode() {
        int hc = getProperty().hashCode();
        return isAscending() ? hc : -hc;
    }       
}