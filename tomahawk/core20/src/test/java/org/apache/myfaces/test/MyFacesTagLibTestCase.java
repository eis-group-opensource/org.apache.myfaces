/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.test;

/**
 * @author Dennis C. Byrne
 */

public class MyFacesTagLibTestCase extends AbstractTagLibTestCase {

    protected static final String META_INF = "META-INF/";

    public MyFacesTagLibTestCase(){

        // TODO get the sandbox in here

        tldPaths = new String[3];
        tldPaths[0] = META_INF + "myfaces_html.tld";
        tldPaths[1] = META_INF + "myfaces_core.tld";
        tldPaths[2] = META_INF + "tomahawk.tld";
        // tldPaths[3] = META_INF + "myfaces_sandbox.tld";

    }

}
