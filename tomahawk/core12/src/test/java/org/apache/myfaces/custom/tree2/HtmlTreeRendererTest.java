/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.tree2;

import java.util.Stack;

import javax.faces.component.html.HtmlForm;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * - .. Root (0)
 * |
 * - .. A (0:0)
 * |    |
 * |    + .. AA (0:0:0)
 * |    |
 * |    + .. AB (0:0:1)
 * |    |
 * |    + .. AC (0:0:2)
 * |         | .. aca (0:0:2:0)
 * |         | .. acb (0:0:2:1)
 * |
 * + .. B (0:1)
 * |    |
 * |    + .. BA (0:1:0)
 * |    |
 * |    + .. BB (0:1:1)
 * |
 * | .. C (0:2)
 * |
 * | .. d (0:3)
 */

public class HtmlTreeRendererTest extends AbstractTreeTestCase
{
    private NodeSimulator nodeSim;

    /**
     * Constructor
     * @param name String
     */
    public HtmlTreeRendererTest(String name)
    {
        super(name);
    }

    /**
     * See abstract class
     */
    protected void setUp() throws Exception
    {
        super.setUp();

        nodeSim = new NodeSimulator();

        // the tree needs a default facet - use a node simulator instead of the usual components
        // so we can detect when a node is rendered
        tree.getFacets().put(DEFAULT_NODE_TYPE, nodeSim);

        // set up the renderer for the component.  with mock stuff we don't have faces config doing this for us
        renderKit.addRenderer(HtmlTree.COMPONENT_FAMILY, HtmlTree.COMPONENT_TYPE, new HtmlTreeRenderer());
    }

    /**
     * Tests the option of hiding the root node (with server side toggling option)
     * @throws Exception
     */
    public void testHideRootNodeServer() throws Exception
    {
        tree.setParent(new HtmlForm());
        tree.setClientSideToggle(false);
        tree.setShowRootNode(false);

        HtmlTreeRenderer treeRenderer = new HtmlTreeRenderer();
        treeRenderer.encodeChildren(facesContext, tree);

        // we expect the nodes to come off the stack in the reverse order in which they were added
        Stack nodeStack = nodeSim.getNodeStack();
        int numRendered = nodeStack.size();
        assertEquals("Unexpected number of nodes rendered", 4, numRendered);

        TreeNode node = (TreeNode)nodeStack.pop();
        assertEquals("d", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("C", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("B", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("A", node.getIdentifier());
    }

    /**
     * Tests the option of hiding the root node (with client side toggling option)
     * @throws Exception
     */
    public void testHideRootNodeClient() throws Exception
    {
        tree.setClientSideToggle(true);
        tree.setShowRootNode(false);

        HtmlTreeRenderer treeRenderer = new HtmlTreeRenderer();
        treeRenderer.encodeChildren(facesContext, tree);

        // we expect the nodes to come off the stack in the reverse order in which they were added
        Stack nodeStack = nodeSim.getNodeStack();
        int numRendered = nodeStack.size();
        assertEquals("Unexpected number of nodes rendered", 11, numRendered);

        TreeNode node = (TreeNode)nodeStack.pop();
        assertEquals("d", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("C", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("BB", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("BA", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("B", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("acb", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("aca", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("AC", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("AB", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("AA", node.getIdentifier());

        node = (TreeNode)nodeStack.pop();
        assertEquals("A", node.getIdentifier());
    }

    public static Test suite()
    {
        return new TestSuite(HtmlTreeRendererTest.class);
    }
}
