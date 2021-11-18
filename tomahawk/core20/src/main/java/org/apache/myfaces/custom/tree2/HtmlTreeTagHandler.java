/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree2;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIViewRoot;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;

/**
 * 
 * @since 1.1.10
 * @author Leonardo Uribe
 */
public class HtmlTreeTagHandler extends ComponentHandler
{
    private static final String IMAGE_PREFIX = "t2";
    private static final String NODE_STATE_EXPANDED = "x";
    private static final String NODE_STATE_CLOSED = "c";
    
    public HtmlTreeTagHandler(ComponentConfig config)
    {
        super(config);
    }

    @Override
    public void onComponentPopulated(FaceletContext ctx, UIComponent c,
            UIComponent parent)
    {
        if (ComponentHandler.isNew(c))
        {
            // t:tree2 requires to force id rendering of UIGraphic images on "expand" and "collapse" facets for
            // a nodeTypeFacet. Long time ago, this was done on HtmlTreeRenderer.encodeNavigation, but that
            // hack will not work well with jsf 2 PSS, because it requires ids to be generated in a "stable" way,
            // and that hack used a counter to generate it.
            for (Map.Entry<String, UIComponent> entry : c.getFacets().entrySet())
            {
                UIComponent nodeTypeFacet = entry.getValue();
                UIComponent expandFacet = nodeTypeFacet.getFacet("expand");
                if (expandFacet != null)
                {
                    //if (! (expandFacet instanceof UIGraphic))
                    //{
                    //    expandFacet = expandFacet.getChildren().get(0);
                    //    nodeTypeFacet.getFacets().put("expand", expandFacet);
                    //}
                    UIGraphic expandImg = (UIGraphic)expandFacet;
                    String id = expandImg.getId();
                    if (id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
                    {
                        expandImg.setId(IMAGE_PREFIX + id.substring(UIViewRoot.UNIQUE_ID_PREFIX.length()) + NODE_STATE_EXPANDED);
                    }
                    
                }
                
                UIComponent collapseFacet = nodeTypeFacet.getFacet("collapse");
                if (collapseFacet != null)
                {
                    //if (! (collapseFacet instanceof UIGraphic))
                    //{
                    //    collapseFacet = collapseFacet.getChildren().get(0);
                    //    nodeTypeFacet.getFacets().put("collapse", collapseFacet);
                    //}
                    UIGraphic collapseImg = (UIGraphic)collapseFacet;
                    String id = collapseImg.getId();
                    if (id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
                    {
                        collapseImg.setId(IMAGE_PREFIX + id.substring(UIViewRoot.UNIQUE_ID_PREFIX.length()) + NODE_STATE_CLOSED);
                    }
                }
            }
        }
    }
}
