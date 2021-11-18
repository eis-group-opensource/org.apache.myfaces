/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.io.Serializable;
import java.util.*;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
class _ComponentFacetMap<V extends UIComponent>
        implements Map<String, V>, Serializable
{
    private static final long serialVersionUID = -3456937594422167629L;
    private UIComponent _component;
    private Map<String, V> _map = new HashMap<String, V>();

    _ComponentFacetMap(UIComponent component)
    {
        _component = component;
    }

    public int size()
    {
        return _map.size();
    }

    public void clear()
    {
        _map.clear();
    }

    public boolean isEmpty()
    {
        return _map.isEmpty();
    }

    public boolean containsKey(Object key)
    {
        checkKey(key);
        return _map.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        checkValue(value);
        return _map.containsValue(value);
    }

    public Collection<V> values()
    {
        return _map.values();
    }

    public void putAll(Map<? extends String, ? extends V> t)
    {
        for (Iterator it = t.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry<String, V> entry = (Entry<String, V>)it.next();
            put(entry.getKey(), entry.getValue());
        }
    }

    public Set<Entry<String, V>> entrySet()
    {
        return _map.entrySet();
    }

    public Set<String> keySet()
    {
        return _map.keySet();
    }

    public V get(Object key)
    {
        checkKey(key);
        return _map.get(key);
    }

    public V remove(Object key)
    {
        checkKey(key);
        V facet = _map.remove(key);
        if (facet != null) facet.setParent(null);
        return facet;
    }

    public V put(String key, V value)
    {
        checkKey(key);
        checkValue(value);
        setNewParent(key, value);
        return _map.put(key, value);
    }


    private void setNewParent(String facetName, UIComponent facet)
    {
        UIComponent oldParent = facet.getParent();
        if (oldParent != null)
        {
            oldParent.getFacets().remove(facetName);
        }
        facet.setParent(_component);
    }

    private void checkKey(Object key)
    {
        if (key == null) throw new NullPointerException("key");
        if (!(key instanceof String)) throw new ClassCastException("key is not a String");
    }

    private void checkValue(Object value)
    {
        if (value == null) throw new NullPointerException("value");
        if (!(value instanceof UIComponent)) throw new ClassCastException("value is not a UIComponent");
    }

}
