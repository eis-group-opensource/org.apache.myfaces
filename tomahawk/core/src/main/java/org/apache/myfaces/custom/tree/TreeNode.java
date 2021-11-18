/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree;

import java.util.Iterator;

/**
 * Defines the requirements for an object that can be used as a tree node for
 * {@link HtmlTree}. (inspired by javax.swing.tree.TreeNode).
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller </a>
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */
public interface TreeNode
{

    /**
     * @return Gets the user object of this node.
     */
    Object getUserObject();

    /**
     * Answer the child at the given index.
     */
    TreeNode getChildAt(int childIndex);

    /**
     * Answer the number of children this node contains.
     */
    int getChildCount();

    /**
     * Answer the parent of this node.
     */
    TreeNode getParent();

    /**
     * Answer the index of the given node in this node's children.
     */
    int getIndex(TreeNode node);

    /**
     * Answer true if this node allows children.
     */
    boolean getAllowsChildren();

    /**
     * Answer true if this is a leaf node.
     */
    boolean isLeaf();

    /**
     * Answer the children of the receiver. The base collection is unmodifyable.
     */
    Iterator children();

}