/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class ListEntries implements org.apache.myfaces.config.element.ListEntries
{

    private String valueClass;
    private List<Entry> entries = new ArrayList<Entry>();


    public String getValueClass()
    {
        return valueClass;
    }


    public void setValueClass(String valueClass)
    {
        this.valueClass = valueClass;
    }



    public void addEntry(Entry entry)
    {
        entries.add(entry);
    }


    public Iterator<Entry> getListEntries()
    {
        return entries.iterator();
    }


    public static class Entry implements org.apache.myfaces.config.element.ListEntry {
        private boolean nullValue;
        private String value;


        public boolean isNullValue()
        {
            return nullValue;
        }


        public void setNullValue()
        {
            this.nullValue = true;
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
}
