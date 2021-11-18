/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit;

import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;
import java.io.IOException;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 *
 */
public abstract class MyfacesResponseStateManager
        extends ResponseStateManager
{
    
    /**
     * Writes url parameters with the state info to be saved.
     * {@link org.apache.myfaces.application.MyfacesStateManager} delegates calls to
     * {@link org.apache.myfaces.application.MyfacesStateManager#writeState} to this method.
     *
     * @deprecated
     */
    public void writeStateAsUrlParams(FacesContext facescontext,
                                               StateManager.SerializedView serializedview) throws IOException
    {
        throw new UnsupportedOperationException("long been deprecated...");
    }
}
