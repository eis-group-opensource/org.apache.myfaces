/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.renderkit;

import javax.faces.render.Renderer;
import javax.faces.render.RenderKit;

import javax.faces.render.ResponseStateManager;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public class WmlRenderKitImpl extends RenderKit {
    private static Log log = LogFactory.getLog(WmlRenderKitImpl.class);

    private Map renderers;
    private ResponseStateManager rsm;

    /** Creates a new instance of RenderKitImpl */
    public WmlRenderKitImpl() {
        log.debug("created object " + this.getClass().getName());
        renderers = new HashMap();
        rsm = new WmlResponseStateManagerImpl();
        //rsm = new com.sun.faces.renderkit.ResponseStateManagerImpl();
    }

    public void addRenderer(String family, String rendererType, javax.faces.render.Renderer renderer) {
        renderers.put(family + "." + rendererType, renderer);
    }

    public Renderer getRenderer(String family, String rendererType) {
        log.debug("getRenderer() family:" + family + " renderType:" + rendererType);
        Renderer renderer = (Renderer)renderers.get(family + "." + rendererType);
        if (renderer == null){
            log.warn("Unsupported component-family/renderer-type: " + family + "/" + rendererType);
        }
        return(renderer);
    }

    public ResponseStateManager getResponseStateManager() {
        return(rsm);
    }

    public javax.faces.context.ResponseStream createResponseStream(java.io.OutputStream outputStream) {
        throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
    }

    public javax.faces.context.ResponseWriter createResponseWriter(java.io.Writer writer, String contentTypeList, String characterEncoding) {
        log.debug("createResponseWriter()");
        if (contentTypeList == null) {
            log.info("No content type list given, creating WmlResponseWriterImpl with default content type.");
            return new WmlResponseWriterImpl(writer, null, characterEncoding);
        }

        StringTokenizer st = new StringTokenizer(contentTypeList, ",");
        while (st.hasMoreTokens()) {
            String contentType = st.nextToken().trim();
            return new WmlResponseWriterImpl(writer, contentType, characterEncoding);
        }

        throw new IllegalArgumentException("ContentTypeList does not contain a supported content type: " + contentTypeList);
    }

}
