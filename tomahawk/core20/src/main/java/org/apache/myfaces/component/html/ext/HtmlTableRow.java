/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.component.UINamingContainer;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.component.NewspaperTable;

/**
 * This component is used to render one row in a t:dataTable, when
 * ajaxRowRender="true" is set.
 * 
 * @author Leonardo Uribe
 */
@JSFComponent
public class HtmlTableRow extends UIPanel
{
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.HtmlTableRow"; 
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTableRow";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlTableRow";

    private String _alternateClientId;
    
    public HtmlTableRow()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }
    
    @Override
    public String getClientId(FacesContext context)
    {
        String inheritedClientId = super.getClientId(context);
        if (getParent() instanceof HtmlDataTable)
        {
            HtmlDataTable dataTable = (HtmlDataTable) getParent();
            if (dataTable.getNewspaperColumns() > 1)
            {
                if (_alternateClientId == null)
                {
                    char separator = UINamingContainer.getSeparatorChar(context); 
                    _alternateClientId = dataTable.getClientId() + separator + 
                                         getNewspaperRowIndex(dataTable) + separator + getId(); 
                    return _alternateClientId;
                }
                else
                {
                    return _alternateClientId;
                }
            }
            else
            {
                return inheritedClientId;
            }
        }
        else
        {
            return inheritedClientId;
        }
    }
    
    public int getNewspaperRowIndex(HtmlDataTable dataTable)
    {
        int rowCount = dataTable.getRowCount();
        
        int first = dataTable.getFirst();
        int rows = dataTable.getRows();
        int last;

        if (rows <= 0)
        {
           last = rowCount;
        }
        else
        {
           last = first + rows;
           if (last > rowCount)
           {
               last = rowCount;
           }
        }

        int newspaperColumns = dataTable.getNewspaperColumns();
        int newspaperRows;
        if((last - first) % newspaperColumns == 0)
            newspaperRows = (last - first) / newspaperColumns;
        else newspaperRows = ((last - first) / newspaperColumns) + 1;

        int currentRow = dataTable.getRowIndex();
        int nr = NewspaperTable.NEWSPAPER_HORIZONTAL_ORIENTATION.equals(dataTable.getNewspaperOrientation()) ? 
                (currentRow-first)/newspaperColumns :
                (currentRow-first) % newspaperRows;
        
        return nr;
    }
    
    @Override
    public void setId(String id)
    {
        super.setId(id);
        _alternateClientId = null;
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }
}
