/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRuleset;

public class HtmlDataTableTagHandler extends ComponentHandler
{
    
    public HtmlDataTableTagHandler(ComponentConfig config)
    {
        super(config);
    }
    
    @Override
    public void onComponentCreated(FaceletContext ctx, UIComponent c,
            UIComponent parent)
    {
         // Add a detailStampRow component, so detailStamp rendering will be delegated
         // to a component that can be used in ajax.
        UIComponent detailStampRow = ctx.getFacesContext().
            getApplication().createComponent(ctx.getFacesContext(),
                HtmlDetailStampRow.COMPONENT_TYPE, HtmlDetailStampRow.DEFAULT_RENDERER_TYPE);
        detailStampRow.setId("detailStampRow");
        c.getFacets().put(AbstractHtmlDataTable.DETAIL_STAMP_ROW_FACET_NAME, detailStampRow);
        
        // Add a special component that is used to render a row on an ajax operation.
        UIComponent row = ctx.getFacesContext().
            getApplication().createComponent(ctx.getFacesContext(),
                HtmlTableRow.COMPONENT_TYPE, HtmlTableRow.DEFAULT_RENDERER_TYPE);
        row.setId("row");
        c.getFacets().put(AbstractHtmlDataTable.TABLE_ROW_FACET_NAME, row);
    }

    @Override
    public void onComponentPopulated(FaceletContext ctx, UIComponent c,
            UIComponent parent)
    {
        //Check and remove detailStampRow component if there is no detailStamp
        UIComponent detailStamp = c.getFacet(AbstractHtmlDataTable.DETAIL_STAMP_FACET_NAME);
        if (detailStamp == null)
        {
            UIComponent detailStampRow = c.getFacet(AbstractHtmlDataTable.DETAIL_STAMP_ROW_FACET_NAME);
            if (detailStampRow != null)
            {
                c.getFacets().remove(AbstractHtmlDataTable.DETAIL_STAMP_ROW_FACET_NAME);
            }
        }
        
        HtmlDataTable dataTable = (HtmlDataTable) c;
        if (!dataTable.isAjaxRowRender())
        {
            c.getFacets().remove(AbstractHtmlDataTable.TABLE_ROW_FACET_NAME);
        }
    }

    protected MetaRuleset createMetaRuleset(Class type)
    {
        return super.createMetaRuleset(type).alias("class", "styleClass");
    }
}
