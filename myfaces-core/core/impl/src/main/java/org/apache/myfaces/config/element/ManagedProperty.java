/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.element;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.config.element.ListEntries;


/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @author Anton Koinov

 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public interface ManagedProperty
{
    // <!ELEMENT managed-property (description*, display-name*, icon*, property-name, property-class?, (map-entries|null-value|value|list-entries))>

    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_MAP = 1;
    public static final int TYPE_NULL = 2;
    public static final int TYPE_VALUE = 3;
    public static final int TYPE_LIST = 4;

    public String getPropertyName();
    public String getPropertyClass();

    public int getType();
    public MapEntries getMapEntries();
    public ListEntries getListEntries();
    public Object getRuntimeValue(FacesContext facesContext);
    public boolean isValueReference();
    public ValueBinding getValueBinding(FacesContext facesContext);
}
