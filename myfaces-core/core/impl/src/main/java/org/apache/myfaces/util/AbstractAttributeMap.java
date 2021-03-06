/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Helper Map implementation for use with different Attribute Maps.
 * 
 * @author Anton Koinov (latest modification by $Author: lu4242 $)
 * @version $Revision: 1000662 $ $Date: 2010-09-24 02:11:42 +0300 (Fri, 24 Sep 2010) $
 */
public abstract class AbstractAttributeMap<V> extends AbstractMap<String, V>
{
    private Set<String> _keySet;
    private Collection<V> _values;
    private Set<Entry<String, V>> _entrySet;

    @Override
    public void clear()
    {
        final List<String> names = new ArrayList<String>();
        for (final Enumeration<String> e = getAttributeNames(); e.hasMoreElements();)
        {
            names.add(e.nextElement());
        }

        for (String name : names)
        {
            removeAttribute(name);
        }
    }

    @Override
    public final boolean containsKey(final Object key)
    {
        return getAttribute(key.toString()) != null;
    }

    @Override
    public boolean containsValue(final Object findValue)
    {
        if (findValue == null)
        {
            return false;
        }

        for (final Enumeration<String> e = getAttributeNames(); e.hasMoreElements();)
        {
            final Object value = getAttribute(e.nextElement());
            if (findValue.equals(value))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public Set<Entry<String, V>> entrySet()
    {
        return (_entrySet != null) ? _entrySet : (_entrySet = new EntrySet());
    }

    @Override
    public V get(final Object key)
    {
        return getAttribute(key.toString());
    }

    @Override
    public boolean isEmpty()
    {
        return !getAttributeNames().hasMoreElements();
    }

    @Override
    public Set<String> keySet()
    {
        return (_keySet != null) ? _keySet : (_keySet = new KeySet());
    }

    @Override
    public final V put(final String key, final V value)
    {
        final V retval = getAttribute(key);
        setAttribute(key, value);
        return retval;
    }

    @Override
    public void putAll(final Map<? extends String, ? extends V> t)
    {
        for (final Entry<? extends String, ? extends V> entry : t.entrySet())
        {
            setAttribute(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public final V remove(final Object key)
    {
        final String key_ = key.toString();
        final V retval = getAttribute(key_);
        removeAttribute(key_);
        return retval;
    }

    @Override
    public int size()
    {
        int size = 0;
        for (final Enumeration<String> e = getAttributeNames(); e.hasMoreElements();)
        {
            size++;
            e.nextElement();
        }
        return size;
    }

    @Override
    public Collection<V> values()
    {
        return (_values != null) ? _values : (_values = new Values());
    }

    abstract protected V getAttribute(String key);

    abstract protected void setAttribute(String key, V value);

    abstract protected void removeAttribute(String key);

    abstract protected Enumeration<String> getAttributeNames();

    private abstract class AbstractAttributeSet<E> extends AbstractSet<E>
    {
        @Override
        public boolean isEmpty()
        {
            return AbstractAttributeMap.this.isEmpty();
        }

        @Override
        public int size()
        {
            return AbstractAttributeMap.this.size();
        }

        @Override
        public void clear()
        {
            AbstractAttributeMap.this.clear();
        }
    }

    private final class KeySet extends AbstractAttributeSet<String>
    {
        @Override
        public Iterator<String> iterator()
        {
            return new KeyIterator();
        }

        @Override
        public boolean contains(final Object o)
        {
            return AbstractAttributeMap.this.containsKey(o);
        }

        @Override
        public boolean remove(final Object o)
        {
            return AbstractAttributeMap.this.remove(o) != null;
        }

    }

    private abstract class AbstractAttributeIterator<E> implements Iterator<E>
    {
        protected final Enumeration<String> _e = getAttributeNames();
        protected String _currentKey;

        public void remove()
        {
            // remove() may cause ConcurrentModificationException.
            // We could throw an exception here, but not throwing an exception
            // allows one call to remove() to succeed
            if (_currentKey == null)
            {
                throw new NoSuchElementException("You must call next() at least once");
            }
            AbstractAttributeMap.this.remove(_currentKey);
        }

        public boolean hasNext()
        {
            return _e.hasMoreElements();
        }

        public E next()
        {
            return getValue(_currentKey = _e.nextElement());
        }

        protected abstract E getValue(String attributeName);
    }

    private final class KeyIterator extends AbstractAttributeIterator<String>
    {
        @Override
        protected String getValue(final String attributeName)
        {
            return attributeName;
        }
    }

    private class Values extends AbstractAttributeSet<V>
    {
        @Override
        public Iterator<V> iterator()
        {
            return new ValuesIterator();
        }

        @Override
        public boolean contains(final Object o)
        {
            if (o == null)
            {
                return false;
            }

            for (final Iterator<V> it = iterator(); it.hasNext();)
            {
                if (o.equals(it.next()))
                {
                    return true;
                }
            }

            return false;
        }

        @Override
        public boolean remove(final Object o)
        {
            if (o == null)
            {
                return false;
            }

            for (final Iterator<V> it = iterator(); it.hasNext();)
            {
                if (o.equals(it.next()))
                {
                    it.remove();
                    return true;
                }
            }

            return false;
        }
    }

    private class ValuesIterator extends AbstractAttributeIterator<V>
    {
        @Override
        protected V getValue(final String attributeName)
        {
            return AbstractAttributeMap.this.get(attributeName);
        }
    }

    private final class EntrySet extends AbstractAttributeSet<Entry<String, V>>
    {
        @Override
        public Iterator<Entry<String, V>> iterator()
        {
            return new EntryIterator();
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean contains(final Object o)
        {
            if (!(o instanceof Entry))
            {
                return false;
            }

            final Entry<String, V> entry = (Entry<String, V>)o;
            final Object key = entry.getKey();
            final Object value = entry.getValue();
            if (key == null || value == null)
            {
                return false;
            }

            return value.equals(AbstractAttributeMap.this.get(key));
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean remove(final Object o)
        {
            if (!(o instanceof Entry))
            {
                return false;
            }

            final Entry<String, V> entry = (Entry<String, V>)o;
            final Object key = entry.getKey();
            final Object value = entry.getValue();
            if (key == null || value == null || !value.equals(AbstractAttributeMap.this.get(key)))
            {
                return false;
            }

            return AbstractAttributeMap.this.remove(((Entry<String, V>)o).getKey()) != null;
        }
    }

    /**
     * Not very efficient since it generates a new instance of <code>Entry</code> for each element and still internaly
     * uses the <code>KeyIterator</code>. It is more efficient to use the <code>KeyIterator</code> directly.
     */
    private final class EntryIterator extends AbstractAttributeIterator<Entry<String, V>>
    {
        @Override
        protected Entry<String, V> getValue(final String attributeName)
        {
            // Must create new Entry every time--value of the entry must stay
            // linked to the same attribute name
            return new EntrySetEntry(attributeName);
        }
    }

    private final class EntrySetEntry implements Entry<String, V>
    {
        private final String _currentKey;

        public EntrySetEntry(final String currentKey)
        {
            _currentKey = currentKey;
        }

        public String getKey()
        {
            return _currentKey;
        }

        public V getValue()
        {
            return AbstractAttributeMap.this.get(_currentKey);
        }

        public V setValue(final V value)
        {
            return AbstractAttributeMap.this.put(_currentKey, value);
        }

        @Override
        public int hashCode()
        {
            int result = 1;
            result = 31 * result + ((_currentKey == null) ? 0 : _currentKey.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final EntrySetEntry other = (EntrySetEntry)obj;
            if (_currentKey == null)
            {
                if (other._currentKey != null)
                    return false;
            }
            else if (!_currentKey.equals(other._currentKey))
                return false;
            return true;
        }

    }
}
