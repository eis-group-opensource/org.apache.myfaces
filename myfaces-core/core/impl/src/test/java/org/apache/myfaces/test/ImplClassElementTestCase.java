/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.test;

/**
 * @author Dennis Byrne
 */

public class ImplClassElementTestCase extends AbstractClassElementTestCase
{

    public ImplClassElementTestCase(){
        
        //resource.add("META-INF/f.tld");
        resource.add("META-INF/myfaces_core.tld");
        resource.add("META-INF/myfaces_html.tld");
        resource.add("META-INF/standard-faces-config.xml");
        
    }
    
}
