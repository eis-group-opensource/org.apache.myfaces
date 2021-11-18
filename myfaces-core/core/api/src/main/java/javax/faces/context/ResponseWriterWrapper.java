/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.context;

import javax.faces.component.UIComponent;
import java.io.IOException;
import java.io.Writer;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
public abstract class ResponseWriterWrapper extends ResponseWriter {
    
    protected abstract ResponseWriter getWrapped();

    public void endElement(String name) throws IOException {
        getWrapped().endElement(name);
    }

    public void writeComment(Object comment) throws IOException {
        getWrapped().writeComment(comment);
    }

    public void startElement(String name, UIComponent component) throws IOException {
        getWrapped().startElement(name, component);
    }

    public void writeText(Object text, String property) throws IOException {
        getWrapped().writeText(text, property);
    }

    public void writeText(char[] text, int off, int len) throws IOException {
        getWrapped().writeText(text, off, len);
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        getWrapped().write(cbuf, off, len);
    }

    public ResponseWriter cloneWithWriter(Writer writer) {
        return getWrapped().cloneWithWriter(writer);
    }

    public void writeURIAttribute(String name, Object value, String property) throws IOException {
        getWrapped().writeURIAttribute(name, value,property);
    }

    public void close() throws IOException {
        getWrapped().close();
    }

    public void endDocument() throws IOException {
        getWrapped().endDocument();
    }

    public void flush() throws IOException {
        getWrapped().flush();
    }

    public String getCharacterEncoding() {
        return getWrapped().getCharacterEncoding();
    }

    public String getContentType() {
        return getWrapped().getContentType();
    }

    public void startDocument() throws IOException {
        getWrapped().startDocument();
    }

    public void writeAttribute(String name, Object value, String property) throws IOException {
        getWrapped().writeAttribute(name, value, property);
    }
    
    /**
     * @since 1.2
     */
    public void writeText(Object object, UIComponent component, String string) throws IOException
    {
        getWrapped().writeText(object, component, string);
    }
    
}
