/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.tree2;

import java.util.List;
import java.util.ArrayList;

public class TreeNodeBase implements TreeNode, Comparable
{
    private static final long serialVersionUID = 278589014441538822L;
    private List children = new ArrayList();
    private String type;
    private String description;
    private boolean leaf;
    private String identifier;

    public TreeNodeBase()
    {}

    public TreeNodeBase(String type, String description, boolean leaf)
    {
        this(type, description, null, leaf);
    }

    public TreeNodeBase(String type, String description, String identifier, boolean leaf)
    {
        this.type = type;
        this.description = description;
        this.identifier = identifier;
        this.leaf = leaf;
    }

    public boolean isLeaf()
    {
        return leaf || (getChildCount() == 0);
    }

    public void setLeaf(boolean leaf)
    {
        this.leaf = leaf;
    }

    public List getChildren()
    {
        return children;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public int getChildCount()
    {
        return getChildren().size();
    }

    public int compareTo(Object obj)
    {
        // branches come before leaves, after this criteria nodes are sorted alphabetically
        TreeNode otherNode = (TreeNode)obj;

        if (isLeaf() && !otherNode.isLeaf())
        {
            // leaves come after branches
            return 1;
        }
        else if (!isLeaf() && otherNode.isLeaf())
        {
            // branches come before leaves
            return -1;
        }
        else
        {
            // both nodes are leaves or both node are branches, so compare the descriptions
            return getDescription().compareTo(otherNode.getDescription());
        }
    }
}
