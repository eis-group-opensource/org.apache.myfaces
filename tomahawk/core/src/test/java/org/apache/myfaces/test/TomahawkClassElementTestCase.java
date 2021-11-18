/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/


package org.apache.myfaces.test;

/**
 * @author Dennis Byrne
 */

public class TomahawkClassElementTestCase extends AbstractClassElementTestCase
{

    public TomahawkClassElementTestCase(){
        
        resource.add("META-INF/tomahawk.tld");
        resource.add("META-INF/faces-config.xml");
        
    }
    
}
