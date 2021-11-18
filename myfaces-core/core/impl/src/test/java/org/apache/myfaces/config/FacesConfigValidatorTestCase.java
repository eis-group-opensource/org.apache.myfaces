/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/


package org.apache.myfaces.config;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.myfaces.config.impl.digester.DigesterFacesConfigDispenserImpl;
import org.apache.myfaces.config.impl.digester.DigesterFacesConfigUnmarshallerImpl;
import org.apache.shale.test.base.AbstractJsfTestCase;

public class FacesConfigValidatorTestCase extends AbstractJsfTestCase
{

    private FacesConfigDispenser dispenser;
    private FacesConfigUnmarshaller unmarshaller;
    
    public FacesConfigValidatorTestCase(String name)
    {
        super(name);
    }
    
    protected void setUp() throws Exception
    {

        super.setUp();
        
        dispenser = new DigesterFacesConfigDispenserImpl();
        unmarshaller = new DigesterFacesConfigUnmarshallerImpl(externalContext);
        try
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(testFacesConfig.getBytes());
            dispenser.feed(unmarshaller.getFacesConfig(bais, null));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void testVerifyExistence(){
        
        Iterator managedBeans = dispenser.getManagedBeans();
        Iterator navRules = dispenser.getNavigationRules();
        
        List list = FacesConfigValidator.validate(managedBeans, navRules, "C:/somePath/");
        
        int expected = 3;
        
        assertTrue(list.size() + " should equal " + expected, list.size() == expected);
        
    }
    
    private static final String testFacesConfig =
        "<?xml version='1.0' encoding='UTF-8'?>" +
        "<!DOCTYPE faces-config PUBLIC " +
            "\"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN\" " +
            "\"http://java.sun.com/dtd/web-facesconfig_1_1.dtd\">" +
            "<faces-config>" +
            "<navigation-rule>" +
            "    <from-view-id>/doesNotExist.jsp</from-view-id>" +
            "    <navigation-case>" +
            "        <from-outcome>doesNotMatter</from-outcome>" +
            "        <to-view-id>/doesNotExist2.jsp</to-view-id>" +
            "    </navigation-case>" +
            "</navigation-rule>" +
            "<managed-bean>" +
            "    <managed-bean-name>exist</managed-bean-name>" +
            "    <managed-bean-class>org.apache.myfaces.config.FacesConfigValidatorTestCase</managed-bean-class>" +
            "    <managed-bean-scope>request</managed-bean-scope>" +
            "</managed-bean>" +
            "<managed-bean>" +
            "    <managed-bean-name>nonExist</managed-bean-name>" +
            "    <managed-bean-class>org.apache.myfaces.config.NonExist</managed-bean-class>" +
            "    <managed-bean-scope>request</managed-bean-scope>" +
            "</managed-bean>" +
       "</faces-config>";
}
