/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * Base class to handle the document family
 * 
 * @JSFComponent
 *   tagClass = "org.apache.myfaces.custom.document.AbstractDocumentTag"
 *   configExcluded = "true"
 *   
 * @author Mario Ivankovits (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class AbstractDocument extends UIComponentBase
{
    private String _state = null;

    public static final String COMPONENT_FAMILY = "javax.faces.Data";

    public AbstractDocument(String renderType)
    {
        setRendererType(renderType);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void setState(String state)
    {
        _state = state;
    }

    /**
     * state="start|end". Used to demarkate the document boundaries
     * 
     * @JSFProperty
     *   literalOnly = "true"
     */
    public String getState()
    {
        return _state;
    }

    public boolean hasState()
    {
        return isStartState() || isEndState();
    }

    public boolean isStartState()
    {
        return "start".equals(getState());
    }

    public boolean isEndState()
    {
        return "end".equals(getState());
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = _state;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _state = (String) values[1];
    }
}