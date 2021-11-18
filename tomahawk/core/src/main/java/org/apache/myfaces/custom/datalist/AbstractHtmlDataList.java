/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.datalist;

import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.UserRoleAware;

/**
 * Similar to dataTable, but does not render a table. 
 * 
 * Instead the layout attribute controls how each dataRow is rendered. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:dataList"
 *   class = "org.apache.myfaces.custom.datalist.HtmlDataList"
 *   tagClass = "org.apache.myfaces.custom.datalist.HtmlDataListTag"
 * @since 1.1.7
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 940152 $ $Date: 2010-05-02 05:03:39 +0300 (Sun, 02 May 2010) $
 */
public abstract class AbstractHtmlDataList
        extends org.apache.myfaces.component.html.ext.HtmlDataTableHack
        implements UserRoleAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlDataList";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.List";
        
    private static Log log = LogFactory.getLog(AbstractHtmlDataList.class);
    private static final int PROCESS_DECODES = 1;
    private static final int PROCESS_VALIDATORS = 2; // not currently in use
    private static final int PROCESS_UPDATES = 3; // not currently in use

    /**
     * Throws NullPointerException if context is null.  Sets row index to -1, 
     * calls processChildren, sets row index to -1.
     */

    public void processDecodes(FacesContext context)
    {

        if (context == null)
            throw new NullPointerException("context");
        if (!isRendered())
            return;

        setRowIndex(-1);
        processChildren(context, PROCESS_DECODES);
        setRowIndex(-1);
        try
        {
            decode(context);
        }
        catch (RuntimeException e)
        {
            context.renderResponse();
            throw e;
        }        
    }

    public void processUpdates(FacesContext context)
    {
        if (context == null)
            throw new NullPointerException("context");
        if (!isRendered())
            return;

        setRowIndex(-1);
        processChildren(context, PROCESS_UPDATES);
        setRowIndex(-1);
        checkUpdateModelError(context);
    }

    public void processValidators(FacesContext context)
    {
        if (context == null)
            throw new NullPointerException("context");
        if (!isRendered())
            return;

        setRowIndex(-1);
        processChildren(context, PROCESS_VALIDATORS);
        setRowIndex(-1);
        checkUpdateModelError(context);        
    }

    /**
     * Iterates over all children, processes each according to the specified 
     * process action if the child is rendered.
     */

    public void processChildren(FacesContext context, int processAction)
    {
        // use this method for processing other than decode ?
        int first = getFirst();
        int rows = getRows();
        int last = rows == 0 ? getRowCount() : first + rows;

        if (log.isTraceEnabled())
        {
            log.trace("processing " + getChildCount()
                            + " children: starting at " + first
                            + ", ending at " + last);
        }

        for (int rowIndex = first; last == -1 || rowIndex < last; rowIndex++)
        {

            setRowIndex(rowIndex);

            if (!isRowAvailable())
            {
                if (log.isTraceEnabled())
                {
                    log.trace("scrolled past the last row, aborting");
                }
                break;
            }

            for (Iterator it = getChildren().iterator(); it.hasNext();)
            {
                UIComponent child = (UIComponent) it.next();
                if (child.isRendered())
                    process(context, child, processAction);
            }
        }
    }

    /**
     * Copy and pasted from UIData in order to maintain binary compatibility.
     */

    private void process(FacesContext context, UIComponent component,
            int processAction)
    {
        switch (processAction)
        {
        case PROCESS_DECODES:
            component.processDecodes(context);
            break;
        case PROCESS_VALIDATORS:
            component.processValidators(context);
            break;
        case PROCESS_UPDATES:
            component.processUpdates(context);
            break;
        }
    }
    
    public void setRowIndex(int rowIndex)
    {
        super.setRowIndex(rowIndex);
        String rowIndexVar = getRowIndexVar();
        String rowCountVar = getRowCountVar();
        if (rowIndexVar != null || rowCountVar != null)
        {
            Map requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
            if (rowIndex >= 0)
            {
                //regular row index, update request scope variables
                if (rowIndexVar != null)
                {
                    requestMap.put(getRowIndexVar(), new Integer(rowIndex));
                }
                if (rowCountVar != null)
                {
                    requestMap.put(getRowCountVar(), new Integer(getRowCount()));
                }
            }
            else
            {
                //rowIndex == -1 means end of loop --> remove request scope variables
                if (rowIndexVar != null)
                {
                    requestMap.remove(getRowIndexVar());
                }
                if (rowCountVar != null)
                {
                    requestMap.remove(getRowCountVar());
                }
            }
        }
    }
    
    /**
     * A parameter name, under which the rowCount is set in request 
     * scope similar to the var parameter.
     * 
     * @JSFProperty
     */
    public abstract String getRowCountVar();
    
    /**
     *  A parameter name, under which the current rowIndex is set in 
     *  request scope similar to the var parameter.
     * 
     * @JSFProperty
     */
    public abstract String getRowIndexVar();

    /**
     * simple|unorderedList|orderedList
     * <ul>
     *   <li>simple = for each dataRow all children are simply rendered</li>
     *   <li>unorderedList = the list is rendered as HTML unordered list (= bullet list)</li>
     *   <li>orderedList = the list is rendered as HTML ordered list</li>
     * </ul>
     * Default: simple
     * 
     * @JSFProperty
     */
    public abstract String getLayout();
    
    /**
     * CSS class to be applied to individual items in the list
     * 
     * @JSFProperty
     */
    public abstract String getItemStyleClass();
    
    /**
     * OnClick handler to be applied to individual items in the list
     * 
     * @JSFProperty
     */
    public abstract String getItemOnClick();
}
