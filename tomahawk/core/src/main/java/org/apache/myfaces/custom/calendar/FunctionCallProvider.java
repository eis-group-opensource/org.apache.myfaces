/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.calendar;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

/**
 * @author Martin Marinschek (latest modification by $Author: mmarinschek $)
 * @version $Revision: 358294 $ $Date: 2005-12-21 15:11:52 +0100 (Mi, 21 Dez 2005) $
 */
public interface FunctionCallProvider
{
    String getFunctionCall(FacesContext facesContext, UIComponent uiComponent, String dateFormat);
}
