/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree2;

import javax.faces.event.ActionEvent;

/**
 * @author Martin Marinschek
 */
public interface Tree {
    void setModel(Object model);

    Object getModel();

    void setVar(String var);

    String getVar();

    TreeNode getNode();

    String getNodeId();

    void setNodeId(String nodeId);

    String[] getPathInformation(String nodeId);

    boolean isLastChild(String nodeId);

    TreeModel getDataModel();

    void expandAll();

    void collapseAll();

    void expandPath(String[] nodePath);

    void collapsePath(String[] nodePath);

    void toggleExpanded();

    boolean isNodeExpanded();

    void setNodeSelected(ActionEvent event);

    boolean isNodeSelected();
}
