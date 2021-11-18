/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.convert;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * This testcase test <code>javax.faces.convert.EnumConverter</code>.
 * 
 * @author Michael Kurz (latest modification by $Author: jakobk $)
 * @version $Revision: 925224 $ $Date: 2010-03-19 16:09:35 +0200 (Fri, 19 Mar 2010) $
 */
public class EnumConverterTest extends AbstractJsfTestCase {
    private enum testEnum {ITEM1, ITEM2};
    private EnumConverter converter;
    
    public EnumConverterTest(String name) {
        super(name);
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        converter = new EnumConverter(testEnum.class);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        converter = null;
    }

    /**
     * Test method for
     * {@link javax.faces.convert.EnumConverter#getAsObject(FacesContext, javax.faces.component.UIComponent, String)}.
     */
    public void testGetAsObject() {
        UIInput input = new UIInput();
        Object convertedObj = converter.getAsObject(FacesContext.getCurrentInstance(), input, "ITEM2");
        assertEquals(convertedObj, testEnum.ITEM2);
    }

    /**
     * Test method for
     * {@link javax.faces.convert.EnumConverter#getAsObject(FacesContext, javax.faces.component.UIComponent, String)}.
     */
    public void testGetAsObjectNull() {
        UIInput input = new UIInput();
           Object convertedObj = converter.getAsObject(FacesContext.getCurrentInstance(), input, null);
           assertNull(convertedObj);
    }
    
    /**
     * Test method for
     * {@link javax.faces.convert.EnumConverter#getAsObject(FacesContext, javax.faces.component.UIComponent, String)}.
     */
    public void testGetAsObjectNoEnum() {
        UIInput input = new UIInput();
        try {
            converter.getAsObject(FacesContext.getCurrentInstance(), input, "NO_ENUM_CONST");
            fail("Converter exception should be thrown");
        } catch (ConverterException e) {
            // should be thrown
        }
    }

    /**
     * Test method for
     * {@link javax.faces.convert.EnumConverter#getAsObject(FacesContext, javax.faces.component.UIComponent, String)}.
     */
    public void testGetAsObjectNoClassSet() {
        Converter testConverter = new EnumConverter();
        UIInput input = new UIInput();
        try {
            testConverter.getAsObject(FacesContext.getCurrentInstance(), input, "ITEM2");
            fail("Converter exception should be thrown");
        } catch (ConverterException e) {
            // should be thrown
        }
    }
    
    /**
     * Test method for
     * {@link javax.faces.convert.EnumConverter#getAsString(FacesContext, javax.faces.component.UIComponent, Object)}.
     */
    public void testGetAsString() {
        UIInput input = new UIInput();
        String convertedStr = converter.getAsString(FacesContext.getCurrentInstance(), input, testEnum.ITEM1);
        assertEquals(convertedStr, testEnum.ITEM1.toString());
    }

    /**
     * Test method for
     * {@link javax.faces.convert.EnumConverter#getAsString(FacesContext, javax.faces.component.UIComponent, Object)}.
     */
    public void testGetAsStringNull() {
        UIInput input = new UIInput();
        String convertedStr = converter.getAsString(FacesContext.getCurrentInstance(), input, null);
        assertEquals(convertedStr, "");
    }

    /**
     * Test method for
     * {@link javax.faces.convert.EnumConverter#getAsString(FacesContext, javax.faces.component.UIComponent, Object)}.
     */
    public void testGetAsStringNoEnum() {
        UIInput input = new UIInput();
        String convertedStr = converter.getAsString(FacesContext.getCurrentInstance(), input, "HALLO");
        assertEquals(convertedStr, "HALLO");
    }

    /**
     * Test method for
     * {@link javax.faces.convert.EnumConverter#getAsString(FacesContext, javax.faces.component.UIComponent, Object)}.
     */
    public void testGetAsStringNoClassSet() {
        Converter testConverter = new EnumConverter();
        UIInput input = new UIInput();
        try {
            testConverter.getAsString(FacesContext.getCurrentInstance(), input, testEnum.ITEM1);
            fail("Converter exception should be thrown");
        } catch (ConverterException e) {
            // should be thrown
        }
    }
}
