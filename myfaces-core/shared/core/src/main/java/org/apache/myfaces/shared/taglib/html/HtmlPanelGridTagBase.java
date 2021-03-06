/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib.html;

import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.apache.myfaces.shared.renderkit.html.HTML;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author: cagatay $)
 * @version $Revision: 606793 $ $Date: 2007-12-25 17:20:46 +0200 (An, 25 Grd 2007) $
 * @deprecated use {@link HtmlPanelGridELTagBase} instead
 */
public abstract class HtmlPanelGridTagBase
        extends org.apache.myfaces.shared.taglib.html.HtmlComponentBodyTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlPanelGridTag.class);

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML table attributes relevant for Grid
    private String _align;
    private String _border;
    private String _bgcolor;
    private String _cellpadding;
    private String _cellspacing;
    private String _datafld;
    private String _datasrc;
    private String _dataformatas;
    private String _frame;
    private String _rules;
    private String _summary;
    private String _width;

    // UIPanel attributes
    // value and converterId --> already implemented in UIComponentTagBase

    // HtmlPanelGrid attributes
    private String _columnClasses;
    private String _columns;
    private String _footerClass;
    private String _headerClass;
    private String _rowClasses;

    public void release() {
        super.release();
        _align=null;
        _border=null;
        _bgcolor=null;
        _cellpadding=null;
        _cellspacing=null;
        _datafld=null;
        _datasrc=null;
        _dataformatas=null;
        _frame=null;
        _rules=null;
        _summary=null;
        _width=null;
        _columnClasses=null;
        _columns=null;
        _footerClass=null;
        _headerClass=null;
        _rowClasses=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.ALIGN_ATTR, _align);
        setIntegerProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.BORDER_ATTR, _border);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.BGCOLOR_ATTR, _bgcolor);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.CELLPADDING_ATTR, _cellpadding);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.CELLSPACING_ATTR, _cellspacing);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.FRAME_ATTR, _frame);
        setStringProperty(component, HTML.RULES_ATTR, _rules);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.html.HTML.SUMMARY_ATTR, _summary);
        setStringProperty(component, HTML.WIDTH_ATTR, _width);

        setStringProperty(component, JSFAttr.COLUMN_CLASSES_ATTR, _columnClasses);
        setIntegerProperty(component, JSFAttr.COLUMNS_ATTR, _columns);
        setStringProperty(component, JSFAttr.FOOTER_CLASS_ATTR, _footerClass);
        setStringProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.HEADER_CLASS_ATTR, _headerClass);
        setStringProperty(component, JSFAttr.ROW_CLASSES_ATTR, _rowClasses);
    }


    public void setAlign(String align)
    {
        _align = align;
    }

    public void setBorder(String border)
    {
        _border = border;
    }

    public void setBgcolor(String bgcolor)
    {
        _bgcolor = bgcolor;
    }

    public void setCellpadding(String cellpadding)
    {
        _cellpadding = cellpadding;
    }

    public void setCellspacing(String cellspacing)
    {
        _cellspacing = cellspacing;
    }

    public void setDatafld(String datafld)
    {
        _datafld = datafld;
    }

    public void setDatasrc(String datasrc)
    {
        _datasrc = datasrc;
    }

    public void setDataformatas(String dataformatas)
    {
        _dataformatas = dataformatas;
    }

    public void setFrame(String frame)
    {
        _frame = frame;
    }

    public void setRules(String rules)
    {
        _rules = rules;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public void setWidth(String width)
    {
        _width = width;
    }

    public void setColumnClasses(String columnClasses)
    {
        _columnClasses = columnClasses;
    }

    public void setColumns(String columns)
    {
        _columns = columns;
    }

    public void setFooterClass(String footerClass)
    {
        _footerClass = footerClass;
    }

    public void setHeaderClass(String headerClass)
    {
        _headerClass = headerClass;
    }

    public void setRowClasses(String rowClasses)
    {
        _rowClasses = rowClasses;
    }
}
