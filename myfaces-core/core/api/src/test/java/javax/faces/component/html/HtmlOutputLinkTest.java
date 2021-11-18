/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 526672 $ $Date: 2007-04-09 11:15:56 +0300 (Mon, 09 Apr 2007) $
 */
public class HtmlOutputLinkTest
{

    /**
     * Test method for {@link javax.faces.component.UIComponentBase#getRendererType()}.
     */
    @Test
    public void testRendererType()
    {
        Assert.assertEquals("javax.faces.Link", new HtmlOutputLink().getRendererType());
    }

}
