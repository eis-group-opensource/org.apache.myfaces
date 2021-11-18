/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.context;

import javax.faces.component.UIComponent;
import java.io.IOException;
import java.io.Writer;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public abstract class ResponseWriter
        extends Writer
{
    public abstract String getContentType();

    public abstract String getCharacterEncoding();

    public abstract void flush()
            throws IOException;

    public abstract void startDocument()
            throws IOException;

    public abstract void endDocument()
            throws IOException;

    public abstract void startElement(String name,
                                      UIComponent component)
            throws IOException;

    public abstract void endElement(String name)
            throws IOException;

    public abstract void writeAttribute(String name,
                                        Object value,
                                        String property)
            throws IOException;

    public abstract void writeURIAttribute(String name,
                                           Object value,
                                           String property)
            throws IOException;

    public abstract void writeComment(Object comment)
            throws IOException;

    public abstract void writeText(Object text,
                                   String property)
            throws IOException;

    public abstract void writeText(char[] text,
                                   int off,
                                   int len)
            throws IOException;

    public abstract ResponseWriter cloneWithWriter(Writer writer);
    
    /**
     * @since 1.2
     */
    public void writeText(Object object, UIComponent component, String string) throws IOException
    {
        writeText(object, string);
    }
}
