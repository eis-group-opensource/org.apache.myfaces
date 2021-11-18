/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.application;

import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public abstract class MyfacesStateManager
        extends StateManager
{
    /**
     * Writes url parameters with the state info to be saved.
     * For every url state marker this method is called once from
     * {@link org.apache.myfaces.taglib.core.ViewTag#doAfterBody()}.
     */
    public abstract void writeStateAsUrlParams(FacesContext facesContext,
                                               SerializedView serializedView) throws IOException;
}
