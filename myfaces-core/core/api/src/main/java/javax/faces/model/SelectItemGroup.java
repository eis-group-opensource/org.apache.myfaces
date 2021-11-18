/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.model;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class SelectItemGroup extends SelectItem
{
  private static final long serialVersionUID = 849845793056900213L;

  private static final SelectItem[] EMPTY_SELECT_ITEMS = new SelectItem[0];

    // FIELDS
    private SelectItem[] _selectItems;

    // CONSTRUCTORS
    public SelectItemGroup()
    {
        super();
        _selectItems = EMPTY_SELECT_ITEMS;
    }

    public SelectItemGroup(String label)
    {
        super("", label, null, false);
        _selectItems = EMPTY_SELECT_ITEMS;
    }

    public SelectItemGroup(String label, String description, boolean disabled, SelectItem[] selectItems)
    {
        super("", label, description, disabled);
        if (selectItems == null) throw new NullPointerException("selectItems");
        _selectItems = selectItems;
    }

    // METHODS
    public SelectItem[] getSelectItems()
    {
        return _selectItems;
    }

    public void setSelectItems(SelectItem[] selectItems)
    {
        if (selectItems == null) throw new NullPointerException("selectItems");
        _selectItems = selectItems;
    }
}
