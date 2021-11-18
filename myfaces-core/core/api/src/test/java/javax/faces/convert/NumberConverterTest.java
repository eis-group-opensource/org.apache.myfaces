/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.convert;

import org.apache.shale.test.base.AbstractJsfTestCase;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import java.util.Locale;

public class NumberConverterTest extends AbstractJsfTestCase
{
    private NumberConverter mock;

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(NumberConverterTest.class);
    }

    public NumberConverterTest(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();

        mock = new NumberConverter();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();

        mock = null;
    }
    /*
     * temporarily comment out tests that fail, until Matthias Wessendorf has time to investigate
     */
    public void testFranceLocaleWithNonBreakingSpace()
    {
        mock.setLocale(Locale.FRANCE);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.GERMANY);
        UIInput input = new UIInput();
        mock.setType("currency");
        String stringValue = mock.getAsString(facesContext, input, new Double(12345.68d));
        Number number = (Number) mock.getAsObject(FacesContext.getCurrentInstance(), input, "12\u00a0345,68 \u20AC");
        assertNotNull(number);
    }
    public void testFranceLocaleWithoutNonBreakingSpace()
    {
        mock.setLocale(Locale.FRANCE);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.GERMANY);
        UIInput input = new UIInput();
        mock.setType("currency");
        Number number = (Number) mock.getAsObject(FacesContext.getCurrentInstance(), input, "12 345,68 \u20AC");
        assertNotNull(number);
    }
    
    /**
     * EUR12,345.68 
     */
    public void testUSLocaleUsingEURCurrencyCode()
    {
        facesContext.getViewRoot().setLocale(Locale.US);
        mock.setLocale(Locale.US);
        UIInput input = new UIInput();
        mock.setType("currency");
        mock.setCurrencyCode("EUR");
        Number testValue = 12345.68d;
        String stringValue = mock.getAsString(facesContext, input, testValue);
        Number number = (Number) mock.getAsObject(facesContext, input, stringValue);
        assertNotNull(number);
        assertEquals(testValue, number);
    }

    /**
     * â‚¬12,345.68
     */
    public void testUKLocaleUsingEURCurrencyCode()
    {
        facesContext.getViewRoot().setLocale(Locale.US);
        mock.setLocale(Locale.UK);
        UIInput input = new UIInput();
        mock.setType("currency");
        mock.setCurrencyCode("EUR");
        Number testValue = 12345.68d;
        String stringValue = mock.getAsString(facesContext, input, testValue);
        Number number = (Number) mock.getAsObject(facesContext, input, stringValue);
        assertNotNull(number);
        assertEquals(testValue, number);
    }
    
    /**
     * 12.345,68 â‚¬
     */
    public void testGermanyLocaleUsingEURCurrencyCode()
    {
        facesContext.getViewRoot().setLocale(Locale.US);
        mock.setLocale(Locale.GERMANY);
        UIInput input = new UIInput();
        mock.setType("currency");
        mock.setCurrencyCode("EUR");
        Number testValue = 12345.68d;
        String stringValue = mock.getAsString(facesContext, input, testValue);
        Number number = (Number) mock.getAsObject(facesContext, input, stringValue);
        assertNotNull(number);
        assertEquals(testValue, number);
    }
    
    public void testCurrencyPattern()
    {
        facesContext.getViewRoot().setLocale(Locale.US);
        mock.setLocale(Locale.US);
        UIInput input = new UIInput();
        mock.setPattern("\u00A4 ###,###.###");
        Number testValue = 12345.68d;
        String stringValue = mock.getAsString(facesContext, input, testValue);
        Number number = (Number) mock.getAsObject(facesContext, input, stringValue);
        assertNotNull(number);
        assertEquals(testValue, number);        
    }

    public void testCurrencyPattern2()
    {
        facesContext.getViewRoot().setLocale(Locale.US);
        mock.setLocale(Locale.GERMANY);
        UIInput input = new UIInput();
        mock.setPattern("\u00A4 ###,###.###");
        mock.setCurrencyCode("USD"); //Since currency is â‚¬, but we are using USD currency code, the output is USD 12.345,68
        Number testValue = 12345.68d;
        String stringValue = mock.getAsString(facesContext, input, testValue);
        Number number = (Number) mock.getAsObject(facesContext, input, stringValue);
        assertNotNull(number);
        assertEquals(testValue, number);        
    }

}
