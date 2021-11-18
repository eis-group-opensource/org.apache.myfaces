/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util.el;

import java.util.Map;
import java.util.Set;
import java.util.Collection;

/**
 * @author Sylvain Vieujot (latest modification by $Author: skitching $)
 * @version $Revision: 355303 $ $Date: 2005-12-09 02:36:08 +0100 (Fr, 09 Dez 2005) $
 *
 */
public abstract class GenericMap implements Map {

    /**
     * This method should return the result of the test.
     */
    protected abstract Object getValue(Object key);

    public int size() {
        return 1;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(Object key) {
        return true;
    }

    public boolean containsValue(Object value) {
        return value instanceof Boolean;
    }

    public Object get(Object key) {
        return getValue(key);
    }

    public Object put(Object key, Object value) {
        return null;
    }

    public Object remove(Object key) {
        return null;
    }

    public void putAll(Map m) {
        // NoOp
    }

    public void clear() {
        // NoOp
    }

    public Set keySet() {
        return null;
    }

    public Collection values() {
        return null;
    }

    public Set entrySet() {
        return null;
    }
}


