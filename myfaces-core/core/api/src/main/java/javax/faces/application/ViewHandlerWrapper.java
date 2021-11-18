/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.application;

import java.io.IOException;
import java.util.Locale;
import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
public abstract class ViewHandlerWrapper extends ViewHandler {
    
    @Override
    public String calculateCharacterEncoding(FacesContext context) {
        return getWrapped().calculateCharacterEncoding(context);
    }

    @Override
    public void initView(FacesContext context) throws FacesException {
        getWrapped().initView(context);
    }

    protected abstract ViewHandler getWrapped();
    
    public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
        getWrapped().renderView(context, viewToRender);
    }

    public void writeState(FacesContext context) throws IOException {
        getWrapped().writeState(context);
    }

    public String calculateRenderKitId(FacesContext context) {
        return getWrapped().calculateRenderKitId(context);
    }

    public Locale calculateLocale(FacesContext context) {
        return getWrapped().calculateLocale(context);
    }

    public UIViewRoot restoreView(FacesContext context, String viewId) {
        return getWrapped().restoreView(context, viewId);
    }

    public String getResourceURL(FacesContext context, String path) {
        return getWrapped().getResourceURL(context, path);
    }

    public String getActionURL(FacesContext context, String viewId) {
        return getWrapped().getActionURL(context, viewId);
    }

    public UIViewRoot createView(FacesContext context, String viewId) {
        return getWrapped().createView(context, viewId);
    }
    
}
