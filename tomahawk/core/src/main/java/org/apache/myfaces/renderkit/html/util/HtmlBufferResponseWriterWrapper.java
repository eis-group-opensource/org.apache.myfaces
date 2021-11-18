/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlResponseWriterImpl;

import javax.faces.context.ResponseWriter;
import java.io.*;

/**A buffer for content which should not directly be rendered to the page.
 *
 * @author Sylvain Vieujot (latest modification by $Author: grantsmith $)
 * @version $Revision: 169649 $ $Date: 2005-05-11 17:47:12 +0200 (Wed, 11 May 2005) $
 */
public class HtmlBufferResponseWriterWrapper extends HtmlResponseWriterImpl {

    /**
     * Buffer writer to write content to and buffer it.
     *
     * Moved from OutputStream to Writer to account for issue
     * TOMAHAWK-648.
     */
    private StringWriter bufferWriter;
    /**
     * Writer to wrap buffer-writer.
     */
    private PrintWriter wrapperWriter;
    /**
     * Original response writer.
     */
    private ResponseWriter initialWriter;


    /**Get the writer that should have originally been written to.
     *
     * @return The original writer.
     */
    public ResponseWriter getInitialWriter()
    {
        return initialWriter;
    }

    /**Create an instance of the HtmlBufferResponseWriterWrapper
     *
     * @param initialWriter The writer the content should have originally gone to, this will only be used to copy settings.
     * @return A properly initialized writer which stores the output in a buffer; writer is wrapped.
     */
    static public HtmlBufferResponseWriterWrapper getInstance(ResponseWriter initialWriter)
    {
        StringWriter bufferWriter = new StringWriter();
        PrintWriter wrapperWriter = new PrintWriter(bufferWriter, true);

        return new HtmlBufferResponseWriterWrapper(initialWriter, bufferWriter, wrapperWriter);

    }

    /**Constructor for the HtmlBufferResponseWriterWrapper.
     *
     * @param initialWriter The writer the content should have originally gone to, this will only be used to copy settings.
     * @param bufferWriter A buffer to store content to.
     * @param wrapperWriter A wrapper around the buffer.
     */
    private HtmlBufferResponseWriterWrapper(ResponseWriter initialWriter, StringWriter bufferWriter, PrintWriter wrapperWriter)
    {
        super(wrapperWriter, (initialWriter == null) ? null : initialWriter.getContentType(),
                (initialWriter == null) ? null : initialWriter.getCharacterEncoding());

        this.bufferWriter = bufferWriter;
        this.wrapperWriter = wrapperWriter;
        this.initialWriter = initialWriter;
    }


    /**Get the content of the buffer.
     *
     * @return The content of the buffered and wrapped writer.
     */
    public String toString()
    {
        wrapperWriter.flush();
        wrapperWriter.close();
        return bufferWriter.toString();
    }
}
