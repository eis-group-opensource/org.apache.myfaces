/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.tree2;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TestCase for HtmlTree
 *
 * @author Sean Schofield
 */
public class HtmlTreeTest extends AbstractTreeTestCase
{
    //private HtmlTree tree;

    /**
     * Constructor
     * @param name Test name
     */
    public HtmlTreeTest(String name)
    {
        super(name);
    }

//    public void setUp()
//    {
//        super.setUp();
//        tree = new HtmlTree();
//    }
//
//    public void tearDown()
//    {
//        super.tearDown();
//    }

    // Return the tests included in this test case.
    public static Test suite()
    {
        return new TestSuite(HtmlTreeTest.class);
    }

    /**
     * Test default values of the tree
     */
    public void testDefaults()
    {
        assertTrue("clientSideToggle default should be true", tree.isClientSideToggle());
        assertTrue("showNav default should be true", tree.isShowNav());
        assertTrue("showLines default should be true", tree.isShowLines());
        assertTrue("showRootNode default should be true", tree.isShowRootNode());
        assertTrue("preserveToggle default should be true", tree.isPreserveToggle());
    }

    /**
     *
     */
    public void testSaveAndRestore()
    {

    }
}
