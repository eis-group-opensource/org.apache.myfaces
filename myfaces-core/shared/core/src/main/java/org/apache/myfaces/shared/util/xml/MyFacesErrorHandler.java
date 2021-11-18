/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util.xml;

import org.apache.commons.logging.Log;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * Convenient error handler for xml sax parsing.
 * @author Manfred Geiler (latest modification by $Author: matzew $)
 * @version $Revision: 557350 $ $Date: 2007-07-18 21:19:50 +0300 (Tr, 18 Lie 2007) $
 */
public class MyFacesErrorHandler
        implements ErrorHandler
{
    private Log _log;

    public MyFacesErrorHandler(Log log)
    {
        _log = log;
    }

    public void warning(SAXParseException exception)
    {
        if (_log.isWarnEnabled()) _log.warn(getMessage(exception), exception);
    }

    public void error(SAXParseException exception)
    {
        _log.error(getMessage(exception), exception);
    }

    public void fatalError(SAXParseException exception)
    {
        _log.fatal(getMessage(exception), exception);
    }

    private String getMessage(SAXParseException exception)
    {
        StringBuffer buf = new StringBuffer();
        buf.append("SAXParseException at");
        buf.append(" URI=");
        buf.append(exception.getSystemId());
        buf.append(" Line=");
        buf.append(exception.getLineNumber());
        buf.append(" Column=");
        buf.append(exception.getColumnNumber());
        return buf.toString();
    }

}
