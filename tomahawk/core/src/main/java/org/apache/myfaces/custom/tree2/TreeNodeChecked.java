/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree2;

import org.apache.myfaces.custom.tree2.TreeNodeBase;

/**
 * Convenience implementation of <code>TreeNode</code> to make the tree
 * easy to use with boolean checkboxes.
 *
 * @author Matthias Wessendorf (changed by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeNodeChecked extends TreeNodeBase
{
    private static final long serialVersionUID = -3319932828983347196L;
    private boolean checked;

    public TreeNodeChecked() {
        super();
    }

    public TreeNodeChecked(String type, String description, boolean leaf) {
        super(type, description, leaf);
    }

    public TreeNodeChecked(String type, String description, String identifier, boolean leaf) {
        super(type, description, identifier, leaf);
    }
    
    public TreeNodeChecked(String type, String description, boolean checked, boolean leaf) {
        super(type, description, leaf);
        this.checked = checked;
    }
    
    public TreeNodeChecked(String type, String description, String identifier, boolean checked, boolean leaf) {
        super(type, description, identifier, leaf);
        this.checked = checked;
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}