/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.MyfacesResponseStateManager;
import org.apache.myfaces.shared_impl.config.MyfacesConfig;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_impl.renderkit.html.util.JavascriptUtils;
import org.apache.myfaces.shared_impl.util.StateUtils;

import javax.faces.application.StateManager;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.ResponseStateManager;
import java.io.IOException;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 951800 $ $Date: 2010-06-06 05:07:42 +0300 (Sun, 06 Jun 2010) $
 */
public class HtmlResponseStateManager
    extends MyfacesResponseStateManager {
    private static final Log log = LogFactory.getLog(HtmlResponseStateManager.class);

    private static final int TREE_PARAM = 0;
    private static final int STATE_PARAM = 1;
    private static final int VIEWID_PARAM = 2;

    public static final String STANDARD_STATE_SAVING_PARAM = "javax.faces.ViewState";

    public void writeState(FacesContext facescontext,
                           StateManager.SerializedView serializedview) throws IOException {
        ResponseWriter responseWriter = facescontext.getResponseWriter();

        Object[] savedState = new Object[3];

        if (facescontext.getApplication().getStateManager().isSavingStateInClient(facescontext)) {
            if (log.isTraceEnabled()) log.trace("Writing state in client");
            Object treeStruct = serializedview.getStructure();
            Object compStates = serializedview.getState();

            if(treeStruct != null)
            {
                savedState[TREE_PARAM]=treeStruct;
            }
            else {
                log.error("No tree structure to be saved in client response!");
            }

            if (compStates != null) {
                savedState[STATE_PARAM] = compStates;
            }
            else {
                log.error("No component states to be saved in client response!");
            }
        }
        else {
            if (log.isTraceEnabled()) log.trace("Writing state in server");
            // write viewSequence
            Object treeStruct = serializedview.getStructure();
            if (treeStruct != null) {
                if (treeStruct instanceof String) {
                    savedState[TREE_PARAM] = treeStruct;
                }
            }
        }

        savedState[VIEWID_PARAM] = facescontext.getViewRoot().getViewId();

        if (log.isTraceEnabled()) log.trace("Writing view state and renderKit fields");

        // write the view state field
        writeViewStateField(facescontext, responseWriter, savedState);

        // renderKitId field
        writeRenderKitIdField(facescontext, responseWriter);
    }

    private void writeViewStateField(FacesContext facesContext,
                                       ResponseWriter responseWriter,
                                       Object savedState) throws IOException
    {
        String serializedState = StateUtils.construct(savedState,
                facesContext.getExternalContext());
        ExternalContext extContext = facesContext.getExternalContext();
        MyfacesConfig myfacesConfig = MyfacesConfig.getCurrentInstance(extContext);
        // Write Javascript viewstate if enabled and if javascript is allowed,
        // otherwise write hidden input
        if (JavascriptUtils.isJavascriptAllowed(extContext) && myfacesConfig.isViewStateJavascript()) {
            HtmlRendererUtils.renderViewStateJavascript(facesContext, STANDARD_STATE_SAVING_PARAM, serializedState);
        } else {
            responseWriter.startElement(HTML.INPUT_ELEM, null);
            responseWriter.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_HIDDEN, null);
            responseWriter.writeAttribute(HTML.NAME_ATTR, STANDARD_STATE_SAVING_PARAM, null);
            if (myfacesConfig.isRenderViewStateId()) {
                responseWriter.writeAttribute(HTML.ID_ATTR, STANDARD_STATE_SAVING_PARAM, null);
            }
            responseWriter.writeAttribute(HTML.VALUE_ATTR, serializedState, null);
            responseWriter.endElement(HTML.INPUT_ELEM);
        }
    }

    private void writeRenderKitIdField(FacesContext facesContext,
                                       ResponseWriter responseWriter) throws IOException {

        String defaultRenderKitId = facesContext.getApplication().getDefaultRenderKitId();
        if (defaultRenderKitId != null && !RenderKitFactory.HTML_BASIC_RENDER_KIT.equals(defaultRenderKitId)) {
            responseWriter.startElement(HTML.INPUT_ELEM, null);
            responseWriter.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_HIDDEN, null);
            responseWriter.writeAttribute(HTML.NAME_ATTR, ResponseStateManager.RENDER_KIT_ID_PARAM, null);
            responseWriter.writeAttribute(HTML.VALUE_ATTR, defaultRenderKitId, null);
            responseWriter.endElement(HTML.INPUT_ELEM);
        }
    }

    @Override
    public Object getState(FacesContext facesContext, String viewId) {
        Object[] savedState = getSavedState(facesContext);
        if (savedState == null) {
            return null;
        }
        
        return new Object[] { savedState[TREE_PARAM], savedState[STATE_PARAM] };
    }

    @Override
    public Object getTreeStructureToRestore(FacesContext facesContext, String viewId) {
        // Although this method won't be called anymore, 
        // it has been kept for backward compatibility.
        Object[] savedState = getSavedState(facesContext);
        if (savedState == null) {
            return null;
        }

        return savedState[TREE_PARAM];
    }

    @Override
    public Object getComponentStateToRestore(FacesContext facesContext) {
        // Although this method won't be called anymore, 
        // it has been kept for backward compatibility.
        Object[] savedState = getSavedState(facesContext);
        if (savedState == null) {
            return null;
        }

        return savedState[STATE_PARAM];
    }
    
    /**
     * Reconstructs the state from the "javax.faces.ViewState" request parameter.
     * 
     * @param facesContext the current FacesContext
     * 
     * @return the reconstructed state, or <code>null</code> 
     *          if there was no saved state
     */
    private Object[] getSavedState(FacesContext facesContext) {
        Object encodedState = 
            facesContext.getExternalContext().
                getRequestParameterMap().get(STANDARD_STATE_SAVING_PARAM);
        if(encodedState==null || (((String) encodedState).length() == 0)) { 
            return null;
        }
        
        Object[] savedState = (Object[]) StateUtils.reconstruct(
                (String) encodedState, facesContext.getExternalContext());

        if (savedState == null)
        {
            if (log.isTraceEnabled()) {
                log.trace("No saved state");
            }
            return null;
        }
        
        String restoredViewId = (String) savedState[VIEWID_PARAM];

        if (restoredViewId == null) {
            // no saved state or state of different viewId
            if (log.isTraceEnabled()) {
                log.trace("No saved state or state of a different viewId: " + restoredViewId);
            }
            
            return null;
        }
        
        return savedState;
    }


    /**
     * Checks if the current request is a postback
     * @since 1.2
     */
    @Override
    public boolean isPostback(FacesContext context)
    {
        return context.getExternalContext()
                .getRequestParameterMap().containsKey(ResponseStateManager.VIEW_STATE_PARAM);
    }
}


