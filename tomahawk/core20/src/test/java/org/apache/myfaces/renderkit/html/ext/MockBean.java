/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.util.Collection;


/**
 * A bean for the test cases.
 * 
 * @author Jakob Korherr (latest modification by $Author: lu4242 $)
 * @version $Revision: 963899 $ $Date: 2010-07-14 01:57:38 +0300 (Wed, 14 Jul 2010) $
 */
public class MockBean
{

    private Collection<Integer> values;

    public Collection<Integer> getValues()
    {
        return values;
    }

    public void setValues(Collection<Integer> values)
    {
        this.values = values;
    }
    
}
