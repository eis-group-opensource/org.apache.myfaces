/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree2;

import java.io.Serializable;

public interface TreeState extends Serializable
{

    /**
     * Indicates whether or not the specified {@link TreeNode} is expanded.
     *
     * @param nodeId The id of the node in question.
     * @return If the node is expanded.
     */
    public boolean isNodeExpanded(String nodeId);

    /**
     * Toggle the expanded state of the specified {@link TreeNode}.
     * @param nodeId The id of the node whose expanded state should be toggled.
     */
    public void toggleExpanded(String nodeId);

    /**
     * Expand the complete path specified.  If any node in the path is already expanded,
     * that node should be left as it is.
     *
     * @param nodePath The path to be expanded.
     */
    public void expandPath(String[] nodePath);

    /**
     * Collapse the complete path specified.  If any node in the path is already collapsed,
     * that node should be left as it is.
     *
     * @param nodePath The path to be collapsed.
     */
    public void collapsePath(String[] nodePath);

    /**
     * Getter for transient property.
     * @return boolean
     */
    public boolean isTransient();

    /**
     * Setter for transient property
     * @param trans boolean
     */
    public void setTransient(boolean trans);

    /**
     * Sets the id of the currently selected node
     * @param nodeId The id of the currently selected node
     */
    public void setSelected(String nodeId);

    /**
     * Indicates whether or not the specified node is selected.
     * @param nodeId String
     * @return boolean
     */
    public boolean isSelected(String nodeId);
}
