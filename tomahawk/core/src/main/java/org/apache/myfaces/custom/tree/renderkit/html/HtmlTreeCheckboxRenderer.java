/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.renderkit.html;

import java.io.IOException;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.myfaces.custom.tree.HtmlTreeCheckbox;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlCheckboxRendererBase;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "org.apache.myfaces.HtmlTreeCheckbox"
 *   type = "org.apache.myfaces.HtmlTreeCheckbox"
 *   
 * @author <a href="mailto:dlestrat@apache.org">David Le Strat </a>
 */
public class HtmlTreeCheckboxRenderer extends HtmlCheckboxRendererBase
{

    /**
     * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        HtmlTreeCheckbox checkbox = (HtmlTreeCheckbox) component;

        String forAttr = checkbox.getFor();
        if (forAttr == null)
        {
            throw new IllegalStateException("Mandatory attribute 'for'");
        }

        UIComponent uiComponent = checkbox.findComponent(forAttr);
        if (uiComponent == null)
        {
            throw new IllegalStateException("Could not find component '" + forAttr
                                            + "' (calling findComponent on component '" + checkbox.getClientId(context) + "')");
        }
        if (!(uiComponent instanceof UISelectMany))
        {
            throw new IllegalStateException("UISelectMany expected");
        }

        UISelectMany uiSelectMany = (UISelectMany) uiComponent;

        Converter converter;
        try
        {
            converter = RendererUtils.findUISelectManyConverter(context, uiSelectMany);
        }
        catch (FacesException e)
        {
            converter = null;
        }

        Set lookupSet = RendererUtils.getSelectedValuesAsSet(context, component, converter, uiSelectMany);

        Object itemValue = checkbox.getItemValue();
        String itemStrValue = null;
        if (converter == null)
        {
            if (null != itemValue)
            {
                itemStrValue = itemValue.toString();
            }
        }
        else
        {
            itemStrValue = converter.getAsString(context, uiSelectMany, itemValue);
        }

        renderCheckbox(context,
                       uiSelectMany,
                       itemStrValue,
                       checkbox.getItemLabel(),
                       isDisabled(context,uiSelectMany),
                       lookupSet.contains(itemStrValue),
                       true);
    }

    /**
     * @see org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlCheckboxRendererBase#isDisabled(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        return super.isDisabled(facesContext, uiComponent);
    }

    /**
     * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void decode(FacesContext facesContext, UIComponent uiComponent)
    {
        if (uiComponent instanceof HtmlTreeCheckbox)
        {
            //nothing to decode
        }
        else
        {
            super.decode(facesContext, uiComponent);
        }
    }
}
