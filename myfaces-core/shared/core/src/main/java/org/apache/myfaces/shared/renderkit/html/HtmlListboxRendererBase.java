/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit.html;

import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.apache.myfaces.shared.renderkit.RendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.component.UISelectOne;
import javax.faces.component.html.HtmlSelectManyListbox;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import java.io.IOException;


/**
 * @author Thomas Spiegl (latest modification by $Author: matzew $)
 * @author Anton Koinov
 * @version $Revision: 597729 $ $Date: 2007-11-23 21:25:55 +0200 (Pn, 23 Lap 2007) $
 */
public class HtmlListboxRendererBase
        extends HtmlRenderer
{
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        org.apache.myfaces.shared.renderkit.RendererUtils.checkParamValidity(facesContext, uiComponent, null);

        int size = (Integer)uiComponent.getAttributes().get(JSFAttr.SIZE_ATTR);

        if (uiComponent instanceof UISelectMany)
        {
            HtmlRendererUtils.renderListbox(facesContext,
                                            (UISelectMany)uiComponent,
                                            isDisabled(facesContext, uiComponent),
                                            size);
        }
        else if (uiComponent instanceof HtmlSelectOneListbox)
        {
            HtmlRendererUtils.renderListbox(facesContext,
                                            (UISelectOne)uiComponent,
                                            isDisabled(facesContext, uiComponent),
                                            size);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported component class " + uiComponent.getClass().getName());
        }
    }

    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        //TODO: overwrite in extended HtmlListboxRenderer and check for enabledOnUserRole
        if (uiComponent instanceof HtmlSelectManyListbox)
        {
            return ((HtmlSelectManyListbox)uiComponent).isDisabled();
        }
        else if (uiComponent instanceof HtmlSelectOneListbox)
        {
            return ((HtmlSelectOneListbox)uiComponent).isDisabled();
        }
        else
        {
            return org.apache.myfaces.shared.renderkit.RendererUtils.getBooleanAttribute(uiComponent, org.apache.myfaces.shared.renderkit.html.HTML.DISABLED_ATTR, false);
        }
    }


    public void decode(FacesContext facesContext, UIComponent uiComponent)
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, null);

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
            return RendererUtils.getConvertedUISelectOneValue(facesContext,
                                                           (UISelectOne)uiComponent,
                                                           submittedValue);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported component class " + uiComponent.getClass().getName());
        }
    }

}
