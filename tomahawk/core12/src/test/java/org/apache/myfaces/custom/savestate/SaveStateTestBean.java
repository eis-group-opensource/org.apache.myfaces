/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.savestate;

import java.io.Serializable;
import java.util.LinkedList;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

/**
 * @author cagatay
 * Test bean for UISaveStateTest
 */
public class SaveStateTestBean implements Serializable, StateHolder {

    private LinkedList linkedList;

    private String name;

    public LinkedList getLinkedList() {
        if(linkedList == null)
            linkedList = new LinkedList();
        return linkedList;
    }

    public void setLinkedList(LinkedList linkedList) {
        this.linkedList = linkedList;
    }

    public boolean isTransient() {
        return false;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[])state;
        name = (String)values[0];
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[1];
        values[0] = name;
        return values;
    }

    public void setTransient(boolean newTransientValue) {
        
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
