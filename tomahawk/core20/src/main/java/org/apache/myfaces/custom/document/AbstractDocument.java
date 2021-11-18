/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

import javax.faces.component.UIComponentBase;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * Base class to handle the document family
 * 
 * @JSFComponent
 *   tagClass = "org.apache.myfaces.custom.document.AbstractDocumentTag"
 *   configExcluded = "true"
 *   
 * @author Mario Ivankovits (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-03 16:58:05 -0500 (jue, 03 jul 2008) $
 */
@JSFComponent(
   tagClass = "org.apache.myfaces.custom.document.AbstractDocumentTag",
   configExcluded = true)
public class AbstractDocument extends UIComponentBase
{
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
        getStateHelper().put(PropertyKeys.state, state );
    }

    /**
     * state="start|end". Used to demarkate the document boundaries
     * 
     */
    @JSFProperty(literalOnly = true)
    public String getState()
    {
        return (String)getStateHelper().get(PropertyKeys.state);
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
    
    protected enum PropertyKeys
    {
        state
    }
}