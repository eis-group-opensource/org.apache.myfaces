/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.application.jsp;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author Bruno Aranda (latest modification by $Author: lu4242 $)
 * @version $Revision: 796388 $ $Date: 2009-07-21 20:10:32 +0300 (Tue, 21 Jul 2009) $
 */
public class ViewResponseWrapper extends HttpServletResponseWrapper
{
    private PrintWriter _writer;
    private CharArrayWriter _charArrayWriter;
    private int _status = HttpServletResponse.SC_OK;
    private WrappedServletOutputStream _byteArrayWriter;

    public ViewResponseWrapper(HttpServletResponse httpServletResponse)
    {
        super(httpServletResponse);
    }

    @Override
    public void sendError(int status) throws IOException
    {
        super.sendError(status);
        _status = status;
    }

    @Override
    public void sendError(int status, String errorMessage) throws IOException
    {
        super.sendError(status, errorMessage);
        _status = status;
    }

    @Override
    public void setStatus(int status)
    {
        super.setStatus(status);
        _status = status;
    }

    @Override
    public void setStatus(int status, String errorMessage)
    {
        super.setStatus(status, errorMessage);
        _status = status;
    }

    public int getStatus()
    {
        return _status;
    }

    public void flushToWrappedResponse() throws IOException
    {
        if (_charArrayWriter != null)
        {
            _charArrayWriter.writeTo(getResponse().getWriter());
            _charArrayWriter.reset();
            _writer.flush();
        }
        else if (_byteArrayWriter != null)
        {
            // MYFACES-1955 cannot call getWriter() after getOutputStream()
            // _byteArrayWriter is not null only if getOutputStream() was called
            // before. This method is called from f:view to flush data before tag
            // start, or if an error page is flushed after dispatch.
            // A resource inside /faces/* (see MYFACES-1815) is handled on flushToWriter.
            // If response.getOuputStream() was called before, an IllegalStateException
            // is raised on response.getWriter(), so we should try through stream.
            try
            {
                _byteArrayWriter.writeTo(getResponse().getWriter(), getResponse().getCharacterEncoding());
            }
            catch (IllegalStateException e)
            {
                getResponse().getOutputStream().write(_byteArrayWriter.toByteArray());
            }
            _byteArrayWriter.reset();
            _byteArrayWriter.flush();
        }
    }

    public void flushToWriter(Writer writer,String encoding) throws IOException
    {
        if (_charArrayWriter != null)
        {
            _charArrayWriter.writeTo(writer);
            _charArrayWriter.reset();
            _writer.flush();
        }
        else if (_byteArrayWriter != null)
        {
            _byteArrayWriter.writeTo(writer,encoding);
            _byteArrayWriter.reset();
            _byteArrayWriter.flush();
        }
        writer.flush();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        if (_charArrayWriter != null) throw new IllegalStateException();
        if (_byteArrayWriter == null)
        {
            _byteArrayWriter = new WrappedServletOutputStream();
        }
        return _byteArrayWriter;
    }

    @Override
    public PrintWriter getWriter() throws IOException
    {
        if (_byteArrayWriter != null) throw new IllegalStateException();
        if (_writer == null)
        {
            _charArrayWriter = new CharArrayWriter(4096);
            _writer = new PrintWriter(_charArrayWriter);
        }
        return _writer;
    }

    @Override
    public void reset()
    {
        if (_charArrayWriter != null)
        {
            _charArrayWriter.reset();
        }
    }

    @Override
    public String toString()
    {
        if (_charArrayWriter != null)
        {
            return _charArrayWriter.toString();
        }
        return null;
    }

    static class WrappedServletOutputStream extends ServletOutputStream
    {
        private WrappedByteArrayOutputStream _byteArrayOutputStream;


        public WrappedServletOutputStream()
        {
            _byteArrayOutputStream = new WrappedByteArrayOutputStream(1024);
        }

        public void write(int i) throws IOException
        {
            _byteArrayOutputStream.write(i);
        }

        public byte[] toByteArray()
        {
            return _byteArrayOutputStream.toByteArray();
        }
        
        /**
         * Write the data of this stream to the writer, using
         * the charset encoding supplied or if null the default charset.
         * 
         * @param out
         * @param encoding
         * @throws IOException
         */
        private void writeTo(Writer out, String encoding) throws IOException{
            //Get the charset based on the encoding or return the default if 
            //encoding == null
            Charset charset = (encoding == null) ? 
                    Charset.defaultCharset() : Charset.forName(encoding);
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer decodedBuffer = decoder.decode(
                    ByteBuffer.wrap(_byteArrayOutputStream.getInnerArray(),
                            0,_byteArrayOutputStream.getInnerCount()));
            if (decodedBuffer.hasArray()){
                out.write(decodedBuffer.array());
            }
        }

        public void reset()
        {
            _byteArrayOutputStream.reset();
        }
        
        /**
         * This Wrapper is used to provide additional methods to 
         * get the buf and count variables, to use it to decode
         * in WrappedServletOutputStream.writeTo and avoid buffer
         * duplication.
         */
        static class WrappedByteArrayOutputStream extends ByteArrayOutputStream{
            
            public WrappedByteArrayOutputStream(){
                super();
            }
            
            public WrappedByteArrayOutputStream(int size){
                super(size);                
            }
            
            private byte[] getInnerArray(){
                return buf; 
            }
            
            private int getInnerCount(){
                return count;
            }
        }

    }
}
