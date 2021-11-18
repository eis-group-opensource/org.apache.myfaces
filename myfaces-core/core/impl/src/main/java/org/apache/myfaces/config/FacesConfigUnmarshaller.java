/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config;

import java.io.InputStream;
import java.io.IOException;

import org.xml.sax.SAXException;

/**
 * Parses a single InputStream into an unmarshalled faces config Object, that can
 * be processed by a respective FacesConfigDispenser.
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public interface FacesConfigUnmarshaller<T>
{
    public T getFacesConfig(InputStream in, String systemId) throws IOException, SAXException;
}
