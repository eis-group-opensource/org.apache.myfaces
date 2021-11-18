/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import javax.el.ELContextEvent;
import javax.el.ELContextListener;
import javax.faces.context.FacesContext;

/**
 * EL context listener which installs the faces context (if present) into el context and dispatches el context events to
 * faces application el context listeners.
 * 
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 514285 $ $Date: 2007-03-04 02:15:00 +0200 (Sun, 04 Mar 2007) $
 */
public class FacesELContextListener implements ELContextListener
{
    public void contextCreated(ELContextEvent ece)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null)
        {
            ece.getELContext().putContext(FacesContext.class, facesContext);

            for (ELContextListener listener : facesContext.getApplication().getELContextListeners())
            {
                listener.contextCreated(ece);
            }
        }
    }

}
