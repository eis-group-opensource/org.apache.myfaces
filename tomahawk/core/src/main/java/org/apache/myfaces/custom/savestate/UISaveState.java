/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.savestate;

import javax.faces.component.StateHolder;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Provides the ability to store a model value inside the view's component tree.
 * <p>
 * JSF provides three scopes for managed beans and therefore all the model
 * objects that the managed beans reference:  request, session, application.
 * However a common requirement is a way for a model object to have a scope
 * that is tied to the duration of the current view; that is longer than the
 * request scope but shorter than session scope.
 * </p> 
 * <p>
 * This component simply holds a reference to an arbitrary object (specified
 * by the value property). Because this object is an ordinary component whose
 * scope is the current view, the reference to the model automatically has that
 * same scope.
 * </p> 
 * <p>
 * When the value is an EL expression, then after the view is restored the
 * recreated target object is stored at the specified location.
 * </p>
 * <p>
 * The object being saved must either:
 * </p>
 * <ul>
 * <li>implement java.io.Serializable, or</li>
 * <li>implement javax.faces.component.StateHolder and have a default
 *   constructor.</li>
 * </ul>
 * <p>
 * Note that the saved object can be "chained" from view to view
 * in order to extend its lifetime from a single view to a sequence
 * of views if desired. A UISaveState component with an EL expression
 * such as "#{someBean}" will save the object state after render, and
 * restore it on postback. If navigation occurs to some other view
 * and that view has a UISaveState component with the same EL expression
 * then the object will simply be saved into the new view, thus extending
 * its lifetime.
 * </p>
 * 
 * @JSFComponent
 *   name = "t:saveState"
 *   tagClass = "org.apache.myfaces.custom.savestate.SaveStateTag"
 * @JSFJspProperty name = "name" returnType = "java.lang.String" tagExcluded = "true"
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class UISaveState
    extends UIParameter
{
    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        Object objectToSave = getValue();
        if (objectToSave instanceof StateHolder)
        {
            values[1] = Boolean.TRUE;
            values[2] = saveAttachedState(context, objectToSave);
        }
        else
        {
            values[1] = Boolean.FALSE;
            values[2] = objectToSave;
        }
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        
        Object savedObject;
        Boolean storedObjectIsAStateHolder = (Boolean) values[1];
        if ( Boolean.TRUE.equals( storedObjectIsAStateHolder ) )
        {
            savedObject = restoreAttachedState(context,values[2]);
        }
        else
        {
            savedObject = values[2];
        }
        ValueBinding vb = getValueBinding("value");
        if (vb != null)
        {
            vb.setValue(context, savedObject);
        }
    }

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.SaveState";
    public static final String COMPONENT_FAMILY = "javax.faces.Parameter";


    public UISaveState()
    {
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }


    //------------------ GENERATED CODE END ---------------------------------------
}
