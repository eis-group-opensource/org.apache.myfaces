/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * @author Sylvain Vieujot (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class ExtensionsResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream stream = null;
    private PrintWriter printWriter = null;
    private String contentType;
    private HttpServletResponse delegate;
    
    private static final Log log = LogFactory.getLog(ExtensionsResponseWrapper.class);

    public ExtensionsResponseWrapper(HttpServletResponse response){
        super( response );
        this.delegate = response;
        stream = new ByteArrayOutputStream();
    }


    public byte[] getBytes() {
        return stream.toByteArray();
    }

    public String toString(){
        try{
            return stream.toString(getCharacterEncoding());
        }catch(UnsupportedEncodingException e){
            // an attempt to set an invalid character encoding would have caused this exception before
            throw new RuntimeException("Response accepted invalid character encoding " + getCharacterEncoding());
        }
    }

    /** This method is used by Tomcat.
     */
    public PrintWriter getWriter(){
        if( printWriter == null ){
            OutputStreamWriter streamWriter = new OutputStreamWriter(stream, Charset.forName(getCharacterEncoding()));
            printWriter = new PrintWriter(streamWriter, true);
            //printWriter = new PrintWriter(stream, true); // autoFlush is true
        }
        return printWriter;
    }

    /** This method is used by Jetty.
    */
    public ServletOutputStream getOutputStream(){
        return new MyServletOutputStream( stream );
    }

    public InputSource getInputSource(){
        ByteArrayInputStream bais = new ByteArrayInputStream( stream.toByteArray() );
        return new InputSource( bais );
    }

     /**
     *  Prevent content-length being set as the page might be modified.
     */
    public void setContentLength(int contentLength) {
        // noop
    }

    public void setContentType(String contentType) {
        super.setContentType(contentType);
        this.contentType = contentType;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void flushBuffer() throws IOException{
        stream.flush();
    }

    public void finishResponse() {
        try {
            if (printWriter != null) {
                printWriter.close();
            } else {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    public HttpServletResponse getDelegate() {
        return delegate;
    }

    /** Used in the <code>getOutputStream()</code> method.
     */
    private class MyServletOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream outputStream;

        public MyServletOutputStream(ByteArrayOutputStream outputStream){
            this.outputStream = outputStream;
        }

        public void write(int b){
            outputStream.write( b );
        }

        public void write(byte[] bytes) throws IOException{
            outputStream.write( bytes );
        }

        public void write(byte[] bytes, int off, int len){
            outputStream.write(bytes, off, len);
        }
    }
}
