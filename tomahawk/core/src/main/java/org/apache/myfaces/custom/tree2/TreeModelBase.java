/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree2;

import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * Model class for the tree component.  It provides random access to nodes in a tree
 * made up of instances of the {@link TreeNode} class.
 *
 * @author Sean Schofield
 * @author Hans Bergsten (Some code taken from an example in his O'Reilly JavaServer Faces book. Copied with permission)
 * @version $Revision: 168205 $ $Date: 2005-05-04 18:59:21 -0400 (Wed, 04 May 2005) $
 */
public class TreeModelBase implements TreeModel
{

    private static final long serialVersionUID = 3969414475396945742L;
//  private static final Log log = LogFactory.getLog(TreeModelBase.class);

    private TreeNode root;
    private TreeState treeState = new TreeStateBase();


    /**
     * Constructor
     * @param root The root TreeNode
     */
    public TreeModelBase(TreeNode root)
    {
        this.root = root;
    }

    // see interface
    public TreeState getTreeState()
    {
        return treeState;
    }

    // see interface
    public void setTreeState(TreeState treeState)
    {
        this.treeState = treeState;
    }



    /**
     * Gets an array of String containing the ID's of all of the {@link TreeNode}s in the path to
     * the specified node.  The path information will be an array of <code>String</code> objects
     * representing node ID's. The array will starting with the ID of the root node and end with
     * the ID of the specified node.
     *
     * @param nodeId The id of the node for whom the path information is needed.
     * @return String[]
     */
    public String[] getPathInformation(String nodeId)
    {
        if (nodeId == null)
        {
            throw new IllegalArgumentException("Cannot determine parents for a null node.");
        }

        ArrayList pathList = new ArrayList();
        pathList.add(nodeId);

        while (nodeId.lastIndexOf(SEPARATOR) != -1)
        {
            nodeId = nodeId.substring(0, nodeId.lastIndexOf(SEPARATOR));
            pathList.add(nodeId);
        }

        String[] pathInfo = new String[pathList.size()];

        for (int i=0; i < pathInfo.length; i++)
        {
            pathInfo[i] = (String)pathList.get(pathInfo.length - i - 1);
        }

        return pathInfo;
    }

    /**
     * Indicates whether or not the specified {@link TreeNode} is the last child in the <code>List</code>
     * of children.  If the node id provided corresponds to the root node, this returns <code>true</code>.
     *
     * @param nodeId The ID of the node to check
     * @return boolean
     */
    public boolean isLastChild(String nodeId)
    {
        if (nodeId.lastIndexOf(SEPARATOR) == -1)
        {
            // root node considered to be the last child
            return true;
        }

        //first get the id of the parent
        String parentId = nodeId.substring(0, nodeId.lastIndexOf(SEPARATOR));
        String childString = nodeId.substring(nodeId.lastIndexOf(SEPARATOR) + 1);
        int childId = Integer.parseInt(childString);
        TreeNode parentNode = getNodeById(parentId);

        return  childId + 1== parentNode.getChildCount();
    }

    public TreeNode getNodeById(String nodeId)
    {
        if (nodeId == null)
            return null;

        TreeNode node = null;
        StringTokenizer st = new StringTokenizer(nodeId, SEPARATOR);

        while (st.hasMoreTokens())
        {
            int nodeIndex = Integer.parseInt(st.nextToken());
            if(node == null)
            {
                node = root;
            }
            else
            {
                // don't worry about invalid index, that exception will be caught later and dealt with
                node = (TreeNode)node.getChildren().get(nodeIndex);
            }
        }

        return node;
    }

    // see interface
    public TreeWalker getTreeWalker()
    {
        return new TreeWalkerBase();
    }
}
