/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.renderkit.html.util;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.JavascriptUtils;

/**
 * This Phase listener is necessary for the autoscroll feature.
 * Its only purpose is to determine the former viewId and store it in request scope
 * so that we can later determine if a navigation has happened during rendering.
 */
public class AutoScrollPhaseListener
        implements PhaseListener
{
    private static final long serialVersionUID = -1087143949215838058L;

    public PhaseId getPhaseId()
    {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(PhaseEvent event)
    {
    }

    public void afterPhase(PhaseEvent event)
    {
        if(event != null)
        {
            FacesContext facesContext = event.getFacesContext();
            UIViewRoot view = facesContext.getViewRoot();
            if(view != null)
            {
                String viewId = view.getViewId();
                if (viewId != null)
                {
                    JavascriptUtils.setOldViewId(facesContext.getExternalContext(), viewId);
                }   
            }
        }
    }

}
