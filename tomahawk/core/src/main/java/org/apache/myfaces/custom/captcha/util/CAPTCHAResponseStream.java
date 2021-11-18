/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.captcha.util;

import java.io.IOException;
import java.io.OutputStream;
import javax.faces.context.ResponseStream;

/**
 * This class is responsible for wrapping the CAPTCHA Image
 * response stream.
 * 
 * @since 1.1.7
 */
public final class CAPTCHAResponseStream extends ResponseStream
{
    private final OutputStream _out;

    public CAPTCHAResponseStream(OutputStream out)
    {
        _out = out;
    }

    public void close() throws IOException
    {
        _out.flush();
        _out.close();
    }

    public void flush() throws IOException
    {
        _out.flush();
    }

    public void write(byte[] b, int off, int len) throws IOException
    {
        _out.write(b, off, len);
    }

    public void write(byte[] b) throws IOException
    {
        _out.write(b);
    }

    public void write(int b) throws IOException
    {
        _out.write(b);
    }
}
