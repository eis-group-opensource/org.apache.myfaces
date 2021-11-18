/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;


/**
 * @author Anton Koinov (latest modification by $Author: matzew $)
 * @version $Revision: 557350 $ $Date: 2007-07-18 21:19:50 +0300 (Tr, 18 Lie 2007) $
 */
public class HashMapUtils
{
    //~ Constructors -------------------------------------------------------------------------------

    protected HashMapUtils()
    {
        // block public access
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Calculates initial capacity needed to hold <code>size</code> elements in
     * a HashMap or Hashtable without forcing an expensive increase in internal
     * capacity. Capacity is based on the default load factor of .75.
     * <p>
     * Usage: <code>Map map = new HashMap(HashMapUtils.calcCapacity(10));<code>
     * </p>
     * @param size the number of items that will be put into a HashMap
     * @return initial capacity needed
     */
    public static final int calcCapacity(int size)
    {
        return ((size * 4) + 3) / 3;
    }

    /**
     * Creates a new <code>HashMap</code> that has all of the elements
     * of <code>map1</code> and <code>map2</code> (on key collision, the latter
     * override the former).
     *
     * @param map1 the fist hashmap to merge
     * @param map2 the second hashmap to merge
     * @return new hashmap
     */
    public static HashMap merge(Map map1, Map map2)
    {
        HashMap retval = new HashMap(calcCapacity(map1.size() + map2.size()));

        retval.putAll(map1);
        retval.putAll(map2);

        return retval;
    }

     /**
     * spit out each name/value pair
     */
    public static String mapToString(Map map){
        Set entries = map.entrySet();
        Iterator iter = entries.iterator();
        StringBuffer buff = new StringBuffer();
        while (iter.hasNext())
        {
            Map.Entry entry = (Map.Entry) iter.next();
            buff.append("[" + entry.getKey() + "," + entry.getValue() + "]\n");
        }
        return buff.toString();
    }

}
