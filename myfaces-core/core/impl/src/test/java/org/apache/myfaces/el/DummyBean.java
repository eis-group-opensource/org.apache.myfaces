/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import java.util.Map;

public class DummyBean {
    private Map map;
    
    private int integerPrimitive = 0;

    public DummyBean(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    
    public int getIntegerPrimitive(){
        return integerPrimitive;
    }
    public void setIntegerPrimitive(int integerPrimitive){
        this.integerPrimitive = integerPrimitive;
    }
}

