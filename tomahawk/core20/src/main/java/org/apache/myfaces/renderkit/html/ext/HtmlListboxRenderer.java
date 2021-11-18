/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlListboxRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;


/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.SelectOne"
 *   type = "org.apache.myfaces.Listbox"
 *   
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.SelectMany"
 *   type = "org.apache.myfaces.Listbox"
 * 
 * @author Manfred Geiler (latest modification by $Author: jakobk $)
 * @version $Revision: 955214 $ $Date: 2010-06-16 15:16:02 +0300 (Wed, 16 Jun 2010) $
 */
public class HtmlListboxRenderer
        extends HtmlListboxRendererBase
{
    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        if (!UserRoleUtils.isEnabledOnUserRole(uiComponent))
        {
            return true;
        }
        else
        {
            return super.isDisabled(facesContext, uiComponent);
        }
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

        if(HtmlRendererUtils.isDisplayValueOnly(uiComponent))
        {
            HtmlRendererUtils.renderDisplayValueOnlyForSelects(facesContext, uiComponent, true);
        }
        else
        {
            super.encodeEnd(facesContext, uiComponent);
        }
    }
    
    /**
     * Overrides HtmlListboxRendererBase to handle valueType attribute on UISelectMany.
     */
    @Override
    public Object getConvertedValue(FacesContext facesContext,
            UIComponent component, Object submittedValue)
            throws ConverterException
    {
        RendererUtils.checkParamValidity(facesContext, component, null);
        
        if (component instanceof UISelectMany) 
        {
            // invoke getConvertedUISelectManyValue() with considerValueType = true
            return RendererUtils.getConvertedUISelectManyValue(facesContext,
                    (UISelectMany) component, submittedValue, true); 
        } 
        else 
        {
            // component is not a UISelectMany --> no change needed
            return super.getConvertedValue(facesContext, component, submittedValue);
        }
    }
    
    /**
     * Overrides HtmlListboxRendererBase to handle valueType attribute on UISelectMany.
     */
    @Override
    protected Converter getConverter(FacesContext facesContext,
            UIComponent component)
    {
        if (component instanceof UISelectMany)
        {
            // invoke findUISelectManyConverterFailsafe() with considerValueType = true
            return HtmlRendererUtils.findUISelectManyConverterFailsafe(facesContext, 
                    (UISelectMany) component, true);
        }
        else
        {
            // component is not a UISelectMany --> no change needed
            return super.getConverter(facesContext, component);
        }
    }

}
