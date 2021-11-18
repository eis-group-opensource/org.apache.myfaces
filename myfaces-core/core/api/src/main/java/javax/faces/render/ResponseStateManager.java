/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.render;

import javax.faces.application.StateManager;
import javax.faces.application.StateManager.SerializedView;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @author Stan Silvert
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public abstract class ResponseStateManager
{
    public static final String RENDER_KIT_ID_PARAM = "javax.faces.RenderKitId";
    public static final String VIEW_STATE_PARAM = "javax.faces.ViewState";
    
    public void writeState(FacesContext context, Object state) throws IOException{
        SerializedView view;
        if (state instanceof SerializedView)
        {
            view = (SerializedView) state;
        }
        else
            if (state instanceof Object[])
            {
                Object[] structureAndState = (Object[]) state;

                if (structureAndState.length == 2)
                {
                    Object structureObj = structureAndState[0];
                    Object stateObj = structureAndState[1];

                    StateManager stateManager = context.getApplication().getStateManager();
                    view = stateManager.new SerializedView(structureObj, stateObj);
                }
                else
                {
                    throw new IOException("The state should be an array of Object[] of lenght 2");
                }
            }
        else
            {
                throw new IOException("The state should be an array of Object[] of lenght 2, or a SerializedView instance");
            }

        writeState(context, view);
    }
    
    /**
     * @deprecated
     */
    public void writeState(FacesContext context,
                           StateManager.SerializedView state)
            throws IOException {
        // does nothing as per JSF 1.2 javadoc
    }

    /**
     * @since 1.2
     */
    public Object getState(FacesContext context, String viewId) {
        Object[] structureAndState = new Object[2];
        structureAndState[0] = getTreeStructureToRestore(context, viewId);
        structureAndState[1] = getComponentStateToRestore(context);
        return structureAndState;
    }
    
    
    /**
     * @deprecated
     */
    public Object getTreeStructureToRestore(FacesContext context,
                                             String viewId) {
        return null;
    }
    

    /**
     * @deprecated
     */
    public Object getComponentStateToRestore(FacesContext context) {
        return null;
    }
    
    /**
     * Checks if the current request is a postback
     * @since 1.2
     */
    public boolean isPostback(FacesContext context) {
      return context.getExternalContext().
        getRequestParameterMap().containsKey(
              ResponseStateManager.VIEW_STATE_PARAM);
    }

}
