/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.application;

import java.io.IOException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
public abstract class StateManagerWrapper extends StateManager {
    
    protected abstract StateManager getWrapped();
    
    public StateManager.SerializedView saveSerializedView(FacesContext context) {
        return getWrapped().saveSerializedView(context);
    }
    
    public Object saveView(FacesContext context) {
        return getWrapped().saveView(context);
    }

    public boolean isSavingStateInClient(FacesContext context) {
        return getWrapped().isSavingStateInClient(context);
    }

    protected Object getTreeStructureToSave(FacesContext context) {
        return getWrapped().getTreeStructureToSave(context);
    }

    protected Object getComponentStateToSave(FacesContext context) {
        return getWrapped().getComponentStateToSave(context);
    }

    public void writeState(FacesContext context, StateManager.SerializedView state) throws IOException {
        getWrapped().writeState(context, state);
    }
    
    public void writeState(FacesContext context, Object state) throws IOException {
        getWrapped().writeState(context, state);
    }

    public UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId) {
        return getWrapped().restoreView(context, viewId, renderKitId);
    }

    protected UIViewRoot restoreTreeStructure(FacesContext context, String viewId, String renderKitId) {
        return getWrapped().restoreTreeStructure(context, viewId, renderKitId);
    }

    protected void restoreComponentState(FacesContext context, UIViewRoot viewRoot, String renderKitId) {
        getWrapped().restoreComponentState(context, viewRoot, renderKitId);
    }
    
}
