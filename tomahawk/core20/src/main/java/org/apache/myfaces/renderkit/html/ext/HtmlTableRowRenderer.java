/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.apache.myfaces.component.html.ext.HtmlTableRow;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.util.ArrayUtils;
import org.apache.myfaces.shared_tomahawk.util.StringUtils;

@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.HtmlTableRow", type = "org.apache.myfaces.HtmlTableRow")
public class HtmlTableRowRenderer extends HtmlTableRenderer
{
    private static final Logger log = Logger.getLogger(HtmlTableRowRenderer.class.getName());
    
    private static final Integer[] ZERO_INT_ARRAY = new Integer[]{0};
    
    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        //super.encodeEnd(facesContext, component);
        RendererUtils.checkParamValidity(facesContext, component, HtmlTableRow.class);
        
        ResponseWriter writer = facesContext.getResponseWriter();
        UIData uiData = (UIData) component.getParent();

        if (uiData instanceof HtmlDataTable)
        {
            HtmlDataTable htmlDataTable = (HtmlDataTable) uiData;

            int rowCount = uiData.getRowCount();

            // begin the table
            // get the CSS styles
            Styles styles = getStyles(uiData);

            int first = uiData.getFirst();
            int rows = uiData.getRows();
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

            int newspaperColumns = getNewspaperColumns(uiData);
            int newspaperRows;
            if((last - first) % newspaperColumns == 0)
                newspaperRows = (last - first) / newspaperColumns;
            else newspaperRows = ((last - first) / newspaperColumns) + 1;
            boolean newspaperHorizontalOrientation = isNewspaperHorizontalOrientation(uiData);

            // get the row indizes for which a new TBODY element should be created
            Integer[] bodyrows = null;
            String bodyrowsAttr = (String) uiData.getAttributes().get(JSFAttr.BODYROWS_ATTR);
            if(bodyrowsAttr != null && !"".equals(bodyrowsAttr)) 
            {   
                String[] bodyrowsString = StringUtils.trim(StringUtils.splitShortString(bodyrowsAttr, ','));
                // parsing with no exception handling, because of JSF-spec: 
                // "If present, this must be a comma separated list of integers."
                bodyrows = new Integer[bodyrowsString.length];
                for(int i = 0; i < bodyrowsString.length; i++) 
                {
                    bodyrows[i] = new Integer(bodyrowsString[i]);
                }
                
            }
            else
            {
                bodyrows = ZERO_INT_ARRAY;
            }
            //int bodyrowsCount = 0;
            
            int savedRow = uiData.getRowIndex();

            //(startRow - first) - ((startRow - first) % newspaperColums)
            
            int nr = newspaperHorizontalOrientation ? 
                    (savedRow-first)/newspaperColumns :
                     savedRow-first; //(savedRow-first) % newspaperRows;
                    
            // walk through the newspaper rows
            //for(int nr = 0; nr < newspaperRows; nr++)
            //{
            boolean rowStartRendered = false;
            // walk through the newspaper columns
            for(int nc = 0; nc < newspaperColumns; nc++) {

                // the current row in the 'real' table
                int currentRow;
                if (newspaperHorizontalOrientation)
                    currentRow = nr * newspaperColumns + nc + first;
                else
                    currentRow = nc * newspaperRows + nr + first;
                
                // if this row is not to be rendered
                if(currentRow >= last) continue;

                // bail if any row does not exist
                uiData.setRowIndex(currentRow);
                if(!uiData.isRowAvailable()) {
                    log.severe("Row is not available. Rowindex = " + currentRow);
                    break;
                }
    
                if (nc == 0) {
                    // first column in table, start new row
                    //beforeRow(facesContext, uiData);

                    // is the current row listed in the bodyrows attribute
                    if(ArrayUtils.contains(bodyrows, currentRow))  
                    {
                        // close any preopened TBODY element first
                        /*
                        if(bodyrowsCount != 0) 
                        {
                            HtmlRendererUtils.writePrettyLineSeparator(facesContext);
                            writer.endElement(HTML.TBODY_ELEM);
                        }
                        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
                        writer.startElement(HTML.TBODY_ELEM, uiData); 
                        // Do not attach bodyrowsCount to the first TBODY element, because of backward compatibility
                        writer.writeAttribute(HTML.ID_ATTR, uiData.getClientId(facesContext) + ":tbody_element" + 
                            (bodyrowsCount == 0 ? "" : bodyrowsCount), null);
                        bodyrowsCount++;
                        */
                    }
                    
                    HtmlRendererUtils.writePrettyLineSeparator(facesContext);
                    renderRowStart(facesContext, writer, uiData, styles, nr);
                    rowStartRendered = true;
                }

                List children = getChildren(uiData);
                for (int j = 0, size = getChildCount(uiData); j < size; j++)
                {
                    UIComponent child = (UIComponent) children.get(j);
                    if (child.isRendered())
                    {
                        boolean columnRendering = child instanceof UIColumn;
                        
                        if (columnRendering)
                            beforeColumn(facesContext, uiData, j);
                           
                        encodeColumnChild(facesContext, writer, uiData, child, styles, nc * uiData.getChildCount() + j);                    
                       
                        if (columnRendering)
                            afterColumn(facesContext, uiData, j);
                    }
                }

                if (hasNewspaperTableSpacer(uiData))
                {
                    // draw the spacer facet
                    if(nc < newspaperColumns - 1) renderSpacerCell(facesContext, writer, uiData);
                }
            }
            if (rowStartRendered)
            {
                renderRowEnd(facesContext, writer, uiData);
                //afterRow(facesContext, uiData);
            }
            //}
            
            if (uiData.getRowIndex() != savedRow)
            {
                uiData.setRowIndex(savedRow);
            }
        }
    }
    
    @Override
    public boolean getRendersChildren()
    {
        return true;
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException
    {
    }
    
    public void decode(FacesContext context, UIComponent component)
    {
        if (context == null)
            throw new NullPointerException("context");
        if (component == null)
            throw new NullPointerException("component");
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException
    {
        if (context == null)
            throw new NullPointerException("context");
        if (component == null)
            throw new NullPointerException("component");
    }

    public String convertClientId(FacesContext context, String clientId)
    {
        if (context == null)
            throw new NullPointerException("context");
        if (clientId == null)
            throw new NullPointerException("clientId");
        return clientId;
    }

    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
        throws ConverterException
    {
        if (context == null)
            throw new NullPointerException("context");
        if (component == null)
            throw new NullPointerException("component");
        return submittedValue;
    }
}
