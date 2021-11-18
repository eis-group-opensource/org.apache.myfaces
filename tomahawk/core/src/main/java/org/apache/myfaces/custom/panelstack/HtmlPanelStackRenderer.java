/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.panelstack;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;


/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.PanelStack"
 * 
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 990487 $ $Date: 2010-08-29 07:25:48 +0300 (Sun, 29 Aug 2010) $
 */
public class HtmlPanelStackRenderer extends HtmlRenderer
{


    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
    }


    public boolean getRendersChildren()
    {
        return true;
    }


    public void encodeChildren(FacesContext facescontext, UIComponent uicomponent) throws IOException
    {
    }


    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlPanelStack.class);

        HtmlPanelStack panelStack = (HtmlPanelStack) uiComponent;
        String selectedPanel = panelStack.getSelectedPanel();
        UIComponent childToRender = null;

        if (selectedPanel != null && selectedPanel.length() > 0)
        {
            // render the selected child
            childToRender = panelStack.findComponent(selectedPanel);
            if (childToRender == null)
            {
                // if not found, render the first child
                if (panelStack.getChildCount() > 0) {
                    childToRender = (UIComponent) panelStack.getChildren().get(0);
                }
            }
        }
        else
        {
            // render the first child
            if (panelStack.getChildCount() > 0) {
                childToRender = (UIComponent) panelStack.getChildren().get(0);
            }
        }

        if (childToRender != null)
        {
            RendererUtils.renderChild(facesContext, childToRender);
        }
    }

}
