/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import java.lang.reflect.Method;
import java.util.Iterator;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Martin Marinschek (latest modification by $Author: grantsmith $)
 * @version $Revision: 169655 $ $Date: 2005-05-11 18:45:06 +0200 (Mi, 11 Mai 2005) $
 */
public class RestoreStateUtils
{
    private static Log log = LogFactory.getLog(RestoreStateUtils.class);

    /**
     * Walk the component tree, executing any component-bindings to reattach
     * components to their backing beans. 
     * <p>
     *  Note that this method effectively breaks encapsulation; instead of
     *  asking each component to update itself and its children, this
     * method just reaches into each component. That makes it impossible
     * for any component to customise its behaviour at this point.
     * <p>
     * This has been filed as an issue against the spec. Until this
     * issue is resolved, we'll add a new marker-interface for components
     * to allow them to define their interest in handling children bindings themselves.
     */
    public static void recursivelyHandleComponentReferencesAndSetValid(FacesContext facesContext,
                                                                       UIComponent parent)
    {
        recursivelyHandleComponentReferencesAndSetValid(facesContext, parent, false);
    }

    public static void recursivelyHandleComponentReferencesAndSetValid(FacesContext facesContext,
                                                                       UIComponent parent, boolean forceHandle)
    {
        Method handleBindingsMethod = getBindingMethod(parent);

        if(handleBindingsMethod!=null && !forceHandle)
        {
            try
            {
                handleBindingsMethod.invoke(parent,new Object[]{});
            }
            catch (Throwable th)
            {
                log.error("Exception while invoking handleBindings on component with client-id:"
                        +parent.getClientId(facesContext),th);
            }
        }
        else
        {
            for (Iterator it = parent.getFacetsAndChildren(); it.hasNext(); )
            {
                UIComponent component = (UIComponent)it.next();

                ValueExpression binding = component.getValueExpression("binding");    //TODO: constant
                if (binding != null)
                {
                    binding.setValue(facesContext.getELContext(), component);
                }

                //This part is not necessary on JSF 1.2
                //if (component instanceof UIInput)
                //{
                //    ((UIInput)component).setValid(true);
                //}

                recursivelyHandleComponentReferencesAndSetValid(facesContext, component);
            }
        }
    }

    /**This is all a hack to work around a spec-bug which will be fixed in JSF2.0
     *
     * @param parent
     * @return true if this component is bindingAware (e.g. aliasBean)
     */
    private static Method getBindingMethod(UIComponent parent)
    {
        Class[] clazzes = parent.getClass().getInterfaces();

        for (int i = 0; i < clazzes.length; i++)
        {
            Class clazz = clazzes[i];

            if(clazz.getName().indexOf("BindingAware")!=-1)
            {
                try
                {
                    return  parent.getClass().getMethod("handleBindings",new Class[]{});
                }
                catch (NoSuchMethodException e)
                {
                    // return
                }
            }
        }

        return null;
    }
}
