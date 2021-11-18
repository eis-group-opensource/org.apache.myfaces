/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

/**
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public interface PanelProperties
{
    /**
     * HTML: The background color of this element.
     * 
     * @JSFProperty
     */
    public abstract String getBgcolor();

    /**
     * HTML: Specifies the width of the border of this element, in pixels.  Deprecated in HTML 4.01.
     * 
     * @JSFProperty
     *   defaultValue="Integer.MIN_VALUE"
     */
    public abstract int getBorder();

    /**
     * HTML: Specifies the amount of empty space between the cell border and
     * its contents.  It can be either a pixel length or a percentage.
     * 
     * @JSFProperty
     */
    public abstract String getCellpadding();

    /**
     * HTML: Specifies the amount of space between the cells of the table.
     * It can be either a pixel length or a percentage of available 
     * space.
     * 
     * @JSFProperty
     */
    public abstract String getCellspacing();

    /**
     * HTML: Controls what part of the frame that surrounds a table is 
     * visible.  Values include:  void, above, below, hsides, lhs, 
     * rhs, vsides, box, and border.
     * 
     * @JSFProperty
     */
    public abstract String getFrame();

    /**
     * HTML: Controls how rules are rendered between cells.  Values include:
     * none, groups, rows, cols, and all.
     * 
     * @JSFProperty
     */
    public abstract String getRules();

    /**
     * HTML: Provides a summary of the contents of the table, for
     * accessibility purposes.
     * 
     * @JSFProperty
     */
    public abstract String getSummary();

    /**
     * HTML: Specifies the desired width of the table, as a pixel length or
     * a percentage of available space.
     * 
     * @JSFProperty
     */
    public abstract String getWidth();

}
