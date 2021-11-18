/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.application;

import org.apache.shale.test.base.AbstractJsfTestCase;

public class NavigationHandlerImplTest extends AbstractJsfTestCase
{

        public static void main(String[] args) {
            junit.textui.TestRunner.run(NavigationHandlerImplTest.class);
        }

        public NavigationHandlerImplTest(String name) {
            super(name);
        }
        
        public void testNavigationRules() throws Exception
        {
            NavigationHandlerImpl nh = new NavigationHandlerImpl();
            this.application.setNavigationHandler(nh);
        }
}