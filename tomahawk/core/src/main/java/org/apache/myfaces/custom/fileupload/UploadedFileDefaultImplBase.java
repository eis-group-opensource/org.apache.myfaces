/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.fileupload;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author Sylvain Vieujot (latest modification by $Author: grantsmith $)
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */
public abstract class UploadedFileDefaultImplBase implements UploadedFile
{

    private String _name = null;
    private String _contentType = null;


    protected UploadedFileDefaultImplBase(String name, String contentType)
    {
        _name = name;
        _contentType = contentType;
    }

    /**
     * Answer the uploaded file contents.
     *
     * @return file contents
     */
    public abstract byte[] getBytes() throws IOException;


    /**
     * Answer the uploaded file contents input stream
     *
     * @return InputStream
     * @throws IOException
     */
    public abstract InputStream getInputStream() throws IOException;


    /**
     * @return Returns the _contentType.
     */
    public String getContentType()
    {
        return _contentType;
    }


    /**
     * @return Returns the _name.
     */
    public String getName()
    {
        return _name;
    }


    /**
     * Answer the size of this file.
     * @return long
     */
    public abstract long getSize();
}
