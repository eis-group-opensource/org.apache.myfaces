/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.event;

import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.component.UIComponent;

import org.apache.myfaces.custom.tree.model.TreePath;


/**
 * Event fired by {@link org.apache.myfaces.custom.tree.HtmlTree} on selection changes.
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */
public class TreeSelectionEvent extends FacesEvent
{
    private static final long serialVersionUID = -361206105634892091L;
    private TreePath oldSelectionPath;
    private TreePath newSelectionPath;


    /**
     * Construct an event.
     *
     * @param uiComponent      event source
     * @param oldSelectionPath path of the old selection, null if no node was selected before
     * @param newSelectionPath path of the current selection
     */
    public TreeSelectionEvent(UIComponent uiComponent, TreePath oldSelectionPath, TreePath newSelectionPath)
    {
        super(uiComponent);
        this.oldSelectionPath = oldSelectionPath;
        this.newSelectionPath = newSelectionPath;
    }


    /**
     * Answer the path of the old selection.
     *
     * @return path of previous (old) selection, null if no node was selected before
     */
    public TreePath getOldSelectionPath()
    {
        return oldSelectionPath;
    }


    /**
     * Answer the path of the current (new) selection.
     *
     * @return path of the new selected node
     */
    public TreePath getNewSelectionPath()
    {
        return newSelectionPath;
    }


    public boolean isAppropriateListener(FacesListener faceslistener)
    {
        return faceslistener instanceof TreeSelectionListener;
    }


    public void processListener(FacesListener faceslistener)
    {
        ((TreeSelectionListener) faceslistener).valueChanged(this);
    }
}
