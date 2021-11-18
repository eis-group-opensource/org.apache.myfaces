/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Adapter to be compatible with the current installations
 *
 * @deprecated use org.apache.myfaces.webapp.filter.ExtensionsFilter
 * @author Mario Ivankovits (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class ExtensionsFilter extends org.apache.myfaces.webapp.filter.ExtensionsFilter
{
    private final static Log log = LogFactory.getLog(ExtensionsFilter.class);
    
    public ExtensionsFilter()
    {
        log.warn("Please adjust your web.xml to use org.apache.myfaces.webapp.filter.ExtensionsFilter");
    }
}
