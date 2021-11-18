/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/


package org.apache.myfaces.custom.dojo;

/**
 * Basic dojo interface
 * which has to implement
 * certain methods regarding the dojo widgets
 * 
 * for now the interfaces are very limited and only 
 * have to implement the variable binding properties
 * 
 * 
 * @author werpu
 *
 */
public interface DojoWidget {
    /**
     * getter for an explizit widget
     * var
     * @return
     */
    public String getWidgetVar();
    
    /**
     * 
     * @param widgetVar
     */
    public void setWidgetVar(String widgetVar);
  
    
    /**
     * forces the internal widget id onto the given value
     * 
     * @param forceIt
     */
    public void setWidgetId(String widgetId);
    
    /**
     * 
     * @return the enforced widgetid
     */
    public String getWidgetId();
}
