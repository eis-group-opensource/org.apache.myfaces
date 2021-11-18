/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import org.apache.myfaces.shared.renderkit.RendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.component.UISelectOne;
import javax.faces.component.html.HtmlSelectManyMenu;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import java.io.IOException;

/**
 * X-CHECKED: tlddoc of h:selectManyListbox
 *
 * @author Manfred Geiler (latest modification by $Author: matzew $)
 * @author Thomas Spiegl
 * @version $Revision: 597729 $ $Date: 2007-11-23 21:25:55 +0200 (Pn, 23 Lap 2007) $
 */
public class HtmlMenuRendererBase
        extends HtmlRenderer
{
    //private static final Log log = LogFactory.getLog(HtmlMenuRenderer.class);

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, null);

        if (component instanceof UISelectMany)
        {
            HtmlRendererUtils.renderMenu(facesContext,
                                         (UISelectMany)component,
                                         isDisabled(facesContext, component));
        }
        else if (component instanceof UISelectOne)
        {
            HtmlRendererUtils.renderMenu(facesContext,
                                         (UISelectOne)component,
                                         isDisabled(facesContext, component));
        }
        else
        {
            throw new IllegalArgumentException("Unsupported component class " + component.getClass().getName());
        }
    }

    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        //TODO: overwrite in extended HtmlMenuRenderer and check for enabledOnUserRole
        if (uiComponent instanceof HtmlSelectManyMenu)
        {
            return ((HtmlSelectManyMenu)uiComponent).isDisabled();
        }
        else if (uiComponent instanceof HtmlSelectOneMenu)
        {
            return ((HtmlSelectOneMenu)uiComponent).isDisabled();
        }
        else
        {
            return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(uiComponent, org.apache.myfaces.shared.renderkit.html.HTML.DISABLED_ATTR, false);
        }
    }

    public void decode(FacesContext facesContext, UIComponent uiComponent)
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, uiComponent, null);

        if (uiComponent instanceof UISelectMany)
        {
            HtmlRendererUtils.decodeUISelectMany(facesContext, uiComponent);
        }
        else if (uiComponent instanceof UISelectOne)
        {
            HtmlRendererUtils.decodeUISelectOne(facesContext, uiComponent);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported component class " + uiComponent.getClass().getName());
        }
    }

    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue) throws ConverterException
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, uiComponent, null);

        if (uiComponent instanceof UISelectMany)
        {
            return org.apache.myfaces.shared.renderkit.RendererUtils.getConvertedUISelectManyValue(facesContext,
                                                               (UISelectMany)uiComponent,
                                                               submittedValue);
        }
        else if (uiComponent instanceof UISelectOne)
        {
            return org.apache.myfaces.shared.renderkit.RendererUtils.getConvertedUISelectOneValue(facesContext,
                                                           (UISelectOne)uiComponent,
                                                           submittedValue);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported component class " + uiComponent.getClass().getName());
        }
    }

}
