/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 517047 $ $Date: 2007-03-12 01:22:47 +0200 (Mon, 12 Mar 2007) $
 */
public class AbstractAttributeMapTest extends TestCase
{
    private TestAttributeMap _testimpl;

    @Override
    protected void setUp() throws Exception
    {
        Map map = new HashMap();
        map.put("key", "value");
        _testimpl = new TestAttributeMap(map);
    }
    
    /**
     * Test method for {@link java.util.AbstractMap#hashCode()}.
     */
    public void testHashCodeEquals()
    {
        assertEquals(_testimpl.hashCode(), _testimpl.hashCode());
    }

    public void testValues() throws Exception
    {
        _testimpl.put("myKey", "myValue");
        assertTrue(_testimpl.values().contains("myValue"));
    }

    private static final class TestAttributeMap extends AbstractAttributeMap
    {
        private final Map _values;

        public TestAttributeMap(Map values)
        {
            _values = values;
        }

        @Override
        protected Object getAttribute(String key)
        {
            return _values.get(key);
        }

        @Override
        protected Enumeration getAttributeNames()
        {
            return new IteratorEnumeration(_values.keySet().iterator());
        }

        @Override
        protected void removeAttribute(String key)
        {
            _values.remove(key);
        }

        @Override
        protected void setAttribute(String key, Object value)
        {
            _values.put(key, value);
        }
    }

}
