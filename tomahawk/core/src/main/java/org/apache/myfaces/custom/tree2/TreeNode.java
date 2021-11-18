/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.tree2;

import java.util.List;
import java.io.Serializable;

/**
 * Defines the requirements for an object that can be used as a tree node for
 * use in a {@link UITreeData} component. (inspired by javax.swing.tree.TreeNode).
 *
 * @author Sean Schofield
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */

public interface TreeNode extends Serializable
{
    public boolean isLeaf();

    public void setLeaf(boolean leaf);

    public List getChildren();

    /**
     * Gets the type of {@link TreeNode}.
     * @return The node type
     */
    public String getType();


    /**
     * Sets the type of {@link TreeNode}.
     * @param type The node type
     */
    public void setType(String type);


    public String getDescription();


    public void setDescription(String description);


    /**
     * Sets the identifier associated with the {@link TreeNode}.
     * @param identifier The identifier
     */
    public void setIdentifier(String identifier);


    /**
     * Gets the identifier asociated with the {@link TreeNode}.
     * @return the identifier
     */
    public String getIdentifier();


    /**
     * Gets the number of children this node has.
     * @return the number of children
     */
    public int getChildCount();
    
    /*
    public TreeNode getParentNode();
    
    public void setParentNode(TreeNode parent);
    */

}
