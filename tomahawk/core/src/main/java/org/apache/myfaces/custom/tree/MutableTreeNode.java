/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree;

/**
 * Defines the requirements for a tree node object that can change -- by adding or removing
 * child nodes, or by changing the contents of a user object stored in the node.
 * (inspired by javax.swing.tree.MutableTreeNode).
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */
public interface MutableTreeNode
        extends TreeNode
{

    /**
     * Add the given child to the children of this node.
     * This will set this node as the parent of the child using {#setParent}.
     */
    void insert(MutableTreeNode child);


    /**
     * Add the given child to the children of this node at index.
     * This will set this node as the parent of the child using {#setParent}.
     */
    void insert(MutableTreeNode child, int index);


    /**
     * Remove the child at the given index.
     */
    void remove(int index);


    /**
     * Remove the given node.
     */
    void remove(MutableTreeNode node);


    /**
     * Sets the user object of this node.
     */
    void setUserObject(Object object);


    /**
     * Remove this node from its parent.
     */
    void removeFromParent();


    /**
     * Set the parent node.
     */
    void setParent(MutableTreeNode parent);
}
