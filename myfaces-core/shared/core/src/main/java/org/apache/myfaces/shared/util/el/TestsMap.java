/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util.el;

import org.apache.myfaces.shared.util.el.GenericMap;

/**
 * You can use this class to perform tests.
 * Use this for example in JSF backing beans, like BaseFace.getUserInRole(String).
 *
 * @author Sylvain Vieujot (latest modification by $Author: skitching $)
 * @version $Revision: 355303 $ $Date: 2005-12-09 02:36:08 +0100 (Fr, 09 Dez 2005) $
 */
public abstract class TestsMap extends GenericMap {

    /**
     * This method should return the result of the test.
     */
    public abstract boolean getTest(String testKey);

    protected Object getValue(Object testKey){
        return Boolean.valueOf(getTest( (String) testKey ));
    }

    public Object get(Object key) {
        if( ! (key instanceof String) )
            return null;
        return Boolean.valueOf(getTest( (String)key ));
    }

    public Boolean put(String key, Boolean value) {
        return Boolean.FALSE;
    }
}


