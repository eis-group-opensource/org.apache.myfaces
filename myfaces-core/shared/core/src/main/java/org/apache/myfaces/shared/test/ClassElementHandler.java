/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.test;

import java.util.List;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @see AbstractClassElementTestCase
 * @author Dennis Byrne
 */

public class ClassElementHandler extends DefaultHandler
{
    
    private boolean clazz ;
    private List elementName = new ArrayList();
    private List className = new ArrayList();
    private StringBuffer buffer ;
    
    public ClassElementHandler(){
        
        elementName.add("component-class");
        elementName.add("tag-class");
        elementName.add("renderer-class");
        elementName.add("validator-class");
        elementName.add("converter-class");
        elementName.add("action-listener");
        elementName.add("navigation-handler");
        elementName.add("variable-resolver");
        elementName.add("property-resolver");
        elementName.add("phase-listener");
        
    }

    public void characters(char[] ch, int start, int length)
    throws SAXException
    {
        if (clazz)
        {
            String string = new String(ch, start, length);
            if(string != null)
            {
                buffer.append(string.trim());
            }
        }
    }
    
    public void startElement(
            String ns, String local, String qName, Attributes atts) 
            throws SAXException
    {
       
         clazz = elementName.contains(qName);
         
         if(clazz)
             buffer = new StringBuffer();
        
    }

    public void endElement(String ns, String local, String qName) 
        throws SAXException
    {
        
        if(clazz){
            className.add(buffer.toString());
            clazz = false;
        }
        
    }

    public List getClassName()
    {
        return className;
    }
    
}
