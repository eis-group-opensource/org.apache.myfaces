/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.convert;

import org.apache.shale.test.base.AbstractJsfTestCase;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import junit.framework.Test;

public class DateTimeConverterTest extends AbstractJsfTestCase
{
    private DateTimeConverter mock;

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(DateTimeConverterTest.class);
    }

    public DateTimeConverterTest(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();

        mock = new DateTimeConverter();
        mock.setTimeZone(TimeZone.getDefault());
        FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.GERMANY);

    }

    protected void tearDown() throws Exception
    {
        super.tearDown();

        mock = null;
    }

    /*
    * Test method for 'javax.faces.component.UIComponentBase.getAsObject()'
    */
    public void testGetAsObject()
    {

        UIInput input = new UIInput();

        mock.setPattern("MM/dd/yyyy");

        //should trow a ConverterException
        try
        {
            mock.getAsObject(FacesContext.getCurrentInstance(),input,"15/15/15");

            assertTrue("this date should not be parsable - and it is, so this is wrong.",false);
        }
        catch (ConverterException e)
        {

        }

        //should not trow a ConverterException
        try
        {
            Date date = (Date) mock.getAsObject(FacesContext.getCurrentInstance(),input,"12/01/01");

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            format.setTimeZone(TimeZone.getDefault());

            String str = format.format(date);

            assertEquals("12/01/01",str);

            format = new SimpleDateFormat("MM/dd/yyyy");
            format.setTimeZone(TimeZone.getDefault());

            str = format.format(date);

            assertEquals("12/01/0001",str);            
        }
        catch (ConverterException e)
        {
            assertTrue("this date should not be parsable - and it is, so this is wrong.",false);
        }
    }
}
