/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.datascroller;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * An event representing a click on some scroller control to
 * change the currently displayed table rows.
 * 
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ScrollerActionEvent extends ActionEvent
{
    private static final long serialVersionUID = -5692343289423906802L;

    private final String mScrollerfacet;

    private final int mPageIndex;

    /**
     * An event representing a user's choice of navigation option
     * <i>except</i> jumping to a specific page.
     * <o>
     * Param scrollerFacet contains the name of the operation performed,
     * which matches one of the public HtmlDataScroller.FACET_* constants.
     */
    public ScrollerActionEvent(UIComponent component, String scrollerfacet)
    {
        super(component);
        mScrollerfacet = scrollerfacet;
        mPageIndex = -1;
    }

    /**
     * An event representing a user's choice to jump straight to page
     * #pageIndex of the available pages of data.
     * 
     * @param component is the DataScroller component 
     * @param pageIndex is in the range 0..(nPages-1), where nPages
     * is (rowsOfDataAvailable/rowsPerPage).
     */
    public ScrollerActionEvent(UIComponent component, int pageIndex)
    {
        super(component);
        if (pageIndex < 0)
        {
            throw new IllegalArgumentException("wrong pageindex");
        }
        mPageIndex = pageIndex;
        mScrollerfacet = null;
    }

    /**
     * Returns a string which matches one of the HtmlDataScroller.FACET_*
     * public constants, or null if the user chose a page# navigation option.
     */
    public String getScrollerfacet()
    {
        return mScrollerfacet;
    }

    /**
     * Return the page of data the user wants to see, or -1 if the
     * user didn't choose a page# navigation option. 
     */
    public int getPageIndex()
    {
        return mPageIndex;
    }
}
