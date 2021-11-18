/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.webapp.webxml;

import junit.framework.TestCase;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 517401 $ $Date: 2007-03-12 23:16:32 +0200 (Pr, 12 Kov 2007) $
 */
public class ServletMappingTest extends TestCase
{

    /**
     * Test method for
     * {@link org.apache.myfaces.shared.webapp.webxml.ServletMapping#ServletMapping(java.lang.String, java.lang.Class, java.lang.String)}.
     */
    public void testExtensionServletMapping()
    {
        ServletMapping mapping = new ServletMapping("xxx", Object.class, "*.faces");
        assertTrue(mapping.isExtensionMapping());
        assertEquals(".faces", mapping.getExtension());
        assertEquals("*.faces", mapping.getUrlPattern());
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.shared.webapp.webxml.ServletMapping#ServletMapping(java.lang.String, java.lang.Class, java.lang.String)}.
     */
    public void testPrefixServletMapping()
    {
        ServletMapping mapping = new ServletMapping("xxx", Object.class, "/faces/*");
        assertFalse(mapping.isExtensionMapping());
        assertEquals("/faces/*", mapping.getUrlPattern());
        assertEquals("/faces", mapping.getPrefix());
    }
}
