/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectOneCountry;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlMenuRenderer;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.SelectOne"
 *   type = "org.apache.myfaces.SelectOneCountryRenderer"
 * 
 * @author Sylvain Vieujot (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class SelectOneCountryRenderer extends HtmlMenuRenderer {
    
    public void encodeEnd(FacesContext facesContext, UIComponent component)
    throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, null);

        SelectOneCountry selectOneCountry = (SelectOneCountry) component;
        ResponseWriter writer = facesContext.getResponseWriter();

        if(HtmlRendererUtils.isDisplayValueOnly(component))
        {
            //HtmlRendererUtils.renderDisplayValueOnlyForSelects(facesContext, component);
            writer.startElement(HTML.SPAN_ELEM, selectOneCountry);
            HtmlRendererUtils.writeIdIfNecessary(writer, selectOneCountry, facesContext);

            String[] supportedAttributes = {HTML.STYLE_CLASS_ATTR, HTML.STYLE_ATTR};
            HtmlRendererUtils.renderHTMLAttributes(writer, selectOneCountry, supportedAttributes);

            String countryCode = selectOneCountry.getValue().toString();
            String countryName = new Locale(countryCode, countryCode).getDisplayCountry( facesContext.getViewRoot().getLocale() );

            writer.write( countryName );

            writer.endElement(HTML.SPAN_ELEM);
            return;
        }

        writer.startElement(HTML.SELECT_ELEM, selectOneCountry);
        HtmlRendererUtils.writeIdIfNecessary(writer, selectOneCountry, facesContext);
        writer.writeAttribute(HTML.NAME_ATTR, component.getClientId(facesContext), null);

        List selectItemList = selectOneCountry.getCountriesChoicesAsSelectItemList();
        Converter converter = HtmlRendererUtils.findUIOutputConverterFailSafe(facesContext, selectOneCountry);

        writer.writeAttribute(HTML.SIZE_ATTR, "1", null);

        HtmlRendererUtils.renderHTMLAttributes(writer, selectOneCountry, HTML.SELECT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if ( isDisabled(facesContext, selectOneCountry) ) {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }

        Set lookupSet = HtmlRendererUtils.getSubmittedOrSelectedValuesAsSet(false, selectOneCountry, facesContext, converter);

        HtmlRendererUtils.renderSelectOptions(facesContext, selectOneCountry, converter, lookupSet, selectItemList);
        // bug #970747: force separate end tag
        writer.writeText("", null);
        writer.endElement(HTML.SELECT_ELEM);
    }
}
