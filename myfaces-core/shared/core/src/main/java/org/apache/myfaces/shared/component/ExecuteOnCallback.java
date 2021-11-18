/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.component;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

/**
 * With findComponent - you get a component, but this component might
 * not be prepared to actually have the correct context information. This
 * is important for e.g. DataTables. They'll need to prepare the component
 * with the current row-state to make sure that the method is executed
 * correctly.
 *
 * @author Martin Marinschek (latest modification by $Author: skitching $)
 */
public interface ExecuteOnCallback
{
    Object execute(FacesContext context, UIComponent component);
}
