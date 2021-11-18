/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.behavior;

public class HtmlRenderedClientEventAttr
{
    private String name;
    private String clientEvent;
    private String value;
    
    public HtmlRenderedClientEventAttr(String name, String clientEvent) {
        this(name, clientEvent, name);
    }
    
    public HtmlRenderedClientEventAttr(String name, String clientEvent, String value) {
        this.name = name;
        this.clientEvent = clientEvent;
        this.value = value;
    }
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getClientEvent()
    {
        return clientEvent;
    }

    public void setClientEvent(String clientEvent)
    {
        this.clientEvent = clientEvent;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
