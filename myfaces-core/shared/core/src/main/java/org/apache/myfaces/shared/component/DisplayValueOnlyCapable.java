/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.component;

/**
 * Behavioral interface.
 * By default, displayValueOnly is false, and the components have the default behaviour.
 * When displayValueOnly is true, the renderer should not render any input widget.
 * Only the text corresponding to the component's value should be rendered instead.
 * 
 * @author Sylvain Vieujot (latest modification by $Author: svieujot $)
 * @author Martin Marinschek
 *
 * @version $Revision: 169739 $ $Date: 2005-05-12 02:45:14 +0200 (Thu, 12 May 2005) $
 */
public interface DisplayValueOnlyCapable
{
    String DISPLAY_VALUE_ONLY_ATTR = "displayValueOnly";
    String DISPLAY_VALUE_ONLY_STYLE_ATTR = "displayValueOnlyStyle";
    String DISPLAY_VALUE_ONLY_STYLE_CLASS_ATTR = "displayValueOnlyStyleClass";
    
    boolean isSetDisplayValueOnly();
    boolean isDisplayValueOnly();
    void setDisplayValueOnly(boolean displayValueOnly);

    String getDisplayValueOnlyStyle();
    void setDisplayValueOnlyStyle(String style);
    
    String getDisplayValueOnlyStyleClass();
    void setDisplayValueOnlyStyleClass(String styleClass);
}
