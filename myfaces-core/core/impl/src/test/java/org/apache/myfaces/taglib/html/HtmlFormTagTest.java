/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.html;

import javax.faces.component.html.HtmlForm;

import org.apache.myfaces.el.LiteralValueExpression;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 527455 $ $Date: 2007-04-11 13:40:28 +0300 (Wed, 11 Apr 2007) $
 */
public class HtmlFormTagTest extends AbstractJsfTestCase
{
    /**
     * @param name
     */
    public HtmlFormTagTest(String name)
    {
        super(name);
    }

    @Override
    @BeforeMethod
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    @Test
    public void testPrependId() throws Exception
    {
        HtmlFormTag htmlFormTag = new HtmlFormTag();
        htmlFormTag.setPrependId(new LiteralValueExpression(false));
        HtmlForm htmlForm = new HtmlForm();
        htmlFormTag.setProperties(htmlForm);
        Assert.assertEquals(false, htmlForm.isPrependId());
    }
}
