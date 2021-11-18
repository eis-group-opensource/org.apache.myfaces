/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.buffer;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.DummyFormUtils;
import org.apache.myfaces.renderkit.html.util.HtmlBufferResponseWriterWrapper;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Data"
 *   type = "org.apache.myfaces.Buffer" 
 * 
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2008-05-24 23:59:15 +0300 (Sat, 24 May 2008) $
 */
public class BufferRenderer extends Renderer {
    private static final Log log = LogFactory.getLog(BufferRenderer.class);

    public static final String RENDERER_TYPE = "org.apache.myfaces.Buffer";

    private HtmlBufferResponseWriterWrapper getResponseWriter(FacesContext context) {
        return HtmlBufferResponseWriterWrapper.getInstance(context.getResponseWriter());
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) {
        RendererUtils.checkParamValidity(facesContext, uiComponent, Buffer.class);
        facesContext.setResponseWriter( getResponseWriter(facesContext) );
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException{
        RendererUtils.checkParamValidity(facesContext, component, Buffer.class);
        RendererUtils.renderChildren(facesContext, component);
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) {
        Buffer buffer = (Buffer)uiComponent;
        HtmlBufferResponseWriterWrapper writer = (HtmlBufferResponseWriterWrapper) facesContext.getResponseWriter();
        buffer.fill(writer.toString(), facesContext);

        facesContext.setResponseWriter( writer.getInitialWriter() );

        if( DummyFormUtils.getDummyFormParameters(facesContext) != null ){
            try{ // Attempt to add the dummy form params (will not work with Sun RI)
                if( DummyFormUtils.isWriteDummyForm(facesContext) )
                    DummyFormUtils.setWriteDummyForm(facesContext, true );
                for(Iterator i = DummyFormUtils.getDummyFormParameters(facesContext).iterator() ; i.hasNext() ;)
                    DummyFormUtils.addDummyFormParameter(facesContext, i.next().toString() );
            } catch (Exception e) {
                log.warn("Dummy form parameters are not supported by this JSF implementation.");
            }
        }
    }

}