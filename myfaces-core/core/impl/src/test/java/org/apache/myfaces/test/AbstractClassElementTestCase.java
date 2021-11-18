/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_impl.test.ClassElementHandler;

import junit.framework.TestCase;

/**
 * This test makes sure all of our components, tags, renderers, 
 * validators, converters, action listeners, phase listeners and
 * core implementation classes are in the build.
 * 
 * This class has been copy and pasted into both tomahawk and core 
 * in order to avoid a compile scoped dependency on junit in shared.
 * 
 * @see ClassElementHandler
 * @author Dennis Byrne
 */

public abstract class AbstractClassElementTestCase extends TestCase
{

    private Log log = LogFactory.getLog(AbstractClassElementTestCase.class);
    
    protected List resource = new ArrayList();
    private List className = new ArrayList();

    protected void setUp() throws Exception
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(false);

        SAXParser parser = factory.newSAXParser();
        ClassElementHandler handler = new ClassElementHandler();
        
        Iterator iterator = resource.iterator();
        
        while(iterator.hasNext()){
            
            String resourceName = (String) iterator.next();
            
            InputStream is = getClass().getClassLoader()
                .getResourceAsStream(resourceName);
        
            if(is == null)
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
        
            if(is == null)
                throw new Exception("Could not locate resource :" + resourceName);
        
            parser.parse(is, handler);
            
        }
        
        className.addAll(handler.getClassName());
        
    }
    
    public void testClassPath(){
        
        int i = 0;
        for(  ; i < className.size() ; i++){
            
            String clazz = (String) className.get(i);
            
            try
            {
                getClass().getClassLoader().loadClass(clazz);
                
            }
            catch (ClassNotFoundException e)
            {
                
                try{
                    
                    Thread.currentThread().getContextClassLoader().loadClass(clazz);
                    
                }catch(ClassNotFoundException e2){
                    
                    assertFalse("Could not load " + clazz, true); 
                    
                }
                
            }
            
        }
        
        log.debug(( i + 1 ) + " class found ");
        
    }
    
}
