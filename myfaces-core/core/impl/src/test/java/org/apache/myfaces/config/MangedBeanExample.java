/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config;

/**
 * @author Dennis C. Byrne
 */

import java.util.List;
import java.util.Map;

public class MangedBeanExample {

    private String managedProperty ;
    private List managedList;
    private List writeOnlyList;
    private Map managedMap;
    private Map writeOnlyMap;
    
    public List getManagedList() {
        return managedList;
    }

    public void setManagedList(List managedList) {
        this.managedList = managedList;
    }

    public String getManagedProperty() {
        return managedProperty;
    }

    public void setManagedProperty(String managedProperty) {
        this.managedProperty = managedProperty;
    }

    public Map getManagedMap() {
        return managedMap;
    }

    public void setManagedMap(Map managedMap) {
        this.managedMap = managedMap;
    }

    public void setWriteOnlyList(List writeOnlyList) {
        this.writeOnlyList = writeOnlyList;
    }

    public void setWriteOnlyMap(Map writeOnlyMap) {
        this.writeOnlyMap = writeOnlyMap;
    }

    public Map getHiddenWriteOnlyMap() {
        return writeOnlyMap;
    }

    public List getHiddenWriteOnlyList() {
        return writeOnlyList;
    }

}
